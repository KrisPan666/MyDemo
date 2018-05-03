package com.jiecheng.zhike.mydemo.draft.utils;


import com.jiecheng.zhike.mydemo.draft.model.Subject;

import java.util.Comparator;

/**
 * 实现排序；
 * author 潘洪雨
 * Email：hypan3.iflytek.com
 * Created by 13159 on 2017/8/23.
 */

public class SortClass implements Comparator {
    @Override
    public int compare(Object obj1, Object obj2) {
        Subject sub1 = (Subject)obj1;
        Subject sub2 = (Subject)obj2;
        int flag = sub2.getTime().compareTo(sub1.getTime());
        return flag;
    }
}
