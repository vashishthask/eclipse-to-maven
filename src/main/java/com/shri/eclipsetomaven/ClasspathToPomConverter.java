package com.shri.eclipsetomaven;

import java.io.File;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.shri.eclipsetomaven.classpathentry.ClasspathConstants;
import com.shri.eclipsetomaven.util.XMLUtil;

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

        Element projectElement = createBasicPomStructure();
        addDependenciesFromClasspath(projectElement);
        return pomDoc;
    }

    private void addDependenciesFromClasspath(Element projectElement) {
        Element dependenciesElement = createDependencyManagementElement(projectElement);

        List<Element> classpathEntryElements = XMLUtil.getElements(
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

    private Element createBasicPomStructure() {
        Element projectElement = pomDoc.createElement("project");
        pomDoc.appendChild(projectElement);

        XMLUtil.addAttributeToElement(pomDoc, projectElement, "xmlns",
                "http://maven.apache.org/POM/4.0.0");
        XMLUtil.addAttributeToElement(pomDoc, projectElement, "xmlns:xsi",
                "http://www.w3.org/2001/XMLSchema-instance");
        XMLUtil.addAttributeToElement(pomDoc, projectElement,
                "xsi:schemaLocation",
                "http://maven.apache.org/POM/4.0.0 http://maven.apache.org/maven-v4_0_0.xsd");
        return projectElement;
    }
}