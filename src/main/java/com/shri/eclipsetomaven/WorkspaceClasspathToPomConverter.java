package com.shri.eclipsetomaven;

import static com.shri.eclipsetomaven.ApplicationPropertyConstants.PRINT_DEPENDENCY_GRAPH;

import java.io.File;
import java.io.FileFilter;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.shri.eclipsetomaven.pom.ClasspathToPomConverter;
import com.shri.eclipsetomaven.util.ApplicationConfig;
import com.shri.eclipsetomaven.util.ClasspathUtil;
import com.shri.eclipsetomaven.util.FileUtil;
import com.shri.eclipsetomaven.util.XMLUtil;
import static com.shri.eclipsetomaven.pom.PomConstants.*;
import static com.shri.eclipsetomaven.ApplicationPropertyConstants.*;

public class WorkspaceClasspathToPomConverter {
	Document pomDoc;
	private File workspaceRoot;

	public WorkspaceClasspathToPomConverter(File workspaceRoot) {
		this.workspaceRoot = workspaceRoot;
	}

	public void convert(File parentFolder) {
		File classpathFile = ClasspathUtil.getClasspathFile(parentFolder);
		if (classpathFile == null) {
			convertInSubFolders(parentFolder);
		} else {
			Document pomXmlDoc = createPomXmlDoc(classpathFile);
			printDependencies(pomXmlDoc);
			if ("true".equals(ApplicationConfig.INSTANCE
					.getValue(CONVERT_TO_MAVEN))) {
				writePomToDisk(pomXmlDoc, classpathFile.getParentFile());
			}
		}
	}

	private void printDependencies(Document pomXmlDoc) {
		if ("true".equals(ApplicationConfig.INSTANCE
				.getValue(PRINT_DEPENDENCY_GRAPH))) {
			Element documentRoot = pomXmlDoc.getDocumentElement();
			String projectName = XMLUtil.getTagValue(documentRoot, NAME);
			print(projectName);
			print("=========================");
			List<Element> dependencies = XMLUtil.getElements(DEPENDENCY,
					documentRoot);
			for (Element dependency : dependencies) {
				String dependencyName = XMLUtil.getTagValue(dependency,
						ARTIFACT_ID);
				String versionNumber = XMLUtil.getTagValue(documentRoot,
						VERSION);
				print("    |--" + dependencyName + "--" + versionNumber);
			}
			print("\n\n");
		}
	}

	private void print(String str) {
		String printIoType = ApplicationConfig.INSTANCE
				.getValue(PRINT_DEPENDENCY_GRAPH_IOTYPE);
		if (PRINT_DEPENDENCY_GRAPH_IOTYPE_CONSOLE.equals(printIoType)) {
			System.out.println(str);
		} else if (PRINT_DEPENDENCY_GRAPH_IOTYPE_FILE.equals(printIoType)) {
			String fileName = ApplicationConfig.INSTANCE
					.getValue(PRINT_DEPENDENCY_GRAPH_FILEPATH);
			if (StringUtils.isNotEmpty(fileName)) {
				FileUtil.appendToFile(fileName, str);
			}
		}
	}

	private Document createPomXmlDoc(File classpathFile) {
		File classpathRoot = classpathFile.getParentFile();
		Document classpathDoc = XMLUtil.getDocument(classpathFile);
		ClasspathToPomConverter classpathToPomConverter = new ClasspathToPomConverter(
				classpathDoc, workspaceRoot, classpathRoot);
		return classpathToPomConverter.createPomDoc();
	}

	private void convertInSubFolders(File parentFolder) {
		// get list of directories
		File[] filteredSubFolders = parentFolder.listFiles(new FileFilter() {
			public boolean accept(File file) {
				return file.isDirectory() && !".hg".equals(file.getName());
			}
		});

		for (int i = 0; i < filteredSubFolders.length; i++) {
			convert(filteredSubFolders[i]);
		}
	}

	private void writePomToDisk(Document pomXmlDoc, File directoryToWriteTo) {
		XMLUtil.writeDocument(pomXmlDoc, directoryToWriteTo, "pom.xml");
	}
}