package com.shri.eclipsetomaven;

import org.apache.commons.lang.StringUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class PomDependencyCreator {
	Document pomDoc;
	
	public PomDependencyCreator(Document pomDoc) {
		super();
		this.pomDoc = pomDoc;
	}

	private static char[] NUMBERS = { '0', '1', '2', '3', '4', '5', '6', '7',
		'8', '9' };

	public void createPomDependencyFromClasspathEntry(
			Element dependenciesElement, String pathAttribute) {
		String jarName = getJarName(pathAttribute);
		String artifactId = getArtifactId(jarName);
		String groupId = artifactId;
		String jarVersion = getJarVersion(jarName);
		createDependencyElement(dependenciesElement, groupId, artifactId,
				jarVersion);
	}
	
	String getArtifactId(String jarName) {
		int indexOfAny = StringUtils.indexOfAny(jarName, NUMBERS);
		if (indexOfAny != -1) {
			return jarName.substring(0, indexOfAny - 1);
		}
		return "";
	}

	String getJarName(String pathAttribute) {
		return pathAttribute.substring(pathAttribute.lastIndexOf('/') + 1);
	}

	String getJarVersion(String jarName) {
		int indexOfAny = StringUtils.indexOfAny(jarName, NUMBERS);
		int jarIndex = jarName.indexOf(".jar");
		if (jarIndex == -1) {
			jarIndex = jarName.indexOf(".zip");
		}
		if (indexOfAny != -1 && jarIndex != -1) {

			return jarName.substring(indexOfAny, jarIndex);
		}
		return "";
	}

	private void createDependencyElement(Element dependenciesElement,
			String groupId, String artifactId, String jarVersion) {
		Element dependencyElement = pomDoc.createElement("dependency");
		dependenciesElement.appendChild(dependencyElement);
		appendElement(dependencyElement, "groupId", groupId);
		appendElement(dependencyElement, "artifactId", artifactId);
		appendElement(dependencyElement, "jarVersion", jarVersion);
	}

	private void appendElement(Element dependencyElement, String tagName,
			String tagValue) {
		Element appendedElement = pomDoc.createElement(tagName);
		appendedElement.appendChild(pomDoc.createTextNode(tagValue));
		dependencyElement.appendChild(appendedElement);
	}
}
