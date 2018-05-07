package com.hzy.mybook.utils;

import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore.Images.Media;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

public class CommUtils {

	public static String GetDeviceBrand() {
		return Build.BRAND;
	}
	/**
	 * 检查是否为数字字符串下滑先组成的字符串
	 * 
	 * @param str
	 * @return
	 */
	public static Boolean isStringAndNum(String str) {
		// 首先,使用Pattern解释要使用的正则表达式，其中^表是字符串的开始，$表示字符串的结尾。
		Pattern pt = Pattern.compile("^[0-9a-zA-Z_]+$");

		// 如果能够匹配则返回true。实际上还有一种方法mt.find()，某些时候，可能不是比对单一的一个字符串，
		// 可能是一组，那如果只要求其中一个字符串符合要求就可以用find方法了.
		return pt.matcher(str).matches();
	}

	public static ArrayList<String> JsonArrayToListArray(String jsonArray, String pre) {
		JSONArray arr = null;
		try {
			arr = new JSONArray(jsonArray);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return JsonArrayToListArray(arr, pre);
	}

	public static ArrayList<String> JsonArrayToListArray(JSONArray jsonArray, String pre) {
		if (jsonArray == null || jsonArray.length() == 0) {
			return null;
		}
		ArrayList<String> list = new ArrayList<String>();
		int length = jsonArray.length();
		for (int i = 0; i < length; i++) {
			try {
				list.add(pre + jsonArray.getString(i));
			} catch (JSONException e) {
				Log.d("debug", "解析数组json错误: " + e.getMessage());
				e.printStackTrace();
			}
		}
		return list;
	}
	
	/**
	 * 获取版本号
	 * @return
	 */
	public static String getVersionName(Context context) {

		try {
			if (context == null) {
				return "";
			}
			
			PackageManager pm = context.getPackageManager();
			return pm.getPackageInfo(context.getPackageName(), 0).versionName;
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
		return "";
	}
	
	/**
	 * 判断是否为汉子
	 * @param anonymousUserName
	 * @return
	 */
	public static boolean checkHanZiAndStringAndNum(String anonymousUserName) {
		if (anonymousUserName == null || anonymousUserName == "") {
			return false;
		}
		
		for (int i = anonymousUserName.length(); --i >= 0;) {
			String b = anonymousUserName.substring(i, i + 1);
			if (!Pattern.matches("[\u4E00-\u9FA5]|[uF900-uFA2D]|[\u3400-\u4DB5]", b) && !isStringAndNum(b)) {
				System.out.println("pat -> " +  b);
				return false;
			}
		}
		return true;
	}
	
	private final static String BASE64_FILE_HEAD = "data:image/jpeg;base64,";
	// 转化为Base64编码字符串
	public static StringBuffer convertBase64(byte[] buffer) {
		return new StringBuffer().append(BASE64_FILE_HEAD).append(
				Base64.encodeToString(buffer, Base64.NO_WRAP));
	}

	
	/**
	 * 判断是否是IP地址
	 * @param str
	 * @return
	 */
	public static boolean isIPAdress( String str ) {
		Pattern pattern = Pattern.compile( "^((\\d|[1-9]\\d|1\\d\\d|2[0-4]\\d|25[0-5]|[*])\\.){3}(\\d|[1-9]\\d|1\\d\\d|2[0-4]\\d|25[0-5]|[*])$" );
		return pattern.matcher( str ).matches();
	}
	
	public static String urlencode(String str) {
		String encode = str;
		try {
			encode = URLEncoder.encode(str, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			return encode;
		}
		return encode;
	}

	public static String urldecode(String str) {
		String encode = str;
		try {
			encode = URLDecoder.decode(str, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			return encode;
		}
		return encode;
	}

	// 刷新相册
	public static void refreshPhoto(Context context, String photoPath) {
		if (context == null || TextUtils.isEmpty(photoPath)) {
			return;
		}
		
		Uri localUri = Uri.fromFile(new File(photoPath));
		Intent localIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, localUri);
		context.sendBroadcast(localIntent);
	}
	
	/**
	 * 得到path路径后缀
	 * 
	 * @param path
	 * @return
	 */
	public static String getSuffixName(String path) {
		if (TextUtils.isEmpty(path)) {
			return "";
		}
		
		int index = path.lastIndexOf(".");

		if (index == -1)
			return "";

		return path.substring(index);
	}
	
	/**
	 * 将list转jsonarray 字符串
	 * @param imgIds
	 * @return
	 */
	public static String toJsonArrayString(List<String> imgIds) {
		if (imgIds == null || imgIds.size() == 0) {
			return "[]";
		}
		
		StringBuilder sb = new StringBuilder();
		sb.append("[");
		for (int i = 0; i < imgIds.size(); i++) {
			sb.append("\"").append(imgIds.get(i)).append("\"");
			sb.append(",");
		}
		sb.deleteCharAt(sb.length() - 1);
		sb.append("]");
		return sb.toString();
	}
	
	/**
	 * 通过查询数据库判断是否有相片
	 * @return
	 */
	public static boolean existPhoto(Context context) {
		if (context == null) {
			Log.d("commutils existPhoto:", "context is null ");
			return true;
		}
		
		String[]proj = new String[]{ Media.SIZE};
		Cursor extrCursor = context.getContentResolver().query(Media.EXTERNAL_CONTENT_URI
													, proj
													, null
													, null
													, null);

		
		boolean extrIsExist = extrCursor == null ? false : extrCursor.moveToFirst();
		Log.d("commutils existPhoto", "判断相册是否存在-> 外部存储:" + extrIsExist);
		// TODO 只判断外部存储的图片
		return extrIsExist /*|| inteIsExist */;
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

	public static ArrayList<String> JsonArrayToListArray(String jsonArrayString) {
		if (jsonArrayString == null) {
			return new ArrayList<String>(0);
		}

		JSONArray jsonArr = null;
		try {
			jsonArr = new JSONArray(jsonArrayString);
		} catch (Exception e) {
			Log.d("JsonArrayToListArray", " " + e);
			return null;
		}

		return JsonArrayToListArray(jsonArr, "");
	}

	/**
	 * @param parMap
	 * @return
	 */
	public static String maptoString(Map<String, String> parMap) {
		StringBuilder sb = new StringBuilder();
		for (Map.Entry<String, String> entry : parMap.entrySet()) {
			if(entry != null) {
				sb.append(entry.getKey() + ":" + entry.getValue());
				sb.append("\n");
			}
		}
		return sb.toString();
	}

	public static boolean isApkDebugable(Context context) {
		try {
			ApplicationInfo info = context.getApplicationInfo();
			return (info.flags & ApplicationInfo.FLAG_DEBUGGABLE) != 0;
		} catch (Exception e) {

		}
		return false;
	}


	public static boolean isNumber(String n) {
		try {
			Long.parseLong(n);
			return true;
		}catch (Exception e) {
			return false;
		}
	}

	public static boolean isAppIsInBackground(Context context) {
		boolean isInBackground = true;
		ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
		if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT) {
			List<ActivityManager.RunningAppProcessInfo> runningProcesses = am.getRunningAppProcesses();
			if (runningProcesses == null) {
				return isInBackground;
			}
			for (ActivityManager.RunningAppProcessInfo processInfo : runningProcesses) {
				//前台程序
				if (processInfo.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
					for (String activeProcess : processInfo.pkgList) {
						if (activeProcess.equals(context.getPackageName())) {
							isInBackground = false;
						}
					}
				}
			}
		} else {
			List<ActivityManager.RunningTaskInfo> taskInfo = am.getRunningTasks(1);
			ComponentName componentInfo = taskInfo.get(0).topActivity;
			if (componentInfo.getPackageName().equals(context.getPackageName())) {
				isInBackground = false;
			}
		}

		return isInBackground;
	}
}
