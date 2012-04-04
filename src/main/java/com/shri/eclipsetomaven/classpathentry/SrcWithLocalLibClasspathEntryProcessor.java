package com.shri.eclipsetomaven.classpathentry;

import java.io.File;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.shri.eclipsetomaven.PomDependencyCreator;

public class SrcWithLocalLibClasspathEntryProcessor implements
		ClasspathEntryProcessor {

	@Override
	public void process(Element dependenciesElement,
			Element classpathEntryElement, File workspaceRoot, Document pomDoc) {
		PomDependencyCreator pomDependencyCreator = new PomDependencyCreator(pomDoc);
		String pathAttribute = classpathEntryElement.getAttribute(ClasspathConstants.PATH_ATTR);
		try {
			Configuration config = new PropertiesConfiguration("application.properties");
			String groupId = config.getString("maven.dependency.groupId.default");
			String artifactId = pathAttribute.substring(1);
			pomDependencyCreator.createPomDependencyFromClasspathEntry(
					dependenciesElement, pathAttribute,groupId, artifactId);
		} catch (ConfigurationException e) {
			throw new IllegalStateException(e);
		}
		
	}
}