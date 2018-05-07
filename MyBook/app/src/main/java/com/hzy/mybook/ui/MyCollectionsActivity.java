package com.hzy.mybook.ui;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.hzy.mybook.R;
import com.hzy.mybook.manager.BookManager;
import com.hzy.mybook.manager.BookidManager;
import com.hzy.mybook.model.Book;
import com.hzy.mybook.model.BookId;
import com.hzy.mybook.ui.adapter.MyCollectionsAdapter;
import com.hzy.mybook.utils.ToastUtils;

import java.io.File;
import java.util.List;

public class MyCollectionsActivity extends AppCompatActivity {
    private BookidManager bookidManager ;
    private BookManager bookManager ;
    private MyCollectionsAdapter myCollectionsAdapter ;
    private ListView listView ;
    private List<BookId> bookIdList ;
    private ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_collections);
        bookidManager = new BookidManager(this);
        bookManager = new BookManager(this);
        bookIdList = bookidManager.getAllBookId();
        myCollectionsAdapter = new MyCollectionsAdapter(getApplicationContext(),R.layout.book_model_item,bookIdList);
        listView = (ListView)findViewById(R.id.mycollections_listview);
        listView.setAdapter(myCollectionsAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
               //打开图书阅读；
                Book book = null ;
                BookId bookid = (BookId) myCollectionsAdapter.getItem(position);
                book = bookManager.get(Long.valueOf(bookid.getBid()));
                readBook(book);
            }
        });
    }

    // 阅读
    private void readBook(Book book) {
        dialog = ProgressDialog.show(MyCollectionsActivity.this, "温馨提示", "正在加载文件", true);
//        Book book = books.get(selectedIndex);
        File file = new File(book.getFilePath());
        if (!file.exists()) {// 文件不存在
            ToastUtils.toast(this, "文件不存在");
            dialog.dismiss();
            return;
        }
        Long id = book.getId();
        Intent intent = new Intent(this, ReadBookActivity.class);
        intent.putExtra("bookId", id);
        dialog.dismiss();
        startActivity(intent);
    }
}
