package com.svashishtha.eclipsetomaven.classpathentry;

import org.w3c.dom.Element;

public class ClasspathEntryProcessorFactory {

    public static ClasspathEntryProcessor create(Element classpathEntryElement) {
        ClasspathEntryAttributes attributes = new ClasspathEntryAttributes();
        setAttributes(classpathEntryElement, attributes);
        ClasspathEntryProcessorType classpathEntryProcessorType = ClasspathEntryProcessorType
        		.valueOf(attributes.getKind().toUpperCase());
        return classpathEntryProcessorType.create(attributes);
    }

	private static void setAttributes(Element classpathEntryElement, ClasspathEntryAttributes attributes) {
		attributes.setKind(classpathEntryElement
                .getAttribute(ClasspathConstants.KIND_ATTRIBUTE));
        attributes.setPath(classpathEntryElement
        		.getAttribute(ClasspathConstants.PATH_ATTR));
        attributes.setExported(classpathEntryElement
        		.getAttribute(ClasspathConstants.EXPORTED_ATTR));
        attributes.setCombinedAccessRules(classpathEntryElement
        		.getAttribute(ClasspathConstants.COMBINEDACCESSRULES_ATTR));
	}
}