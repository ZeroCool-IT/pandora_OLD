/**
 * File engine.utility/ZipUtility.java
 * @author Marco Battisti - 0207845
 * Corso Mobile Programming 2013/2014
 * TV Series Android App Project
 */

package engine.utility;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import android.content.Context;

/**
 * Utility class for managing zip files
 * 
 * @author Marco Battisti
 * 
 */
public class ZipUtility {

	/**
	 * Unpack the given InputStream, representing ZIP file, to app cache dir
	 * 
	 * @param context
	 *            is the context of the application
	 * @param s
	 *            is the series, for creating a dir named as its ID
	 * @param is
	 *            is the InputStream representig the zip file to unpack
	 * @return true if operation is successful, false otherwise
	 */
	public static boolean unpackZip(Context context, String s, InputStream is) {

		ZipInputStream zis;
		try {
			String filename;
			File cache = context.getCacheDir();
			File dir = new File(cache.getAbsolutePath() + "/" + s);
			dir.mkdir();
			zis = new ZipInputStream(new BufferedInputStream(is));
			ZipEntry ze;
			byte[] buffer = new byte[1024];
			int count;

			while ((ze = zis.getNextEntry()) != null) {
				filename = ze.getName();

				// Need to create directories if not exists, or
				// it will generate an Exception...
				if (ze.isDirectory()) {
					File fmd = new File(dir.getAbsolutePath() + filename);
					fmd.mkdirs();
					continue;
				}

				FileOutputStream fout = new FileOutputStream(
						dir.getAbsolutePath() + "/" + filename);

				while ((count = zis.read(buffer)) != -1) {
					fout.write(buffer, 0, count);
				}

				fout.close();
				zis.closeEntry();
			}

			zis.close();
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}

		return true;
	}

	/**
	 * Convert an InputStream to String
	 * 
	 * @param is
	 *            the given InputStrem
	 * @return a String
	 * @throws IOException
	 *             if operation fails
	 */
	public static String convertStreamToString(InputStream is)
			throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(is));
		StringBuilder sb = new StringBuilder();
		String line = null;
		while ((line = reader.readLine()) != null) {
			sb.append(line).append("\n");
		}
		reader.close();
		return sb.toString();
	}

	/**
	 * Get a String from a file
	 * 
	 * @param filePath
	 *            is the path of the file
	 * @return a String representing the file
	 * @throws IOException
	 *             if operation fails
	 */
	public static String getStringFromFile(String filePath) throws IOException {
		File fl = new File(filePath);
		FileInputStream fin = new FileInputStream(fl);
		String ret = convertStreamToString(fin);
		// Make sure you close all streams.
		fin.close();
		return ret;
	}

}
