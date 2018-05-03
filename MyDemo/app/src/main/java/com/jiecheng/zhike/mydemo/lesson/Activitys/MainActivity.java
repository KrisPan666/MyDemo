package com.jiecheng.zhike.mydemo.lesson.Activitys;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.jiecheng.zhike.mydemo.R;
import com.jiecheng.zhike.mydemo.lesson.adapters.MenuLeftAdapter;
import com.jiecheng.zhike.mydemo.lesson.models.MenuInfo;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    List<MenuInfo> infoList = new ArrayList<MenuInfo>();
    MenuLeftAdapter menuLeftAdapter;
    ListView listView;
    TextView textView;
    MenuInfo menuInfo;
    Button button1;
    Button button2;
    Button button3;
    Button button4;
//    Button button5;
    int position;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lesson_activity_main);
        //1.隐藏原有的title；
        hide();
        //2.初始化Menu数据；
        initMenu();
        //3.绑定事件；
        bindEvent();
    }
    //隐藏原有的标题；
    private void hide() {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
    }
    //初始化左侧菜单；
   private void initMenu(){
       int imageId1 = R.mipmap.baiban;
       int imageId2 = R.mipmap.jujiao;
       int imageId3 = R.mipmap.xuanze;
       int imageId4 = R.mipmap.huabi;
       int imageId5 = R.mipmap.banca;
       int imageId6 = R.mipmap.hudong;
       int imageId7 = R.mipmap.fenxiang;
       int imageId8 = R.mipmap.luzhi;
       int imageId9 = R.mipmap.kuaizhao;
       int imageId10 = R.mipmap.ic_launcher_round;
       MenuInfo menuInfo1 = new MenuInfo(imageId1,"白板");
       infoList.add(menuInfo1);
       MenuInfo menuInfo2= new MenuInfo(imageId2,"聚焦");
       infoList.add(menuInfo2);
       MenuInfo menuInfo3 = new MenuInfo(imageId3,"选择");
       infoList.add(menuInfo3);
       MenuInfo menuInfo4 = new MenuInfo(imageId4,"画笔");
       infoList.add(menuInfo4);
       MenuInfo menuInfo5 = new MenuInfo(imageId5,"板擦");
       infoList.add(menuInfo5);
       MenuInfo menuInfo6 = new MenuInfo(imageId6,"互动");
       infoList.add(menuInfo6);
       MenuInfo menuInfo7 = new MenuInfo(imageId7,"分享");
       infoList.add(menuInfo7);
       MenuInfo menuInfo8 = new MenuInfo(imageId8,"录制");
       infoList.add(menuInfo8);
       MenuInfo menuInfo9 = new MenuInfo(imageId9,"快照");
       infoList.add(menuInfo9);
       MenuInfo menuInfo10 = new MenuInfo(imageId10,"未知");
       infoList.add(menuInfo10);
       MenuInfo menuInfo11 = new MenuInfo(imageId10,"未知");
       infoList.add(menuInfo11);
       MenuInfo menuInfo12 = new MenuInfo(imageId10,"未知");
       infoList.add(menuInfo12);
       MenuInfo menuInfo13 = new MenuInfo(imageId10,"未知");
       infoList.add(menuInfo13);
       MenuInfo menuInfo14 = new MenuInfo(imageId10,"未知");
       infoList.add(menuInfo14);
    }
    //绑定事件；
    private void bindEvent(){
        menuLeftAdapter = new MenuLeftAdapter(this,R.layout.lesson_item_menu_layout,infoList);
        listView = (ListView)findViewById(R.id.left_list);
        listView.setAdapter(menuLeftAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                MenuInfo menuInfo = menuLeftAdapter.getItem(i);
                Log.d("能否得到menuInfo:",menuInfo.toString());
                    showPenDialog(menuInfo);
            }
        });
        button1 = (Button)findViewById(R.id.Myparent);
        button2 = (Button)findViewById(R.id.video);
        button3 = (Button)findViewById(R.id.FragmentDemo);
        button4 = (Button)findViewById(R.id.FragmentDemo2);
//        button5 = (Button)findViewById(R.id.FragmentDemo2);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(MainActivity.this,StudentActivity.class);
                startActivity(intent1);
            }
        });
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent2 = new Intent(MainActivity.this,VideoActivity.class);
                startActivity(intent2);
            }
        });
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent3 = new Intent(MainActivity.this,FragmentActivity.class);
                startActivity(intent3);
            }
        });
        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent4 = new Intent(MainActivity.this,NewsActivity.class);
                startActivity(intent4);
            }
        });


    }
    private void showPenDialog(MenuInfo info) {
        if(info.getName().equals("互动")){
            DialogInterface.OnClickListener listener;
            listener = new DialogInterface.OnClickListener()
            {
                @Override
                public void onClick(DialogInterface dialog, int which)
                {
                    finish();
                }
            };
            final CharSequence msg = "确定执行吗？";
            final AlertDialog builder = new AlertDialog.Builder(MainActivity.this)
                    .create();
            builder.show();
            builder.getWindow().setContentView(R.layout.lesson_activity_my_dialog);//设置弹出框加载的布局
            TextView tv_title = (TextView) builder.findViewById(R.id.tv_dialog_title);
            tv_title.setText(msg);
            builder.getWindow()
                    .findViewById(R.id.button2)
                    .setOnClickListener(new View.OnClickListener() {  //按钮点击事件
                        @Override
                        public void onClick(View v) {
                            builder.dismiss();
                        }
                    });
        }
    }
    @Override
    public void onClick(View view) {

    }
}
