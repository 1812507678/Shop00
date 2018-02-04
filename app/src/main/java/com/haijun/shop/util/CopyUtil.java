package com.haijun.shop.util;

import android.content.ClipboardManager;
import android.content.Context;

/**
 * Created by haijun on 2018/2/3.
 */

public class CopyUtil {

    //复制文字
    public static void copyText(String text,Context context) {
        // 得到剪贴板管理器
        ClipboardManager cmb = (ClipboardManager)context.getSystemService(Context.CLIPBOARD_SERVICE);
        cmb.setText(text);
    }
}
