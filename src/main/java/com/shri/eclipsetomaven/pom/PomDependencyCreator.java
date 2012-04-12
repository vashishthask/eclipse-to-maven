package com.shri.eclipsetomaven.pom;

import org.w3c.dom.Element;

public interface PomDependencyCreator {

	public abstract void createPomDependencyFromClasspathEntry(
			Element dependenciesElement, String pathAttribute);

	public abstract void createPomDependencyFromClasspathEntry(
			Element dependenciesElement, String pathAttribute, String groupId,
			String artifactId);

}