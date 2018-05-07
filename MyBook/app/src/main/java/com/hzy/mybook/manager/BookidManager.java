package com.hzy.mybook.manager;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.hzy.mybook.db.BookidDataBaseHelper;
import com.hzy.mybook.model.BookId;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 13159 on 2018/5/7.
 */

public class BookidManager {
    private BookidDataBaseHelper dataBaseHelper;
    private SQLiteDatabase db;

    public BookidManager(Context mcontext) {
        this.dataBaseHelper = new BookidDataBaseHelper(mcontext);
        db = dataBaseHelper.getWritableDatabase();
    }

    //向数据库插入图书id；
    public void insertbookid(BookId bookId){
        ContentValues values = new ContentValues();
        //创建数据库；
        //保存用户信息数据；
//        values.put("id",bookId.getKey());
        values.put("bid",bookId.getBid());
        db.insert("bookid",null,values);
//        db.execSQL("insert into bookid(bid) values (?)", new Object[]{bookId.getBid()});
    }


    public List<BookId> getAllBookId() {
        BookId bookId = null;
        List<BookId> bookIdList = new ArrayList<BookId>();
        Cursor cursor = db.rawQuery("select * from bookid",null);
        if(cursor.moveToFirst()){
            do{
                //遍历Cursor对象，并打印；
                bookId = new BookId();
                String id = cursor.getString(cursor.getColumnIndex("id"));
                String  bid = cursor.getString(cursor.getColumnIndex("bid"));
                bookId.setKey(id);
                bookId.setBid(bid);

                bookIdList.add(bookId);
            }while (cursor.moveToNext());
        }
//        SortClass sort = new SortClass();
//        Collections.sort(draft, sort);
        return bookIdList;
    }


    public BookId find(String bid) {
//        SQLiteDatabase database = dbOpenHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from bookid where bid=?", new String[]{bid.toString()});
        BookId bookid = null;
        if (cursor.moveToFirst()) {
            Long key = cursor.getLong(cursor.getColumnIndex("id"));
            String bookId = cursor.getString(cursor.getColumnIndex("bid"));
            bookid = new BookId();
//            bookid.setKey(String.valueOf(key));
            bookid.setBid(bookId);
        }
        return bookid;
    }

    }
