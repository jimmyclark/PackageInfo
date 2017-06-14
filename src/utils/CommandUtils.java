package utils;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class CommandUtils {
	/**
	 * ִ���������
	 * �˺������������ж�
	 * @param commandStr �����������
	 * @return true ��ʾ�ɹ� 
	 * 		   false ��ʾʧ��
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
	 * ִ���������
	 * �˺������������ж�
	 * @param commandStr �����������
	 * @return true ��ʾ�ɹ� 
	 * 		   false ��ʾʧ��
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
