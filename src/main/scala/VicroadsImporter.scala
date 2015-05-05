package VicroadsImporter

import scalikejdbc._
import org.apache.spark._
import org.apache.spark.SparkConf
import org.apache.spark.SparkContext
import org.apache.spark.SparkContext._
import org.joda.time._
import org.joda.time.format._

object VicroadsImporter {

    def fetchSitesIds(): List[(Int, Int)] = {
        DB readOnly { implicit session =>
            sql"SELECT id, number FROM sites WHERE type = 'scats'".map(rs => (rs.int("id"), rs.int("number"))).list.apply()
        }
    }

    def populateSiteAlarms(siteAlarms: Array[SiteAlarmRecord]) {
        DB localTx { implicit session => 
            siteAlarms.foreach { siteAlarm =>
                sql"INSERT INTO site_alarms (site_id, timestamp, event, start) VALUES (${siteAlarm.site_id}, ${siteAlarm.timestamp}, ${siteAlarm.event}, ${siteAlarm.start});".update.apply()
            }
        }
    } 

    case class SiteAlarmRecord (
        site_id     : Int,
        timestamp   : DateTime,
        event       : String,
        start       : Boolean
    )

    def main(args: Array[String]) {

        if (args.length == 0) {
            println("Please include the CSV file PATH as an argument")
            return
        }

        val conf = new SparkConf().setMaster("local").setAppName("VicroadsImporter") 
        val sc = new SparkContext(conf)

        Class.forName("org.postgresql.Driver")
        ConnectionPool.singleton(
            "jdbc:postgresql://localhost:5432/vicroads_ahi",
            "postgres", "postgres"
        )

        // Query the database for scats in the sites table
        val scatsSites = fetchSitesIds()
        val (scat_ids, scat_numbers) = scatsSites.unzip

        // TODO: Convert to use arguments
        val csvFile = sc.textFile(args(0))

        val header = csvFile.map(line => line.split(",").map(_.trim)).first

        val data = csvFile.map(line => line.split(",")
                          .map(_.trim))
                          .filter(_(0) != header(0))

        val siteAlarms = data
            .filter(sa => sa(5) != "" && scat_numbers.contains(sa(5).toInt) && (sa(6) == "+" || sa(6) == "-") && sa(7).length > 0)
            .map(sa => SiteAlarmRecord(
                scatsSites.filter( {case (k, v) => (v == sa(5).toInt) }).map(_._1).head,
                DateTime.parse("%s %s".format(sa(2), sa(3)), DateTimeFormat.forPattern("dMMMYY HH:mm:ss")),
                sa(7),
                (sa(6) == "+"))
            ).collect().distinct

        println("\nNumber of site valid site alarms: %s\n".format(siteAlarms.length))

        // Insert alarm in site alarm
        populateSiteAlarms(siteAlarms)

        println("\n------ End of program ------\n")
    }
}