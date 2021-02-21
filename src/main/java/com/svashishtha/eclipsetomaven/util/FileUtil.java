package com.svashishtha.eclipsetomaven.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Path;
import java.util.List;

public class FileUtil {
    public static File searchFolder(String pathAtt, File workspaceRoot) {
        try {
        	String rootPath = workspaceRoot.getAbsolutePath();
        	if (pathAtt.startsWith("/"))
        		pathAtt = pathAtt.substring(1);
        	return findFolder(pathAtt, rootPath);
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

	private static File findFolder(String pathAtt, String rootPath) throws IOException {
		List<Path> files = new FindFile().find(rootPath, pathAtt);
		if(files == null || files.size() == 0){
			return null;
		}
		return files.get(0).toFile();
	}

    public static void copy(String fromFileName, String toFileName)
            throws IOException {
        File fromFile = new File(fromFileName);

        validateFromFile(fromFile);

        File toFile = new File(toFileName);
        if (toFile.isDirectory())
            toFile = new File(toFile, fromFile.getName());

        if (toFile.exists()) {
            shouldOverwrite(toFileName, toFile);
        } else {
            String parent = getParentPath(toFile);
            File dir = new File(parent);
            validateParentDir(dir);
        }

        copyFile(fromFile, toFile);
    }

	private static String getParentPath(File toFile) {
		String parent = toFile.getParent();
		if (parent == null)
		    parent = System.getProperty("user.dir");
		return parent;
	}

	private static void shouldOverwrite(String toFileName, File toFile) throws IOException {
		if (!toFile.canWrite())
		    throw new IOException("FileCopy: "
		            + "destination file is unwriteable: " + toFileName);
		BufferedReader in = new BufferedReader(new InputStreamReader(
		        System.in));
		if (!in.readLine().equalsIgnoreCase("Y"))
		    throw new IOException("FileCopy: "
		            + "existing file was not overwritten.");
	}

	private static void copyFile(File fromFile, File toFile) throws FileNotFoundException, IOException {
		FileInputStream from = null;
        FileOutputStream to = null;
        try {
            from = new FileInputStream(fromFile);
            to = new FileOutputStream(toFile);
            writeStreams(from, to);
        } finally {
            closeStreams(from, to);
        }
	}

	private static void writeStreams(FileInputStream from, FileOutputStream to) throws IOException {
		byte[] buffer = new byte[4096];
		int bytesRead;

		while ((bytesRead = from.read(buffer)) != -1)
		    to.write(buffer, 0, bytesRead); // write
	}

	private static void closeStreams(FileInputStream from, FileOutputStream to) {
		if (from != null)
		    try {
		        from.close();
		    } catch (IOException e) {
		        ;
		    }
		if (to != null){
		    try {
		        to.close();
		    } catch (IOException e) {
		        ;
		    }
		}
	}

	private static void validateParentDir(File dir) throws IOException {
		if (!dir.exists())
		    throw new IOException("FileCopy: "
		            + "destination directory doesn't exist: " + dir.getPath());
		if (dir.isFile())
		    throw new IOException("FileCopy: "
		            + "destination is not a directory: " + dir.getPath());
		if (!dir.canWrite())
		    throw new IOException("FileCopy: "
		            + "destination directory is unwriteable: " + dir.getPath());
	}

	private static void validateFromFile(File fromFile) throws IOException {
		if (!fromFile.exists())
            throw new IOException("FileCopy: " + "no such source file: "
                    + fromFile.getPath());
        if (!fromFile.canRead())
            throw new IOException("FileCopy: " + "source file is unreadable: "
                    + fromFile.getPath());
	}
    
    public static void appendToFile(String fileName, String lineToAppend) {
    	try {
			FileWriter fw = new FileWriter(fileName,true); 
			fw.write(lineToAppend+"\n");//appends the string to the file 
			fw.close();
		} catch (IOException e) {
			throw new IllegalStateException(e);
		} 
	}
}
