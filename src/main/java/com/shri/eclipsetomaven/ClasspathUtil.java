package com.shri.eclipsetomaven;

import java.io.File;
import java.io.FilenameFilter;

public class ClasspathUtil {
	
	private static final String CLASSPATH = ".classpath";
	
	public static  File getClasspathFile(File directory) {
		FilenameFilter filter = new FilenameFilter() {

			public boolean accept(File dir, String name) {
				return CLASSPATH.equals(name);
			}
		};

		File[] files = directory.listFiles(filter);
		if (files != null && files.length > 0)
			return files[0];
		else
			return null;

	}
}