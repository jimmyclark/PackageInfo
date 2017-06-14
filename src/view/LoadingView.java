package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRootPane;
import javax.swing.border.EmptyBorder;

import utils.ViewUtils;

public class LoadingView extends JFrame{
	/**
	 *����Id
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * ��������
	 */
	private static final String TITLE_STR = "���ڽ��������Ժ򡣡���";
	/**
	 * Loading����Ŀ�� 
	 */
	private static final int SCREEN_WIDTH = 300;
	/**
	 * Loading����ĸ߶� 
	 */
	private static final int SCREEN_HEIGHT = 100;
	
	/**
	 * ����һ��Loading����
	 * @param mainView ��ҳ�������
	 */
	public LoadingView(){
		this.setTitle(TITLE_STR);
		this.setResizable(false);
		this.setSize(SCREEN_WIDTH,SCREEN_HEIGHT);
		this.setLocation(ViewUtils.setScreenCenterWidth(SCREEN_WIDTH),ViewUtils.setScreenCenterHeight(SCREEN_HEIGHT));
		this.setUndecorated(true);
		this.getRootPane().setWindowDecorationStyle(JRootPane.INFORMATION_DIALOG);
		this.addWindowListener(new WindowAdapter() {
			   public void windowClosing(WindowEvent e) {
				   ViewController.getInstance().cancelLoadingView();
			  }
		});
		initView();
	}
	
	public void initView(){
		this.setLayout(new BorderLayout());
		
		JPanel onePanel  = createTitlePanel();
		this.add(onePanel,BorderLayout.CENTER);
		
	}
	
	private JPanel createTitlePanel() {
		JPanel textViewPanel = new JPanel();
		textViewPanel.setLayout(new BorderLayout());
		textViewPanel.setBorder(new EmptyBorder(10,10,10,10));
		JLabel titleLabel = new JLabel(TITLE_STR);
		titleLabel.setFont(new Font("����",Font.BOLD,20));
		titleLabel.setForeground(Color.BLUE);
		textViewPanel.add(titleLabel, BorderLayout.CENTER);
		return textViewPanel;
	}
}
