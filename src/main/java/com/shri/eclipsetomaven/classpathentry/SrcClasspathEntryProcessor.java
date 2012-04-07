package com.shri.eclipsetomaven.classpathentry;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileVisitOption;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.EnumSet;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.shri.eclipsetomaven.util.MoveTree;

public class SrcClasspathEntryProcessor implements ClasspathEntryProcessor {

	@Override
	public void process(Element dependenciesElement,
			Element classpathEntryElement, File workspaceRoot, Document pomDoc,
			File classpathRoot) {
		String pathAttribute = classpathEntryElement
				.getAttribute(ClasspathConstants.PATH_ATTR);
		if ("Resources".equals(pathAttribute)
				|| "Properties".equals(pathAttribute)) {
			moveToSrcMainResources(pathAttribute, classpathRoot);
		} else {
			moveToSrcMainJava(pathAttribute, classpathRoot);
		}
	}

	void moveToSrcMainJava(String pathAttribute, File classpathRoot) {
		moveSources(pathAttribute, classpathRoot, "src/main/java");
	}

	void moveToSrcMainResources(String pathAttribute, File classpathRoot) {
		moveSources(pathAttribute, classpathRoot, "src/main/resources");
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

	private void moveFiles(Path moveFrom, Path moveTo) {
		MoveTree walk = new MoveTree(moveFrom, moveTo);
		EnumSet opts = EnumSet.of(FileVisitOption.FOLLOW_LINKS);
		try {
			Files.walkFileTree(moveFrom, opts, Integer.MAX_VALUE, walk);
		} catch (IOException e) {
			throw new IllegalStateException(e);
		}
	}
}