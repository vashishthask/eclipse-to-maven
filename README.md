eclipse-to-maven
================
[eclipse-to-maven](https://github.com/vashishthask/eclipse-to-maven/) mavenises the existing Eclipse based workspace.

#Features

* Converts .classpath files into pom.xml
* Moves source folders according to Maven convention. So for instance Java sources go to src/main/java folder.
* Removes spaces in the names of the folders. So "Calculator Component" becomes "CalculatorComponent"
* Right now Mavenisation is limited to generating dependencies in the pom. However this is a good first step in moving forward. With small changes you should be able to run the build for your projects.

#Getting Started

##Preparation
* As eclipse-to-maven uses java.nio features of JDK-7, you need to have it or download it from either [openjdk] (http://openjdk.java.net/projects/jdk7/) or from [Oracle JDK location] (http://jdk7.java.net/)
* Declare an environment variable called JAVA_HOME_7 which points to the JDK-7 home directory.
* Create the build of eclipse-to-maven using Maven "mvn clean install"
* Copy existing workspace into a separate directory.

##Running it from Eclipse

* Import eclipse-to-maven in any eclipse workspace.
* Right click EclipseToMaven --> Run As --> Run Configurations --> 
* Pass the Eclipse workspace location with application Arguments as follows:
![eclipse-to-maven args](http://sampreshan.svashishtha.com/wp-content/uploads/2012/04/eclipse-to-maven-2.png)
* Run the application



##Running eclipse-to-maven from Command Prompt
* Make sure JAVA_HOME is pointing to JDK-7
* Run the following from command prompt:

	mvn clean install exec:java -Dexec.args="&lt;eclipse workspace path&gt;"

