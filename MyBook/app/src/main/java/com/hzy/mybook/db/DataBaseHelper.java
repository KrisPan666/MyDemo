package com.hzy.mybook.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

import com.hzy.mybook.model.BookColumn;

import static com.hzy.mybook.model.BookColumn.BEGIN;
import static com.hzy.mybook.model.BookColumn.FILE_PATH;
import static com.hzy.mybook.model.BookColumn.LAST_READ_TIME;
import static com.hzy.mybook.model.BookColumn.NAME;
import static com.hzy.mybook.model.BookColumn.PASSWORD;
import static com.hzy.mybook.model.BookColumn.PROGRESS;
import static com.hzy.mybook.model.BookColumn.TABLE_NAME;

/**
 * 获取SQLiter数据连接的工具类：
 */
public class DataBaseHelper extends SQLiteOpenHelper {
    // 数据库名称
    private static final String DATABASE_NAME = "MyBookActivity.db";
    // 数据库的版本号
    private static final int VERSION = 1;
    /**
     * 对外提供构造函数：必须通过获得Context才能创建SQLiteOpenHelper
     * @param context
     */
    public DataBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }
    /**
     * 必须要重写
     * @param context
     * @param name
     * @param factory
     * @param version
     */
    public DataBaseHelper(Context context, String name, CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    /**
     * 用来创建数据库的表
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        StringBuilder sb = new StringBuilder();
        sb.append("create table " + TABLE_NAME);
        sb.append("(");
        sb.append(BookColumn._ID + " integer primary key autoincrement,");
        sb.append(NAME + " text,");
        sb.append(PASSWORD + " text,");
        sb.append(FILE_PATH + " text,");
        sb.append(LAST_READ_TIME + " integer,");
        sb.append(BEGIN + " integer,");
        sb.append(PROGRESS + " text");
        sb.append(")");
        db.execSQL(sb.toString());

    }

    /**
     * 当版本改变的时候，调用此方法，用来修改表的字段，演示的时候可以drop table，真实项目只能alter table
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table " + TABLE_NAME);
        onCreate(db);
    }
}
