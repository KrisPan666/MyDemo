package com.jiecheng.zhike.mydemo.filelist;

import java.io.File;
import java.util.HashMap;

/**
 * author by panhongyu(@hypan3.iflytek.com)
 * Created by 13159 on 2017/7/31.
 */

public class FileInfo {

    private static long M_1 = 1024 * 1024;
    private static long K_1 = 1024;

    private String name;
    private int imageId;
    private File path;
    private long size;
    private long lastModified;
    private String date;
    private String fileCount;

    public String getFileCount() {
        return fileCount;
    }

    public void setFileCount(String fileCount) {
        this.fileCount = fileCount;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
    //    private FileType fileType;

    public FileInfo(int imageId, String name){
        this.imageId=imageId;
        this.name=name;
    }

    public FileInfo() {
    }

//    public FileType getFileType() {
//        return fileType;
//    }
//
//    public void setFileType(FileType fileType) {
//        this.fileType = fileType;
//    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }

    public File getPath() {
        return path;
    }

    public void setPath(File path) {
        this.path = path;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public long getLastModified() {
        return lastModified;
    }

    public void setLastModified(long lastModified) {
        this.lastModified = lastModified;
    }

    public boolean isDir() {
        return path.isDirectory();
    }

    private static HashMap<String, FileType> fileExtNameMap = new HashMap<>();

    public static FileType getFileType(String fileExtName) {
        if (!fileExtNameMap.containsKey(fileExtName)) {
            return FileType.FILE_ICON_OTHER;
        }
        return fileExtNameMap.get(fileExtName);
    }

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

}
