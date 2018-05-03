package com.jiecheng.zhike.mydemo.draft.model;

/**
 * Created by 13159 on 2017/8/18.
 */

public class Subject {
    private int id;
    private String  subName;
    private String  subType;
    private String subNum;
    private String  time;
    private boolean isChecked;

    public Subject() {
    }

    public Subject(String subName, String subType, String subNum, String time) {
        this.subName = subName;
        this.subType = subType;
        this.subNum = subNum;
        this.time = time;
    }

    public Subject(int id, String subName, String subType, String subNum, String time, boolean isChecked) {
        this.id = id;
        this.subName = subName;
        this.subType = subType;
        this.subNum = subNum;
        this.time = time;
        this.isChecked = isChecked;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSubName() {
        return subName;
    }

    public void setSubName(String subName) {
        this.subName = subName;
    }

    public String getSubType() {
        return subType;
    }

    public void setSubType(String subType) {
        this.subType = subType;
    }

    public String getSubNum() {
        return subNum;
    }

    public void setSubNum(String subNum) {
        this.subNum = subNum;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    @Override
    public String toString() {
        return "Subject{" +
                "id=" + id +
                ", subName='" + subName + '\'' +
                ", subType='" + subType + '\'' +
                ", subNum=" + subNum +
                ", time='" + time + '\'' +
                '}';
    }
}
