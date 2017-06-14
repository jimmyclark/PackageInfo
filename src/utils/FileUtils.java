package utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;

public class FileUtils {
	public static String getCurrentLocation() {
		String path = FileUtils.class.getProtectionDomain().getCodeSource().getLocation().getFile();
		try {
			path = java.net.URLDecoder.decode(path, "UTF-8"); // 转换处理中文及空格
		} catch (java.io.UnsupportedEncodingException e) {
			return null;
		}
		
		if(path.startsWith("/")){
			path = path.substring(1);
		}
			
		if(path.endsWith(".jar")){
			path = path.substring(0, path.lastIndexOf("/") + 1);
		}
		
		return path;
	}

	/**
	 * @param fileName
	 * @return long
	 */
	public static long getFileSize(String fileName) {
		File file = new File(fileName);
		if (!file.exists())
			return 0;
		return file.length();
	}

	/**
	 * 删除文件夹的方法
	 * 
	 * @param sPath
	 *            文件路径
	 * @return true 文件删除成功 false 文件删除失败
	 */
	public static boolean deleteDirectory(String sPath) {
		if (!sPath.endsWith("/")) {
			sPath += "/";
		}

		File dirFile = new File(sPath);
		if (!dirFile.exists() || !dirFile.isDirectory()) {
			return false;
		}
		boolean flag = true;
		File[] files = dirFile.listFiles();
		for (int i = 0; i < files.length; i++) {
			if (files[i].isFile()) {
				flag = deleteFile(files[i].getAbsolutePath());
				if (!flag)
					break;
			} else {
				flag = deleteDirectory(files[i].getAbsolutePath());
				if (!flag)
					break;
			}
		}
		if (!flag)
			return false;
		// 删除当前目录
		if (dirFile.delete()) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 删除单个文件
	 * 
	 * @param sPath
	 *            文件路径
	 * @return true 文件删除成功 false 文件删除失败
	 */
	private static boolean deleteFile(String sPath) {
		boolean flag = false;
		File file = new File(sPath);
		// 路径为文件且不为空则进行删除
		if (file.exists() && file.isFile()) {
			file.delete();
			flag = true;
		}
		return flag;
	}

	/**
	 * @param fileName
	 * @return
	 */
	public static String readFileByLines(String fileName) {
		StringBuilder sb = new StringBuilder();
		File file = new File(fileName);
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new FileReader(file));
			String tempString = null;
			// 一次读入一行，直到读入null为文件结束
			while ((tempString = reader.readLine()) != null) {
				// 显示行号
				sb.append(tempString + "###");
			}
			reader.close();

		} catch (IOException e) {
			e.printStackTrace();
			return null;

		} finally {
			if (reader != null) {
				try {
					reader.close();
					return sb.toString();
				} catch (IOException e1) {
					return null;
				}
			}
		}
		return sb.toString();
	}

	public static boolean writeString(String fileName, String str) {
		try {
			File file = new File(fileName);
			if (!file.exists())
				file.createNewFile();
			FileOutputStream out = new FileOutputStream(file, true);
			OutputStreamWriter ow = new OutputStreamWriter(out, "utf-8");
			// StringBuffer sb = new StringBuffer();
			// sb.append(str);
			ow.write(str + "\n");
			ow.close();
			out.close();
			return true;
		} catch (IOException ex) {
			return false;
		}
	}
}
