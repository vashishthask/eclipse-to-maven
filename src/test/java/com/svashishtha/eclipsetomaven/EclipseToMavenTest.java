package com.svashishtha.eclipsetomaven;

import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

public class EclipseToMavenTest {
	@Rule
	public TemporaryFolder tempFolder = new TemporaryFolder();
	private EclipseToMaven eclipseToMaven;
	File rootFolder;
	
	@Before
	public void setup() throws IOException {
	    rootFolder = tempFolder.newFolder();
		eclipseToMaven = new EclipseToMaven(rootFolder);
	}
	
	@Test
	public void testRemoveSpacesInDirectoryName()  {
		File folderWithSpace = new File(rootFolder, "folder with space");
		folderWithSpace.mkdir();
		eclipseToMaven.removeSpacesInDirectoryName(folderWithSpace);
		File newFile = new File(rootFolder, "folderwithspace");
		assertTrue(newFile.exists() && newFile.isDirectory());
		assertTrue(!folderWithSpace.exists());
	}
	
}