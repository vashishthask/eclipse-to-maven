package com.shri.eclipsetomaven;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.commons.lang.StringUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.shri.eclipsetomaven.util.XMLUtil;

public class ClasspathToPomConverter {
	private static final String PATH_ATTRIBUTE = "path";
	private static final String KIND_ATTRIBUTE = "kind";
	private static final String CLASSPATHENTRY_TAG_NAME = "classpathentry";
	private static char [] NUMBERS = {'0','1','2','3','4','5','6','7','8','9'};
	private Document classpathDoc;
	private File workspaceRoot;
	Document pomDoc;

	public ClasspathToPomConverter(Document classpathDoc, File workspaceRoot) {
		this.classpathDoc = classpathDoc;
		this.workspaceRoot = workspaceRoot;
	}

	private List<Element> getClasspathntryElementsWithCombineaccessrulesAttribute(
			List<Element> classpathEntryElements) {
		List<Element> elementsWithCombineaccessruleAttribute = new ArrayList<Element>();
		for (Element element : classpathEntryElements) {
			 String attribute = element.getAttribute("combineaccessrules");
			 if("false".equals(attribute)){
				 elementsWithCombineaccessruleAttribute.add(element);
			 }
			
		}
		return elementsWithCombineaccessruleAttribute;
	}

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
		Element dependencyManagementElement = pomDoc.createElement("dependencyManagement");
	    Element dependenciesElement = pomDoc.createElement("dependencies");
	    projectElement.appendChild(dependencyManagementElement);
	    dependencyManagementElement.appendChild(dependenciesElement);
	    
		List<Element> classpathEntryElements = XMLUtil.getElements(CLASSPATHENTRY_TAG_NAME, classpathDoc.getDocumentElement());
		List<Element> classpathntryElementsWithCombineaccessrulesAttribute = getClasspathntryElementsWithCombineaccessrulesAttribute(classpathEntryElements);
		
		for (Element classpathentry : classpathntryElementsWithCombineaccessrulesAttribute) {
			String kindAtt = classpathentry.getAttribute(KIND_ATTRIBUTE);
			if("src".equals(kindAtt)){
				String pathAtt = classpathentry.getAttribute(PATH_ATTRIBUTE);
				File pathFolder = searchFolder(pathAtt, workspaceRoot);
				Document classpathDoc = getClasspathDocUnder(pathFolder);
				parseCombinedClasspathFalseClasspath(classpathDoc, dependenciesElement);
			}
			
		}
	}

	private Element createBasicPomStructure() {
		Element projectElement = pomDoc.createElement("project"); 
	    projectElement.setAttribute("xmlns", "http://maven.apache.org/POM/4.0.0");
	    projectElement.setAttribute("xmlns:xsi", "http://www.w3.org/2001/XMLSchema-instance");
	    projectElement.setAttribute("xsi:schemaLocation", "http://maven.apache.org/POM/4.0.0 http://maven.apache.org/maven-v4_0_0.xsd");
		return projectElement;
	}

	private void parseCombinedClasspathFalseClasspath(Document classpathDoc2, Element dependenciesElement) {
		List<Element> classpathEntries = getClasspathentryOfLibKind(classpathDoc2.getDocumentElement());
		for (Element classpathEntry : classpathEntries) {
			String pathAttribute = classpathEntry.getAttribute(PATH_ATTRIBUTE);
			String jarName = getJarName(pathAttribute);
			//TODO groupId is fixed for now
			String groupId = "com.bankwest.lendnet";
			String artifactId = getArtifactId(jarName);
			String jarVersion = getJarVersion(jarName);
			createDependencyElement(dependenciesElement, groupId, artifactId, jarVersion);
		}
		
	}

	private void createDependencyElement(Element dependenciesElement, String groupId, String artifactId,
			String jarVersion) {
		Element dependencyElement = pomDoc.createElement("dependency");
		dependenciesElement.appendChild(dependencyElement);
		appendElement(dependenciesElement, "groupId", groupId);
		appendElement(dependenciesElement, "artifactId", artifactId);
		appendElement(dependenciesElement, "jarVersion", jarVersion);
	}

	private void appendElement(Element dependenciesElement, String tagName, String tagValue) {
		Element appendedElement = pomDoc.createElement(tagName);
		appendedElement.appendChild(pomDoc.createTextNode(tagValue));
	}

	String getArtifactId(String jarName) {
		return jarName.substring(0, StringUtils.indexOfAny(jarName, NUMBERS )-1);
	}

	String getJarName(String pathAttribute) {
		return pathAttribute.substring(pathAttribute.lastIndexOf('/')+1);
	}

	private List<Element> getClasspathentryOfLibKind(Element classpathDocElement) {
		List<Element> classpathEntriesElements = XMLUtil.getElements(CLASSPATHENTRY_TAG_NAME, classpathDocElement);
		List<Element> classpathEntriesOfLibKind = new ArrayList<Element>();
		for (Element classpathEntryElement : classpathEntriesElements) {
			String kindAtt = classpathEntryElement.getAttribute(KIND_ATTRIBUTE);
			if("lib".equals(kindAtt)){
				classpathEntriesOfLibKind.add(classpathEntryElement);
			}
		}
		// TODO Auto-generated method stub
		return classpathEntriesOfLibKind;
	}

	private Document getClasspathDocUnder(File pathFolder) {
		// TODO Auto-generated method stub
		return null;
	}

	private File searchFolder(String pathAtt, File workspaceRoot2) {
		// TODO Auto-generated method stub
		return null;
	}

	String getJarVersion(String jarName) {
		return jarName.substring(StringUtils.indexOfAny(jarName, NUMBERS ), jarName.indexOf(".jar"));
	}

}