package com.hzy.mybook.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by 13159 on 2018/1/31.
 */

public class BookidDataBaseHelper extends SQLiteOpenHelper {
    public static final  String CREATE_BOOKID= "create table bookid("
            +"id Integer primary key autoincrement,"
            +"bid text)";
    private Context mcontext;
    private static int version = 1;

    public BookidDataBaseHelper(Context context) {
        super(context, "BookId.db", null, version);
        mcontext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_BOOKID);
//        Toast.makeText(mcontext,"表创建成功！",Toast.LENGTH_SHORT).show();
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldversion, int newversion) {
        db.execSQL("drop table if exists bookid");
        onCreate(db);
    }
}
