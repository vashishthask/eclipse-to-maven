package com.shri.eclipsetomaven.classpathentry;

import java.io.File;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class DefaultClasspathEntryProcessor implements ClasspathEntryProcessor {

	@Override
	public void process(Element dependenciesElement,
			Element classpathEntryElement, File workspaceRoot, Document pomDoc) {
		//DO Nothing
	}
}
