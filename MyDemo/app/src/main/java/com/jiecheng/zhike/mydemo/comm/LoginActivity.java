package com.jiecheng.zhike.mydemo.comm;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.jiecheng.zhike.mydemo.R;

public class LoginActivity extends BaseActivity implements View.OnClickListener{
    /**
     * demo登录首页
     * author：潘洪雨
     * email：hypan3.iflytek.com
     */
    private EditText text_userName;
    private EditText text_password;
    private Button btn_login;
    private Button btn_edit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main1);
        btn_login = (Button)findViewById(R.id.login);
        btn_edit = (Button)findViewById(R.id.edit);
        btn_login.setOnClickListener(this);
        btn_edit.setOnClickListener(this);
    }
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.login:
                login();
                break;
            case R.id.edit:
                edit();
                break;
            default:
                break;
        }
    }
    /**
     * 登录
     */
    private void login() {
        //实例化一个用户
        UserInfo userInfo = new UserInfo("qwe","123");
        String userName1 = userInfo.getName();
        String password1 = userInfo.getPassword();
        text_userName = (EditText)findViewById(R.id.userName_Edit);
        text_password = (EditText)findViewById(R.id.password_Edit);
        String userName = text_userName.getText().toString();
        String password = text_password.getText().toString();
//        if(!userName.isEmpty()&&!password.isEmpty()){
//            if(userName.equals(userName1)&&password.equals(password1)){
//                Toast.makeText(this,"欢迎进入帅逼的草稿箱demo",Toast.LENGTH_LONG).show();
                Intent intent = new Intent(LoginActivity.this,AddDemoActivity.class);
                startActivity(intent);
//                finish();
//            }else if(!userName.equals(userName1)||!password.equals(password1)){
//                Toast.makeText(this,"用户名或密码不正确，请重新输入！",Toast.LENGTH_LONG).show();
//            }
//        }else{
//            Toast.makeText(this,"用户名或密码不能为空！",Toast.LENGTH_LONG).show();
//        }
    }

    /**
     * 注册
     */
    private void edit() {

    }
}
