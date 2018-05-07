package com.hzy.mybook.ui;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.hzy.mybook.R;
import com.hzy.mybook.controllor.ActivityCollector;
import com.hzy.mybook.db.UserDataBaseHelper;
import com.hzy.mybook.manager.UserManager;
import com.hzy.mybook.model.User;
import com.hzy.mybook.utils.ToastUtils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class MyCenterActivity extends AppCompatActivity implements View.OnClickListener{
    private LinearLayout msetup;
    private LinearLayout mtext;
    private LinearLayout mybookshelf;
    private LinearLayout shopping ;
    private TextView username ;
    private TextView uid ;
    private Button back_btn;
    private LinearLayout mycenter_line;
    private LinearLayout myCollection;
    private RelativeLayout back_rela;
    private UserDataBaseHelper uDBH;
    private static SQLiteDatabase db;
    private UserManager userManager;
    private LinearLayout myInfo;
    private User user = null ;//用户
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_center);
        uDBH = new UserDataBaseHelper(this);
        db = uDBH.getReadableDatabase();
        initUI();
        ActivityCollector.addActivity(this);
        mycenter_line.setVisibility(View.VISIBLE);
        back_rela.setVisibility(View.GONE);
    }

    private void initUI() {
        msetup = (LinearLayout)findViewById(R.id.setup);
        mtext = (LinearLayout) findViewById(R.id.statement);
        mybookshelf = (LinearLayout)findViewById(R.id.mybookshelf);
        shopping = (LinearLayout)findViewById(R.id.shopping);
        username = (TextView)findViewById(R.id.username);
        uid = (TextView)findViewById(R.id.uid);
        back_btn = (Button)findViewById(R.id.back_btn);
        mycenter_line = (LinearLayout)findViewById(R.id.my_center);
        myCollection = (LinearLayout)findViewById(R.id.collection);
        back_rela = (RelativeLayout)findViewById(R.id.back);
        myInfo = (LinearLayout)findViewById(R.id.my_info);
        //显示登录名和key
        if(userManager ==null){
            userManager = UserManager.instance();
            user = userManager.getUser();
            if(user!=null){
                username.setText(user.getLoginName());
                uid.setText(user.getKey());
            }else{
                username.setText("用户未登录");
                uid.setText("null");
            }
        }else{
            user = userManager.getUser();
            if(user!=null){
                username.setText(user.getLoginName());
                uid.setText(user.getKey());
            }else{
                username.setText("用户未登录");
                uid.setText("null");
            }
        }
        msetup.setOnClickListener(this);
        mtext.setOnClickListener(this);
        mybookshelf.setOnClickListener(this);
        shopping.setOnClickListener(this);
        back_btn.setOnClickListener(this);
        myInfo.setOnClickListener(this);
        myCollection.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.setup:
                goback();
                break;
            case R.id.statement:
                gotoStatement();
                break;
            case R.id.mybookshelf:
                gotoMyBookShelf();
                break;
            case R.id.collection:
                gotoMyCollect();
                break;
            case R.id.shopping:
                gotoMyShop();
                break;
            case R.id.back_btn:
                gotoLogin();
                break;
            case R.id.my_info:
                if("用户未登录".equals(username.getText().toString())){
                    Login();
                }else{
                    //查看个人信息；
                    lookUserInfo();
                }
                break;
            default:
                break;
        }
    }

    /**
     * 我的收藏；
     */
    private void gotoMyCollect() {
        if(checkUser()){
            Intent intent = new Intent(this,MyCollectionsActivity.class);
            startActivity(intent);
        }else{
            ToastUtils.showLong(this,"用户未登录不可查看！");
        }

    }

    //检查用户是否登陆；
    private boolean checkUser() {
       return  !"用户未登录".equals(username.getText().toString());
    }

    //打开推荐商城；
    private void gotoMyShop() {
        Intent intent = new Intent(this,WebViewActivity.class);
        startActivity(intent);
    }

    //打开书架；
    private void gotoMyBookShelf() {
        Intent intent = new Intent(this,MyBookActivity.class);
        startActivity(intent);
    }

    //查看个人信息；
    private void lookUserInfo() {
        Intent intent = new Intent(this,UserInfoActivity.class);
        startActivity(intent);
    }

    private void Login() {
        Intent intent = new Intent(this,LoginActivity.class);
        startActivity(intent);
    }

    private void goback() {
        if(userManager.getUser() != null){
            mycenter_line.setVisibility(View.GONE);
            back_rela.setVisibility(View.VISIBLE);
        }else{
//            back_btn.setText("未登录，点击进入登录");
            Toast.makeText(getApplicationContext(),"未登录，无法进入设置！",Toast.LENGTH_SHORT).show();
        }
    }

    private void gotoStatement() {
        Intent intent = new Intent(this,StatementActivity.class);
        startActivity(intent);
    }

    /**
     * 进入登录页面；
     */
    private void gotoLogin() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("确定要退出登录吗?");
        builder.setTitle("温馨提示");
        builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                Intent intent = new Intent(getApplicationContext(),LoginActivity.class);
                startActivity(intent);
                writefile();
            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.create().show();
    }

    private void writefile() {
        String rootDir = null ;
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            rootDir = Environment.getExternalStorageDirectory() + "/isLogin.txt";
        } else {
            rootDir = "/mnt/sdcard/isLogin.txt";
        }// mnt/sdcard
        File file = new File(rootDir);
        //判断文件是否存在，存在就删除
        if (file.exists()){
            file.delete();
        }
        try {
            //创建文件
            file.createNewFile();
            //给一个吐司提示，显示创建成功
//            Toast.makeText(getApplicationContext(), "文件创建成功", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
        }
        // write
        FileWriter fw = null;
        try {
            fw = new FileWriter(file, true);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write("no");
            bw.flush();
            bw.close();
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
