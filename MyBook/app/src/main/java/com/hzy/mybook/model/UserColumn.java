package com.hzy.mybook.model;

import android.provider.BaseColumns;

/**
 * 图书领域类的辅助类，定义book相关操作的常量字段
 * Created by 13159 on 2018/1/13.
 */
public interface UserColumn extends BaseColumns {
    String USER_TABLE_NAME = "user";
    String KEY = "key";// 用户账号；
    String LOGIN_NAME = "loginName";// 登录名
    String PASS_WORD = "passWord";// 登录密码
    String STR_EXT = "strExt";// 扩展信息，包括个人信息；
}
