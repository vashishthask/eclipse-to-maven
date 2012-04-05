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

		File srcFolder = new File(folderToMove, "com/shri/test");
		srcFolder.mkdirs();
		File sourceFile = new File(srcFolder, "Sample.java");
		sourceFile.createNewFile();
		
		File textFile = new File(folderToMove, "sample.txt");
		textFile.createNewFile();
		
		
		processor.moveToSrcMainJava("Main", rootFolder);
		File newSourceFileLocation = new File(rootFolder, "src/main/java/com/shri/test/Sample.java");
		assertTrue(newSourceFileLocation.exists());
		assertFalse(folderToMove.exists());
	}

	@Test
	public void testMoveToSrcMainResources() {
	}

}