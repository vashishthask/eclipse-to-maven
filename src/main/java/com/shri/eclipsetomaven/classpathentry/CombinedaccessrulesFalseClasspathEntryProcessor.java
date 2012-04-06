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
        this.pomDoc = pomDoc;
        this.workspaceRoot = workspaceRoot;
        this.classpathRoot = classpathRoot;
        String pathAtt = classpathEntryElement
                .getAttribute(ClasspathConstants.PATH_ATTR);
        File pathFolder = FileUtil.searchFolder(pathAtt, workspaceRoot);
        if(pathFolder == null){
        	System.err.println("Could not find folder with path:"+pathAtt);
        	return;
        }
        File classpathFile = ClasspathUtil.getClasspathFile(pathFolder);
        if (classpathFile != null) {
            Document classpathDoc = XMLUtil.getDocument(classpathFile);
            parseClasspathDoc(classpathDoc, dependenciesElement);
        }
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