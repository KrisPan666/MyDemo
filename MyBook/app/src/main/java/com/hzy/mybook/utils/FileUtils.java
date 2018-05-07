package com.hzy.mybook.utils;

import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.RandomAccessFile;

public class FileUtils {
    private static final String[] filters = {".txt", ".xml", ".zip"};

    // 过滤不是以".txt", ".xml", ".zip" 结尾的文件，不过滤文件夹
    public static File[] read(File file) {
        if (file.isDirectory()) {// 是文件夹
            File[] files = file.listFiles(new FileFilter() {// 设置过滤条件

                public boolean accept(File pathname) {
                    if (pathname.isDirectory()) {// 是文件夹，true不过滤
                        return true;
                    }
                    boolean flag = false;
                    if (pathname.isFile()) {// 是文件
                        String fileName = pathname.getName().toLowerCase();
                        for (int i = 0; i < filters.length; i++) {
                            if (fileName.endsWith(filters[i])) {// 传进来的文件时以".txt",
                                // ".xml",
                                // ".zip"结尾的，就true不过滤
                                flag = true;
                                break;
                            }
                        }
                    }
                    return flag;
                }
            });

            // for (int i = 0; i < files.length; i++) {
            // System.out.println(files[i].getName());
            // }
            return files;
        }
        return null;
    }

    public static String readString(File file, int begin, int size) {
        try {
            DataInputStream dataInputStream = new DataInputStream(new FileInputStream(file));
            Log.d("MyBookActivity", "文件总长度：" + file.length());
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            // 从什么位置开始读
            dataInputStream.skip(begin);
            // 读多少数据
            byte[] b = new byte[size];
            int i = -1;
            if ((i = dataInputStream.read(b, 0, b.length)) != -1) {
                out.write(b, 0, i);
                // randomAccessFile.skipBytes(1024);
                String string = out.toString("UTF-8");
                Log.d("MyBookActivity", "begin：" + begin);
                Log.d("MyBookActivity", "size：" + size);
                // Log.d("MyBookActivity", "读取的文本：" + string);
                return string;
            }
            out.close();
            dataInputStream.close();
        } catch (Exception e) {
            Log.d("MyBookActivity", e.getMessage());
        }
        return null;
    }

    public static String readString0(File file, int begin, int size) {
        try {
            RandomAccessFile randomAccessFile = new RandomAccessFile(file, "r");
            Log.d("MyBookActivity", "文件总长度：" + randomAccessFile.length());
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            // 从什么位置开始读
            randomAccessFile.seek(begin);
            // 读多少数据
            byte[] b = new byte[size];
            int i = -1;
            if ((i = randomAccessFile.read(b, 0, b.length)) != -1) {
                out.write(b, 0, i);
                String string = out.toString("UTF-8");
                Log.d("MyBookActivity", "begin：" + begin);
                Log.d("MyBookActivity", "size：" + size);
                Log.d("MyBookActivity", "FilePointer：" + randomAccessFile.getFilePointer());
                // Log.d("MyBookActivity", "读取的文本：" + string);
                return string;
            }
            out.close();
            randomAccessFile.close();
        } catch (Exception e) {
            Log.d("MyBookActivity", e.getMessage());
        }
        return null;
    }

}
