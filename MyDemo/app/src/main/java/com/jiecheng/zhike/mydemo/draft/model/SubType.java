package com.jiecheng.zhike.mydemo.draft.model;

/**
 * Created by 13159 on 2017/8/18.
 */

public class SubType {
    private String type;

    public SubType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "SubType{" +
                "type='" + type + '\'' +
                '}';
    }
}
