package com.svashishtha.eclipsetomaven.classpathentry;

import java.io.File;

import com.svashishtha.eclipsetomaven.pom.PomDependencyCreator;
import com.svashishtha.eclipsetomaven.pom.PomDependencyCreatorImpl;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class LibExportedTrueClasspathEntryProcessor implements
        ClasspathEntryProcessor {

    @Override
    public void process(Element dependenciesElement,
            Element classpathEntryElement, File workspaceRoot, Document pomDoc,
            File classpathRoot) {
        PomDependencyCreator pomDependencyCreator = new PomDependencyCreatorImpl(
                pomDoc);
        String pathAttribute = classpathEntryElement
                .getAttribute(ClasspathConstants.PATH_ATTR);
        pomDependencyCreator.createPomDependencyFromClasspathEntry(
                dependenciesElement, pathAttribute);

    }

}
