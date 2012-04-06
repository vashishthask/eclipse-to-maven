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
		//setup java sources in a folder.
	    File folderToMove = new File(rootFolder, "Main");
		firstFolderSetup(folderToMove);

		File secondFolder = new File(rootFolder, "Model");
		secondFolderSetup(secondFolder );
		
		
		processor.moveToSrcMainJava("Main", rootFolder);
		File newSourceFileLocation = new File(rootFolder, "src/main/java/com/shri/test/main/Sample.java");
		assertTrue(newSourceFileLocation.exists());

		processor.moveToSrcMainJava("Model", rootFolder);
	    File newSourceFileLocation2 = new File(rootFolder, "src/main/java/com/shri/test/model/Model.java");
	    assertTrue(newSourceFileLocation2.exists());

	}

    private void firstFolderSetup(File folderToMove) throws IOException {
        File srcFolder = new File(folderToMove, "com/shri/test/main");
		srcFolder.mkdirs();
		File sourceFile = new File(srcFolder, "Sample.java");
		sourceFile.createNewFile();
		
		File textFile = new File(folderToMove, "sample.txt");
		textFile.createNewFile();
    }
    
    private void secondFolderSetup(File folderToMove) throws IOException {
        File srcFolder = new File(folderToMove, "com/shri/test/model");
        srcFolder.mkdirs();
        File sourceFile = new File(srcFolder, "Model.java");
        sourceFile.createNewFile();
        
        File textFile = new File(folderToMove, "model.txt");
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