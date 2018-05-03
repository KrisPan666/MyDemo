package com.jiecheng.zhike.mydemo.httpJson.ui;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.jiecheng.zhike.mydemo.R;
import com.jiecheng.zhike.mydemo.comm.BaseActivity;
import com.jiecheng.zhike.mydemo.httpJson.adapter.BankAdapter;
import com.jiecheng.zhike.mydemo.httpJson.model.Bank;
import com.jiecheng.zhike.mydemo.httpJson.model.Resp;
import com.jiecheng.zhike.mydemo.httpJson.util.JsonController;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class HttpJsonMainActivity extends BaseActivity implements View.OnClickListener {
    private Button request_btn;
//    private TextView response_text;
    private List<Bank> list_bank;
    private List<Resp> list_resp;
    private JsonController jsonController = new JsonController();
    private BankAdapter bankAdapter;
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.httpjson_activity_http_json_main);
        request_btn = (Button)findViewById(R.id.requet_btn);
//        response_text =(TextView)findViewById(R.id.response_text);
        request_btn.setOnClickListener(this);
    }
    @Override
    public void onClick(View view) {
        if(view.getId()==R.id.requet_btn){
            //http+json;
//            sendRequestWithHttpURLConnection();
            //okhttp+gson;
            sendRequestWithokHttpURLConnection();
        }
    }

    /**
     * okHttp+Gson
     * 通过okHttp发送网络请求
     */
    private void sendRequestWithokHttpURLConnection() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                OkHttpClient client = new OkHttpClient();
                Request request = new Request.Builder()
                        .url("http://mdm.changyan.com/ireport/record/list?teacherid=1010000001000161460&page=1&limit=10&classid=")
                        .build();
                try {
                    Response response = client.newCall(request).execute();
                    String responseData = response.body().string();
                    URLDecoder decoder = new URLDecoder();
                    String respose2 = decoder.decode(responseData,"UTF-8");
                    Log.d("解码后的json字符串：",respose2);
                    System.out.println("解码后的json字符串："+respose2);
                    showResponse(respose2);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    /**
     * Http+json
     * 通过http发送网络请求；
     */
    private void sendRequestWithHttpURLConnection() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                HttpURLConnection connection = null;
                BufferedReader read = null;
                try {
                    URL url =new URL("http://mdm.changyan.com/ireport/record/list?teacherid=1010000001000161460&page=1&limit=10&classid=");
                    connection = (HttpURLConnection)url.openConnection();
                    connection.setRequestMethod("GET");
                    connection.setConnectTimeout(8000);
                    connection.setReadTimeout(8000);
                    InputStream in = connection.getInputStream();
                    read = new BufferedReader(new InputStreamReader(in));
                    //对获取到的输入流进行读取
                    StringBuilder response = new StringBuilder();
                    String line;
                    while ((line =  read.readLine())!=null){
                        response.append(line);
                    }
                    //URL解码。参考：http://blog.csdn.net/shadow_930/article/details/51436217
                    URLDecoder decoder = new URLDecoder();
                    String respose2 = decoder.decode(response.toString(),"UTF-8");
                    Log.d("解码后的json字符串：",respose2);
                    System.out.println("解码后的json字符串："+respose2);
                    showResponse(respose2);
                }catch (Exception e){
                    e.printStackTrace();
                }finally {
                    if(read!=null){
                        try {
                            read.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    if(connection!=null){
                        connection.disconnect();
                    }
                }
            }
        }).start();
    }
    private void showResponse(final String response) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                list_bank = jsonController.getjson(response);
//                list_bank = new GsonController().parseJSONWithGSON(response);
                bankAdapter = new BankAdapter(HttpJsonMainActivity.this,R.layout.httpjson_item_bank,list_bank);
                listView = (ListView)findViewById(R.id.bank_list_view);
                listView.setAdapter(bankAdapter);
//                response_text.setText(response);
            }
        });
    }
}
