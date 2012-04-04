package com.shri.eclipsetomaven;

import static org.junit.Assert.assertTrue;

import java.io.File;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

public class EclipseToMavenTest {
	@Rule
	public TemporaryFolder tempFolder = new TemporaryFolder();

//	@Test
//	public void testCopyCurrentWorkspaceToAnotherLocation() {
//		EclipseToMaven eclipseToMaven = new EclipseToMaven();
//		eclipseToMaven.copyCurrentWorkspaceToAnotherLocation();
//	}
	
	@Test
	public void testRemoveSpacesInDirectoryName() throws Exception {
		File folder = tempFolder.newFolder();
		File folderWithSpace = new File(folder, "folder with space");
		folderWithSpace.mkdir();
		EclipseToMaven eclipseToMaven = new EclipseToMaven();
		eclipseToMaven.removeSpacesInDirectoryName(folderWithSpace);
		File newFile = new File(folder, "folderwithspace");
		assertTrue(newFile.exists() && newFile.isDirectory());
		assertTrue(!folderWithSpace.exists());
	}
}
