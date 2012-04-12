package com.shri.eclipsetomaven;

import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

import org.apache.commons.lang.StringUtils;

public class EclipseToMaven {

    private static final String WORKSPACE_ROOT = "C:\\dev\\2\\workspace\\hg\\hg-support\\LendNet";
    File workspaceRoot;
    protected List<File> directories = new ArrayList<File>();

    public static void main(String args[]) throws Exception {
        EclipseToMaven eclipseToMaven = new EclipseToMaven();
        eclipseToMaven.maveniseTheEclipseWorkspace(WORKSPACE_ROOT);
    }

    public void maveniseTheEclipseWorkspace(String workspaceRootPath) {
        workspaceRoot = new File(workspaceRootPath);
        copyCurrentWorkspaceToAnotherLocation();
        WorkspaceClasspathToPomConverter converter = new WorkspaceClasspathToPomConverter(
                workspaceRoot);
        converter.convert(workspaceRoot);
        findDirectoriesInWorkspace(workspaceRoot);
        removeSpacesOfProjectFolders();
    }

    void findDirectoriesInWorkspace(File parent) {
        // get list of directories
        File[] filteredSubFolders = parent.listFiles(new FileFilter() {
            public boolean accept(File file) {
                return file.isDirectory();
            }
        });

        for (int i = 0; i < filteredSubFolders.length; i++) {
            directories.add(filteredSubFolders[i]);
            findDirectoriesInWorkspace(filteredSubFolders[i]);
        }
    }

    void removeSpacesOfProjectFolders() {
    	// Generate an iterator. Start just after the last element.
    	ListIterator<File> li = directories.listIterator(directories.size());

    	// Iterate in reverse.
    	while(li.hasPrevious()) {
    		removeSpacesInDirectoryName(li.previous());
    	}
    }

    void removeSpacesInDirectoryName(File directory) {
        String directoryName = directory.getName();
        if (StringUtils.contains(directoryName, ' ')) {
            String destinationDirectoryName = StringUtils.remove(directoryName,
                    ' ');
            directory.renameTo(new File(directory.getParentFile(),
                    destinationDirectoryName));
        }
    }

    void copyCurrentWorkspaceToAnotherLocation() {
    }
}