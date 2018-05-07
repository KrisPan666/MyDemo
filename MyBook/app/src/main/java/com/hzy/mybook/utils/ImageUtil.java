package com.hzy.mybook.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;

/**
 * 封装游戏图片按钮数组
 */
public class ImageUtil {
    /**
     * 获得相应大小的Bitmap
     **/
    public static Bitmap getBitmap(Context context, int id, int width, int height) {
        // 生成一张白纸
        Bitmap paper = Bitmap.createBitmap(width, height, Config.ARGB_8888);
        // 生成一块画板
        Canvas board = new Canvas(paper); // 把白纸贴到画板上
        // 要画的图片
        Drawable image = context.getResources().getDrawable(id);
        // 设置所画图片的宽高
        image.setBounds(0, 0, width, height);
        // 将图片交给画板，画板会将图片贴在白纸上
        image.draw(board);
        // 图片已经贴在白纸上了，我们需要的是纸，而不是画板
        return paper;
    }
}
