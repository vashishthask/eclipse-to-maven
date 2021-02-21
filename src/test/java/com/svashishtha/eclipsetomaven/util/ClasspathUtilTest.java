package com.svashishtha.eclipsetomaven.util;

import static org.junit.Assert.assertNotNull;

import java.io.File;

import org.junit.Ignore;
import org.junit.Test;

public class ClasspathUtilTest {

	private File classpathFile;

	@Test
	@Ignore
	public void testGetClasspathFile() {
		File folderToLook = new File("C:\\dev\\workspace\\hg\\hg-support\\LendNet\\Calculators Component\\Calculators");
		classpathFile = ClasspathUtil.getClasspathFile(folderToLook);
		assertNotNull(classpathFile);
	}

}
