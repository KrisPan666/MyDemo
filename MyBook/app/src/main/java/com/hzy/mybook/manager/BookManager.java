package com.hzy.mybook.manager;

/**
 * Created by 13159 on 2018/1/12.
 */
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.hzy.mybook.model.Book;
import com.hzy.mybook.model.BookColumn;
import com.hzy.mybook.db.DataBaseHelper;
import java.util.ArrayList;
import java.util.Date;
import static com.hzy.mybook.model.BookColumn.BEGIN;
import static com.hzy.mybook.model.BookColumn.FILE_PATH;
import static com.hzy.mybook.model.BookColumn.LAST_READ_TIME;
import static com.hzy.mybook.model.BookColumn.NAME;
import static com.hzy.mybook.model.BookColumn.PASSWORD;
import static com.hzy.mybook.model.BookColumn.PROGRESS;
import static com.hzy.mybook.model.BookColumn.TABLE_NAME;

/**
 * 图书业务逻辑类: 因为业务比较简单，故同SQLite DAO放在一起
 */
public class BookManager {
    private static final String TAG = "MyBookActivity";
    private DataBaseHelper dataBaseHelper;

    public BookManager(Context context) {
        dataBaseHelper = new DataBaseHelper(context);
    }
    /**
     * 以下为SQLite的相关操作
     * 保存book
     * @param book
     *          ：图书领域类对象
     * @return：返回保存的主键
     */
    public Long save(Book book) {
        SQLiteDatabase sqLiteDatabase = dataBaseHelper.getWritableDatabase();
        // 重构方法的演示
        ContentValues values = bookToContentValues(book);
        // 只有使用对象的方法操作SQLite才能返回保存的主键,具体可以看Android源码，O(∩_∩)O哈哈~
        Long count = sqLiteDatabase.insert(TABLE_NAME, null, values);
        sqLiteDatabase.close();
        return count;
    }

    /**
     * 修改book
     * @param book
     *          ：图书领域类对象
     * @return：返回保存的主键
     */
    public long update(Book book) {
        SQLiteDatabase sqLiteDatabase = dataBaseHelper.getWritableDatabase();
        // 重构方法的演示
        ContentValues values = bookToContentValues(book);
        // Log.d(TAG, "BookService id:" + book.getId());
        // Log.d(TAG, "BookService:" + values);
        long count = sqLiteDatabase.update(TABLE_NAME, values, BookColumn._ID + "=?", new String[] { book.getId().toString() });
        sqLiteDatabase.close();
        return count;
    }

    /**
     * 删除book
     * @param 图书领域类对象主键
     * @return：返回保存的主键
     */
    public long delete(Long id) {
        SQLiteDatabase sqLiteDatabase = dataBaseHelper.getWritableDatabase();
        long count = sqLiteDatabase.delete(TABLE_NAME, BookColumn._ID + "=?", new String[] { id.toString() });
        sqLiteDatabase.close();
        return count;
    }

    /**
     * 通过主键获取图书领域类对象
     * @param 图书领域类对象主键
     * @return：返回图书领域类对象
     */
    public Book get(Long id) {
        Book book = null;
        SQLiteDatabase sqLiteDatabase = dataBaseHelper.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.query(TABLE_NAME, new String[] { BookColumn._ID, NAME, PASSWORD, FILE_PATH, LAST_READ_TIME, BEGIN, PROGRESS }, BookColumn._ID + "=?",
                new String[] { id.toString() }, null, null, null);
        if (cursor.moveToFirst()) {
            book = cursorToBook(cursor);
            cursor.close();// 关闭游标
        }
        sqLiteDatabase.close();
        return book;
    }

    // 实际应用应该考虑使用分页。。。。
    /**
     * 分页
     * @param firstResult
     *          从什么位置开始取数据
     * @param maxResult
     *          总共取多少条记录
     * @return获得分页后所有实体
     */
    // public ArrayList<Emp> findPage(int firstResult, int maxResult) {
    // ArrayList<Emp> emps = new ArrayList<Emp>();
    // SQLiteDatabase db = databaseHelper.getReadableDatabase();
    // Cursor cursor = db.query("emp", new String[] { "emp_id", "name" },
    // "limit ?,?", new String[] { String.valueOf(firstResult),
    // String.valueOf(maxResult) }, null, null, null);
    // // Cursor cursor = db.rawQuery("select * from emp limit ?,?",
    // // new String[] { String.valueOf(firstResult), String.valueOf(maxResult)
    // });
    // while (cursor.moveToNext()) {
    // int emp_id = cursor.getInt(cursor.getColumnIndex("emp_id"));
    // String name = cursor.getString(cursor.getColumnIndex("name"));
    // emps.add(new Emp(emp_id, name));
    // }
    // return emps;
    // }

    /**
     * 获取所有图书领域类对象 在Android里面直接返回集合的实现类，性能啊。。。
     * @return：返回所有图书领域类对象
     */
    public ArrayList<Book> getAll() {
        ArrayList<Book> books = new ArrayList<Book>();
        SQLiteDatabase sqLiteDatabase = dataBaseHelper.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.query(TABLE_NAME, new String[] { BookColumn._ID, NAME, PASSWORD, FILE_PATH, LAST_READ_TIME, BEGIN, PROGRESS }, null, null, null, null,
                LAST_READ_TIME + " desc");
        while (cursor.moveToNext()) {
            Book book = cursorToBook(cursor);// 注意实例的位置，某某同学你懂的。。。
            books.add(book);
        }
        cursor.close();// 关闭游标
        sqLiteDatabase.close();
        return books;
    }

    // 内部private公共方法
    // 因为insert会bookToContentValues，update也会bookToContentValues，故抽取此方法
    private ContentValues bookToContentValues(Book book) {
        // alt+shift+r批量更改局部变量
        ContentValues values = new ContentValues();
        values.put(NAME, book.getName());
        values.put(PASSWORD, book.getPassword());
        values.put(FILE_PATH, book.getFilePath());
        // 在SQLite里面使用long来存放日期方便处理
        if (book.getLastReadTime() != null) {// 不为空才获得getTime，不是会出现空指针异常
            values.put(LAST_READ_TIME, book.getLastReadTime().getTime());
        }
        values.put(BEGIN, book.getBegin());
        values.put(PROGRESS, book.getProgress());
//        values.put(ISCOLLECTED, book.isCollected());
        return values;
    }

    // 查询的时候使用
    private Book cursorToBook(Cursor cursor) {
        Long id = cursor.getLong(cursor.getColumnIndex(BookColumn._ID));
        String name = cursor.getString(cursor.getColumnIndex(NAME));
        String password = cursor.getString(cursor.getColumnIndex(PASSWORD));
        String filePath = cursor.getString(cursor.getColumnIndex(FILE_PATH));
        Date lastReadTime = new Date(cursor.getLong(cursor.getColumnIndex(LAST_READ_TIME)));
        int begin = cursor.getInt(cursor.getColumnIndex(BEGIN));
        String progress = cursor.getString(cursor.getColumnIndex(PROGRESS));
//        String isCollected = cursor.getString(cursor.getColumnIndex(ISCOLLECTED));
        Book book = new Book(name, filePath, lastReadTime, begin, progress);
        book.setId(id);
        book.setPassword(password);
        return book;
    }
}
