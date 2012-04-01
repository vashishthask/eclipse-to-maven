package com.shri.eclipsetomaven;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

public class ClasspathToPomConverterTest {
	ClasspathToPomConverter converter;
	
	@Before
	public void setup(){
		converter = new ClasspathToPomConverter(null, null);
	}

	@Test
	public void testGetJarName() {
		String jarpath = "/LendNet/lib/audit-6.0.5-SNAPSHOT.jar";
		String jarName = converter.getJarName(jarpath);
		assertEquals("audit-6.0.5-SNAPSHOT.jar", jarName);
	}
	
	@Test
	public void testGetArtifactId() throws Exception {
		String jarName = "audit-6.0.5-SNAPSHOT.jar";
		String artifactId = converter.getArtifactId(jarName);
		System.out.println(artifactId);
		assertEquals("audit", artifactId);
	}
	
	@Test
	public void testGetJarVersion() throws Exception {
		String jarName = "audit-6.0.5-SNAPSHOT.jar";
		String jarVersion = converter.getJarVersion(jarName);
		assertEquals("6.0.5-SNAPSHOT", jarVersion);
	}
}
