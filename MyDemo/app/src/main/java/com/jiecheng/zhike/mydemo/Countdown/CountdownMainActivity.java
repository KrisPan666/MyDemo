package com.jiecheng.zhike.mydemo.Countdown;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.jiecheng.zhike.mydemo.R;
import com.jiecheng.zhike.mydemo.comm.BaseActivity;

public class CountdownMainActivity extends BaseActivity implements View.OnClickListener {
    public static final int START_TIME = 1;
    private int tag = 0;//秒个位初始状态
    private int time = 60 * 60 * 60 ;

    private Button timeStart_btn1;
    private TextView sTv_time2;
    private TextView sTv_time1;
    private TextView mTv_time2;
    private TextView mTv_time1;
    private TextView hTv_time2;
    private TextView hTv_time1;
    private TextView mTv_test;
    private Handler hd = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case START_TIME:
                    while (time > 0) {
                        try{
                            Thread.sleep(1000);
                            time--;
                            int hh = time / 60 / 60 % 60;
                            int mm = time / 60 % 60;
                            int ss = time % 60;
                            Log.d("倒计时：","还剩" + hh + "小时" + mm + "分钟" + ss + "秒");
                            hTv_time1.setText(Integer.toString(hh));
                            mTv_time1.setText(Integer.toString(mm));
                            sTv_time1.setText(Integer.toString(ss));
//                        mTv_time1.setText("还剩：" + hh + "小时" + mm + "分钟" + ss + "秒");
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }

//                    int h = 0;//小时
//                    int d = 0;//分钟
//                    int s = 0;//秒
//                    int temp = (tag++)%3600;
//                    if(tag<=3600){
//                        d = tag/60;
//                        mTv_time1.setText(Integer.toString(d/10));
//                        mTv_time2.setText(Integer.toString(d%10));
//                        if(tag%60!=0){
//                            s = tag%60;
//                            sTv_time1.setText(Integer.toString(s/10));
//                            sTv_time2.setText(Integer.toString(s%10));
//                        }
//                        if(tag%60==0){
//                            sTv_time1.setText(Integer.toString(0));
//                            sTv_time2.setText(Integer.toString(0));
//                        }
//                    }else{
//                        h= tag/3600;
//                        if(temp!=0){
//                            if(temp>60){
//                                d = temp/60;
//                                mTv_time1.setText(Integer.toString(d/10));
//                                mTv_time2.setText(Integer.toString(d%10));
//                                if(temp%60!=0){
//                                    s = temp%60;
//                                    sTv_time1.setText(Integer.toString(s/10));
//                                    sTv_time2.setText(Integer.toString(s%10));
//                                }
//                            }else{
//                                s = temp;
//                                sTv_time1.setText(Integer.toString(s/10));
//                                sTv_time2.setText(Integer.toString(s%10));
//                            }
//                        }
//                    }

            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.timehandler_show_layout);
        timeStart_btn1 = (Button)findViewById(R.id.start1);
        sTv_time2 = (TextView)findViewById(R.id.s_time2);
        sTv_time1 = (TextView)findViewById(R.id.s_time1);
        mTv_time2 = (TextView)findViewById(R.id.m_time2);
        mTv_time1 = (TextView)findViewById(R.id.m_time1);
        hTv_time2 = (TextView)findViewById(R.id.h_time2);
        hTv_time1 = (TextView)findViewById(R.id.h_time1);
        mTv_test = (TextView)findViewById(R.id.str);
        int hh = time / 60 / 60 % 60;
        int mm = time / 60 % 60;
        int ss = time % 60;
        hTv_time1.setText(Integer.toString(hh));
        mTv_time1.setText(Integer.toString(mm));
        sTv_time1.setText(Integer.toString(ss));
        timeStart_btn1.setOnClickListener(this);
    }
    @Override
    public void onClick(final View view) {
        switch (view.getId()){
            case R.id.start1:
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Looper.prepare();
                        while(true){
                            Message message = new Message();
                            message.what=START_TIME;
                            hd.sendMessage(message);
//                            hd.sendEmptyMessageDelayed(START_TIME, 1000);
//                            try {
//                                Thread.sleep(1000);
//                            } catch (InterruptedException e) {
//                                e.printStackTrace();
//                            }
                        }
                    }
                }).start();
                Looper.loop();
                break;
            default:
                break;
        }
    }
}
