package com.jiecheng.zhike.mydemo.comm;

/**
 * Created by 13159 on 2017/8/26.
 */

public class Demo {
    private String demoName;
    private String time;

    public Demo() {
    }

    public Demo(String demoName, String time) {
        this.demoName = demoName;
        this.time = time;
    }

    public String getDemoName() {
        return demoName;
    }

    public void setDemoName(String demoName) {
        this.demoName = demoName;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return "Demo{" +
                "demoName='" + demoName + '\'' +
                ", time='" + time + '\'' +
                '}';
    }
}
