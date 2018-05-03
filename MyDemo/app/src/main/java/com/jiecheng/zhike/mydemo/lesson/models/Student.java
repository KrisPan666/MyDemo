package com.jiecheng.zhike.mydemo.lesson.models;

/**
 * Created by 13159 on 2017/8/9.
 */

public class Student {
    private String id;
    private String loginName;
    private String userName;
    private String userPhoto;
    private String banStatus;

    public Student() {
    }
    public Student(String id, String loginName, String userName, String userPhoto, String banStatus) {
        this.id = id;
        this.loginName = loginName;
        this.userName = userName;
        this.userPhoto = userPhoto;
        this.banStatus = banStatus;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPhoto() {
        return userPhoto;
    }

    public void setUserPhoto(String userPhoto) {
        this.userPhoto = userPhoto;
    }

    public String getBanStatus() {
        return banStatus;
    }

    public void setBanStatus(String banStatus) {
        this.banStatus = banStatus;
    }

    @Override
    public String toString() {
        return "Student{" +
                "id='" + id + '\'' +
                ", loginName='" + loginName + '\'' +
                ", userName='" + userName + '\'' +
                ", userPhoto='" + userPhoto + '\'' +
                ", banStatus='" + banStatus + '\'' +
                '}';
    }
}
