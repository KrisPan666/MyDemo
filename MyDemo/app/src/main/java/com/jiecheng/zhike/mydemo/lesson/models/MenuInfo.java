package com.jiecheng.zhike.mydemo.lesson.models;

/**
 * Created by 13159 on 2017/8/8.
 */

public class MenuInfo {
    private int id;
    private String name;
    private int pos;


    public MenuInfo(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    @Override
    public String toString() {
        return "MenuInfo{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", pos=" + pos +
                '}';
    }

}
