package com.svashishtha.eclipsetomaven.classpathentry;

import java.io.File;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class SrcWithLocalLibClasspathEntryProcessor implements
		ClasspathEntryProcessor {

	

	@Override
	public void process(Element dependenciesElement,
			Element classpathEntryElement, File workspaceRoot, Document pomDoc,
			File classpathRoot) {
	    LocalLibDependencyCreator dependencyCreator = new LocalLibDependencyCreator();
	    dependencyCreator.createLocalLibDependency(classpathEntryElement, dependenciesElement, pomDoc);
	}
}