# Vic Roads Importer

This project is a spike project to import CSV files into Postgres

## Setup guide

.. How to install Scala and SBT

## Note worthy commands

These are some helpful commands in SBT

### Start SBT

In the root dir of the project (VicRoadsImporter) run ``$ sbt``  
You can now run commands in SBT, such as;  
``> compile``

### Batch mode

Run commands in batch mode by separating them by spaces, such as;  
``$ sbt clean compile``

### Include arguments

When ever you want commands to include arguments, the command and arguments should be wrapped in quotes, such as;  
``$ sbt "do-something arg1 arg2" where do-something is the command, and arg1/arg2 are the arguments

### Continuous build and test
To speed up your edit-compile-test cycle, you can ask sbt to automatically recompile or run tests whenever you save a source file.

Make a command run when one or more source files change by prefixing the command with ``~``. For example, in interactive mode try:  
``> ~ compile``  
Press enter to stop watching for changes

### Common commands
Here are some of the most common sbt commands. For a more complete list, see Command Line Reference.

``clean`` Deletes all generated files (in the target directory).  
``compile`` Compiles the main sources (in src/main/scala and src/main/java directories).  
``test`` Compiles and runs all tests.  
``console`` Starts the Scala interpreter with a classpath including the compiled sources and all dependencies. To return to sbt, type :quit, Ctrl+D (Unix), or Ctrl+Z (Windows).  
``run <argument>*`` Runs the main class for the project in the same virtual machine as sbt.  
``package`` Creates a jar file containing the files in src/main/resources and the classes compiled from src/main/scala and src/main/java.  
``help <command>`` Displays detailed help for the specified command. If no command is provided, displays brief descriptions of all commands.  
``reload`` Reloads the build definition (build.sbt, project/*.scala, project/*.sbt files). Needed if you change the build definition.  

###History Commands
Interactive mode remembers history, even if you exit sbt and restart it. The simplest way to access history is with the up arrow key. The following commands are also supported:

``!`` Show history command help.  
``!!`` Execute the previous command again.  
``!:`` Show all previous commands.  
``!:n`` Show the last n commands.  
``!n`` Execute the command with index n, as shown by the !: command.  
``!-n`` Execute the nth command before this one.  
``!string`` Execute the most recent command starting with 'string'  
``!?string`` Execute the most recent command containing 'string'  