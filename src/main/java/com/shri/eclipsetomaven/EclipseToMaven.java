package com.shri.eclipsetomaven;

import java.io.File;

public class EclipseToMaven {

	private static final String WORKSPACE_ROOT = "C:\\dev\\workspace\\hg\\hg-support\\LendNet";
//	private static final String WORKSPACE_ROOT = "/Users/shrikant/code/LendNet/LendNet";
	private static final String DESTINATION_WORKSPACE_ROOT = "C:\\Temp\\LendNet";
	File workspaceRoot;

	public static void main(String args[]) throws Exception {
		EclipseToMaven eclipseToMaven = new EclipseToMaven();
		eclipseToMaven.displayFiles(WORKSPACE_ROOT);
	}

	public  void displayFiles(String filePath) {
		workspaceRoot = new File(filePath);
		copyCurrentWorkspaceToAnotherLocation();
		WorkspaceClasspathToPomConverter converter = new WorkspaceClasspathToPomConverter(workspaceRoot);
		converter.convert(workspaceRoot);
		removeSpacesOfProjectFolders();
	}

	private void removeSpacesOfProjectFolders() {
	}

	void copyCurrentWorkspaceToAnotherLocation() {
//		try {
//			FileUtils.copyDirectory(new File(WORKSPACE_ROOT), new File(DESTINATION_WORKSPACE_ROOT));
//		} catch (IOException e) {
//			throw new IllegalStateException(e);
//		}
	}
}