package utils;

import java.awt.Image;
import java.io.File;
import java.util.ArrayList;

import javax.swing.ImageIcon;

import org.dom4j.Document;

public class APKUtils {
	public static final String APKTOOL = FileUtils.getCurrentLocation() + "lib/apktool.jar";

	public static String getBluePayId(String apkFileName) {
		if (apkFileName == null || apkFileName.isEmpty()) {
			return null;
		}

		apkFileName = apkFileName.substring(0, apkFileName.length() - 4) + "/assets/BluePay.ref";
		try {
			if (new File(apkFileName).exists()) {
				String content = FileUtils.readFileByLines(apkFileName);

				String[] productId = content.split("</productId>");

				if (productId.length > 1) {
					return productId[0].split("<productId value=\"100\">")[1];
				}
			}
		} catch (Exception e) {
			return null;
		}

		return null;

	}

	public static ImageIcon getDrawableIcon(String apkFileName) {
		if (apkFileName == null || apkFileName.isEmpty()) {
			return null;
		}

		apkFileName = apkFileName.substring(0, apkFileName.length() - 4) + "/res/drawable/icon.png";
		if (new File(apkFileName).exists()) {
			ImageIcon icon = new ImageIcon(apkFileName);
			icon.setImage(icon.getImage().getScaledInstance(50, 50,Image.SCALE_DEFAULT));
			return icon;
		}

		return null;
	}

	public static String getMolAppKey(String apkFileName) {
		if (apkFileName == null || apkFileName.isEmpty()) {
			return null;
		}

		apkFileName = apkFileName.substring(0, apkFileName.length() - 4) + "/res/values/strings.xml";

		if (new File(apkFileName).exists()) {
			Document document = XMLUtils.getDocument(apkFileName);

			String molAppKey = XMLUtils.getValueFromElementAttribute(document, "mol_appkey");

			return molAppKey;
		}

		return null;
	}

	public static String getMolAppId(String apkFileName) {
		if (apkFileName == null || apkFileName.isEmpty()) {
			return null;
		}

		apkFileName = apkFileName.substring(0, apkFileName.length() - 4) + "/res/values/strings.xml";

		if (new File(apkFileName).exists()) {
			Document document = XMLUtils.getDocument(apkFileName);

			String molAppId = XMLUtils.getValueFromElementAttribute(document, "mol_appid");

			return molAppId;
		}

		return null;
	}

	public static String getFaceBookHashCode(String apkFileName) {
		if (apkFileName == null || apkFileName.isEmpty()) {
			return null;
		}

		apkFileName = apkFileName.substring(0, apkFileName.length() - 4) + "/res/values/strings.xml";

		if (new File(apkFileName).exists()) {
			Document document = XMLUtils.getDocument(apkFileName);

			String hashCode = XMLUtils.getValueFromElementAttribute(document, "facebook_hash_code");

			return hashCode;
		}

		return null;
	}

	public static String getGoogleBillingCode(String apkFileName) {
		if (apkFileName == null || apkFileName.isEmpty()) {
			return null;
		}

		apkFileName = apkFileName.substring(0, apkFileName.length() - 4) + "/res/values/strings.xml";

		if (new File(apkFileName).exists()) {
			Document document = XMLUtils.getDocument(apkFileName);

			String app_billing = XMLUtils.getValueFromElementAttribute(document, "google_billing_code");

			return app_billing;
		}

		return null;
	}

	public static String getAPI(String apkFileName) {
		if (apkFileName == null || apkFileName.isEmpty()) {
			return null;
		}

		apkFileName = apkFileName.substring(0, apkFileName.length() - 4) + "/res/values/strings.xml";

		if (new File(apkFileName).exists()) {
			Document document = XMLUtils.getDocument(apkFileName);

			String api = XMLUtils.getValueFromElementAttribute(document, "umeng_channel");

			if (api.split("-").length > 0 && api.split("-").length == 3) {
				return api.split("-")[2];
			}

			return null;
		}

		return null;
	}

	public static String getFaceBookAppName(String apkFileName) {
		if (apkFileName == null || apkFileName.isEmpty()) {
			return null;
		}

		apkFileName = apkFileName.substring(0, apkFileName.length() - 4) + "/res/values/strings.xml";

		if (new File(apkFileName).exists()) {
			Document document = XMLUtils.getDocument(apkFileName);

			String appName = XMLUtils.getValueFromElementAttribute(document, "facebook_app_name");

			return appName;
		}

		return null;
	}

