package com.shri.eclipsetomaven.classpathentry;

import java.io.File;


import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.shri.eclipsetomaven.pom.PomDependencyCreator;
import com.shri.eclipsetomaven.pom.PomDependencyCreatorImpl;
import com.shri.eclipsetomaven.util.ApplicationConfig;
import static com.shri.eclipsetomaven.ApplicationPropertyConstants.*;

public class SrcWithLocalLibClasspathEntryProcessor implements
		ClasspathEntryProcessor {

	

	@Override
	public void process(Element dependenciesElement,
			Element classpathEntryElement, File workspaceRoot, Document pomDoc,
			File classpathRoot) {
		PomDependencyCreator pomDependencyCreator = new PomDependencyCreatorImpl(
				pomDoc);
		String pathAttribute = classpathEntryElement
				.getAttribute(ClasspathConstants.PATH_ATTR);
		String groupId =  ApplicationConfig.INSTANCE.getValue(MAVEN_DEPENDENCY_GROUP_ID_DEFAULT);
		String artifactId = pathAttribute.substring(1);
		artifactId = artifactId.replaceAll("\\s", "");
		pomDependencyCreator.createPomDependencyFromClasspathEntry(
				dependenciesElement, pathAttribute, groupId, artifactId);

	}
}