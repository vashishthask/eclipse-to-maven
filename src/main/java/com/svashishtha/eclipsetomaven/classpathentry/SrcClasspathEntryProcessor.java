package com.svashishtha.eclipsetomaven.classpathentry;

import java.io.File;

import com.svashishtha.eclipsetomaven.ApplicationPropertyConstants;
import com.svashishtha.eclipsetomaven.EclipseToMavenFoldersMover;
import com.svashishtha.eclipsetomaven.util.ApplicationConfig;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class SrcClasspathEntryProcessor implements ClasspathEntryProcessor {

	

	@Override
	public void process(Element dependenciesElement,
			Element classpathEntryElement, File workspaceRoot, Document pomDoc,
			File classpathRoot) {
		String pathAttribute = classpathEntryElement
				.getAttribute(ClasspathConstants.PATH_ATTR);
		
		String convertToMaven = ApplicationConfig.INSTANCE
				.getValue(ApplicationPropertyConstants.CONVERT_TO_MAVEN);
		if ("true".equals(convertToMaven)) {
			moveSourcesToMavenFolders(classpathRoot, pathAttribute);
		}
	}

	void moveSourcesToMavenFolders(File classpathRoot, String pathAttribute) {
		EclipseToMavenFoldersMover foldersMover = new EclipseToMavenFoldersMover();
		if ("Resources".equals(pathAttribute)
				|| "Properties".equals(pathAttribute)) {
			foldersMover.moveToSrcMainResources(pathAttribute, classpathRoot);
		} else if ("test".equals(pathAttribute)) {
			foldersMover.moveToSrcTestJava(pathAttribute, classpathRoot);
		} else {
			foldersMover.moveToSrcMainJava(pathAttribute, classpathRoot);
		}
	}
}