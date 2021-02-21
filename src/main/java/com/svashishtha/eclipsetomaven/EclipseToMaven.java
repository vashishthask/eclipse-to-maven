package com.svashishtha.eclipsetomaven;


import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

import com.svashishtha.eclipsetomaven.util.ApplicationConfig;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;

public class EclipseToMaven {
    
	File workspaceRoot;
    protected List<File> directories = new ArrayList<>();

    public static void main(String[] args) {
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
        if("true".equals(ApplicationConfig.INSTANCE.getValue(ApplicationPropertyConstants.WORKSPACE_PROJECTNAME_REMOVE_SPACE))){
            removeSpacesOfProjectFolders();
        }
    }

    void findDirectoriesInWorkspace(File parent) {
        // get list of directories
        File[] filteredSubFolders = parent.listFiles(new FileFilter() {
            public boolean accept(File file) {
                return file.isDirectory();
            }
        });

        assert filteredSubFolders != null;
        for (File filteredSubFolder : filteredSubFolders) {
            directories.add(filteredSubFolder);
            findDirectoriesInWorkspace(filteredSubFolder);
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