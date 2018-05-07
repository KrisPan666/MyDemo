package com.hzy.mybook.ui;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.hzy.mybook.R;
import com.hzy.mybook.controllor.ActivityCollector;
import com.hzy.mybook.db.UserDataBaseHelper;
import com.hzy.mybook.manager.UserManager;
import com.hzy.mybook.model.User;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * 登录相关控制
 * author：潘洪雨
 * date: 2018/02/02
 */
public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText mAccount_et;
    private EditText mPassword_et;
    private Button mLogin_btn ;
    private Button mRegister_btn ;
    private UserManager userManager ;
    private UserDataBaseHelper uDBH;
    private static SQLiteDatabase db;
    //验证码验证；
    private EditText ver_edt;
    private TextView ver_bg;
    private TextView ver_get;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initUI();
        getVer();
        uDBH = new UserDataBaseHelper(this);
        db = uDBH.getReadableDatabase();
        ActivityCollector.addActivity(this);
    }

    private void initUI() {
        mAccount_et = (EditText)findViewById(R.id.account);
        mPassword_et = (EditText)findViewById(R.id.password);
        mLogin_btn = (Button)findViewById(R.id.button_login);
        mRegister_btn = (Button)findViewById(R.id.button_register);
        ver_edt = (EditText)findViewById(R.id.ver_value);
        ver_bg = (TextView) findViewById(R.id.ver_bg);
        ver_get = (TextView) findViewById(R.id.ver_get);
        mAccount_et.setText("");
        mPassword_et.setText("");

        ver_get.setOnClickListener(this);
        mLogin_btn.setOnClickListener(this);
        mRegister_btn.setOnClickListener(this);
        ver_bg.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.button_login:
                login();
                break;
            case R.id.button_register:
                register();
                break;
            case R.id.ver_get:
                getVer();
                break;
            case R.id.ver_bg:
                getVer();
                break;
            default:
                break;
        }
    }

    //随机生成验证码；
    private void getVer() {
        String[] str1 = {"1","2","3","4","5","6","7","8","9","0","a","b","c","d","e","fghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ"};
        List<String> list = new ArrayList<String>();
        for(int i = 0 ;i<10 ; i++){
            list.add(""+i);
        }
        list.add("a");list.add("e");list.add("c");list.add("y");list.add("p");list.add("t");list.add("m");list.add("f");list.add("q");
        list.add("w");list.add("v");list.add("b");list.add("F");list.add("S");list.add("C");list.add("J");list.add("K");list.add("O");
        list.add("E");list.add("L");list.add("A");list.add("R");
        List<String> list2 = new ArrayList<String>();
        //随机的网list2添加字符；
        Random r = new Random();
        for(int j=0;j<4;j++){
            list2.add(list.get(r.nextInt(list.size())));
        }
        String[] toBeStored = list2.toArray(new String[list2.size()]);
        StringBuilder sb = new StringBuilder();
        sb.append(toBeStored[0]);
        sb.append(toBeStored[1]);
        sb.append(toBeStored[2]);
        sb.append(toBeStored[3]);
//        Toast.makeText(getApplicationContext(),"sb："+sb.toString(),Toast.LENGTH_SHORT).show();
        ver_bg.setText(sb.toString());
    }
    //判断验证码是否正确；
    private boolean isVerTrue(){
        String verEdt = ver_edt.getText().toString() ;
        if(verEdt == null){
            Toast.makeText(getApplicationContext(),"验证码不能为空！",Toast.LENGTH_SHORT).show();
        }
        return verEdt.equals(ver_bg.getText().toString());
    }

    /**
     * 注册
     */
    private void register() {
        Intent intent = new Intent(getApplicationContext(),RegisterActivity.class);
        startActivity(intent);
    }

    /**
     * 登录
     */
    private void login() {
        User user = null;
        String account = mAccount_et.getText().toString();
        String password = mPassword_et.getText().toString();
        if(userManager == null){
            userManager = UserManager.instance();
            user = userManager.select(account,db);
        }else {
           user = userManager.select(account,db);
        }
        if(isVerTrue() && user != null && account.equals(user.getLoginName()) && password.equals(user.getPassWord())){
            userManager.setUser(user);
            Intent intent = new Intent(getApplicationContext(),MyBookActivity.class);
            startActivity(intent);
            writeFile();//写入配置文件，用于判断是否显示引导页；登录一次后引导页就不现实了，yes||no
        }else if(account == null || password == null){
            Toast.makeText(getApplicationContext(),"用户名或密码不可为空！",Toast.LENGTH_SHORT).show();
        }else if(account != null && password != null && !isVerTrue()){
            Toast.makeText(getApplicationContext(),"验证码不正确！",Toast.LENGTH_SHORT).show();
        }
        else{
            Toast.makeText(getApplicationContext(),"用户名或密码不正确！",Toast.LENGTH_SHORT).show();
        }
    }

    //写入配置文件，用于判断是否显示引导页；
    private void writeFile() {
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
            bw.write("yes");
            bw.flush();
            bw.close();
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
     if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            dialog();
            return false;
        }
        return false;
    }
    private void dialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("确定要退出吗?");
        builder.setTitle("温馨提示");
        builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
//                LoginActivity.this.finish();
                ActivityCollector.finishAll();
                // 返回home
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_MAIN);
                intent.addCategory(Intent.CATEGORY_HOME);
                startActivity(intent);
            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.create().show();
    }
}
