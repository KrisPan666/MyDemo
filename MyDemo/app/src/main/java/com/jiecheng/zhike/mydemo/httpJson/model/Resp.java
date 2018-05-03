package com.jiecheng.zhike.mydemo.httpJson.model;

import java.util.List;

/**
 * Created by 13159 on 2017/9/1.
 */

public class Resp {
    private int code;

    private List<Bank> data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public List<Bank> getData() {
        return data;
    }

    public void setData(List<Bank> data) {
        this.data = data;
    }
}
