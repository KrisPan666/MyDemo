package com.jiecheng.zhike.mydemo.Utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;

import com.iflytek.log.Lg;
import com.iflytek.utils.io.FileUtils;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;
import java.util.TimeZone;
import java.util.regex.Pattern;

public class CommUtils {
	public static final boolean IS_DEVELOP = false;
	private static final String TAG = "CommUtils";

	// 题目图片路径
	public static String QUESTION_IMG_PATHS = "";

	public static String getQuesImgPaths() {
		return QUESTION_IMG_PATHS;
	}

	public static void setQuesImgPaths(String quesImgPaths) {
		QUESTION_IMG_PATHS = quesImgPaths;
	}

	/**
	 * 得到文件后缀名称
	 * 
	 * @param path
	 * @return
	 */
	public static String getSuffixName(String path) {
		File file = new File(path);
		if (!file.exists())
			return "";

		String name = file.getName();
		int index = name.lastIndexOf(".");

		if (index == -1)
			return "";

		return name.substring(index);
	}

	/**
	 * 包裹文件 file/// 协议
	 * 
	 * @param qstImgPath
	 * @return
	 */
	public static String wrapFile(String qstImgPath) {
		if (TextUtils.isEmpty(qstImgPath)) {
			return qstImgPath;
		}

		if ((qstImgPath.startsWith("assets://"))) {
			return qstImgPath;
		}

		if (!qstImgPath.startsWith("file://")) {
			qstImgPath = "file://" + qstImgPath;
		}

		return qstImgPath;
	}

	/**
	 * 获取ip地址
	 * @return
	 */
	public static String getHostIP() {

		String hostIp = null;
		try {
			Enumeration nis = NetworkInterface.getNetworkInterfaces();
			InetAddress ia = null;
			while (nis.hasMoreElements()) {
				NetworkInterface ni = (NetworkInterface) nis.nextElement();
				Enumeration<InetAddress> ias = ni.getInetAddresses();
				while (ias.hasMoreElements()) {
					ia = ias.nextElement();
					if (ia instanceof Inet6Address) {
						continue;// skip ipv6
					}
					String ip = ia.getHostAddress();
					if (!"127.0.0.1".equals(ip)) {
						hostIp = ia.getHostAddress();
						break;
					}
				}
			}
		} catch (SocketException e) {
			Log.i("lc", "SocketException");
			e.printStackTrace();
		}
		return hostIp;

	}

	/**
	 * 
	 * @return
	 */
	public static Object wrapJsonArray(String... qustionImgPaths) {
		JSONArray jsonArray = new JSONArray();
		if (qustionImgPaths == null) {
			return jsonArray;
		}

		for (int i = 0; i < qustionImgPaths.length; i++) {
			jsonArray.put(qustionImgPaths[i]);
		}
		return jsonArray;
	}

	@SuppressLint("SimpleDateFormat")
	private static SimpleDateFormat format = new SimpleDateFormat(
			"yyyy-MM-dd HH:mm:ss");

	public static String getFormatDate(long datecreated) {
		return format.format(new Date(datecreated));
	}

	public static String urlencode(String str) {
		String encode = str;
		try {
			// RFC-1738编码规则改为RFC-2396编码规则
			encode = URLEncoder.encode(str, "UTF-8").replace("+", "%20");
		} catch (Exception e) {
			return encode;
		}
		return encode;
	}

	public static String urldcode(String str) {
		String encode = str;
		try {
			encode = URLDecoder.decode(str, "UTF-8");
		} catch (Exception e) {
			return encode;
		}
		return encode;
	}

	//get curr  pc ip

	public static String getPCIp() {
		File ipFile = new File(Environment.getExternalStorageDirectory() + "/iflytek/", "ipconfig.txt");
		if (ipFile.isFile() && ipFile.exists()) {
			String ip = FileUtils.readText(ipFile.getAbsolutePath(), "utf-8").toString();
			if (TextUtils.isEmpty(ip)) {
				return "";
			} else {
				return ip;
			}
		}
		return "";
	}

