package com.shri.eclipsetomaven.util;

import static java.nio.file.StandardCopyOption.ATOMIC_MOVE;
import static java.nio.file.StandardCopyOption.COPY_ATTRIBUTES;
import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;

import java.io.IOException;
import java.nio.file.DirectoryNotEmptyException;
import java.nio.file.FileVisitOption;
import java.nio.file.FileVisitResult;
import java.nio.file.FileVisitor;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.util.EnumSet;

public class MoveTree implements FileVisitor {
	private final Path moveFrom;
	private final Path moveTo;
	static FileTime time = null;

	public MoveTree(Path moveFrom, Path moveTo) {
		this.moveFrom = moveFrom;
		this.moveTo = moveTo;
	}

	static void moveSubTree(Path moveFrom, Path moveTo) throws IOException {
		try {
			Files.move(moveFrom, moveTo, REPLACE_EXISTING, ATOMIC_MOVE);
		} catch (IOException e) {
			e.printStackTrace();
			System.err.println("Unable to move " + moveFrom + " [" + e + "]");
		}
	}

	@Override
	public FileVisitResult postVisitDirectory(Object dir, IOException exc)
			throws IOException {
		Path newdir = moveTo.resolve(moveFrom.relativize((Path) dir));
		try {
			Files.setLastModifiedTime(newdir, time);
			Files.delete((Path) dir);
		} catch (IOException e) {
			System.err.println("Unable to copy all attributes to: " + newdir
					+ " [" + e + "]");
		}
		return FileVisitResult.CONTINUE;
	}

	@Override
	public FileVisitResult preVisitDirectory(Object dir,
			BasicFileAttributes attrs) throws IOException {
		Path newdir = moveTo.resolve(moveFrom.relativize((Path) dir));
		try {
			Files.copy((Path) dir, newdir, REPLACE_EXISTING, COPY_ATTRIBUTES);
			time = Files.getLastModifiedTime((Path) dir);
		} catch(DirectoryNotEmptyException e){
			System.err.println("Unable to move+++ " + newdir + " [" + e + "]");
			
		} catch (IOException e) {
			e.printStackTrace();
			System.err.println("Unable to move " + newdir + " [" + e + "]");
			return FileVisitResult.SKIP_SUBTREE;
		}
		return FileVisitResult.CONTINUE;
	}

	@Override
	public FileVisitResult visitFile(Object file, BasicFileAttributes attrs)
			throws IOException {
		System.out.println("Move file: " + (Path) file);
		moveSubTree((Path) file,
				moveTo.resolve(moveFrom.relativize((Path) file)));
		return FileVisitResult.CONTINUE;
	}

	@Override
	public FileVisitResult visitFileFailed(Object file, IOException exc)
			throws IOException {
		return FileVisitResult.CONTINUE;
	}
	
	public static void main(String[] args) throws IOException {
        Path moveFrom = Paths.get("C:/rafaelnadal");
        Path moveTo = Paths.get("C:/ATP/players/rafaelnadal");
        MoveTree walk = new MoveTree(moveFrom, moveTo);
        EnumSet opts = EnumSet.of(FileVisitOption.FOLLOW_LINKS);
        Files.walkFileTree(moveFrom, opts, Integer.MAX_VALUE, walk);
    }
}
