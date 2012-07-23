package com.shri.eclipsetomaven.classpathentry;

import static com.shri.eclipsetomaven.ApplicationPropertyConstants.MAVEN_DEPENDENCY_GROUP_ID_DEFAULT;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.shri.eclipsetomaven.pom.PomDependencyCreator;
import com.shri.eclipsetomaven.pom.PomDependencyCreatorImpl;
import com.shri.eclipsetomaven.util.ApplicationConfig;

public class LocalLibDependencyCreator {
    
    public void createLocalLibDependency(Element classpathEntryElement, Element dependenciesElement, Document pomDoc ){
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
