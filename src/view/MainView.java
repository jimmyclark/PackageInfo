package view;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;

import utils.APKUtils;
import utils.FileUtils;
import utils.ViewUtils;

public class MainView extends JFrame {

	private static final long serialVersionUID = 1L;
	private static final int SCREEN_WIDTH = 1280;
	private static final int SCREEN_HEIGHT = 720;

	private JTextField fileField;
	private JTable table;
	private DefaultTableModel model;
	private HashMap<String, Boolean> hasValueMap;
	private HashMap<String, JLabel> labelMapList;
	private ArrayList<String> allApkFiles;

	private int[] counts;

	private static final String[] HEADER = { "序号", "文件", "名字", "icon", "包名", "版本号", "内部版本", "API", "fb appid",
			"fb hashkey", "fb name", "Google billing", "blue pay" };

	public MainView() {
		this.setTitle("获取包体信息");
		this.setLocation(ViewUtils.getScreenWidth() / 2 - SCREEN_WIDTH / 2,
				ViewUtils.getScreenHeight() / 2 - SCREEN_HEIGHT / 2);
		this.setSize(SCREEN_WIDTH, SCREEN_HEIGHT);
		this.setVisible(true);
		this.setResizable(false);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		this.initView();
	}

	public void initView() {
		JPanel titlePanel = initTitlePanel();
		this.add(titlePanel, BorderLayout.NORTH);

		JPanel contentPanel = initContentPanel();
		this.add(contentPanel, BorderLayout.CENTER);
	}

	private JPanel initContentPanel() {
		JPanel panel = new JPanel();
		// Object [][] cellData = {{"c_1","c2"},{"c3","c4"}};
		// String []columnNames = {"col1","col2"};
		Object[][] cellData = {};
		model = new DefaultTableModel(cellData, HEADER) {
			/**
			*/
			private static final long serialVersionUID = 6390978235409396310L;

			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};

		table = new JTable(model);
		table.setRowHeight(50);
		table.getColumnModel().getColumn(0).setMaxWidth(30);
		table.getColumnModel().getColumn(1).setPreferredWidth(70);
		table.getColumnModel().getColumn(2).setMaxWidth(60);
		table.getColumnModel().getColumn(3).setMaxWidth(60);
		table.getColumnModel().getColumn(4).setPreferredWidth(70);
		table.getColumnModel().getColumn(5).setMaxWidth(50);
		table.getColumnModel().getColumn(6).setMaxWidth(50);
		table.getColumnModel().getColumn(7).setMaxWidth(100);
		table.getColumnModel().getColumn(8).setPreferredWidth(30);
		table.getColumnModel().getColumn(9).setPreferredWidth(50);
		table.getColumnModel().getColumn(10).setPreferredWidth(40);
		table.getColumnModel().getColumn(12).setMaxWidth(50);
		table.getTableHeader().setReorderingAllowed(false);

		JScrollPane spanel = new JScrollPane(table);
		spanel.setPreferredSize(new Dimension(SCREEN_WIDTH - 50, SCREEN_HEIGHT - 150));
		panel.add(spanel);
		return panel;
	}

