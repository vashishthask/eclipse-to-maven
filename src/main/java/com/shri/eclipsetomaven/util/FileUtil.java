package com.shri.eclipsetomaven.util;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

public class FileUtil {
	public static File searchFolder(String pathAtt, File workspaceRoot2) {
		FindFile findFile = new FindFile();
		String rootPath = workspaceRoot2.getAbsolutePath();
		if (pathAtt.startsWith("/"))
			pathAtt = pathAtt.substring(1);

		List<Path> files = null;

		try {
			files = findFile.find(rootPath, pathAtt);
		} catch (IOException e) {
			throw new IllegalStateException(e);
		}
		return files.get(0).toFile();
	}

}
