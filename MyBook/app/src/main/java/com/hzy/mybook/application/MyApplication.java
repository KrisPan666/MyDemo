package com.hzy.mybook.application;

import android.app.Application;

import com.facebook.stetho.Stetho;

/**
 * Created by 13159 on 2018/2/3.
 */

public class MyApplication extends Application {
    public void onCreate() {
        super.onCreate();
        Stetho.initializeWithDefaults(this);
    }
}
