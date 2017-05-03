package com.example.su.mygzzx.xuanfochung;

import android.content.Context;
import android.view.WindowManager;

/**
 * Created by su
 * on 2017/3/16.
 */
public class ScreenUtils {
    /**
     * 获取屏幕宽度  *  * @return
     */
    @SuppressWarnings("deprecation")
    public static int getScreenWidth(Context context) {
        return ((WindowManager) context.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay().getWidth();
    }

    /**
     * 获取屏幕宽度  *  * @return
     */
    @SuppressWarnings("deprecation")
    public static int getScreenHeight(Context context) {
        return ((WindowManager) context.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay().getHeight();
    }
}
