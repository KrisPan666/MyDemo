package com.hzy.mybook.manager;

/**
 * 文字管理工具，设置文字的大小；
 * 可以扩展增加大小、颜色、字体、加粗等等
 * Created by hzy on 2018/5/4.
 */

public class TextManager {

    private int m_fontSize = 28;//字体默认大小变量；

    public int getM_fontSize() {
        return m_fontSize;
    }
    public void setM_fontSize(int m_fontSize) {
        this.m_fontSize = m_fontSize;
    }

    // 创建文字管理器单例
    private static TextManager mInstance;
    public static synchronized TextManager instance() {
        if (mInstance == null) {
            mInstance = new TextManager();
        }
        return mInstance;
    }
}
