package com.shri.eclipsetomaven;

import java.io.File;
import java.io.FileFilter;
import java.io.FilenameFilter;

import org.w3c.dom.Document;

public class ReadClasspath {

	private static final String CLASSPATH = ".classpath";

	public static void main(String args[]) throws Exception {
		displayFiles("/Users/shrikant/code");
	}/* end main */

	public static void displayFiles(String filePath) {
		displayFiles(new File(filePath));
	}

	public static void displayFiles(File folder) {
		File classpathFile = getClasspathFile(folder);
		if (classpathFile == null) {
			searchClasspathFileInSubfolders(folder);
		} else {
			Document pomXmlDoc = createPomXmlDoc(classpathFile);
			writePomToDisk(pomXmlDoc);
		}
	}

	private static Document createPomXmlDoc(File classpathFile) {
		Document classpathDoc = getClasspathDocument(classpathFile);
		System.out.println("CLASSPATH@@@@@@@@@@@@@@@@");
		System.out.println(XMLUtil.prettyPrint(classpathFile));
		Document pomXmlDoc = createPomXmlDoc(classpathDoc);
		return pomXmlDoc;
	}

	private static void searchClasspathFileInSubfolders(File folder) {
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

	private static void writePomToDisk(Document pomXmlDoc) {
		// TODO Auto-generated method stub

	}

	private static Document createPomXmlDoc(Document classpathDoc) {
		// TODO Auto-generated method stub
		return null;
	}

	private static Document getClasspathDocument(File classpathFile) {
		// TODO Auto-generated method stub
		return null;
	}

	private static File getClasspathFile(File directory) {
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