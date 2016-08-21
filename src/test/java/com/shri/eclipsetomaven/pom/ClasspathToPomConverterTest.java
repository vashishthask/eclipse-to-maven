package com.shri.eclipsetomaven.pom;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.svashishtha.dom.DomEditor;
import com.svashishtha.dom.DomParser;

public class ClasspathToPomConverterTest {
	ClasspathToPomConverter converter;
	
	@Rule
	public TemporaryFolder tempFolder = new TemporaryFolder();

	private File newFolder;
	
	@Before
	public void setup() throws IOException {
		newFolder = tempFolder.newFolder();

	}
	
	@Test
	public void testJarNamesWithoutVersion() throws Exception {
		Document classpathDoc = DomParser.parse(this.getClass().getResourceAsStream("/classpath-sample2.xml"));
		assertNotNull(classpathDoc);
		converter = new ClasspathToPomConverter(classpathDoc, null, newFolder);
		Document pomDoc = converter.createPomDoc();
		List<Element> elements = DomEditor.getElements("dependency", pomDoc.getDocumentElement());
		boolean elementFound = false;
		for (Element element : elements) {
			String artifactId = DomEditor.getTagValue(element, "artifactId");
			String jarVersion = DomEditor.getTagValue(element, "version");
			if("junit".equals(artifactId)){
				elementFound = true;
				assertEquals("", jarVersion);
			}
		}
		assertTrue(elementFound);
	}
}