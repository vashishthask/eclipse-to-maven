package com.shri.eclipsetomaven.util;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import java.io.IOException;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import org.w3c.dom.Document;

public class XMLUtilTest {
	
	@Rule
	public TemporaryFolder tempFolder = new TemporaryFolder();

	@Test
	public void testGetDocumentString() {
		String xmlString = "<?xml version=\"1.0\"?><catalog><book id=\"bk101\"><author>Gambardella, Matthew</author><title>XML Developer's Guide</title><genre>Computer</genre><price>44.95</price><publish_date>2000-10-01</publish_date><description>An in-depth look at creating applications with XML.</description></book></catalog>";
		Document doc = XMLUtil.getDocument(xmlString);
		assertNotNull(doc);
	}
	
	@Test
	public void testWriteDocumentToDisk() {
		String xmlString = "<?xml version=\"1.0\"?><catalog><book id=\"bk101\"><author>Gambardella, Matthew</author><title>XML Developer's Guide</title><genre>Computer</genre><price>44.95</price><publish_date>2000-10-01</publish_date><description>An in-depth look at creating applications with XML.</description></book></catalog>";
		Document doc = XMLUtil.getDocument(xmlString);
		try {
			XMLUtil.writeDocumentToDisk(doc, tempFolder.newFolder(), "sample.xml");
		} catch (IOException e) {
			fail(e.getMessage());
		}
	}
}