package com.shri.eclipsetomaven.pom;

import java.io.File;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.shri.eclipsetomaven.ApplicationPropertyConstants;
import com.shri.eclipsetomaven.ClasspathentryElementsProcessor;
import com.shri.eclipsetomaven.EclipseToMavenFoldersMover;
import com.shri.eclipsetomaven.classpathentry.ClasspathConstants;
import com.shri.eclipsetomaven.util.ApplicationConfig;
import com.svashishtha.dom.DomEditor;

public class ClasspathToPomConverter {

    private File workspaceRoot;
    private Document classpathDoc;
    private Document pomDoc;

    private File classpathRoot;

    public ClasspathToPomConverter(Document classpathDoc, File workspaceRoot,
            File classpathRoot) {
        this.classpathDoc = classpathDoc;
        this.workspaceRoot = workspaceRoot;
        this.classpathRoot = classpathRoot;
    }

    public Document createPomDoc() {
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db;
        try {
            db = dbf.newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            throw new IllegalStateException(e);
        }

        pomDoc = db.newDocument();

        BasicPomStructure basicPomStructure = new BasicPomStructure(pomDoc, classpathRoot);
        Element projectElement = basicPomStructure.createBasicPomStructure();
        
        addDependenciesFromClasspath(projectElement);
        if("true".equals(ApplicationConfig.INSTANCE.getValue(ApplicationPropertyConstants.CONVERT_TO_MAVEN))){
            handleWebContentIfAny();
        }
        return pomDoc;
    }

    private void handleWebContentIfAny() {
		File j2eeFile = new File(classpathRoot, ".j2ee");
		File webContentFolder = new File(classpathRoot, "WebContent");
		if(j2eeFile.exists() && webContentFolder.exists() && webContentFolder.isDirectory()){
			EclipseToMavenFoldersMover foldersMover = new EclipseToMavenFoldersMover();
			foldersMover.moveToSrcMainWebapp("WebContent", classpathRoot);
		}
	}

	private void addDependenciesFromClasspath(Element projectElement) {
        Element dependenciesElement = createDependencyManagementElement(projectElement);

        List<Element> classpathEntryElements = DomEditor.getElements(
                ClasspathConstants.CLASSPATHENTRY_TAG_NAME,
                classpathDoc.getDocumentElement());
        ClasspathentryElementsProcessor processor = new ClasspathentryElementsProcessor(
                pomDoc, workspaceRoot, classpathRoot);
        processor.process(dependenciesElement, classpathEntryElements);
    }

    private Element createDependencyManagementElement(Element projectElement) {
        Element dependencyManagementElement = pomDoc
                .createElement("dependencyManagement");
        Element dependenciesElement = pomDoc.createElement("dependencies");
        projectElement.appendChild(dependencyManagementElement);
        dependencyManagementElement.appendChild(dependenciesElement);
        return dependenciesElement;
    }
}