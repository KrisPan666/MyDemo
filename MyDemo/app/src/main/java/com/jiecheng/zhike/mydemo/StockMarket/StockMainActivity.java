package com.jiecheng.zhike.mydemo.StockMarket;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.jiecheng.zhike.mydemo.R;
import com.jiecheng.zhike.mydemo.StockMarket.Utils.ExtMarketOilStockParse;
import com.jiecheng.zhike.mydemo.comm.BaseActivity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class StockMainActivity extends BaseActivity implements View.OnClickListener{
    private TextView textView;
    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.stock_main_layout);
        textView = (TextView)findViewById(R.id.text);
        button = (Button)findViewById(R.id.btn);

        button.setOnClickListener(this);
    }
    @Override
    public void onClick(View view) {
        if(R.id.btn == view.getId()){
            toast("开始爬数据！！");
            new Thread(new Runnable() {
                @Override
                public void run() {
                    OkHttpClient client = new OkHttpClient();
                    Request request = new Request.Builder()
                            .url(getStr())
                            .build();
                    try {
                        Response response = client.newCall(request).execute();

                        String responseData = response.body().string();

//                        toast("Body部分是："+responseData);
//                        URLDecoder decoder = new URLDecoder();
//                        String respose2 = decoder.decode(responseData,"UTF-8");
                        Log.d("解码后的json字符串：", responseData);
                        System.out.println("解码后的json字符串："+ responseData);

                        List<ExtMarketOilStockModel> oilstocks = new ArrayList<ExtMarketOilStockModel>();
                        try {
                            String str = "";
                            oilstocks= ExtMarketOilStockParse.parseurl(responseData);
                            for(int i = 0 ; i<oilstocks.size() ; i ++){
                                str+=oilstocks.get(i).toString();
                            }
                            showReponse(str);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }
    }
    private String getStr(){
        List<String> urloillist=new ArrayList<String>();
        List<String> urlcarlist=new ArrayList<String>();
        List<ExtMarketOilStockModel> oilstocks=new ArrayList<ExtMarketOilStockModel>();
        List<ExtMarketOilStockModel> carstocks=new ArrayList<ExtMarketOilStockModel>();
        //石油相关股票就两页，对应两个地址
        String url1="http://nufm.dfcfw.com/EM_Finance2014NumericApplication/JS.aspx?type=CT&cmd=C.BK04641&sty=FCOIATA&sortType=C&sortRule=-1&page=1&pageSize=20&js=var%20quote_123%3d{rank:[(x)],pages:(pc)}&token=7bc05d0d4c3c22ef9fca8c2a912d779c&jsName=quote_123&_g=0.13204790262127375";
//        String url1="http://quote.eastmoney.com/center/list.html#70_2";
        String url2="http://nufm.dfcfw.com/EM_Finance2014NumericApplication/JS.aspx?type=CT&cmd=C.BK04641&sty=FCOIATA&sortType=C&sortRule=-1&page=2&pageSize=20&js=var%20quote_123%3d{rank:[(x)],pages:(pc)}&token=7bc05d0d4c3c22ef9fca8c2a912d779c&jsName=quote_123&_g=0.6972178580603532";
        urloillist.add(url1);
        urloillist.add(url2);
//        for (int i = 0; i < urloillist.size(); i++) {
            //解析url
//            try {
//                oilstocks= ExtMarketOilStockParse.parseurl(url1);
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
            //存储每页的数据
//            MYSQLControl.insertoilStocks(oilstocks);
//        }
        return "http://quote.stockstar.com/stock/ranklist_a_3_1_1.html";
    }

    private void showReponse(final String str){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                textView.setText(str);
            }
        });
    }
}
