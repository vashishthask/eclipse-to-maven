package com.shri.eclipsetomaven.classpathentry;

import org.w3c.dom.Element;


public class ClasspathEntryProcessorFactory {

	public static ClasspathEntryProcessor create(Element classpathEntryElement) {
		String combineaccessrulesAttribute = classpathEntryElement.getAttribute(ClasspathConstants.COMBINEDACCESSRULES_ATTR);
		
		String kindAtt = classpathEntryElement.getAttribute(ClasspathConstants.KIND_ATTRIBUTE);
		String exportedAttribute = classpathEntryElement.getAttribute(ClasspathConstants.EXPORTED_ATTR);
		String pathAtt = classpathEntryElement.getAttribute(ClasspathConstants.PATH_ATTR);
		if (ClasspathConstants.FALSE.equals(combineaccessrulesAttribute) && ClasspathConstants.SRC_ATTR.equals(kindAtt)) {
			return new CombinedaccessrulesFalseClasspathEntryProcessor();
		} else if (ClasspathConstants.TRUE.equals(exportedAttribute) && ClasspathConstants.LIB_ATTR.equals(kindAtt)){
			return new LibExportedTrueClasspathEntryProcessor();
		} else if (ClasspathConstants.LIB_ATTR.equals(kindAtt)){
			return new LibClasspathEntryProcessor();
		} else if(ClasspathConstants.SRC_ATTR.equals(kindAtt) && !pathAtt.startsWith("/")){
			return new SrcClasspathEntryProcessor();
		} else if (ClasspathConstants.SRC_ATTR.equals(kindAtt) && pathAtt.startsWith("/")){
			return new SrcWithLocalLibClasspathEntryProcessor();
		}
		return new DefaultClasspathEntryProcessor();
	}
}
