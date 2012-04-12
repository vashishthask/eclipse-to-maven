package com.shri.eclipsetomaven;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileVisitOption;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.EnumSet;

import com.shri.eclipsetomaven.util.MoveTree;

public class EclipseToMavenFoldersMover {
	
	public void moveToSrcTestJava(String pathAttribute, File classpathRoot) {
		moveSources(pathAttribute, classpathRoot, "src/test/java");
	}

	public void moveToSrcMainJava(String pathAttribute, File classpathRoot) {
		moveSources(pathAttribute, classpathRoot, "src/main/java");
	}

	public void moveToSrcMainResources(String pathAttribute, File classpathRoot) {
		moveSources(pathAttribute, classpathRoot, "src/main/resources");
	}
	
	public void moveToSrcMainWebapp(String pathAttribute, File classpathRoot) {
		moveSources(pathAttribute, classpathRoot, "src/main/webapp");
	}

	private void moveSources(String pathAttribute, File classpathRoot,
			String destinationFolderName) {
		File destinationFolder = new File(classpathRoot, destinationFolderName);
		if (!destinationFolder.exists()) {
			destinationFolder.mkdirs();
		}
		File sourceDirectory = new File(classpathRoot, pathAttribute);
		Path moveFromPath = Paths.get(sourceDirectory.getAbsolutePath());
		Path moveToPath = Paths.get(destinationFolder.getAbsolutePath());
		if (moveToPath.startsWith(moveFromPath)) {
			handleSrcParentOfDestination(moveFromPath, moveToPath);
		} else {
			moveFiles(moveFromPath, moveToPath);
		}
	}

	private void handleSrcParentOfDestination(Path moveFromPath, Path moveToPath) {
		File [] moveFromFileChildren = moveFromPath.toFile().listFiles();
		File moveToPathParent = moveToPath.getParent().toFile();
		for (int i = 0; i < moveFromFileChildren.length; i++) {
			File moveFromFileChild = moveFromFileChildren[i];
			if(moveFromFileChild.getAbsolutePath().equals(moveToPathParent.getAbsolutePath())){
				continue;
			} else {
				Path modifiedMoveToPath = moveToPath.resolve(moveFromFileChild.getName());
				moveFiles(moveFromFileChild.toPath(), modifiedMoveToPath);
			}
		}
	}

	@SuppressWarnings("unchecked")
	private void moveFiles(Path moveFrom, Path moveTo) {
		MoveTree walk = new MoveTree(moveFrom, moveTo);
		EnumSet<FileVisitOption> opts = EnumSet.of(FileVisitOption.FOLLOW_LINKS);
		try {
			Files.walkFileTree(moveFrom, opts, Integer.MAX_VALUE, walk);
		} catch (IOException e) {
			throw new IllegalStateException(e);
		}
	}

}
