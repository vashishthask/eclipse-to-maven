package com.svashishtha.eclipsetomaven.classpathentry;

import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertTrue;

import java.util.List;

import org.junit.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.svashishtha.dom.DomEditor;
import com.svashishtha.dom.DomParser;

public class ClasspathEntryProcessorFactoryTest {
    @Test
    public void shouldCreateCombinedAccessRulesFalseClasspathEntryProcessor(){
    	Document classpathDoc = DomParser
    			.parseXmlString("<classpath><classpathentry combineaccessrules=\"false\" kind=\"src\" path=\"/LendNet Libraries Server\"/></classpath>");
    	List <Element> elements = DomEditor.getElements("classpathentry", classpathDoc.getDocumentElement());
        ClasspathEntryProcessor processor = ClasspathEntryProcessorFactory
        		.create(elements.get(0));
        assertNotNull(processor);
        assertTrue(processor instanceof CombinedaccessrulesFalseClasspathEntryProcessor);
    }


    @Test
    public void shouldCreateLibExportedTrueClasspathEntryProcessor(){
    	Document classpathDoc = DomParser
    			.parseXmlString("<classpath><classpathentry kind=\"lib\" exported=\"true\" path=\"/Legal DB Web App/activation.jar\"/></classpath>");
    	List <Element> elements = DomEditor.getElements("classpathentry", classpathDoc.getDocumentElement());
        ClasspathEntryProcessor processor = ClasspathEntryProcessorFactory
        		.create(elements.get(0));
        assertNotNull(processor);
        assertTrue(processor instanceof LibExportedTrueClasspathEntryProcessor);
    }

    @Test
    public void shouldCreateLibClasspathEntryProcessor(){
    	Document classpathDoc = DomParser
    			.parseXmlString("<classpath><classpathentry kind=\"lib\" path=\"/Legal DB Web App/activation.jar\"/></classpath>");
    	List <Element> elements = DomEditor.getElements("classpathentry", classpathDoc.getDocumentElement());
        ClasspathEntryProcessor processor = ClasspathEntryProcessorFactory
        		.create(elements.get(0));
        assertNotNull(processor);
        assertTrue(processor instanceof LibClasspathEntryProcessor);
    }

    @Test
    public void shouldCreateSrcClasspathEntryProcessor(){
    	Document classpathDoc = DomParser
    			.parseXmlString("<classpath><classpathentry kind=\"src\" path=\"Resources\"/></classpath>");
    	List <Element> elements = DomEditor.getElements("classpathentry", classpathDoc.getDocumentElement());
        ClasspathEntryProcessor processor = ClasspathEntryProcessorFactory
        		.create(elements.get(0));
        assertNotNull(processor);
        assertTrue(processor instanceof SrcClasspathEntryProcessor);
    }

    @Test
    public void shouldCreateSrcWithLocalLibClasspathEntryProcessor(){
    	Document classpathDoc = DomParser
    			.parseXmlString("<classpath><classpathentry exported=\"true\" kind=\"src\" path=\"/Legal DB Server\"/></classpath>");
    	List <Element> elements = DomEditor.getElements("classpathentry", classpathDoc.getDocumentElement());
        ClasspathEntryProcessor processor = ClasspathEntryProcessorFactory
        		.create(elements.get(0));
        assertNotNull(processor);
        assertTrue(processor instanceof SrcWithLocalLibClasspathEntryProcessor);
      
    }
}
