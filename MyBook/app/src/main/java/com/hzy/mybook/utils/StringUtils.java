package com.hzy.mybook.utils;

import android.annotation.SuppressLint;
import android.text.TextUtils;
import android.widget.EditText;

import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLDecoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

public class StringUtils {
	public static boolean isEmpty(CharSequence value) {
		return TextUtils.isEmpty(value);
	}

	public static boolean isEmpty(EditText editText) {
		return TextUtils.isEmpty(editText.getText().toString().trim());
	}
	
	public static Map<String, String> splitQuery(URL url) throws UnsupportedEncodingException {
	    Map<String, String> query_pairs = new LinkedHashMap<String, String>();
	    String query = url.getQuery();
	    String[] pairs = query.split("&");
	    for (String pair : pairs) {
	        int idx = pair.indexOf("=");
	        query_pairs.put(URLDecoder.decode(pair.substring(0, idx), "UTF-8"), URLDecoder.decode(pair.substring(idx + 1), "UTF-8"));
	    }
	    return query_pairs;
	}
	
	/**
	 *按照字节长度，截取字符串以more为后缀，此方法是为了中英文显示长度的一致。不那么准确
	 * */
	public static String cutString(String str, int length, String suffix) {
		int reInt = 0;
		String reStr = "";
		if (isEmpty(str))
			return "";
		char[] tempChar = str.toCharArray();
		for (int i = 0; (i < tempChar.length && length > reInt); i++) {
			String char_str = String.valueOf(tempChar[i]);
			byte[] b = char_str.getBytes();
			reInt += b.length;
			reStr += tempChar[i];
		}
		if (length == reInt || (length == reInt - 1))
			reStr += suffix;
		return reStr;
	}
	

	public static boolean parseArrayInt(int[] array, String value, String split) {
		if (array == null || StringUtils.isEmpty(value))
			return false;

		boolean ret = false;
		String[] ss = value.split(split);
		for (int i = 0; i < array.length && i < ss.length; i++) {
			ret = true;
			array[i] = Integer.parseInt(ss[i]);
		}

		return ret;
	}

	public static boolean isEqual(String compa, String compb) {
		return compa.equals(compb);
	}

	// 去除字符串尾部空格
	public static String trimEnd(String str) {
		return str.replaceAll("[\\s]*$", "");
	}

	// 将字符串中所有的非标准字符（双字节字符）替换成两个标准字符（**，或其他的也可以）
	public static String convert(String str) {
		return str.replaceAll("[^\\x00-\\xff]", "**");
	}

