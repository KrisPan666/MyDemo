package com.hzy.mybook.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.hzy.mybook.R;
import com.hzy.mybook.manager.BookManager;
import com.hzy.mybook.model.Book;
import com.hzy.mybook.model.BookId;

import java.util.List;

/**
 * Created by 13159 on 2018/5/6.
 */

public class MyCollectionsAdapter extends BaseAdapter {
    private Context context;
    private int resourceId;
    private ImageView thumbImg_iv;
    private TextView bookTitle_tv;
    private TextView bookCreateTime_tv;
    private BookManager bookManager ;
    private Book book;
    private BookId bookId;

    List<BookId> bookid_list;
    LayoutInflater layoutInflater;
    @Override
    public int getCount() {
        return bookid_list.size();
    }

    @Override
    public Object getItem(int i) {
        return bookid_list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    public void setItem(List<BookId> items){
        this.bookid_list = items;
        notifyDataSetChanged();
    }
    public MyCollectionsAdapter(Context context, int resourceId, List<BookId> list) {
        this.context = context;
        this.resourceId = resourceId;
        this.bookid_list = list;
        layoutInflater = LayoutInflater.from(context);
    }
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        bookId = (BookId) getItem(i);
        String  bid = bookId.getBid();
        bookManager = new BookManager(context);
        book = bookManager.get(Long.valueOf(bid));

        view = layoutInflater.inflate(resourceId, null);

        thumbImg_iv = (ImageView) view.findViewById(R.id.thumb_img);
        bookTitle_tv = (TextView) view.findViewById(R.id.book_title);
        bookCreateTime_tv = (TextView) view.findViewById(R.id.book_create_time);

        bookTitle_tv.setText("《"+book.getName()+"》");
        bookCreateTime_tv.setText("收藏  "+book.getLastReadTime());
        return view;
    }
}
