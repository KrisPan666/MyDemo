package com.jiecheng.zhike.mydemo.draft.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by 13159 on 2017/8/17.
 */
public class SubjectDataBaseHelper extends SQLiteOpenHelper {
    public static final  String CREATE_SUB= "create table subject("
            +"id Integer primary key autoincrement,"
            +"subName text,"
            +"subType text,"
            +"subNum integer,"
            +"createTime text)";
    private Context mcontext;
    private static int version = 4;

    public SubjectDataBaseHelper(Context context) {
        super(context, "myClass.db", null, version);
        mcontext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_SUB);
//        Toast.makeText(mcontext,"表创建成功！",Toast.LENGTH_SHORT).show();
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldversion, int newversion) {
        db.execSQL("drop table if exists subject");
        onCreate(db);
    }
}