	public static void copyDatabaseFile(Context context) {
		String srcDir = "/data/data/com.iflytek.mcv/databases/mcv_interaction.db";
		String dstDir = Environment.getExternalStorageDirectory().getPath()
				+ "/mcv_interaction.db";

		File srcFile = new File(srcDir);
		if (!srcFile.exists()) {
			return;
		}

		try {

			InputStream in = new FileInputStream(srcFile);
			int size = in.available();
			byte buf[] = new byte[size];
			in.read(buf);
			in.close();

			FileOutputStream out = new FileOutputStream(dstDir);
			out.write(buf);
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 得到文件名称
	 * 
	 * @param strUri
	 * @return
	 */
	public static String getFileName(String strUri) {
		if (TextUtils.isEmpty(strUri)) {
			return "";
		}

		int lastIndex = strUri.replace("\\", "/").lastIndexOf("/");
		if (lastIndex >= 0) {
			String fileName = strUri.substring(lastIndex + 1);

			// 如果是http url的需要去掉query参数
			if (!TextUtils.isEmpty(fileName)
					&& (strUri.startsWith("http") || strUri.startsWith("https"))) {
				// 去掉 url后面的query参数
				int lastIndexEnd = strUri.lastIndexOf("?");
				if (lastIndexEnd >= 0) {
					return strUri.substring(lastIndex + 1, lastIndexEnd);
				}
			}
			return fileName;
		}
		return strUri;
	}

	/**
	 * 将json数组转成string类型的集合
	 * 
	 * @param jsonString
	 * @return
	 */
	public static ArrayList<String> jsonArrayToList(String jsonString) {
		if (jsonString == null) {
			return null;
		}

		JSONArray jsonArr = null;
		try {
			jsonArr = new JSONArray(jsonString);
		} catch (Exception e) {
			Lg.e("parsejson", " " + e);
			return null;
		}

		return jsonArrayToList(jsonArr);
	}

	/**
	 * 将json数组转成string类型的集合
	 * 
	 * @param urlJsonArr
	 * @return
	 */
	public static ArrayList<String> jsonArrayToList(JSONArray urlJsonArr) {
		if (urlJsonArr == null || urlJsonArr.length() <= 0) {
			return null;
		}

		ArrayList<String> arr = new ArrayList<String>();
		for (int i = 0; i < urlJsonArr.length(); i++) {
			try {
				arr.add(urlJsonArr.getString(i));
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		return arr;
	}

	public static ArrayList<String> wrapList(String url) {
		ArrayList<String> imgUrls = new ArrayList<String>(1);
		if (url !=null){
			imgUrls.add(url);
		}
		return imgUrls;
	}

	/**
	 * 将字符数字转成数字
	 * 
	 * @param strNum
	 * @return
	 */
	public static int toInteger(String strNum, int defaultNum) {
		int rotate = defaultNum;
		if (strNum != null && !strNum.isEmpty()) {
			try {
				rotate = Integer.parseInt(strNum);
			} catch (Exception e) {
				return defaultNum;
			}
		}
		return rotate;
	}

	/**
	 * 填充list , 会重置list所有值
	 * 
	 * @param list
	 * @param value
	 */
	public static void fillList(List<String> list, int num,
			String value) {
		list.clear();
		for (int i = 0; i < num; i++) {
			list.add(value);
		}
	}

	public static boolean isEmpty(List<?> list) {
		return list == null || list.isEmpty();
	}

	public static <T> List<T> newList(List<T> srcList) {
		if (isEmpty(srcList)) {
			return null;
		}

		ArrayList<T> list = new ArrayList<T>(srcList.size());
		list.addAll(srcList);
		return list;
	}

	/**
	 * 格式化时长
	 * 
	 * @author 崔达
	 * @param timeMillis
	 * @return
	 */
	public static String formatTimeLength(long timeMillis) {

		timeMillis /= 1000;
		long minute = timeMillis / 60l;
		long hour = minute / 60l;
		long second = timeMillis % 60;
		minute %= 60;
		// 时长不足1小时
		if (hour == 00)
			return String.format("%02d:%02d", minute, second);
		else
			return String.format("%02d:%02d:%02d", hour, minute, second);
	}


	public static JSONArray toJsonArray(String jsonArrayString) {
		JSONArray jsonArr = null;
		try {
			jsonArr = new JSONArray(jsonArrayString);
		} catch (JSONException e) {
			Lg.e("toJsonArray error", " " + e);
			return new JSONArray();
		}
		return jsonArr;
	}

	public static boolean isEmpty(JSONArray array) {
		return array == null || array.length() == 0;
	}


	/**
	 * 读取文件内容
	 * 
	 * @param filePathAndName
	 *            String 如 c:\\1.txt 绝对路径
	 * @return boolean
	 */
	public static String readFile(String filePathAndName) {
		StringBuffer buffer = FileUtils.readText(filePathAndName,
				FileUtils.UTF_8);
		return buffer == null ? "" : buffer.toString();
	}

	/**
	 * 写入文件
	 * 
	 * @param filePathAndName
	 *            String 如 c:\\1.txt 绝对路径
	 */
	public static void writeFile(String filePathAndName, String fileContent) {
		FileUtils.writeString(filePathAndName, fileContent);
	}

	public static <T> boolean isEmpty(T[] picIds) {
		return picIds == null || picIds.length == 0;
	}

	/**
	 *
	 * @param obj
	 * @return
	 */
	public static <T> boolean isEmpty(Object obj) {
		return obj == null || obj == "";
	}

	/**
	 * 删除文件
	 * 
	 * @param paths
	 */
	public static void deleteFiles(List<String> paths) {
		if (isEmpty(paths)) {
			Lg.e(TAG, "没有可删除文件");
			return;
		}

		for (String path : paths) {
			if (!TextUtils.isEmpty(path)) {
				Lg.write_file(TAG, "delete file : " + path);
				new File(path).delete();
			}
		}
	}

	/**
	 * 删除文件
	 * 
	 * @param paths
	 * @param
	 */
	public static void deleteFiles(File[] paths) {
		if (isEmpty(paths)) {
			Lg.e(TAG, "没有可删除文件");
			return;
		}

		for (File path : paths) {
			if (path != null) {
				Lg.write_file(TAG, "delete file : " + path);
				path.delete();
			}
		}
	}

	public static <T> ArrayList<T> wrapList(T info) {
		ArrayList<T> list = new ArrayList<T>(1);
		list.add(info);
		return list;
	}

	public static <T>  int toSize(List<T> list) {
		return list == null ? 0 : list.size();
	}


	/**
	 * 判断是否是IP地址
	 * @param ip
	 * @return
	 */
	public static boolean isIPAdress(String ip) {
		if (TextUtils.isEmpty(ip)) {
			Lg.e(TAG, "ip address is null");
			return false;
		}

		Pattern pattern = Pattern.compile("^((\\d|[1-9]\\d|1\\d\\d|2[0-4]\\d|25[0-5]|[*])\\.){3}(\\d|[1-9]\\d|1\\d\\d|2[0-4]\\d|25[0-5]|[*])$");
		boolean valid = pattern.matcher(ip).matches();

		if (!valid)
			Lg.e(TAG, "ip address invalid : " + ip);

		return valid;
	}

	public static final int SECONDS_IN_DAY = 60 * 60 * 24;
	public static final long MILLIS_IN_DAY = 1000L * SECONDS_IN_DAY;

	public static boolean isSameDayOfMillis(final long ms1, final long ms2) {
		final long interval = ms1 - ms2;
		return interval < MILLIS_IN_DAY
				&& interval > -1L * MILLIS_IN_DAY
				&& toDay(ms1) == toDay(ms2);
	}

	private static long toDay(long millis) {
		return (millis + TimeZone.getDefault().getOffset(millis)) / MILLIS_IN_DAY;
	}

    public static <T> List<T> mergeList(List<T> list1, List<T> list2) {
		if (list1 == null && list2 == null) {
			return null;
		}

		if (list1 == null) {
			list1 = new ArrayList<>();
		}
		if (list2 != null) {
			list1.addAll(list2);
		}
		return list1;
    }

    public static <T>List<T> collectionToList(Collection<T> values) {
		List<T> list = new ArrayList<>(values.size());
		if (values == null) {
			return list;
		}
		for (T v : values) {
			list.add(v);
		}
		return list;
    }

	/**
	 * 删除指定文件夹下所有文件
	 * @param path 文件夹完整绝对路径
	 * @return
	 *  create by panhongyu on 2018.4.8
	 */
	public static boolean delAllFile(String path) {
		boolean flag = false;
		File file = new File(path);
		if (!file.exists()) {
			return flag;
		}
		if (!file.isDirectory()) {
			return flag;
		}
		String[] tempList = file.list();
		File temp = null;
		for (int i = 0; i < tempList.length; i++) {
			if (path.endsWith(File.separator)) {
				temp = new File(path + tempList[i]);
			} else {
				temp = new File(path + File.separator + tempList[i]);
			}
			if (temp.isFile()) {
				temp.delete();
			}
			if (temp.isDirectory()) {
				delAllFile(path + "/" + tempList[i]);//先删除文件夹里面的文件
				delFolder(path + "/" + tempList[i]);//再删除空文件夹
				flag = true;
			}
		}
		return flag;
	}

	//删除文件夹
	public static void delFolder(String folderPath) {
		try {
			delAllFile(folderPath); //删除完里面所有内容
			String filePath = folderPath;
			filePath = filePath.toString();
			File myFilePath = new File(filePath);
			myFilePath.delete(); //删除空文件夹
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static List<String> refreshFileList(String strPath) {
		List<String> filelist = new ArrayList<>();
		File dir = new File(strPath);
		File[] files = dir.listFiles();
		if (files == null){
			return null;
		}else{
			for (int i = 0; i < files.length; i++) {
				if (files[i].isDirectory()) {
					refreshFileList(files[i].getAbsolutePath());
				} else {
					String strFileName = files[i].getAbsolutePath().toLowerCase();
					System.out.println("---"+strFileName);
					filelist.add(files[i].getAbsolutePath());
				}
			}
		}
		return filelist;
	}

	/**
	 * 将某个目录下的问价拷贝到另一个目录下;
	 * create by panhongyu on 2018.4.8;
	 */
	public static void copyFile(String originalName, String targetName){
		try {
			InputStream in = new FileInputStream(originalName);
			int size = in.available();
			byte buf[] = new byte[size];
			in.read(buf);
			in.close();

			FileOutputStream out = new FileOutputStream(targetName);
			out.write(buf);
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
