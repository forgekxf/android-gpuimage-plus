package com.bhtc.huajuan.push.util;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.support.annotation.ColorRes;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import com.bhtc.huajuan.push.App;

/**
 * Created by kouxiongfei on 2017/5/13.
 */

public class UIUtils {
    public static void showToastSafe(final String info) {
        App.getMainThreadHandler().post(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(App.App, info, Toast.LENGTH_SHORT).show();
            }
        });
    }

    public static boolean isEmpty(String str) {
        if (str == null || "".equals(str) || "".equals(str.trim()))
            return true;
        else
            return false;
    }

    /**
     * dip转换px
     */
    public static int dp2px(int dip) {
        final float scale = getContext().getResources().getDisplayMetrics().density;
        return (int) (dip * scale + 0.5f);
    }

    /**
     * dip转换px
     */
    public static int dp2px(float dip) {
        final float scale = getContext().getResources().getDisplayMetrics().density;
        return (int) (dip * scale + 0.5f);
    }

    public static void showToastSafe(final int id) {
        showToastSafe(App.App.getResources().getString(id));
    }

    /**
     * 获取颜色
     */
    public static int getColor(@ColorRes int resId) {
        return App.App.getResources().getColor(resId);
    }

    /**
     * 获取文字
     */
    public static String getString(int resId) {
        String string = App.App.getResources().getString(resId);
        return string;
    }

    /**
     * 获取文字(附加字符串) : %1$s
     */
    public static String getString(int id, Object... formatArgs) {
        String string = getResources().getString(id, formatArgs);
        return getString(string);
    }

    /**
     * 获取文字
     */
    public static String getString(String defalutString) {
        String replaceString = SPUtils.getString(Constants.SpName.TEXT, defalutString, "");
        if (!TextUtils.isEmpty(replaceString)) {
            return replaceString;
        }
        return defalutString;
    }

    /**
     * 加载布局
     */
    public static View inflate(Context mContext, int resId) {
        return LayoutInflater.from(mContext == null ? getContext() : mContext).inflate(resId, null);
    }

    public static Context getContext() {
        return App.App;
    }

    public static Drawable getDrawable(int resId) {
        return getResources().getDrawable(resId);
    }

    public static Resources getResources() {
        return getContext().getResources();
    }
}
