package com.jiecheng.zhike.mydemo.filelist.Utils;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.text.TextUtils;

import com.jiecheng.zhike.mydemo.filelist.FileType;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;

/**
 * Created by 13159 on 2017/8/3.
 */

public class fileUtil {
    private static long M_1 = 1024 * 1024;
    private static long K_1 = 1024;
    private static HashMap<String, FileType> fileExtNameMap = new HashMap<>();
    static {
        fileExtNameMap.put(".wav", FileType.FILE_ICON_MOVIE);
        fileExtNameMap.put(".mp4", FileType.FILE_ICON_MOVIE);
        fileExtNameMap.put(".rmvb", FileType.FILE_ICON_MOVIE);
        fileExtNameMap.put(".f4v", FileType.FILE_ICON_MOVIE);
        fileExtNameMap.put(".flv", FileType.FILE_ICON_MOVIE);
        fileExtNameMap.put(".avi", FileType.FILE_ICON_MOVIE);
        fileExtNameMap.put(".wmv", FileType.FILE_ICON_MOVIE);

        fileExtNameMap.put(".mp3", FileType.FILE_ICON_MUSIC);

        fileExtNameMap.put(".jpg", FileType.FILE_ICON_PICTURE);
        fileExtNameMap.put(".bmp", FileType.FILE_ICON_PICTURE);
        fileExtNameMap.put(".png", FileType.FILE_ICON_PICTURE);
        fileExtNameMap.put(".jpeg", FileType.FILE_ICON_PICTURE);
        fileExtNameMap.put(".gif", FileType.FILE_ICON_PICTURE);

//        fileExtNameMap.put(".apk", FileType.FILE_ICON_APK);
//
//        fileExtNameMap.put(".xls", FileType.FILE_ICON_EXCEL);
//        fileExtNameMap.put(".xlsx", FileType.FILE_ICON_EXCEL);

        fileExtNameMap.put(".doc", FileType.FILE_ICON_WORD);
        fileExtNameMap.put(".docx", FileType.FILE_ICON_WORD);

        fileExtNameMap.put(".ppt", FileType.FILE_ICON_PPT);
        fileExtNameMap.put(".pptx", FileType.FILE_ICON_PPT);

        fileExtNameMap.put(".pdf", FileType.FILE_ICON_PDF);

        fileExtNameMap.put(".htm", FileType.FILE_ICON_WEBPAGE);
        fileExtNameMap.put(".mht", FileType.FILE_ICON_WEBPAGE);
        fileExtNameMap.put(".html", FileType.FILE_ICON_WEBPAGE);
        fileExtNameMap.put(".xml", FileType.FILE_ICON_WEBPAGE);
        fileExtNameMap.put(".js", FileType.FILE_ICON_WEBPAGE);
        fileExtNameMap.put(".css", FileType.FILE_ICON_WEBPAGE);

        fileExtNameMap.put(".txt", FileType.FILE_ICON_TXT);

        fileExtNameMap.put(".rar", FileType.FILE_ICON_RAR);
        fileExtNameMap.put(".zip", FileType.FILE_ICON_RAR);
        fileExtNameMap.put(".7z", FileType.FILE_ICON_RAR);
        fileExtNameMap.put(".tar", FileType.FILE_ICON_RAR);
        fileExtNameMap.put(".gz", FileType.FILE_ICON_RAR);

        fileExtNameMap.put(".log", FileType.FILE_ICON_TEXT);
    }

    public static FileType getFileType(File file) {
        String extname = getExtName(file.getName());
        if (!fileExtNameMap.containsKey(extname)) {
            return FileType.FILE_ICON_OTHER;
        }
        return fileExtNameMap.get(extname);
    }

    private static String getExtName(String name) {
        int i  = name.indexOf('.');
        return (i > 0 ? name.substring(i, name.length()) : "");
    }

    // 获取sd卡目录
    public static String getSdCardDirectory() {
        return Environment.getExternalStorageDirectory().getPath();
    }

    /**
     * 打开文件
     * @param file
     */
    public static void openFile(Context context, File file) {
        //Uri uri = Uri.parse("file://"+file.getAbsolutePath());
        Intent intent = new Intent();
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        //设置intent的Action属性
        intent.setAction(Intent.ACTION_VIEW);
        //获取文件file的MIME类型
        String type = getMIMEType(file);
        //设置intent的data和Type属性。
        intent.setDataAndType(/*uri*/Uri.fromFile(file), type);
        //跳转
        context.startActivity(intent);
    }

