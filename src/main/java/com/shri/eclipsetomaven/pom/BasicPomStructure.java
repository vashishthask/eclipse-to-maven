package com.shri.eclipsetomaven.pom;

import java.io.File;

import org.apache.commons.lang.StringUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.shri.eclipsetomaven.classpathentry.ClasspathConstants;
import com.shri.eclipsetomaven.util.XMLUtil;

public class BasicPomStructure {
    
    private final Document pomDoc;
    private final File classpathRoot;

    public BasicPomStructure(Document pomDoc, File classpathRoot) {
        super();
        this.pomDoc = pomDoc;
        this.classpathRoot = classpathRoot;
    }


    
    public Element createBasicPomStructure() {
        Element projectElement = pomDoc.createElement("project");
        pomDoc.appendChild(projectElement);
        addAttributesToProjectElement(projectElement);
        addDefaultElementsToProjectElement(projectElement);
        return projectElement;
    }

    private void addAttributesToProjectElement(Element projectElement) {
        XMLUtil.addAttributeToElement(pomDoc, projectElement, PomConstants.ATT_XMLNS,
                PomConstants.ATT_VALUE_XMLNS);
        XMLUtil.addAttributeToElement(pomDoc, projectElement, PomConstants.ATT_XMLNS_XSI,
                PomConstants.ATT_VALUE_XMLNS_XSI);
        XMLUtil.addAttributeToElement(pomDoc, projectElement,
                PomConstants.ATT_XSI_SCHEMA_LOCATION,
                PomConstants.ATT_VALUE_XSI_SCHEMA_LOCATION);
    }

    private void addDefaultElementsToProjectElement(Element projectElement) {
        addModelVersion(projectElement);
        addArtifactId(projectElement);
        addPackaging(projectElement);
        addName(projectElement);
    }


    private void addName(Element projectElement) {
        Element nameElement = pomDoc.createElement(PomConstants.ELT_NAME);
        projectElement.appendChild(nameElement);
        nameElement.appendChild(pomDoc.createTextNode(classpathRoot.getName()));
    }


    private void addPackaging(Element projectElement) {
        String packaging = PomConstants.PACKAGING_JAR;
        File j2eeFile = new File(classpathRoot, ClasspathConstants.FILE_J2EE);
        File webContent = new File(classpathRoot, ClasspathConstants.DIR_WEB_CONTENT);
        if(j2eeFile.exists() && webContent.exists()){
            packaging = PomConstants.PACKAGING_WAR;
        }
        Element packagingElement = pomDoc.createElement(PomConstants.ELT_PACKAGING);
        projectElement.appendChild(packagingElement);
        packagingElement.appendChild(pomDoc.createTextNode(packaging));
    }


    private void addArtifactId(Element projectElement) {
        Element nameElement = pomDoc.createElement(PomConstants.ARTIFACT_ID);
        projectElement.appendChild(nameElement);
        String directoryName = classpathRoot.getName();
        String artifactId = StringUtils.remove(directoryName, ' ');
        nameElement.appendChild(pomDoc.createTextNode(artifactId));
    }

    private void addModelVersion(Element projectElement) {
        Element modelVersionElement = pomDoc.createElement(PomConstants.ELT_MODEL_VERSION);
        projectElement.appendChild(modelVersionElement);
        modelVersionElement.appendChild(pomDoc.createTextNode(PomConstants.MODEL_VERSION_VALUE));        
    }
}