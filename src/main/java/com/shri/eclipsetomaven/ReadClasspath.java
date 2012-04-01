package com.shri.eclipsetomaven;

import java.io.File;

import java.io.FileFilter;
import java.io.FilenameFilter;

import org.w3c.dom.Document;

public class ReadClasspath {

	private static final String WORKSPACE_ROOT = "/Users/shrikant/code";
	private static final String CLASSPATH = ".classpath";
	File workspaceRoot ;

	public static void main(String args[]) throws Exception {
		ReadClasspath readClasspath = new ReadClasspath();
		readClasspath.displayFiles(WORKSPACE_ROOT);
	}/* end main */

	public  void displayFiles(String filePath) {
		workspaceRoot = new File(filePath);
		displayFiles(workspaceRoot);
	}

	public  void displayFiles(File folder) {
		File classpathFile = getClasspathFile(folder);
		if (classpathFile == null) {
			searchClasspathFileInSubfolders(folder);
		} else {
			Document pomXmlDoc = createPomXmlDoc(classpathFile);
			writePomToDisk(pomXmlDoc);
		}
	}

	private  Document createPomXmlDoc(File classpathFile) {
		Document classpathDoc = getClasspathDocument(classpathFile);
		return createPomXmlDoc(classpathDoc);
	}

	private  void searchClasspathFileInSubfolders(File folder) {
		// get list of directories
		File[] folders = folder.listFiles(new FileFilter() {
			public boolean accept(File file) {
				return file.isDirectory();
			}
		});

		for (int i = 0; i < folders.length; i++) {
			displayFiles(folders[i]);
		}

	}

	private  void writePomToDisk(Document pomXmlDoc) {
		// TODO Auto-generated method stub

	}

	private  Document createPomXmlDoc(Document classpathDoc) {
		ClasspathToPomConverter converter = new ClasspathToPomConverter(classpathDoc, workspaceRoot);
		return converter.createPomDoc();
	}

	private  Document getClasspathDocument(File classpathFile) {
		// TODO Auto-generated method stub
		return null;
	}

	private  File getClasspathFile(File directory) {
		FilenameFilter filter = new FilenameFilter() {

			public boolean accept(File dir, String name) {
				return CLASSPATH.equals(name);
			}
		};

		File[] files = directory.listFiles(filter);
		if (files != null && files.length > 0)
			return files[0];
		else
			return null;

	}

}