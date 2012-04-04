package com.shri.eclipsetomaven.classpathentry;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class SrcClasspathEntryProcessor implements ClasspathEntryProcessor {

	@Override
	public void process(Element dependenciesElement,
			Element classpathEntryElement, File workspaceRoot, Document pomDoc,  File classpathRoot) {
		String pathAttribute = classpathEntryElement.getAttribute(ClasspathConstants.PATH_ATTR);
		if("Resources".equals(pathAttribute) || "Properties".equals(pathAttribute)){
			moveToSrcMainResources(pathAttribute, classpathRoot);
		} else {
			moveToSrcMainJava(pathAttribute, classpathRoot);
		}
	}

	private void moveToSrcMainJava(String pathAttribute, File classpathRoot) {
		moveSources(pathAttribute, classpathRoot, "src/main/java");
	}
	
	private void moveToSrcMainResources(String pathAttribute, File classpathRoot) {
		moveSources(pathAttribute, classpathRoot, "src/main/resources");
	}

	private void moveSources(String pathAttribute, File classpathRoot, String destinationFolder) {
		File srcMainJavaDir = new File(classpathRoot.getParent(), destinationFolder);
		if(!srcMainJavaDir.exists()){
			srcMainJavaDir.mkdirs();
		}
		try {
			FileUtils.moveDirectory(new File(classpathRoot.getParent(), pathAttribute), srcMainJavaDir);
		} catch (IOException e) {
			throw new IllegalStateException(e);
		}
	}
}