	public static String getUTF8Dom() {
		byte[] buffer = new byte[3];
		buffer[0] = (byte) 0xEF;
		buffer[1] = (byte) 0xBB;
		buffer[2] = (byte) 0xBF;

		try {
			return new String(buffer, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		return "";
	}

	public static boolean parseArrayString(String[] array, String value,
                                           String split) {
		if (array == null || StringUtils.isEmpty(value))
			return false;

		boolean ret = false;
		String[] ss = value.split(split);
		for (int i = 0; i < array.length && i < ss.length; i++) {
			ret = true;
			array[i] = ss[i];
		}

		return ret;
	}

	public static boolean copyString(String[] src, String[] dest) {
		if (src == null || dest == null)
			return false;

		for (int i = 0; i < dest.length; i++) {
			dest[i] = "";
		}

		for (int i = 0; i < dest.length && i < src.length; i++) {
			dest[i] = src[i];
		}

		return true;
	}
	
	@SuppressLint("DefaultLocale")
	public static boolean isContainsIgnore(String compa, String str) {
		try{
			if(compa.contains(str))
				return true;
			else
				return compa.contains(str.toUpperCase());
		}catch(Exception ex) {}
		
		return false;
	}
	
	public static boolean isContains(String compa, String str) {
		try{
			return compa.contains(str);
		}catch(Exception ex) {}
		
		return false;
	}
	
	public static String getUrlName(String url) {
		if (StringUtils.isEmpty(url))
			return "";

		int index = url.indexOf("?");
		if (index > 0)
			url = url.substring(0, index);

		index = url.lastIndexOf("/");
		if (index < 0)
			index = url.indexOf("\\");

		if (index < 0)
			return url;

		return url.substring(index + 1);
	}

	public static String getTime(long tm) {
		int h = (int) (tm / 3600);
		int m = (int) (tm / 60);
		int s = (int) (tm % 60);

		if (h > 0)
			return String.format("%02d:%02d:%02d", h, m, s);

		else if (m > 0)
			return String.format("%02d:%02d", m, s);

		return String.format("%02d", s);
	}

	public static String getTimeCN(long tm) {
		int h = (int) (tm / 3600);
		int m = (int) (tm / 60);
		int s = (int) (tm % 60);

		if (h > 0)
			return String.format("%02d小时%02d分%02d秒", h, m, s);

		else if (m > 0)
			return String.format("%02d分%02d秒", m, s);

		return String.format("%02d秒", s);
	}

	public static String getDateTime(long tm) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return sdf.format(new Date(tm));
	}

	public static double parseDouble(String value, double default_value) {
		try {
			return Double.parseDouble(value);
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return default_value;
	}
	
	public static Float parseFloat(String value, float default_value) {
		try {
			return Float.parseFloat(value);
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return default_value;
	}

	public static int parseInt(String value, int default_value) {
		try {
			return Integer.parseInt(value);
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return default_value;
	}

	public static Date parseDate(String date) {
		if (TextUtils.isEmpty(date))
			return null;

		SimpleDateFormat sdf = null;
		if (date.length() == 10)
			sdf = new SimpleDateFormat("yyyy-MM-dd");

		else if (date.length() == 16)
			sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");

		else if (date.length() == 19)
			sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		else
			return null;

		try {
			return sdf.parse(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}

		return null;
	}

	public static String subString(String src, String find, int second) {
		int index = -find.length(), n = 0;
		do {
			int end = src.indexOf(find, index + find.length());
			if (end < 0)
				break;

			n++;
			index = end;
			if (n == second)
				return src.substring(0, index);

		} while (index > 0 && n < second);

		return src;
	}

	public static String delString(String src, String db, String de) {
		int index = -db.length();
		do {
			index = src.indexOf(db, index + db.length());
			if (index > 0) {
				int end = src.indexOf(de, index + de.length());
				if (end > 0)
					src = src.substring(0, index)
							+ src.substring(end + de.length());
				else
					src = src.substring(0, index);
			}
		} while (index > 0);

		return src;
	}

	public static int parseInt(String value) {
		try {
			return Integer.parseInt(value);
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return 0;
	}

	public static long parseLong(String value, long default_value) {
		try {
			return Long.parseLong(value);
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return default_value;
	}

	public static boolean isASCII(String str) {
		if (isEmpty(str))
			return false;
		for (int i = 0; i < str.length(); i++) {
			if (str.charAt(i) >= 128 || str.charAt(i) < 0) {
				return false;
			}
		}
		return true;
	}

	public static final int MIN_LEN = 4;

	public static String delUTF8Dom(StringBuffer xml) {
		int len = xml.length() > MIN_LEN ? MIN_LEN : xml.length();
		String tmp = xml.substring(0, len);
		int index = tmp.indexOf('<');

		if (index < 0)
			index = tmp.indexOf('{');

		if (index < 0)
			index = tmp.indexOf('[');

		if (index > 0) {
			for (int i = 0; i < index; i++) {
				xml.setCharAt(i, ' ');
			}
		}

		return xml.toString();
	}
	
	public static String urldcode(String str) {
		String encode = str;
		try {
			encode = URLDecoder.decode(str, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			return encode;
		}
		return encode;
	}
}
