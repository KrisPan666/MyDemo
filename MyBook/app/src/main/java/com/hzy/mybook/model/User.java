package com.hzy.mybook.model;

/**
 * Created by 13159 on 2018/1/25.
 */

public class User {
    private int id ;//主键id
    private String key ;//爱阅账号
    private String loginName ;//用户登录名，手机、邮箱、自定义
    private String passWord ;
//    private String realName ;
//    private int age ;
//    private String sex ;
//    private String address ;
//    private String phone ;
//    private String email ;
//    private String Motto ;
    private String strExt ;

    public User() {
    }

    public User(String key, String loginName, String passWord, String strExt) {
        this.key = key;
        this.loginName = loginName;
        this.passWord = passWord;
        this.strExt = strExt;
    }

    public User(int id, String key, String loginName, String passWord, String strExt) {
        this.id = id;
        this.key = key;
        this.loginName = loginName;
        this.passWord = passWord;
        this.strExt = strExt;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    public String getStrExt() {
        return strExt;
    }

    public void setStrExt(String strExt) {
        this.strExt = strExt;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", key='" + key + '\'' +
                ", loginName='" + loginName + '\'' +
                ", passWord='" + passWord + '\'' +
                ", strExt='" + strExt + '\'' +
                '}';
    }
}
