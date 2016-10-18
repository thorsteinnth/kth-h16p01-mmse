kth-mmse-sepcli readme

The project was created in IntelliJ and built with Gradle. The source code, along with the test cases, is available in the deliverable zip file. The deliverable also includes an executable .jar file to run the SepClient. You can also build the .jar file yourself.

Build the program and create the .jar file:
Java 8 and Gradle 3.0 is required to build the program.
In command line: navigate to the project directory ("kth-mmse-seplcli"), there you should see the file "gradle.build". Run the command line function "gradle build". Build should be successful and the .jar file is now available at "kth-mmse-sepcli/build/libs".

Run the client and unit tests.
To run the client (the .jar file) you need to have JVM 1.8 (Java 8) setup on your machine. 

Run unit tests:
write the following command in command line (in the same directory as the .jar file) "java -jar kth-mmse-sepcli-1.0-SNAPSHOT.jar runUnitTests".

Run the client: 
write the following command in command line (in the same directory as the .jar file) "java -jar kth-mmse-sepcli-1.0-SNAPSHOT.jar".

Run the event request acceptance test:
write the following command in command line (in the same directory as the .jar file) ”java -jar kth-mmse-sepcli-1.0-SNAPSHOT.jar runAcceptanceTest1”.

Run the create client record acceptance test:
write the following command in command line (in the same directory as the .jar file) ”java -jar kth-mmse-sepcli-1.0-SNAPSHOT.jar runAcceptanceTest2”
