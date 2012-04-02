package com.shri.eclipsetomaven;

import static org.junit.Assert.assertEquals;

import java.io.File;

import org.junit.Before;
import org.junit.Test;

public class ClasspathToPomConverterTest {
	WorkspaceClasspathToPomConverter converter;
	
//	@Before
//	public void setup(){
//		converter = new WorkspaceClasspathToPomConverter(null, null);
//	}
//
//	@Test
//	public void testGetJarName() {
//		String jarpath = "/LendNet/lib/audit-6.0.5-SNAPSHOT.jar";
//		String jarName = converter.getJarName(jarpath);
//		assertEquals("audit-6.0.5-SNAPSHOT.jar", jarName);
//	}
//	
//	@Test
//	public void testGetArtifactId() throws Exception {
//		String jarName = "audit-6.0.5-SNAPSHOT.jar";
//		String artifactId = converter.getArtifactId(jarName);
//		System.out.println(artifactId);
//		assertEquals("audit", artifactId);
//	}
//	
//	@Test
//	public void testGetJarVersion() throws Exception {
//		String jarName = "audit-6.0.5-SNAPSHOT.jar";
//		String jarVersion = converter.getJarVersion(jarName);
//		assertEquals("6.0.5-SNAPSHOT", jarVersion);
//	}
//	
//	@Test
//	public void testSearchFolder() throws Exception {
//		String relativePath = "/LendNet Libraries Server";
//		File workspaceRoot = new File("C:\\dev\\workspace\\hg\\hg-support\\LendNet");
//		File folder = converter.searchFolder(relativePath, workspaceRoot);
//		System.out.println(folder.getAbsolutePath());
//	}
}
