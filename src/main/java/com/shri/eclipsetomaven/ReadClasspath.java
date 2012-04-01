package com.shri.eclipsetomaven;

import java.io.File;
import java.io.FileFilter;

import org.w3c.dom.Document;

import com.shri.eclipsetomaven.util.XMLUtil;

public class ReadClasspath {

	private static final String WORKSPACE_ROOT = "/Users/shrikant/code";
	File workspaceRoot;

	public static void main(String args[]) throws Exception {
		ReadClasspath readClasspath = new ReadClasspath();
		readClasspath.displayFiles(WORKSPACE_ROOT);
	}/* end main */

	public  void displayFiles(String filePath) {
		workspaceRoot = new File(filePath);
		displayFiles(workspaceRoot);
	}

	public  void displayFiles(File folder) {
		File classpathFile = ClasspathUtil.getClasspathFile(folder);
		if (classpathFile == null) {
			searchClasspathFileInSubfolders(folder);
		} else {
			Document pomXmlDoc = createPomXmlDoc(classpathFile);
			writePomToDisk(pomXmlDoc);
		}
	}

	private  Document createPomXmlDoc(File classpathFile) {
		Document classpathDoc = XMLUtil.getDocument(classpathFile);
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

}