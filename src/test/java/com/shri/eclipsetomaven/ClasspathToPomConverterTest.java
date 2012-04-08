package com.shri.eclipsetomaven;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertTrue;

import java.util.List;

import org.junit.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.shri.eclipsetomaven.util.XMLUtil;

public class ClasspathToPomConverterTest {
	ClasspathToPomConverter converter;
	
	@Test
	public void testJarNamesWithoutVersion() throws Exception {
		Document classpathDoc = XMLUtil.getDocument(this.getClass().getResourceAsStream("/classpath-sample2.xml"));
		assertNotNull(classpathDoc);
		converter = new ClasspathToPomConverter(classpathDoc, null, null);
		Document pomDoc = converter.createPomDoc();
		List<Element> elements = XMLUtil.getElements("dependency", pomDoc.getDocumentElement());
		boolean elementFound = false;
		for (Element element : elements) {
			String artifactId = XMLUtil.getTagValue(element, "artifactId");
			String jarVersion = XMLUtil.getTagValue(element, "jarVersion");
			if("junit".equals(artifactId)){
				elementFound = true;
				assertEquals("", jarVersion);
			}
		}
		assertTrue(elementFound);
	}
}