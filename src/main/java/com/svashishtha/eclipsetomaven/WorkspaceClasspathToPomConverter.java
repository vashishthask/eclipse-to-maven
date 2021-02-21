package com.svashishtha.eclipsetomaven;

import java.io.File;
import java.io.FileFilter;
import java.util.List;

import com.svashishtha.eclipsetomaven.pom.ClasspathToPomConverter;
import com.svashishtha.eclipsetomaven.pom.PomConstants;
import com.svashishtha.eclipsetomaven.util.ApplicationConfig;
import com.svashishtha.eclipsetomaven.util.ClasspathUtil;
import com.svashishtha.eclipsetomaven.util.FileUtil;
import org.apache.commons.lang.StringUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.svashishtha.dom.DomEditor;
import com.svashishtha.dom.DomParser;
import com.svashishtha.dom.io.PrettyPrinter;

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
            Document pomXmlDoc = createPomXmlDoc(classpathFile);
            printDependencies(pomXmlDoc);
            if ("true".equals(ApplicationConfig.INSTANCE
                    .getValue(ApplicationPropertyConstants.CONVERT_TO_MAVEN))) {
                writePomToDisk(pomXmlDoc, classpathFile.getParentFile());
            }
        }
    }

    private void printDependencies(Document pomXmlDoc) {
        if (!"true".equals(ApplicationConfig.INSTANCE
                .getValue(ApplicationPropertyConstants.PRINT_DEPENDENCY_GRAPH)))
            return;
        Element documentRoot = pomXmlDoc.getDocumentElement();
        String projectName = DomEditor.getTagValue(documentRoot, PomConstants.NAME);
        print(projectName);
        print("=========================");
        List<Element> dependencies = DomEditor.getElements(PomConstants.DEPENDENCY,
                documentRoot);
        for (Element dependency : dependencies) {
            String dependencyName = DomEditor.getTagValue(dependency,
                    PomConstants.ARTIFACT_ID);
            String versionNumber = DomEditor.getTagValue(documentRoot,
                    PomConstants.VERSION);
            print("    |--" + dependencyName + "--" + versionNumber);
        }
        print("\n\n");
    }

    private void print(String str) {
        String printIoType = ApplicationConfig.INSTANCE
                .getValue(ApplicationPropertyConstants.PRINT_DEPENDENCY_GRAPH_IOTYPE);
        if (ApplicationPropertyConstants.PRINT_DEPENDENCY_GRAPH_IOTYPE_CONSOLE.equals(printIoType)) {
            System.out.println(str);
        } else if (ApplicationPropertyConstants.PRINT_DEPENDENCY_GRAPH_IOTYPE_FILE.equals(printIoType)) {
            String fileName = ApplicationConfig.INSTANCE
                    .getValue(ApplicationPropertyConstants.PRINT_DEPENDENCY_GRAPH_FILEPATH);
            if (StringUtils.isNotEmpty(fileName)) {
                FileUtil.appendToFile(fileName, str);
            }
        }
    }

    private Document createPomXmlDoc(File classpathFile) {
        File classpathRoot = classpathFile.getParentFile();
        Document classpathDoc = DomParser.getDocument(classpathFile);
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
        PrettyPrinter.printToFile(pomXmlDoc, directoryToWriteTo, "pom.xml");
    }
}