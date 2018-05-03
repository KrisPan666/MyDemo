package com.jiecheng.zhike.mydemo;

import android.app.Application;

import com.facebook.stetho.Stetho;

/**
 * Created by 13159 on 2017/8/17.
 */

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Stetho.initializeWithDefaults(this);
    }
}
