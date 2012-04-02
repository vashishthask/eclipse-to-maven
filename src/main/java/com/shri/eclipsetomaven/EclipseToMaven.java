package com.shri.eclipsetomaven;

import java.io.File;

public class EclipseToMaven {

	private static final String WORKSPACE_ROOT = "C:\\dev\\workspace\\hg\\hg-support\\LendNet";
	File workspaceRoot;

	public static void main(String args[]) throws Exception {
		EclipseToMaven eclipseToMaven = new EclipseToMaven();
		eclipseToMaven.displayFiles(WORKSPACE_ROOT);
	}/* end main */

	public  void displayFiles(String filePath) {
		workspaceRoot = new File(filePath);
		copyCurrentWorkspaceToAnotherLocation();
		WorkspaceClasspathToPomConverter converter = new WorkspaceClasspathToPomConverter(workspaceRoot);
		converter.convert(workspaceRoot);
		removeSpacesOfProjectFolders();
	}

	private void removeSpacesOfProjectFolders() {
		// TODO Auto-generated method stub
		
	}

	private void copyCurrentWorkspaceToAnotherLocation() {
		// TODO Auto-generated method stub
		
	}
}