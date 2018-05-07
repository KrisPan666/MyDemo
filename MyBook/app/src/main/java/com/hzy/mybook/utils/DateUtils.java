package com.hzy.mybook.utils;
import java.text.SimpleDateFormat;
import java.util.Date;
public class DateUtils {
	private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	private static final SimpleDateFormat sdfTime = new SimpleDateFormat("HH:mm");
	public static String format(Long milliseconds) {
		return sdf.format(new Date(milliseconds));
	}
	public static String format(Date date) {
		return sdf.format(date);
	}
	public static String formatTime() {
		return sdfTime.format(new Date());
	}
}
