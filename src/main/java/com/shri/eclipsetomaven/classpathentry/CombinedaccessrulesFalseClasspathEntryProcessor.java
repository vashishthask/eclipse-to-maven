package com.shri.eclipsetomaven.classpathentry;

import java.io.File;
import java.util.List;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.shri.eclipsetomaven.ClasspathentryElementsProcessor;
import com.shri.eclipsetomaven.util.ClasspathUtil;
import com.shri.eclipsetomaven.util.FileUtil;
import com.shri.eclipsetomaven.util.XMLUtil;

public class CombinedaccessrulesFalseClasspathEntryProcessor implements
        ClasspathEntryProcessor {

    private Document pomDoc;
    private File workspaceRoot;
    private File classpathRoot;

    @Override
    public void process(Element dependenciesElement,
            Element classpathEntryElement, File workspaceRoot, Document pomDoc,
            File classpathRoot) {
        setupParameters(workspaceRoot, pomDoc, classpathRoot);
        boolean exported = isExported(classpathEntryElement);
        includeReferencedProject(dependenciesElement, classpathEntryElement);
        if (exported) {
            includeLibrariesOfReferencedProject(dependenciesElement, classpathEntryElement);
        }
    }

    private void includeReferencedProject(Element dependenciesElement,
            Element classpathEntryElement) {
        LocalLibDependencyCreator dependencyCreator = new LocalLibDependencyCreator();
        dependencyCreator.createLocalLibDependency(classpathEntryElement, dependenciesElement, pomDoc);
    }

    private void includeLibrariesOfReferencedProject(
            Element dependenciesElement, Element classpathEntryElement) {
        File referencedProjectFolder = getReferencedProjectFolder(classpathEntryElement);
        if (referencedProjectFolder == null) {
            return;
        }
        handleClasspathOfReferencedProject(dependenciesElement,
                referencedProjectFolder);
    }

    private boolean isExported(Element classpathEntryElement) {
        String exported = classpathEntryElement
                .getAttribute(ClasspathConstants.EXPORTED);
        if ("true".equals(exported)) {
            return true;
        }
        return false;
    }

    private void handleClasspathOfReferencedProject(
            Element dependenciesElement, File referencedProjectFolder) {
        File classpathFile = ClasspathUtil
                .getClasspathFile(referencedProjectFolder);
        if (classpathFile != null) {
            Document classpathDoc = XMLUtil.getDocument(classpathFile);
            parseClasspathDoc(classpathDoc, dependenciesElement);
        }
    }

    private void setupParameters(File workspaceRoot, Document pomDoc,
            File classpathRoot) {
        this.pomDoc = pomDoc;
        this.workspaceRoot = workspaceRoot;
        this.classpathRoot = classpathRoot;
    }

    private File getReferencedProjectFolder(Element classpathEntryElement) {
        String pathAtt = classpathEntryElement
                .getAttribute(ClasspathConstants.PATH_ATTR);
        File folder = FileUtil.searchFolder(pathAtt, workspaceRoot);
        if (folder == null) {
            System.err.println("Could not find folder with path:" + pathAtt);
        }
        return folder;
    }

    private void parseClasspathDoc(Document classpathDoc,
            Element dependenciesElement) {
        List<Element> classpathEntriesElements = XMLUtil.getElements(
                ClasspathConstants.CLASSPATHENTRY_TAG_NAME,
                classpathDoc.getDocumentElement());
        ClasspathentryElementsProcessor processor = new ClasspathentryElementsProcessor(
                pomDoc, workspaceRoot, classpathRoot);
        processor.process(dependenciesElement, classpathEntriesElements);
    }
}