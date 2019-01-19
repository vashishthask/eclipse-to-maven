# eclipse-to-maven
[eclipse-to-maven](https://github.com/vashishthask/eclipse-to-maven/) mavenises the existing Eclipse based workspace.

# Features

* Converts .classpath files into pom.xml
* If required, prints the depdency graph of eclipse projects.
* Moves source folders according to Maven convention. So for instance Java sources go to src/main/java folder.
* Removes spaces in the names of the folders. So "Calculator Component" becomes "CalculatorComponent"
* Right now Mavenisation is limited to generating dependencies in the pom. However this is a good first step in moving forward. With small changes you should be able to run the build for your projects.

# Getting Started

## Preparation
* As eclipse-to-maven uses java.nio features, you need to have JDK 1.7+
* Create the build of eclipse-to-maven using Maven "mvn clean install"
* Copy existing workspace into a separate directory.

## Setup
Please follow following `src/main/java/resources/application.properties` setup before running the application
* For converting existing eclipse workspace to maven, set 'convert.to.maven' property as 'true'. This switch is useful if you just want to print the dependency tree (read-only operation) for instance.

    `convert.to.maven=true`

* For removing spaces in folder names, use following switch:

    `workspace.projectname.remove.space=true`

* For printing the dependency tree of eclipse projects set following properties

    `print.dependency.graph=true`
    
    `print.dependency.graph.iotype=file`
    
    `print.dependency.graph.filepath=<file path>`

* For internal dependencies groupId and project groupId , you may want to setup default as follows:

	`maven.dependency.groupId.default=...`

	`maven.pom.groupId.default=...`

## Running eclipse-to-maven from Eclipse

* Import eclipse-to-maven in any eclipse workspace.
* Right click EclipseToMaven --> Run As --> Run Configurations --> 
* Pass the Eclipse workspace location with application Arguments as follows:
![eclipse-to-maven args](http://sampreshan.svashishtha.com/wp-content/uploads/2012/04/eclipse-to-maven-2.png)
* Run the application



## Running eclipse-to-maven from Command Prompt
Make sure JAVA_HOME is pointing to JDK-1.7+.

Run the following from command prompt:

	$ mvn clean install exec:java -Dexec.args="<eclipse workspace path>"
	
# Blog posts on eclipse-to-maven
* [Converting Eclipse Projects to Maven : eclipse-to-maven Intro](https://github.com/vashishthask/eclipse-to-maven/wiki/Introducing-eclipse-to-maven)
* [Converting Eclipse Workspace to Maven – Why eclipse-to-maven?](https://github.com/vashishthask/eclipse-to-maven/wiki/Why-eclipse-to-maven%3F)

