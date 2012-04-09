package com.shri.eclipsetomaven;

import static org.junit.Assert.assertNotNull;

import java.io.File;

import org.junit.Ignore;
import org.junit.Test;

import com.shri.eclipsetomaven.util.ClasspathUtil;

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
