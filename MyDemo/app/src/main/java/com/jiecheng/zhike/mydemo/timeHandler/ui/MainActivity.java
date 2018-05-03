package com.jiecheng.zhike.mydemo.timeHandler.ui;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.jiecheng.zhike.mydemo.R;
import com.jiecheng.zhike.mydemo.comm.BaseActivity;

public class MainActivity extends BaseActivity implements View.OnClickListener {
    public static final  int UPDATE_TEXT = 1;
    private Button button;
    private TextView textView;

    private Handler hd = new Handler(){
        public void handleMessage(Message msg){
            switch (msg.what){
                case UPDATE_TEXT:
                    //z在这里进行UI操作；
                    textView.setText("我要改变");
                    break;
                default:
                    break;
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.timehandler_activity_main);
        button = (Button)findViewById(R.id.changeText);
        textView = (TextView)findViewById(R.id.text);
        button.setOnClickListener(this);
    }
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.changeText:
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                       Message message = new Message();
                        message.what=UPDATE_TEXT;
                        hd.sendMessage(message);
                    }
                }).start();
            break;
            default:
                break;
        }
    }
}
