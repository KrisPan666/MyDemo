package com.jiecheng.zhike.mydemo.comm;

import android.app.Activity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 13159 on 2017/8/29.
 */

public class ActivityCollector {
    public static List<Activity> activitis = new ArrayList<Activity>();

    //增加Activity
    public static void addActivity(Activity activity){
        activitis.add(activity);
    }
    public static List<Activity> getActivitis(){
        return activitis;
    }
    //移除Activity
    public static void removeActivity(Activity activity){
        activitis.remove(activity);
    }
    //结束所有的Activity
    public static void finishAll(){
        for(Activity activity:activitis){
            if(!activity.isFinishing()){
                activity.finish();
            }
        }
    }
}
