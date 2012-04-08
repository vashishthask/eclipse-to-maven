package com.shri.eclipsetomaven.classpathentry;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

public class SrcClasspathEntryProcessorTest {
	
	SrcClasspathEntryProcessor processor = new SrcClasspathEntryProcessor();
	
	@Rule
	public TemporaryFolder tempFolder = new TemporaryFolder();
	File rootFolder;
	
	@Before
	public void setup() throws IOException {
		rootFolder = tempFolder.newFolder();
	}
	
	@Test
	public void testMoveToSrcMainJava() throws IOException {
	    File firstFolder = new File(rootFolder, "Main");
		folderSetup(firstFolder, "com/shri/test/main", "Sample.java", "Sample.txt");

		File secondFolder = new File(rootFolder, "Model");
		folderSetup(secondFolder, "com/shri/test/model", "Model.java", "model.txt" );
		
		
		processor.moveToSrcMainJava("Main", rootFolder);
		File newSourceFileLocation = new File(rootFolder, "src/main/java/com/shri/test/main/Sample.java");
		assertTrue(newSourceFileLocation.exists());

		processor.moveToSrcMainJava("Model", rootFolder);
	    File newSourceFileLocation2 = new File(rootFolder, "src/main/java/com/shri/test/model/Model.java");
	    assertTrue(newSourceFileLocation2.exists());

	}
	
	@Test
	public void testMoveSourcesToMavenFolders() throws IOException {
		//setup java sources in a folder.
	    File firstFolder = new File(rootFolder, "src");
	    folderSetup(firstFolder, "com/shri/test/main", "Sample.java", "Sample.txt");

		File secondFolder = new File(rootFolder, "test");
		folderSetup(secondFolder, "com/shri/test/model", "SampleTest.java", "model.txt" );
		
		//setup java sources in a folder.
	    File thirdFolder = new File(rootFolder, "Main");
	    folderSetup(thirdFolder, "com/shri/test/model", "Main.java", "main.txt" );
	    
		//setup java sources in a folder.
	    File fourthFolder = new File(rootFolder, "Resources");
	    folderSetup(fourthFolder, "com/shri/test", "application.properties", "sample.xml" );
		
		processor.moveSourcesToMavenFolders(rootFolder, "src");
		processor.moveSourcesToMavenFolders(rootFolder, "test");
		processor.moveSourcesToMavenFolders(rootFolder, "Main");
		processor.moveSourcesToMavenFolders(rootFolder, "Resources");
		
		File fileLocation = new File(rootFolder, "src/main/java/com/shri/test/main/Sample.java");
		assertTrue(fileLocation.exists());
		
		fileLocation = new File(rootFolder, "src/test/java/com/shri/test/model/SampleTest.java");
		assertTrue(fileLocation.exists());
		
		fileLocation = new File(rootFolder, "src/main/java/com/shri/test/model/Main.java");
		assertTrue(fileLocation.exists());
		
		fileLocation = new File(rootFolder, "src/main/resources/com/shri/test/application.properties");
		assertTrue(fileLocation.exists());
		
		fileLocation = new File(rootFolder, "src/main/resources/sample.xml");
		assertTrue(fileLocation.exists());

	}
	
	@Test
	public void testMoveToSrcMainJavaIfSrcAndTargetSame() throws IOException {
		//setup java sources in a folder.
	    File firstFolder = new File(rootFolder, "src");
		folderSetup(firstFolder, "com/shri/test/main", "Sample.java", "Sample.txt");

		File secondFolder = new File(rootFolder, "test");
		folderSetup(secondFolder, "com/shri/test/model", "Model.java", "model.txt" );
		
		processor.moveToSrcMainJava("src", rootFolder);
		File newSourceFileLocation = new File(rootFolder, "src/main/java/com/shri/test/main/Sample.java");
		assertTrue(newSourceFileLocation.exists());

		processor.moveToSrcMainJava("test", rootFolder);
	    File newSourceFileLocation2 = new File(rootFolder, "src/main/java/com/shri/test/model/Model.java");
	    assertTrue(newSourceFileLocation2.exists());

	}

    private void folderSetup(File folderToMove, String packageName, String firstFileName, String secondFileName) throws IOException {
        File srcFolder = new File(folderToMove, packageName);
		srcFolder.mkdirs();
		File sourceFile = new File(srcFolder, firstFileName);
		sourceFile.createNewFile();
		
		File textFile = new File(folderToMove, secondFileName);
		textFile.createNewFile();
    }


	@Test
	public void testMoveToSrcMainResources() throws IOException {
	     File folderToMove = new File(rootFolder, "Resources");

	     File srcFolder = new File(folderToMove, "com/shri/test");
	     srcFolder.mkdirs();
	     File sourceFile = new File(srcFolder, "application.properties");
	     sourceFile.createNewFile();
	     
	     File textFile = new File(folderToMove, "sample.xml");
	     textFile.createNewFile();

	     processor.moveToSrcMainResources("Resources", rootFolder);
	     File newApplicationPropertiesFileLocation = new File(rootFolder, "src/main/resources/com/shri/test/application.properties");
	     assertTrue(newApplicationPropertiesFileLocation.exists());
	     File newXmlFileLocation = new File(rootFolder, "src/main/resources/sample.xml");
	     assertTrue(newXmlFileLocation.exists());
	     assertFalse(folderToMove.exists());
	}

}