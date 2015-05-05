name := "VicRoadsImporter"

version := "0.1.0"

scalaVersion := "2.11.6"

libraryDependencies ++= Seq (
	"org.postgresql"  %  "postgresql" 			 % "9.4-1200-jdbc41",
  "org.scalikejdbc" %% "scalikejdbc"       % "2.2.6",
  "com.h2database"  %  "h2"                % "1.4.187",
  "ch.qos.logback"  %  "logback-classic"   % "1.1.3",
  "org.apache.spark" %% "spark-core" 			 % "1.2.0",
  "org.apache.spark" %% "spark-sql" 			 % "1.2.0"
)