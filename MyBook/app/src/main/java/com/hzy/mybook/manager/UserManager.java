package com.hzy.mybook.manager;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.hzy.mybook.db.UserDataBaseHelper;
import com.hzy.mybook.model.BookId;
import com.hzy.mybook.model.User;

/**
 * 用户数据管理相关业务
 * Created by 13159 on 2018/1/25.
 */
public class UserManager {
    // 创建用户数据管理器单例
    private static UserManager mInstance;
    private SQLiteDatabase db;

    //临时保存用户信息；
    private User user ;
    private BookId bookId ;
    public User getUser() {
        return user;
    }
    public void setUser(User user) {
        this.user = user;
    }

    public UserManager() {
    }

    public static synchronized UserManager instance() {
        if (mInstance == null) {
            mInstance = new UserManager();
        }
        return mInstance;
    }

    private static final String TAG = "LoginActivity";
    private UserDataBaseHelper dataBaseHelper;

    public UserManager(Context mcontext) {
        this.dataBaseHelper = new UserDataBaseHelper(mcontext);
        db = dataBaseHelper.getWritableDatabase();
    }
    /**
     * 以下为SQLite的相关操作
     * 保存user
     * @param user
     */

    //向数据库插入数据；
    public void insert(User user , SQLiteDatabase db){
        ContentValues values = new ContentValues();
        //创建数据库；
                //保存用户信息数据；
                values.put("key",user.getKey());
                values.put("loginName",user.getLoginName());
                values.put("passWord",user.getPassWord());
                values.put("strExt",user.getStrExt());
                db.insert("user",null,values);
    }

    /**
     * 查询操作
     */
    public User select(String loginName,SQLiteDatabase db){
        User user = null;
//        Cursor cursor = db.query("subject",null,null,null,null,null,null);
//        SQLiteDatabase db = dataBaseHelper.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from user where loginName=?",new String[]{loginName});
        if(cursor.moveToFirst()){
                //遍历Cursor对象，并打印；
                user = new User();
                String key = cursor.getString(cursor.getColumnIndex("key"));
                String loginName1 = cursor.getString(cursor.getColumnIndex("loginName"));
                String  passWord = cursor.getString(cursor.getColumnIndex("passWord"));
                String  strExt = cursor.getString(cursor.getColumnIndex("strExt"));
                user.setKey(key);
                user.setLoginName(loginName1);
                user.setPassWord(passWord);
                user.setStrExt(strExt);
                // Toast.makeText(this,"查询数据成功！",Toast.LENGTH_SHORT).show();
        }
        return user;
    }

}
