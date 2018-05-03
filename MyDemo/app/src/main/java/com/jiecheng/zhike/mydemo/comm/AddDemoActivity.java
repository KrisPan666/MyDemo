package com.jiecheng.zhike.mydemo.comm;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.jiecheng.zhike.mydemo.Countdown.CountdownMainActivity;
import com.jiecheng.zhike.mydemo.LoveChat.LoveChatClientActivity;
import com.jiecheng.zhike.mydemo.R;
import com.jiecheng.zhike.mydemo.Screenshot.ScreenshotMainActivity;
import com.jiecheng.zhike.mydemo.StockMarket.StockMainActivity;
import com.jiecheng.zhike.mydemo.Utils.PxAndDpActivity;
import com.jiecheng.zhike.mydemo.broadCast.BroadCastActivityMainActivity;
import com.jiecheng.zhike.mydemo.browser.BrowserMainActivity;
import com.jiecheng.zhike.mydemo.draft.adapter.mainAdapter;
import com.jiecheng.zhike.mydemo.draft.ui.Draft.DrafListActivity;
import com.jiecheng.zhike.mydemo.filelist.FileListActivity;
import com.jiecheng.zhike.mydemo.httpJson.ui.HttpJsonMainActivity;
import com.jiecheng.zhike.mydemo.httpJson.ui.WebViewMainActivity;
import com.jiecheng.zhike.mydemo.lesson.Activitys.MainActivity;
import com.jiecheng.zhike.mydemo.photoUpload.PhotoUploadActivity;
import com.jiecheng.zhike.mydemo.sort.SortMainActivity;

import java.util.ArrayList;
import java.util.List;

public class AddDemoActivity extends BaseActivity implements View.OnClickListener {
    private static String TAG = "AddDemoActivity";
    private ListView listView;
    private List<Demo> list= new ArrayList<Demo>();;
    private  Demo demo;
    private mainAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main2_layout);
        list = initDemo();
        mAdapter = new mainAdapter(this,R.layout.main_item_layout,list);
        listView = (ListView)findViewById(R.id.demo_listView);
        listView.setAdapter(mAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                //Demo选择；
                selectDemo(position);
            }
        });
    }
    /**
     * 一些触发事件，例如返回、编辑等一些操作；
     * @param view
     */
    @Override
    public void onClick(View view) {

    }
    /**
     * 新增Demo，并将Demo放进List；
     * @return
     */
    private List<Demo> initDemo(){
            demo = new Demo("草稿箱","2017-08-21");
            list.add(demo);
            demo = new Demo("文件管理器","2017-08-28 18:43");
            list.add(demo);
            demo = new Demo("Lesson","2017-08-28 20:13");
            list.add(demo);
            demo = new Demo("BroadCast","2017-08-29 9:14");
            list.add(demo);
            demo = new Demo("线程（一）","2017-08-30 11:31");
            list.add(demo);
            demo = new Demo("线程（二）--定时器","2017-08-30 13:59");
            list.add(demo);
            demo = new Demo("WebView","2017-08-31 10:22");
            list.add(demo);
            demo = new Demo("HttpJson","2017-08-31 13:01");
            list.add(demo);
            demo = new Demo("浏览器","2017-09-01 15:13");
            list.add(demo);
            demo = new Demo("聊天室","2017-09-05 11:18");
            list.add(demo);
            demo = new Demo("截屏","2017-09-12 10:09");
            list.add(demo);
            demo = new Demo("px和dp间的转换","2017-11-3 9:13");
            list.add(demo);
            demo = new Demo("股票市场","2017-11-10 11:51");
            list.add(demo);
            demo = new Demo("倒计时","2017-12-26 17:24");
            list.add(demo);
            demo = new Demo("系统相册上传照片","2018-4-3 12:00");
            list.add(demo);
            demo = new Demo("排序","2018-4-20 9:13");
            list.add(demo);
        return list;
    }
    /**
     * 项目选择器；
     */
    private void selectDemo(int position){
        Demo demo = (Demo) mAdapter.getItem(position);
        if(demo!=null && !demo.getDemoName().isEmpty()) {
            if (demo.getDemoName().equals("草稿箱")) {
                Intent intent1 = new Intent(AddDemoActivity.this, DrafListActivity.class);
                startActivity(intent1);
            }
            else if(demo.getDemoName().equals("文件管理器")){
                Intent intent2 = new Intent(AddDemoActivity.this, FileListActivity.class);
                startActivity(intent2);
            }
            else if(demo.getDemoName().equals("Lesson")){
                Intent intent3 = new Intent(AddDemoActivity.this, MainActivity.class);
                startActivity(intent3);
            }
            else if(demo.getDemoName().equals("BroadCast")){
                Intent intent4 = new Intent(AddDemoActivity.this, BroadCastActivityMainActivity.class);
                startActivity(intent4);
            }
            else if(demo.getDemoName().equals("线程（一）")){
                Intent intent5 = new Intent(AddDemoActivity.this, com.jiecheng.zhike.mydemo.timeHandler.ui.MainActivity.class);
                startActivity(intent5);
            }
            else if(demo.getDemoName().equals("线程（二）--定时器")){
                Intent intent6 = new Intent(AddDemoActivity.this, com.jiecheng.zhike.mydemo.timeHandler.ui.TimeHandlerActivity.class);
                startActivity(intent6);
            }
            else if(demo.getDemoName().equals("WebView")){
                Intent intent7 = new Intent(AddDemoActivity.this, WebViewMainActivity.class);
                startActivity(intent7);
            }
            else if(demo.getDemoName().equals("HttpJson")){
                Intent intent8 = new Intent(AddDemoActivity.this, HttpJsonMainActivity.class);
                startActivity(intent8);
            }
            else if(demo.getDemoName().equals("浏览器")){
                Intent intent9 = new Intent(AddDemoActivity.this, BrowserMainActivity.class);
                startActivity(intent9);
            }
            else if(demo.getDemoName().equals("聊天室")){
                Intent intent10 = new Intent(AddDemoActivity.this, LoveChatClientActivity.class);
                startActivity(intent10);
            }
            else if(demo.getDemoName().equals("截屏")){
                Intent intent11 = new Intent(AddDemoActivity.this, ScreenshotMainActivity.class);
                startActivity(intent11);
            }
            else if(demo.getDemoName().equals("px和dp间的转换")){
                Intent intent12 = new Intent(AddDemoActivity.this, PxAndDpActivity.class);
                startActivity(intent12);
            }
            else if(demo.getDemoName().equals("股票市场")){
                Intent intent13 = new Intent(AddDemoActivity.this, StockMainActivity.class);
                startActivity(intent13);
            }
            else if(demo.getDemoName().equals("倒计时")){
                Intent intent14 = new Intent(AddDemoActivity.this, CountdownMainActivity.class);
                startActivity(intent14);
            }
            else if(demo.getDemoName().equals("系统相册上传照片")){
                Intent intent15 = new Intent(AddDemoActivity.this, PhotoUploadActivity.class);
                startActivity(intent15);
            }
            else if(demo.getDemoName().equals("排序")){
                Intent intent16 = new Intent(AddDemoActivity.this, SortMainActivity.class);
                startActivity(intent16);
            }
        }
    }
}