    /***根据文件后缀回去MIME类型****/
    private static String getMIMEType(File file) {
        String type="*/*";
        String end = getExtName(file.getName());
        if (TextUtils.isEmpty(end)) {
            return type;
        }

        //在MIME和文件类型的匹配表中找到对应的MIME类型。
        for (int i = 0; i < MIME_MapTable.length; i++){
            if(end.equals(MIME_MapTable[i][0]))
                type = MIME_MapTable[i][1];
        }
        return type;
    }

    //建立一个MIME类型与文件后缀名的匹配表
    private static final String[][] MIME_MapTable={
            //{后缀名，    MIME类型}
            {".3gp",    "video/3gpp"},
            {".apk",    "application/vnd.android.package-archive"},
            {".asf",    "video/x-ms-asf"},
            {".avi",    "video/x-msvideo"},
            {".bin",    "application/octet-stream"},
            {".bmp",    "image/bmp"},
            {".c",      "text/plain"},
            {".class",  "application/octet-stream"},
            {".conf",   "text/plain"},
            {".cpp",    "text/plain"},
            {".doc",    "application/msword"},
            {".exe",    "application/octet-stream"},
            {".gif",    "image/gif"},
            {".gtar",   "application/x-gtar"},
            {".gz",     "application/x-gzip"},
            {".h",      "text/plain"},
            {".htm",    "text/html"},
            {".html",   "text/html"},
            {".jar",    "application/java-archive"},
            {".java",   "text/plain"},
            {".jpeg",   "image/jpeg"},
            {".jpg",    "image/jpeg"},
            {".js",     "application/x-javascript"},
            {".log",    "text/plain"},
            {".m3u",    "audio/x-mpegurl"},
            {".m4a",    "audio/mp4a-latm"},
            {".m4b",    "audio/mp4a-latm"},
            {".m4p",    "audio/mp4a-latm"},
            {".m4u",    "video/vnd.mpegurl"},
            {".m4v",    "video/x-m4v"},
            {".mov",    "video/quicktime"},
            {".mp2",    "audio/x-mpeg"},
            {".mp3",    "audio/x-mpeg"},
            {".mp4",    "video/mp4"},
            {".mpc",    "application/vnd.mpohun.certificate"},
            {".mpe",    "video/mpeg"},
            {".mpeg",   "video/mpeg"},
            {".mpg",    "video/mpeg"},
            {".mpg4",   "video/mp4"},
            {".mpga",   "audio/mpeg"},
            {".msg",    "application/vnd.ms-outlook"},
            {".ogg",    "audio/ogg"},
            {".pdf",    "application/pdf"},
            {".png",    "image/png"},
            {".pps",    "application/vnd.ms-powerpoint"},
            {".ppt",    "application/vnd.ms-powerpoint"},
            {".prop",   "text/plain"},
            {".rar",    "application/x-rar-compressed"},
            {".rc",     "text/plain"},
            {".rmvb",   "audio/x-pn-realaudio"},
            {".rtf",    "application/rtf"},
            {".sh",     "text/plain"},
            {".tar",    "application/x-tar"},
            {".tgz",    "application/x-compressed"},
            {".txt",    "text/plain"},
            {".wav",    "audio/x-wav"},
            {".wma",    "audio/x-ms-wma"},
            {".wmv",    "audio/x-ms-wmv"},
            {".wps",    "application/vnd.ms-works"},
            {".xml",    "text/xml"},
            {".xml",    "text/plain"},
            {".z",      "application/x-compress"},
            {".zip",    "application/zip"},
            {"",        "*/*"}
    };
    /**
     * 将字节转成K和 M单位
     * @param size
     * @return
     */
    public static String toSize(long size) {
        if (size >= M_1) {
            return getTwoNum(((float)size / M_1)) + " M";
        }
        if (size >= K_1) {
            return getTwoNum(((float)size / K_1)) + " K";
        }
        return size + " B";
    }
    private static float getTwoNum(float num) {
        return (float) ((int)(num * 100)) / 100;
    }

    /**
     * 读取文件；
     * @panhongyu
     * @return
     */
    public static String getString(File file) {
        InputStream InputStream = null;
        try {
            InputStream = new FileInputStream(file);
        } catch (Exception e) {
            e.printStackTrace();
        }
        InputStreamReader inputStreamReader = null;
        try {
            inputStreamReader = new InputStreamReader(InputStream, "gbk");
        } catch (UnsupportedEncodingException e1) {
            e1.printStackTrace();
        }
        BufferedReader reader = new BufferedReader(inputStreamReader);
        StringBuffer sb = new StringBuffer();
        String line;
        try {
            while ((line = reader.readLine()) != null) {
                sb.append(line);
                sb.append("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }


}
