package com.bhtc.huajuan.push.util;

import android.content.Context;
import android.widget.ImageView;

import com.bhtc.huajuan.push.R;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

/**
 * Created by kouxiongfei on 2017/5/16.
 */

public class GlideManager {
    private GlideManager() {
    }

    private static class GlideHolder {
        private static GlideManager mInstance = new GlideManager();
    }

    public static GlideManager getInstance() {
        return GlideHolder.mInstance;
    }


    public void loadCircleImg(Context context, String url, ImageView imageView) {
        Glide.with(context.getApplicationContext())
                .load(url)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .placeholder(R.drawable.ic_user_icon_default)
                .error(R.drawable.ic_user_icon_default)
                .crossFade()
                .fitCenter()
                .transform(new GlideCircleTransform(context))
                .into(imageView);
    }

    public void loadImage(Context context, String url, ImageView imageView) {
        Glide
                .with(context.getApplicationContext())//IllegalArgumentException: You cannot start a load for a destroyed activity 加载时页面已关闭（只能用application，牺牲内存）
                .load(url)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .placeholder(R.drawable.icon_loading)
                .error(R.drawable.icon_loading)
                .fitCenter()
                .animate(R.anim.fade_in)
                .into(imageView);
    }

    public void loadLiveHeaderCircleImage(Context context, String url, ImageView imageView, int drawable) {
        Glide
                .with(context.getApplicationContext())
                .load(url)
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .placeholder(drawable)
                .error(drawable)
                .crossFade()
                .fitCenter()
                .transform(new GlideCircleTransform(context))
                .into(imageView);
    }
}
