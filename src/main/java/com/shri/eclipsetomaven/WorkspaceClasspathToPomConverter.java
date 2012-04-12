package com.shri.eclipsetomaven;

import java.io.File;
import java.io.FileFilter;

import org.w3c.dom.Document;

import com.shri.eclipsetomaven.pom.ClasspathToPomConverter;
import com.shri.eclipsetomaven.util.ClasspathUtil;
import com.shri.eclipsetomaven.util.XMLUtil;

public class WorkspaceClasspathToPomConverter {
    Document pomDoc;
    private File workspaceRoot;

    public WorkspaceClasspathToPomConverter(File workspaceRoot) {
        this.workspaceRoot = workspaceRoot;
    }

    public void convert(File parentFolder) {
        File classpathFile = ClasspathUtil.getClasspathFile(parentFolder);
        if (classpathFile == null) {
            convertInSubFolders(parentFolder);
        } else {
            System.err.println("CLASSPATH NAME:"
                    + classpathFile.getAbsolutePath());
            Document pomXmlDoc = createPomXmlDoc(classpathFile);
            // XMLUtil.prettyPrint(pomXmlDoc);
            writePomToDisk(pomXmlDoc, classpathFile.getParentFile());
        }
    }

    private Document createPomXmlDoc(File classpathFile) {
        File classpathRoot = classpathFile.getParentFile();
        Document classpathDoc = XMLUtil.getDocument(classpathFile);
        ClasspathToPomConverter classpathToPomConverter = new ClasspathToPomConverter(
                classpathDoc, workspaceRoot, classpathRoot);
        return classpathToPomConverter.createPomDoc();
    }

    private void convertInSubFolders(File parentFolder) {
        // get list of directories
        File[] filteredSubFolders = parentFolder.listFiles(new FileFilter() {
            public boolean accept(File file) {
                return file.isDirectory() && !".hg".equals(file.getName());
            }
        });

        for (int i = 0; i < filteredSubFolders.length; i++) {
            convert(filteredSubFolders[i]);
        }
    }

    private void writePomToDisk(Document pomXmlDoc, File directoryToWriteTo) {
//        XMLUtil.writeDocumentToDisk(pomXmlDoc, directoryToWriteTo, "pom.xml");
        XMLUtil.writeDocument(pomXmlDoc, directoryToWriteTo, "pom.xml");
    }
}