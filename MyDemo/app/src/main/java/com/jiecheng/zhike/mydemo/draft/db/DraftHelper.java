package com.jiecheng.zhike.mydemo.draft.db;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.jiecheng.zhike.mydemo.draft.model.Subject;
import com.jiecheng.zhike.mydemo.draft.utils.SortClass;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 对草稿箱数据操作相关SQL
 * author：潘洪雨
 * Created by 13159 on 2017/8/28.
 */

public class DraftHelper {
    private static final String TAG = "DraftHelper";
    private SubjectDataBaseHelper sDBH;
    private SQLiteDatabase db;
    public DraftHelper(Context context) {
        sDBH = new SubjectDataBaseHelper(context);
        db = sDBH.getWritableDatabase();
    }
    /**
     * 删除操作
     */
    public  boolean delete(List<Subject> list) {
        SQLiteDatabase db = sDBH.getWritableDatabase();
        Subject subject = null;
        try{
            //事物开始；
            db.beginTransaction();
            if(list.size() == 0){
                Log.e(TAG, "请选择后删除");
            }else{
                for(int i=0;i<list.size();i++){
                    subject = list.get(i);
                    Log.d("我選擇的Item:",subject.toString());
                    db.execSQL("delete from subject where subName=?",new String[]{subject.getSubName()});
                }
                //事务成功;
                db.setTransactionSuccessful();
                return true;
            }
        }catch(SQLException e){
            e.printStackTrace();
            return false;
        } finally{
            // 结束
            db.endTransaction();
            db.close();
        }
        return true;
    }
    /**
     * 查询操作
     */
    public List<Subject> select(){
        Subject sub = null;
        List<Subject> draft = new ArrayList<Subject>();
//        Cursor cursor = db.query("subject",null,null,null,null,null,null);
        Cursor cursor = db.rawQuery("select * from subject",null);
        if(cursor.moveToFirst()){
            do{
                //遍历Cursor对象，并打印；
                sub = new Subject();
                String subName = cursor.getString(cursor.getColumnIndex("subName"));
                String  subType = cursor.getString(cursor.getColumnIndex("subType"));
                String  subNum = cursor.getString(cursor.getColumnIndex("subNum"));
                String  time = cursor.getString(cursor.getColumnIndex("createTime"));
                sub.setSubName(subName);
                sub.setSubType(subType);
                sub.setSubNum(subNum);
                sub.setTime(time);
                draft.add(sub);
                // Toast.makeText(this,"查询数据成功！",Toast.LENGTH_SHORT).show();
            }while (cursor.moveToNext());
        }
        SortClass sort = new SortClass();
        Collections.sort(draft, sort);
        return draft;
    }
}
