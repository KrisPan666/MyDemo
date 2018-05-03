package com.jiecheng.zhike.mydemo.broadCast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

/**
 * Created by 13159 on 2017/8/29.
 */

public class AnotherBroadcastReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Toast.makeText(context,"reveiver in AnotherReceiverBroadCast",Toast.LENGTH_LONG).show();
        abortBroadcast();
    }
}
