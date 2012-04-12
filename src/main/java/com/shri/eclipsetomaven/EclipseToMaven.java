package com.shri.eclipsetomaven;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;

public class EclipseToMaven {
    File workspaceRoot;
    protected List<File> directories = new ArrayList<File>();

    public static void main(String args[]) throws Exception {
        if (args.length < 1)
            usage();
        EclipseToMaven eclipseToMaven = new EclipseToMaven(new File(args[0]));
        eclipseToMaven.maveniseTheEclipseWorkspace();
    }
    
    
    public EclipseToMaven(File workspaceRoot) {
        this.workspaceRoot = workspaceRoot;
    }

    static void usage() {
        System.err.println("usage: java EclipseToMaven <eclipse workspace root>");
        System.exit(-1);
    }

    public void maveniseTheEclipseWorkspace() {
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
            
            String destinationDirectoryName = StringUtils.remove(directoryName,' ');
            try {
                FileUtils.moveDirectory(directory, new File(directory.getParent(),destinationDirectoryName ));
            } catch (IOException e) {
                throw new IllegalStateException(e);
            }
        }
    }

    void copyCurrentWorkspaceToAnotherLocation() {
    }
}