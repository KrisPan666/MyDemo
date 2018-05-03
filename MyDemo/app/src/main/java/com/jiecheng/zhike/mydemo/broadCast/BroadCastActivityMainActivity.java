package com.jiecheng.zhike.mydemo.broadCast;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.jiecheng.zhike.mydemo.R;
import com.jiecheng.zhike.mydemo.comm.BaseActivity;

public class BroadCastActivityMainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.broadcast_activity_main);
        Button button = (Button)findViewById(R.id.MyBroadCastSend);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent("com.example.broadcastbestpratice.FORCE_OFFLINE");
                sendBroadcast(intent);
            }
        });
    }
}
