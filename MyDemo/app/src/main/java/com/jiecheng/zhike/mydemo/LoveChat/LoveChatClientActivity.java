package com.jiecheng.zhike.mydemo.LoveChat;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.jiecheng.zhike.mydemo.R;
import com.jiecheng.zhike.mydemo.comm.BaseActivity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class LoveChatClientActivity extends BaseActivity implements View.OnClickListener {
    EditText show,msg;
    Button send;
    Handler handler;
    Socket client;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lovechat_client_layout);
        handler = new MyHandler();
        show = (EditText) findViewById(R.id.show);
        msg = (EditText) findViewById(R.id.msg);
        send = (Button) findViewById(R.id.send);
        send.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        String message = msg.getText().toString();
//   System.out.println("msg:"+message);
        new EchoThread(LoveChatClientActivity.this,message).start();
    }
    public class MyHandler extends Handler{
        @Override
        public void handleMessage(Message msg) {
            switch(msg.what){
                case 1:
                    Toast.makeText(LoveChatClientActivity.this, "建立连接失败", Toast.LENGTH_LONG).show();
                    break;
                case 2:
                    String message = (String) msg.obj;
                    System.out.println("Handler:"+message);
                    show.append("\n"+message);
                    break;
            }
        }
    }
    private class EchoThread extends Thread {
        private Context context;
        private String msg;

        EchoThread(Context context, String msg) {
            this.context = context;
            this.msg = msg;
        }
        public void run() {
            if (client == null) {
                try {
                    client = new Socket("10.5.116.64", 1111);
                } catch (IOException e) {
                    Message message = new Message();
                    message.what = 1;
                    handler.sendMessage(message);
                }
            }
            System.out.println("建立连接");
            try {
                BufferedReader in;
                BufferedReader input;
                PrintWriter out;
                in = new BufferedReader(new InputStreamReader(client.getInputStream()));
                out = new PrintWriter(client.getOutputStream());
                String line = msg;
                if (!"bye".equals(line)) {
                    System.out.println("line:" + line);
                    out.println(line);
                    out.flush();
                    String echo = in.readLine();
                    System.out.println("server:" + echo);
                    Message message = new Message();
                    message.obj = echo;
                    message.what = 2;
                    handler.sendMessage(message);
                }
            } catch (Exception e) {

            }
        }
      }
    }
