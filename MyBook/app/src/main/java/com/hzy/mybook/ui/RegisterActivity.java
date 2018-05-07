package com.hzy.mybook.ui;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.hzy.mybook.R;
import com.hzy.mybook.controllor.ActivityCollector;
import com.hzy.mybook.db.UserDataBaseHelper;
import com.hzy.mybook.manager.UserManager;
import com.hzy.mybook.model.User;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.UUID;
/**
 * 注册相关控制
 * author：phy
 * date: 2018/02/02
 */
public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText mAccount_et;
    private EditText mPassword_et;
    private EditText mRealName;
    private EditText mAge;
    private EditText mSex;
    private EditText mPhone;
    private EditText mEmail;
    private EditText mAddress;
    private Button mRegister_btn ;
    UserManager userManager ;
    private UserDataBaseHelper uDBH;
//    private static SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initUI();
        ActivityCollector.addActivity(this);
        uDBH = new UserDataBaseHelper(this);
//        SQLiteDatabase db = uDBH.getWritableDatabase();
        mRegister_btn.setOnClickListener(this);

    }

    private void initUI() {
        mAccount_et = (EditText)findViewById(R.id.account_register);
        mPassword_et = (EditText)findViewById(R.id.password_register);
        mRegister_btn = (Button)findViewById(R.id.to_register);
        mPhone = (EditText)findViewById(R.id.phone);
        mEmail = (EditText)findViewById(R.id.email);
        mAddress = (EditText)findViewById(R.id.address);
        mRealName = (EditText)findViewById(R.id.realname);
        mAge = (EditText)findViewById(R.id.age);
        mSex = (EditText)findViewById(R.id.sex);
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.to_register){
            String account = mAccount_et.getText().toString();
            String password = mPassword_et.getText().toString();
            String phone = mPhone.getText().toString();
            String email = mEmail.getText().toString();
            String address = mAddress.getText().toString();
            String realName = mRealName.getText().toString();
            String age = mAge.getText().toString();
            String sex = mSex.getText().toString();
            String key = UUID.randomUUID().toString();
            String strExt = "" ;
            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put("phone",phone);
                jsonObject.put("email",email);
                jsonObject.put("address",address);
                jsonObject.put("realname",realName);
                jsonObject.put("sex",sex);
                jsonObject.put("age",age);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            strExt = jsonObject.toString();
            if(userManager == null ){
                userManager = UserManager.instance();
                SQLiteDatabase db1 = uDBH.getReadableDatabase();
                User user1 = userManager.select(account,db1);
                if(user1 == null){
                    User user2 = new User(key,account,password,strExt);
                    SQLiteDatabase db2 = uDBH.getWritableDatabase();
                    userManager.insert(user2,db2);
                    Toast.makeText(getApplicationContext(),"注册成功，快去登录体验吧！",Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(getApplicationContext(),"改用户已被注册，请换其他的用户名！",Toast.LENGTH_SHORT).show();
                }
            }else{
                SQLiteDatabase db1 = uDBH.getReadableDatabase();
                User user1 = userManager.select(account,db1);
                if(user1 == null){
                    User user2 = new User(key,account,password,strExt);
                    SQLiteDatabase db2 = uDBH.getWritableDatabase();
                    userManager.insert(user2,db2);
                    Toast.makeText(getApplicationContext(),"注册成功，快去登录体验吧！",Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(getApplicationContext(),"改用户已被注册，请换其他的用户名！",Toast.LENGTH_SHORT).show();
                }
            }

        }
    }
}
