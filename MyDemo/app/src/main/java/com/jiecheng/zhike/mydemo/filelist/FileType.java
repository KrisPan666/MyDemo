package com.jiecheng.zhike.mydemo.filelist;

import com.jiecheng.zhike.mydemo.R;

/**
 * Created by lxz on 2017/8/1.
 */

public enum FileType {
    FILE_ICON_MUSIC(R.mipmap.filesystem_icon_music),
    FILE_ICON_MOVIE(R.mipmap.filesystem_icon_movie),
    FILE_ICON_PICTURE(R.mipmap.filesystem_icon_photo),
    FILE_ICON_WORD(R.mipmap.filesystem_icon_word),
    FILE_ICON_PPT(R.mipmap.filesystem_icon_ppt),
    FILE_ICON_TEXT(R.mipmap.filesystem_icon_text),
    FILE_ICON_TXT(R.mipmap.filesystem_icon_txt),
    FILE_ICON_PDF(R.mipmap.filesystem_icon_pdf),
    FILE_ICON_RAR(R.mipmap.filesystem_icon_rar),
    FILE_ICON_WEBPAGE(R.mipmap.filesystem_icon_web),
    FILE_ICON_OTHER(R.mipmap.filesystem_icon_default);
    public int code;
    private FileType(int i) {
        this.code = i;
    }

}
