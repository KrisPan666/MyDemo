package com.hzy.mybook.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.hzy.mybook.R;
import com.hzy.mybook.controllor.ActivityCollector;
import com.hzy.mybook.manager.UserManager;
import com.hzy.mybook.model.User;

import org.json.JSONException;
import org.json.JSONObject;

public class UserInfoActivity extends AppCompatActivity {
    private TextView loginname;
    private TextView key;
    private TextView mrealname;
    private TextView mphone;
    private TextView mage;
    private TextView msex;
    private TextView maddress;
    private TextView memail;
    private UserManager userManager;
    private User user = null ;//用户
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);
        initUI();
        ActivityCollector.addActivity(this);
        if(userManager == null){
            userManager = UserManager.instance();
        }
        user = userManager.getUser();
        loginname.setText(user.getLoginName());
        key.setText(user.getKey());
        try {
            JSONObject jsonObject = new JSONObject(user.getStrExt());
            String realname = jsonObject.optString("realname");
            String phone = jsonObject.optString("phone");
            String sex = jsonObject.optString("sex");
            String age = jsonObject.optString("age");
            String address = jsonObject.optString("address");
            String email = jsonObject.optString("email");
            mrealname.setText(realname);
            mphone.setText(phone);
            maddress.setText(address);
            msex.setText(sex);
            mage.setText(age);
            memail.setText(email);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void initUI() {
        loginname = (TextView)findViewById(R.id.loginname);
        key = (TextView)findViewById(R.id.key);
        mrealname = (TextView)findViewById(R.id.realname);
        msex = (TextView)findViewById(R.id.sex);
        mage = (TextView)findViewById(R.id.age);
        mphone = (TextView)findViewById(R.id.phone);
        maddress = (TextView)findViewById(R.id.address);
        memail = (TextView)findViewById(R.id.email);
    }
}
