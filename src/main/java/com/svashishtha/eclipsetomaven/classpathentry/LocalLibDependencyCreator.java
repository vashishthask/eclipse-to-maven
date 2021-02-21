package com.svashishtha.eclipsetomaven.classpathentry;

import com.svashishtha.eclipsetomaven.ApplicationPropertyConstants;
import com.svashishtha.eclipsetomaven.pom.PomDependencyCreator;
import com.svashishtha.eclipsetomaven.pom.PomDependencyCreatorImpl;
import com.svashishtha.eclipsetomaven.util.ApplicationConfig;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class LocalLibDependencyCreator {
    
    public void createLocalLibDependency(Element classpathEntryElement, Element dependenciesElement, Document pomDoc ){
        PomDependencyCreator pomDependencyCreator = new PomDependencyCreatorImpl(
                pomDoc);
        String pathAttribute = classpathEntryElement
                .getAttribute(ClasspathConstants.PATH_ATTR);
        String groupId =  ApplicationConfig.INSTANCE.getValue(ApplicationPropertyConstants.MAVEN_DEPENDENCY_GROUP_ID_DEFAULT);
        String artifactId = pathAttribute.substring(1);
        artifactId = artifactId.replaceAll("\\s", "");
        pomDependencyCreator.createPomDependencyFromClasspathEntry(
                dependenciesElement, pathAttribute, groupId, artifactId);
    }

}