	public JPanel initTitlePanel() {
		JPanel panel = new JPanel();
		JButton button = new JButton("获 取");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				gainPackageInfo();
			}
		});

		fileField = new JTextField(50);
		fileField.setEditable(false);

		JButton scanButtn = new JButton("浏览");
		scanButtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				String[] currentFiles = fileField.getText().split("/");

				JFileChooser chooser = new JFileChooser();
				if ("".equals(fileField.getText())) {
					chooser.setCurrentDirectory(new File(FileUtils.getCurrentLocation()));

				} else {
					chooser.setCurrentDirectory(new File(currentFiles[currentFiles.length - 1]));
				}

				chooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
				int returnCode = chooser.showOpenDialog(MainView.this);

				if (returnCode == JFileChooser.APPROVE_OPTION) {
					String path = chooser.getSelectedFile().getPath();
					path = path.replace("\\", "/");

					if (isQualifiedFile(path)) {
						fileField.setText(path);

					} else {
						JOptionPane.showMessageDialog(MainView.this, "不含有APK文件或APK文件格式错误，请检查");
					}
				}

			}

		});

		panel.setLayout(new FlowLayout());
		panel.add(fileField);
		panel.add(scanButtn);
		panel.add(button);
		// panel.setBorder(new EmptyBorder(20,100, 20,100));
		return panel;
	}

	private boolean isQualifiedFile(String path) {
		if (path.isEmpty())
			return false;
		File file = new File(path);
		if (file.isDirectory()) {
			String[] allFiles = file.list();
			for (int i = 0; i < allFiles.length; i++) {
				if (APKUtils.isAPK(path + "/" + allFiles[i])) {
					return true;
				}

			}

			return false;

		} else {
			if (APKUtils.isAPK(path)) {
				return true;
			}
		}

		return false;
	}

	private void gainPackageInfo() {
		ViewController.getInstance().showLoadingView();

		String filePath = fileField.getText();

		if (isQualifiedFile(filePath)) {
			if (new File(filePath).isDirectory()) {
				showDirectoryAPKInfo(filePath);

			} else {
				new Thread() {
					@Override
					public void run() {
						hasValueMap = new HashMap<String, Boolean>();
						labelMapList = new HashMap<String, JLabel>();
						
						execShowData(filePath, 0, 1);
						try {
							Thread.sleep(1500);
							deleteAllFile();
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
				}.start();
			}

		} else if ("".equals(filePath.trim())) {
			showDirectoryAPKInfo(FileUtils.getCurrentLocation());

		} else {
			JOptionPane.showMessageDialog(MainView.this, "当前目录（不包含子目录）不含有APK文件，请检查");
			ViewController.getInstance().cancelLoadingView();
		}

	}

	private void showDirectoryAPKInfo(String filePath) {
		allApkFiles = this.getAllApkFiles(filePath);

		if (allApkFiles.size() <= 0) {
			JOptionPane.showMessageDialog(MainView.this, "当前目录（不包含子目录）不含有APK文件，请检查");
			ViewController.getInstance().cancelLoadingView();
			return;
		}

		model.setRowCount(0);

		hasValueMap = new HashMap<String, Boolean>();
		labelMapList = new HashMap<String, JLabel>();
		
		new Thread() {
			@Override
			public void run() {
				for (int i = 0; i < allApkFiles.size(); i++) {
					final String fileName = allApkFiles.get(i);
					final int index = i;

					execShowData(fileName, index, allApkFiles.size());

					if (index == allApkFiles.size() - 1) {
						ViewController.getInstance().cancelLoadingView();
						deleteAllFile();
					}
				}
			}
		}.start();
		
		
	}

	private void deleteAllFile() {
		for (int i = 0; i < allApkFiles.size(); i++) {
			String name = allApkFiles.get(i);
			// 3.删除文件夹
			FileUtils.deleteDirectory(name.substring(0, name.length() - 4));
		}

		allApkFiles.clear();
	}

	private void execShowData(String fileName, int index, int totalSize) {
		// 1.反编译包
		boolean isOk = APKUtils.deCompileApk(fileName, fileName.substring(0, fileName.length() - 4));

		if (!isOk) {
			JOptionPane.showMessageDialog(MainView.this, "包解析出错，文件名" + fileName + "，请检查");
			ViewController.getInstance().cancelLoadingView();
			return;
		}
		
		// 2.获取信息
		String[] row = new String[13];

		row[0] = ++index + "";

		// 文件
		row[1] = fileName.substring(fileName.lastIndexOf("/") + 1);

		// 序号
		FileUtils.writeString("alog.txt", row[1]);

		// 名字
		row[2] = APKUtils.getAppName(fileName);
		FileUtils.writeString("alog.txt", row[2]);

		// icon
		row[3] = null;

		// 包名
		row[4] = APKUtils.getPackageName(fileName);
		FileUtils.writeString("alog.txt", row[4]);

		// 版本号
		row[5] = APKUtils.getVersionName(fileName);
		FileUtils.writeString("alog.txt", row[5]);

		// 内部版本号
		row[6] = APKUtils.getVersionCode(fileName);
		FileUtils.writeString("alog.txt", row[6]);

		// API
		row[7] = APKUtils.getAPI(fileName);

		// fb appid
		row[8] = APKUtils.getFaceBookAppid(fileName);

		// fb hashKey
		row[9] = APKUtils.getFaceBookHashCode(fileName);

		// fb name
		row[10] = APKUtils.getFaceBookAppName(fileName);

		// google billing
		row[11] = APKUtils.getGoogleBillingCode(fileName);

		// blue pay id
		row[12] = APKUtils.getBluePayId(fileName);

		model.addRow(row);
		// table.setValueAt(new JLabel(APKUtils.getDrawableIcon(fileName)), 0,
		// 3);
		// table.setValueAt(new JLabel(APKUtils.getDrawableIcon(fileName)),
		// index, 3);
		table.getColumnModel().getColumn(3).setCellRenderer(new TableCellRenderer() {
			private JLabel label;

			@Override
			public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
					boolean hasFocus, int row, int column) {
				System.out.println(allApkFiles);
				System.out.println(labelMapList);
				if(allApkFiles != null && allApkFiles.size() <= 0 && labelMapList != null && labelMapList.size() > 0){
					System.out.println("here??????");
					return labelMapList.get(row * column + "");
				}
				
				if (allApkFiles != null && allApkFiles.size() > 0) {
					String newFileName = allApkFiles.get(row);
					label = new JLabel(APKUtils.getDrawableIcon(newFileName));
					if(labelMapList.get(row * column + "") == null)
						labelMapList.put(row * column + "", label);
					return label;
				} else {

					return label;
				}
			}
		});
	}

	private ArrayList<String> getAllApkFiles(String filePath) {
		String[] fileNames = new File(filePath).list();
		ArrayList<String> allApkFiles = new ArrayList<String>();

		for (int i = 0; i < fileNames.length; i++) {
			if (APKUtils.isAPK(filePath + "/" + fileNames[i])) {
				allApkFiles.add(filePath + "/" + fileNames[i]);
			}
		}

		return allApkFiles;
	}

}
