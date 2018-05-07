package com.hzy.mybook.model;

import java.text.DecimalFormat;
import java.util.Date;

/**
 * Created by 13159 on 2018/1/12.
 */

public class Book {
    private static final DecimalFormat decimalFormat = new DecimalFormat("0.00");
    private Long id;
    private String name;// 书名
    private String password;// 密码
    private String filePath;// 文件路径
    private Date lastReadTime;// 最后阅读时间
    private int begin = 0;// 从那里开始看书
    private String progress;// 进度比例
    private String isCollected = "no";// 是否被收藏

    public Book() {
    }

    public Book(Long id, String name, String password, String filePath, Date lastReadTime, int begin, String progress) {
        this.id = id;
        this.name = name;
        this.password = password;
        this.filePath = filePath;
        this.lastReadTime = lastReadTime;
        this.begin = begin;
        this.progress = progress;
    }
    public Book(String name, String filePath, Date lastReadTime, int begin, String progress) {
        this.name = name;
        this.filePath = filePath;
        this.lastReadTime = lastReadTime;
        this.begin = begin;
        this.progress = progress;
//        this.isCollected = isCollected;

    }

//    public Book(String name, String filePath, Date lastReadTime, int begin, String progress) {
//        this.name = name;
//        this.filePath = filePath;
//        this.lastReadTime = lastReadTime;
//        this.begin = begin;
//        this.progress = progress;
//    }



    public static DecimalFormat getDecimalFormat() {
        return decimalFormat;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public Date getLastReadTime() {
        return lastReadTime;
    }

    public void setLastReadTime(Date lastReadTime) {
        this.lastReadTime = lastReadTime;
    }

    public int getBegin() {
        return begin;
    }

    public void setBegin(int begin) {
        this.begin = begin;
    }

    public String getProgress() {
        return progress;
    }

    public void setProgress(String progress) {
        this.progress = progress;
    }
//
    public String isCollected() {
        return isCollected;
    }

    public void setCollected(String collected) {
        isCollected = collected;
    }
    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", password='" + password + '\'' +
                ", filePath='" + filePath + '\'' +
                ", lastReadTime=" + lastReadTime +
                ", begin=" + begin +
                ", progress='" + progress + '\'' +
                '}';
    }
}
