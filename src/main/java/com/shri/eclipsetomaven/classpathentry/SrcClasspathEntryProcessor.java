package com.shri.eclipsetomaven.classpathentry;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.shri.eclipsetomaven.util.Copy;

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
            String destinationFolderName) {
        File destinationFolder = new File(classpathRoot, destinationFolderName);
        if (!destinationFolder.exists()) {
            destinationFolder.mkdirs();
        }
        File sourceDirectory = new File(classpathRoot, pathAttribute);

        File[] files = sourceDirectory.listFiles();
        if (files != null && files.length > 0) {
            for (int i = 0; i < files.length; i++) {
                // FileUtils.moveToDirectory(files[i], srcMainJavaDir, false);
                Copy.copyFiles(files[i], destinationFolder, true, false, false);

            }
        }
        sourceDirectory.delete();
    }
}