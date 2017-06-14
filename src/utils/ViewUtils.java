package utils;

import java.awt.Dimension;
import java.awt.Toolkit;

public class ViewUtils {
	
	/**
	 * ��ȡ��Ļ��ȵķ���
	 * no parameters
	 * @return ��Ļ���   int
	 */
	public static int getScreenWidth(){
		//��ȡ��Ļ��С
	    Dimension screenSize =Toolkit.getDefaultToolkit().getScreenSize();
	    return screenSize.width;
	}
	
	/**
	 * ��ȡ��Ļ�߶ȵķ���
	 * no parameters
	 * @return ��Ļ�߶�   int
	 */
	public static int getScreenHeight(){
		//��ȡ��Ļ��С
	    Dimension screenSize =Toolkit.getDefaultToolkit().getScreenSize();
	    return screenSize.height;
	}
	
	/**
	 * ��ȡĳ��������Ҫ��������Ļ����Ŀ��
	 * @param view_width ĳ������Ŀ��
	 * @return ��Ҫ��������Ļ����Ŀ��
	 */
	public static int setScreenCenterWidth(int view_width){
		return (getScreenWidth()-view_width)/2;
	}
	
	/**
	 * ��ȡĳ��������Ҫ��������Ļ����ĸ߶�
	 * @param view_height ĳ������ĸ߶�
	 * @return ��Ҫ��������Ļ����ĸ߶�
	 */
	public static int setScreenCenterHeight(int view_height){
		return (getScreenHeight()-view_height)/2;
	}
}