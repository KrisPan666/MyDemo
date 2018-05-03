package com.jiecheng.zhike.mydemo.broadCast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;

import com.jiecheng.zhike.mydemo.comm.ActivityCollector;
import com.jiecheng.zhike.mydemo.comm.LoginActivity;

/**
 * Created by 13159 on 2017/8/29.
 */

public class ForceOfflineReceiver extends BroadcastReceiver {
    /**
     * 实现强制下线；
     */
    @Override
    public void onReceive(final Context context, Intent intent) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Warning!");
        builder.setMessage("You are forced to be offline,Please try to login again!");
        builder.setCancelable(false);
        builder.setPositiveButton("", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                ActivityCollector.finishAll();//销毁所有活动
                Intent intent1 = new Intent(context,LoginActivity.class);
                context.sendBroadcast(intent1);//重新启动的登录；
            }
        });
        builder.show();
    }
}