	public static String getFaceBookAppid(String apkFileName) {
		if (apkFileName == null || apkFileName.isEmpty()) {
			return null;
		}

		apkFileName = apkFileName.substring(0, apkFileName.length() - 4) + "/res/values/strings.xml";

		if (new File(apkFileName).exists()) {
			Document document = XMLUtils.getDocument(apkFileName);

			String facebookId = XMLUtils.getValueFromElementAttribute(document, "app_id");

			return facebookId;
		}

		return null;
	}

	public static String getAppName(String apkFileName) {
		if (apkFileName == null || apkFileName.isEmpty()) {
			return null;
		}

		apkFileName = apkFileName.substring(0, apkFileName.length() - 4) + "/res/values/strings.xml";
		
		FileUtils.writeString("alog.txt", "appName:" + apkFileName);
		
		if (new File(apkFileName).exists()) {
			FileUtils.writeString("alog.txt", "appName: here???");
			Document document = XMLUtils.getDocument(apkFileName);
			FileUtils.writeString("alog.txt", "appName:" + apkFileName);
			String appName = XMLUtils.getValueFromElementAttribute(document, "app_name");

			return appName;
		}

		return null;
	}

	/**
	 * 快速得到包名
	 * 
	 * @param definiteFile
	 * @return
	 */
	public static String getPackageName(String apkFileName) {
		if (apkFileName == null || apkFileName.isEmpty()) {
			return null;
		}

		apkFileName = apkFileName.substring(0, apkFileName.length() - 4) + "/AndroidManifest.xml";

		if (new File(apkFileName).exists()) {
			Document document = XMLUtils.getDocument(apkFileName);
			ArrayList<String> arrayList = new ArrayList<String>();
			String packageName = XMLUtils.getAttributeValueFromRootNode(document, arrayList, "package");

			return packageName;
		}
		return null;
	}

	/**
	 * 快速得到版本号
	 * 
	 * @param apkFileName
	 * @return
	 */
	public static String getVersionName(String apkFileName) {
		if (apkFileName == null || apkFileName.isEmpty()) {
			return null;
		}

		String baseInfo = getBaseInfo(apkFileName);
		String[] version1 = baseInfo.split("versionName: ");

		if (version1.length > 1) {
			String version2 = version1[1];
			return version2.split("###")[0];
		}

		return null;
	}

	/**
	 * 快速得到内部版本号
	 * 
	 * @param originApk
	 * @return
	 */
	public static String getVersionCode(String apkFileName) {
		if (apkFileName == null || apkFileName.isEmpty()) {
			return null;
		}

		String baseInfo = getBaseInfo(apkFileName);
		String[] version1 = baseInfo.split("versionCode: ");

		if (version1.length > 1) {
			String version2 = version1[1];
			return version2.split("###")[0].substring(1, version2.split("###")[0].length() - 1);
		}

		return null;
	}

	public static String getBaseInfo(String apkFileName) {
		if (apkFileName == null || apkFileName.isEmpty()) {
			return null;
		}

		apkFileName = apkFileName.substring(0, apkFileName.length() - 4) + "/apktool.yml";

		FileUtils.writeString("alog.txt", apkFileName);
		if (new File(apkFileName).exists()) {
			String readContent = FileUtils.readFileByLines(apkFileName);
			return readContent;
		}

		return null;
	}

	/**
	 * 反编译APK
	 * 
	 * @param originApk
	 * @param definApk
	 * @return
	 */
	public static boolean deCompileApk(String originApk, String defineFile) {
		if (originApk == null || originApk.isEmpty()) {
			return false;
		}
		
		if (defineFile == null || defineFile.isEmpty()) {
			defineFile = originApk.substring(0, originApk.length() - 4);
		}

		if (isAPK(originApk)) {
			String getPackageCommand = "java -jar " + APKTOOL + " d " + originApk + " -o " + defineFile;
			
			FileUtils.writeString("alog.txt",getPackageCommand );
			boolean isDecomp = CommandUtils.exeCmd(getPackageCommand);
			return isDecomp;
		}

		return false;
	}

	/**
	 * 
	 * @param apkFileName
	 * @return
	 */
	public static boolean isAPK(String apkFileName) {
		if (apkFileName == null || apkFileName.isEmpty())
			return false;

		if (apkFileName.trim().endsWith(".apk")) {
			return true;
		}

		return false;
	}
}
