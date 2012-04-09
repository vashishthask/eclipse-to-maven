package com.shri.eclipsetomaven;

import static org.junit.Assert.assertTrue;

import java.io.File;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

public class EclipseToMavenTest {
	@Rule
	public TemporaryFolder tempFolder = new TemporaryFolder();
	private EclipseToMaven eclipseToMaven;
	
	@Before
	public void setup() {
		eclipseToMaven = new EclipseToMaven();

	}

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
		eclipseToMaven.removeSpacesInDirectoryName(folderWithSpace);
		File newFile = new File(folder, "folderwithspace");
		assertTrue(newFile.exists() && newFile.isDirectory());
		assertTrue(!folderWithSpace.exists());
	}
	
	@Test
	public void testFindDirectoriesInWorkspace() throws Exception {
		eclipseToMaven.findDirectoriesInWorkspace(new File("/tmp/lendnet"));
		eclipseToMaven.removeSpacesOfProjectFolders();
	}
}
