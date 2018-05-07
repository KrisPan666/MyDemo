package com.hzy.mybook.model;

/**
 * Created by 13159 on 2018/5/6.
 */

public class BookId {
    private String id ;
    private String bid ;

    public BookId() {
    }

    public BookId(String bookid) {
        this.bid = bookid;
    }

    public BookId(String key, String bid) {
        this.id = key;
        this.bid = bid;
    }

    public String getKey() {
        return id;
    }

    public void setKey(String key) {
        this.id = key;
    }

    public String getBid() {
        return bid;
    }

    public void setBid(String bid) {
        this.bid = bid;
    }

    @Override
    public String toString() {
        return "BookId{" +
                "key='" + id + '\'' +
                ", bid='" + bid + '\'' +
                '}';
    }
}
