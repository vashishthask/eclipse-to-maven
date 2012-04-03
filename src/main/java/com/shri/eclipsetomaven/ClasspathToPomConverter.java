package com.shri.eclipsetomaven;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.shri.eclipsetomaven.util.ClasspathUtil;
import com.shri.eclipsetomaven.util.FindFile;
import com.shri.eclipsetomaven.util.XMLUtil;

public class ClasspathToPomConverter {
	
	private File workspaceRoot;
	private Document classpathDoc;

	private static final String PATH_ATTRIBUTE = "path";
	private static final String KIND_ATTRIBUTE = "kind";
	private static final String CLASSPATHENTRY_TAG_NAME = "classpathentry";
	PomDependencyCreator pomDependencyCreator;

	public ClasspathToPomConverter(Document classpathDoc, File workspaceRoot) {
		this.classpathDoc = classpathDoc;
		this.workspaceRoot = workspaceRoot;
	}
	
	private Document pomDoc;
	
	
	public Document createPomDoc() {
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder db;
		try {
			db = dbf.newDocumentBuilder();
		} catch (ParserConfigurationException e) {
			throw new IllegalStateException(e);
		}

		pomDoc = db.newDocument();

		Element projectElement = createBasicPomStructure();
		addDependenciesFromClasspath(projectElement);
		return pomDoc;
	}

	private void addDependenciesFromClasspath(Element projectElement) {
		Element dependenciesElement = createDependencyManagementElement(projectElement);

		List<Element> classpathEntryElements = XMLUtil.getElements(
				CLASSPATHENTRY_TAG_NAME, classpathDoc.getDocumentElement());
		createDependenciesFromCombinedcaccessrulesAttributeEntry(
				dependenciesElement, classpathEntryElements);
	}

	private void createDependenciesFromCombinedcaccessrulesAttributeEntry(
			Element dependenciesElement, List<Element> classpathEntryElements) {
		List<Element> classpathntryElementsWithCombineaccessrulesAttribute = getClasspathntryElementsWithCombineaccessrulesAttribute(classpathEntryElements);

		for (Element classpathentry : classpathntryElementsWithCombineaccessrulesAttribute) {
			String pathAtt = classpathentry.getAttribute(PATH_ATTRIBUTE);
			File pathFolder = searchFolder(pathAtt, workspaceRoot);
			File classpathFile = ClasspathUtil.getClasspathFile(pathFolder);
			if (classpathFile != null) {
				Document classpathDoc = XMLUtil.getDocument(classpathFile);
				parseCombinedClasspathFalseClasspath(classpathDoc,
						dependenciesElement);
			}

		}
	}

	private Element createDependencyManagementElement(Element projectElement) {
		Element dependencyManagementElement = pomDoc
				.createElement("dependencyManagement");
		Element dependenciesElement = pomDoc.createElement("dependencies");
		projectElement.appendChild(dependencyManagementElement);
		dependencyManagementElement.appendChild(dependenciesElement);
		return dependenciesElement;
	}

	private Element createBasicPomStructure() {
		Element projectElement = pomDoc.createElement("project");
		pomDoc.appendChild(projectElement);

		XMLUtil.addAttributeToElement(pomDoc, projectElement, "xmlns",
				"http://maven.apache.org/POM/4.0.0");
		XMLUtil.addAttributeToElement(pomDoc, projectElement, "xmlns:xsi",
				"http://www.w3.org/2001/XMLSchema-instance");
		XMLUtil.addAttributeToElement(pomDoc, projectElement, "xsi:schemaLocation",
				"http://maven.apache.org/POM/4.0.0 http://maven.apache.org/maven-v4_0_0.xsd");
		return projectElement;
	}



	private void parseCombinedClasspathFalseClasspath(Document classpathDoc2,
			Element dependenciesElement) {
		List<Element> classpathEntries = getClasspathentryOfLibKind(classpathDoc2
				.getDocumentElement());
		pomDependencyCreator = new PomDependencyCreator(pomDoc);
		for (Element classpathEntry : classpathEntries) {
			String pathAttribute = classpathEntry.getAttribute(PATH_ATTRIBUTE);
			pomDependencyCreator.createPomDependencyFromClasspathEntry(dependenciesElement,
					pathAttribute);
		}

	}

	
	private List<Element> getClasspathntryElementsWithCombineaccessrulesAttribute(
			List<Element> classpathEntryElements) {
		List<Element> elementsWithCombineaccessruleAttribute = new ArrayList<Element>();
		for (Element element : classpathEntryElements) {
			String attribute = element.getAttribute("combineaccessrules");
			String kindAtt = element.getAttribute(KIND_ATTRIBUTE);
			if ("false".equals(attribute) && "src".equals(kindAtt)) {
				elementsWithCombineaccessruleAttribute.add(element);
			}

		}
		return elementsWithCombineaccessruleAttribute;
	}



	private List<Element> getClasspathentryOfLibKind(Element classpathDocElement) {
		List<Element> classpathEntriesElements = XMLUtil.getElements(
				CLASSPATHENTRY_TAG_NAME, classpathDocElement);
		List<Element> classpathEntriesOfLibKind = new ArrayList<Element>();
		for (Element classpathEntryElement : classpathEntriesElements) {
			String kindAtt = classpathEntryElement.getAttribute(KIND_ATTRIBUTE);
			if ("lib".equals(kindAtt)) {
				classpathEntriesOfLibKind.add(classpathEntryElement);
			}
		}
		return classpathEntriesOfLibKind;
	}

	File searchFolder(String pathAtt, File workspaceRoot2) {
		FindFile findFile = new FindFile();
		String rootPath = workspaceRoot2.getAbsolutePath();
		if (pathAtt.startsWith("/"))
			pathAtt = pathAtt.substring(1);

		List<Path> files = null;

		try {
			files = findFile.find(rootPath, pathAtt);
		} catch (IOException e) {
			throw new IllegalStateException(e);
		}
		return files.get(0).toFile();
	}

}
