package utils;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class CommandUtils {
	/**
	 * 执行命令语句
	 * 此后的所有命令将被中断
	 * @param commandStr 命令语句内容
	 * @return true 表示成功 
	 * 		   false 表示失败
	 */
	public static boolean exeCmd(String commandStr) {
		BufferedReader br = null;
		try {
			Process p = Runtime.getRuntime().exec(commandStr);
			br = new BufferedReader(new InputStreamReader(p.getInputStream()));
			String line = null;
			StringBuilder sb = new StringBuilder();
			while ((line = br.readLine()) != null) {
				sb.append(line + "\n");
			}

			FileUtils.writeString("alog.txt", sb.toString());
			
			p.waitFor();

		} catch (Exception e) {
			e.printStackTrace();
			return false;
		} finally {
			if (br != null) {
				try {
					br.close();
					return true;
				} catch (Exception e) {
					e.printStackTrace();
					return false;
				}
			}
		}
		
		return false;
	}
	
	/**
	 * 执行命令语句
	 * 此后的所有命令将被中断
	 * @param commandStr 命令语句内容
	 * @return true 表示成功 
	 * 		   false 表示失败
	 */
	public static String exeCmdForString(String commandStr) {
		BufferedReader br = null;
		try {
			Process p = Runtime.getRuntime().exec(commandStr);
			br = new BufferedReader(new InputStreamReader(p.getInputStream()));
			String line = null;
			StringBuilder sb = new StringBuilder();
			while ((line = br.readLine()) != null) {
				sb.append(line + "\n");
			}
			
			p.waitFor();
			
			return sb.toString();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			if (br != null) {
				try {
					br.close();
					return null;
				} catch (Exception e) {
					e.printStackTrace();
					return null;
				}
			}
		}
	}
}
