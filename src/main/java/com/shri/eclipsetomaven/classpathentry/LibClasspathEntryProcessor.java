package com.shri.eclipsetomaven.classpathentry;

import java.io.File;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.shri.eclipsetomaven.PomDependencyCreator;

public class LibClasspathEntryProcessor implements ClasspathEntryProcessor {

    @Override
    public void process(Element dependenciesElement,
            Element classpathEntryElement, File workspaceRoot, Document pomDoc,
            File classpathRoot) {
        PomDependencyCreator pomDependencyCreator = new PomDependencyCreator(
                pomDoc);
        String pathAttribute = classpathEntryElement
                .getAttribute(ClasspathConstants.PATH_ATTR);
        pomDependencyCreator.createPomDependencyFromClasspathEntry(
                dependenciesElement, pathAttribute);
    }

}
