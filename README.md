#eclipse-to-maven
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

##Setup
Please follow following `src/main/java/resources/application.properties` setup before running the application
* For converting existing eclipse workspace to maven, set 'convert.to.maven' property as 'true'

    `convert.to.maven=true`

* For printing the dependency tree of eclipse projects set following properties

    `print.dependency.graph=true`
    
    `print.dependency.graph.iotype=file`
    
    `print.dependency.graph.filepath=/tmp/dependency.txt`

* For internal dependencies groupId and project groupId , you may want to setup default as follows:

	`maven.dependency.groupId.default=...`

	`maven.pom.groupId.default=...`

##Running it from Eclipse

* Import eclipse-to-maven in any eclipse workspace.
* Right click EclipseToMaven --> Run As --> Run Configurations --> 
* Pass the Eclipse workspace location with application Arguments as follows:
![eclipse-to-maven args](http://sampreshan.svashishtha.com/wp-content/uploads/2012/04/eclipse-to-maven-2.png)
* Run the application



##Running eclipse-to-maven from Command Prompt
Make sure JAVA_HOME is pointing to JDK-7.

Run the following from command prompt:

	$ mvn clean install exec:java -Dexec.args="<eclipse workspace path>"
	
#Blog posts on eclipse-to-maven
* [Converting Eclipse Projects to Maven : eclipse-to-maven Intro](http://sampreshan.svashishtha.com/2012/04/12/converting-eclipse-projects-to-maven-eclipse-to-maven-intro/)
* [Converting Eclipse Workspace to Maven – Why eclipse-to-maven?](http://sampreshan.svashishtha.com/2012/04/30/converting-eclipse-workspace-to-maven-why-eclipse-to-maven/)

