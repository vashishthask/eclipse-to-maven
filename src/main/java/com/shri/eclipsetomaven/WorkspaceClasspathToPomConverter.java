package com.shri.eclipsetomaven;

import java.io.File;
import java.io.FileFilter;

import org.w3c.dom.Document;

import com.shri.eclipsetomaven.util.ClasspathUtil;
import com.shri.eclipsetomaven.util.XMLUtil;

public class WorkspaceClasspathToPomConverter {
	Document pomDoc;
	private File workspaceRoot;
	

	public WorkspaceClasspathToPomConverter(File workspaceRoot) {
		this.workspaceRoot = workspaceRoot;
	}



	public  void convert(File folder) {
		File classpathFile = ClasspathUtil.getClasspathFile(folder);
		if (classpathFile == null) {
			searchClasspathFileInSubfolders(folder);
		} else {
			Document pomXmlDoc = createPomXmlDoc(classpathFile);
			System.out.println(XMLUtil.prettyPrint(pomXmlDoc));
			writePomToDisk(pomXmlDoc);
		}
	}
	
	private  Document createPomXmlDoc(File classpathFile) {
		Document classpathDoc = XMLUtil.getDocument(classpathFile);
		ClasspathToPomConverter classpathToPomConverter = new ClasspathToPomConverter(classpathDoc, workspaceRoot);
		return classpathToPomConverter.createPomDoc();
	}

	private  void searchClasspathFileInSubfolders(File folder) {
		// get list of directories
		File[] folders = folder.listFiles(new FileFilter() {
			public boolean accept(File file) {
				return file.isDirectory()&& !".hg".equals(file.getName());
			}
		});

		for (int i = 0; i < folders.length; i++) {
			convert(folders[i]);
		}
	}

	private  void writePomToDisk(Document pomXmlDoc) {
		// TODO Auto-generated method stub

	}



}