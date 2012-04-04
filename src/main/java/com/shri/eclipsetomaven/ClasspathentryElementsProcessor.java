package com.shri.eclipsetomaven;

import java.io.File;
import java.util.List;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.shri.eclipsetomaven.classpathentry.ClasspathEntryProcessor;
import com.shri.eclipsetomaven.classpathentry.ClasspathEntryProcessorFactory;

public class ClasspathentryElementsProcessor {
	
	private Document pomDoc;
	private File workspaceRoot;
	
	

	public ClasspathentryElementsProcessor(Document pomDoc, File workspaceRoot) {
		super();
		this.pomDoc = pomDoc;
		this.workspaceRoot = workspaceRoot;
	}

	public void process(Element dependenciesElement,
			List<Element> classpathEntryElements) {
		for (Element classpathEntryElement : classpathEntryElements) {
			ClasspathEntryProcessor processor = ClasspathEntryProcessorFactory.create(classpathEntryElement);
			processor.process(dependenciesElement, classpathEntryElement, workspaceRoot, pomDoc);
		}
	}

}
