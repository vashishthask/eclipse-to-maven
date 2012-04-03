package com.shri.eclipsetomaven;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class FileUtil {
	/**
	 * This function will copy files or directories from one location to another.
	 * note that the source and the destination must be mutually exclusive. This 
	 * function can not be used to copy a directory to a sub directory of itself.
	 * The function will also have problems if the destination files already exist.
	 * @param src -- A File object that represents the source for the copy
	 * @param dest -- A File object that represnts the destination for the copy.
	 * @throws IOException if unable to copy.
	 */
	public static void copyFiles(File src, File dest) throws IOException {
		//Check to ensure that the source is valid...
		if (!src.exists()) {
			throw new IOException("copyFiles: Can not find source: " + src.getAbsolutePath()+".");
		} else if (!src.canRead()) { //check to ensure we have rights to the source...
			throw new IOException("copyFiles: No right to source: " + src.getAbsolutePath()+".");
		}
		//is this a directory copy?
		if (src.isDirectory()) 	{
			if (!dest.exists()) { //does the destination already exist?
				//if not we need to make it exist if possible (note this is mkdirs not mkdir)
				if (!dest.mkdirs()) {
					throw new IOException("copyFiles: Could not create direcotry: " + dest.getAbsolutePath() + ".");
				}
			}
			//get a listing of files...
			String list[] = src.list();
			//copy all the files in the list.
			for (int i = 0; i < list.length; i++)
			{
				File dest1 = new File(dest, list[i]);
				File src1 = new File(src, list[i]);
				copyFiles(src1 , dest1);
			}
		} else { 
			//This was not a directory, so lets just copy the file
			FileInputStream fin = null;
			FileOutputStream fout = null;
			byte[] buffer = new byte[4096]; //Buffer 4K at a time (you can change this).
			int bytesRead;
			try {
				//open the files for input and output
				fin =  new FileInputStream(src);
				fout = new FileOutputStream (dest);
				//while bytesRead indicates a successful read, lets write...
				while ((bytesRead = fin.read(buffer)) >= 0) {
					fout.write(buffer,0,bytesRead);
				}
			} catch (IOException e) { //Error copying file... 
				IOException wrapper = new IOException("copyFiles: Unable to copy file: " + 
							src.getAbsolutePath() + "to" + dest.getAbsolutePath()+".");
				wrapper.initCause(e);
				wrapper.setStackTrace(e.getStackTrace());
				throw wrapper;
			} finally { //Ensure that the files are closed (if they were open).
				if (fin != null) { fin.close(); }
				if (fout != null) { fout.close(); }
			}
		}
	}
}
