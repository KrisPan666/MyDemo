package com.hzy.mybook.model;

import android.provider.BaseColumns;

/**
 * 图书领域类的辅助类，定义book相关操作的常量字段
 * Created by 13159 on 2018/1/13.
 */
public interface BookColumn extends BaseColumns {
    String TABLE_NAME = "books";
    String NAME = "name";// 书名
    String PASSWORD = "password";// 密码
    String FILE_PATH = "filePath";// 文件路径
    String LAST_READ_TIME = "lastReadTime";// 最后阅读时间
    String PROGRESS = "progress";// 进度比例
    String  BEGIN = "begin";// 当前页码
//    String ISCOLLECTED = "isCollected";// 当前页码
}
