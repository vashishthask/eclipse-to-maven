package com.svashishtha.eclipsetomaven.classpathentry;

public enum ClasspathEntryProcessorType {
	SRC {
		ClasspathEntryProcessor create(ClasspathEntryAttributes attributes) {
			if (ClasspathConstants.FALSE.equals(attributes.getCombinedAccessRules())) {
				return new CombinedaccessrulesFalseClasspathEntryProcessor();
			} else if (attributes.getPath().startsWith("/")) {
				return new SrcWithLocalLibClasspathEntryProcessor();
			}
			return new SrcClasspathEntryProcessor();
		}

	},
	LIB {
		ClasspathEntryProcessor create(ClasspathEntryAttributes attributes) {
			if (ClasspathConstants.TRUE.equals(attributes.getExported())) {
				return new LibExportedTrueClasspathEntryProcessor();
			}
			return new LibClasspathEntryProcessor();

		}
	},
	CON, OUTPUT;

	ClasspathEntryProcessor create(ClasspathEntryAttributes attributes) {
		return new DefaultClasspathEntryProcessor();
	}
}
