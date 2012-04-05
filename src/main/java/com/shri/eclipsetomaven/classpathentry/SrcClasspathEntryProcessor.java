package com.shri.eclipsetomaven.classpathentry;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class SrcClasspathEntryProcessor implements ClasspathEntryProcessor {

    @Override
    public void process(Element dependenciesElement,
            Element classpathEntryElement, File workspaceRoot, Document pomDoc,
            File classpathRoot) {
        String pathAttribute = classpathEntryElement
                .getAttribute(ClasspathConstants.PATH_ATTR);
        if ("Resources".equals(pathAttribute)
                || "Properties".equals(pathAttribute)) {
            moveToSrcMainResources(pathAttribute, classpathRoot);
        } else {
            moveToSrcMainJava(pathAttribute, classpathRoot);
        }
    }

    void moveToSrcMainJava(String pathAttribute, File classpathRoot) {
        moveSources(pathAttribute, classpathRoot, "src/main/java");
    }

    void moveToSrcMainResources(String pathAttribute, File classpathRoot) {
        moveSources(pathAttribute, classpathRoot, "src/main/resources");
    }

    private void moveSources(String pathAttribute, File classpathRoot,
            String destinationFolder) {
        File srcMainJavaDir = new File(classpathRoot, destinationFolder);
        if (!srcMainJavaDir.exists()) {
            srcMainJavaDir.mkdirs();
        }
        try {
            File sourceDirectory = new File(classpathRoot, pathAttribute);
            
            File[] files = sourceDirectory.listFiles();
            if (files != null && files.length > 0) {
                for (int i = 0; i < files.length; i++) {
                    FileUtils.moveToDirectory(files[i], srcMainJavaDir, false);
                }
            }
            sourceDirectory.delete();
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }
}