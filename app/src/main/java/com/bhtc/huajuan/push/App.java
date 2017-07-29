package com.bhtc.huajuan.push;

import android.os.Handler;
import android.support.multidex.MultiDexApplication;

import com.bhtc.huajuan.push.bean.HttpAddressBean;
import com.bhtc.huajuan.push.util.ApiUrls;
import com.bhtc.huajuan.push.util.Constants;
import com.bhtc.huajuan.push.util.HttpHelper;
import com.bhtc.huajuan.push.util.MyCallBack;
import com.bhtc.huajuan.push.util.SPUtils;
import com.bugtags.library.Bugtags;
import com.bugtags.library.BugtagsOptions;
import com.qiniu.pili.droid.streaming.StreamingEnv;

import okhttp3.Call;

/**
 * Created by kouxiongfei on 2017/5/10.
 */

public class App extends MultiDexApplication {

    public static App App;
    /**
     * 主线程Handler
     */
    private static Handler mMainThreadHandler;

    @Override
    public void onCreate() {
        super.onCreate();
        App = this;
        BugtagsOptions options = new BugtagsOptions.Builder().
                trackingCrashLog(true).
                trackingConsoleLog(true).
                trackingUserSteps(true).
                build();
        Bugtags.setUserData("uid", SPUtils.getString(Constants.SpName.USER_INFO, Constants.SpKeyUserInfo.UID, ""));
        Bugtags.setUserData("user_mobile", SPUtils.getString(Constants.SpName.USER_INFO, Constants.SpKeyUserInfo.USER_MOBILE, ""));
        Bugtags.start(BuildConfig.DEBUG ? "f7e4fae1fd47b483a068286d4e7908cc" : "94b6b54a20f2cbf5b1f3c2d586c5c088", this, BuildConfig.DEBUG ? Bugtags.BTGInvocationEventShake : Bugtags.BTGInvocationEventNone, options);

        mMainThreadHandler = new Handler();
        StreamingEnv.init(getApplicationContext());
//        initHttp();
//        LeakUrlTimeOut.initHttp(this);
    }

    public static App getInstance() {
        return App;
    }

    /**
     * 获取主线程的handler
     */
    public static Handler getMainThreadHandler() {
        return mMainThreadHandler;
    }

    public static void initHttp() {
        //获取各个主域名
        //获取有效期
        Long mExpireTime = SPUtils.getLong("base_http", "expire_time", 0);
        if (mExpireTime != 0) {
            //判断是否过期
            if (mExpireTime < System.currentTimeMillis() / 1000) {
                initBaseHttp();
            } else {
                userBeforeAddress();
            }
        } else {
            initBaseHttp();
        }
    }

    private static void initBaseHttp() {
        HttpHelper.getHttpBase(new MyCallBack<HttpAddressBean>(HttpAddressBean.class, App) {
            @Override
            public void onError(Call call, Exception e) {
                e.printStackTrace();
                userBeforeAddress();
            }

            @Override
            public void onResponse(HttpAddressBean httpAddressBean) {
                if (!isEmpty(httpAddressBean.getExpire_time())) {
                    SPUtils.saveLong("base_http", "expire_time", Long.parseLong(httpAddressBean.getExpire_time()) + System.currentTimeMillis() / 1000);
                    if (!isEmpty(httpAddressBean.getApp_dm())) {
                        SPUtils.saveString("base_http", "app_dm", httpAddressBean.getApp_dm());
                        ApiUrls.BASE = httpAddressBean.getApp_dm();
                    }
                    if (!isEmpty(httpAddressBean.getHtml5_dm())) {
                        SPUtils.saveString("base_http", "html5_dm", httpAddressBean.getHtml5_dm());
                        ApiUrls.HTML5_BASE = httpAddressBean.getHtml5_dm();
                    }
                    if (!isEmpty(httpAddressBean.getStatic_dm())) {
                        SPUtils.saveString("base_http", "static_dm", httpAddressBean.getStatic_dm());
                        ApiUrls.STATIC_BASE = httpAddressBean.getStatic_dm();
                    }
                    if (!isEmpty(httpAddressBean.getAnalytics_dm())) {
                        SPUtils.saveString("base_http", "analytics_dm", httpAddressBean.getAnalytics_dm());
                        ApiUrls.ANALYTICS_BASE = httpAddressBean.getAnalytics_dm();
                    }
                    if (!isEmpty(httpAddressBean.getLivestreaming_dm())) {
                        SPUtils.saveString("base_http", "livestreaming_dm", httpAddressBean.getLivestreaming_dm());
                        ApiUrls.LIVESTREAMING_DM = httpAddressBean.getLivestreaming_dm();
                    }
                    if (!isEmpty(httpAddressBean.getLivestreaming_ws_dm())) {
                        SPUtils.saveString("base_http", "livestreaming_ws_dm", httpAddressBean.getLivestreaming_ws_dm());
                        ApiUrls.LIVESTREAMING_WS_DM = httpAddressBean.getLivestreaming_ws_dm();
                    }
                }
            }
        });
    }

    private static void userBeforeAddress() {
        if (!isEmpty(SPUtils.getString("base_http", "app_dm", ""))) {
            ApiUrls.BASE = SPUtils.getString("base_http", "app_dm", "");
        }
        if (!isEmpty(SPUtils.getString("base_http", "html5_dm", ""))) {
            ApiUrls.HTML5_BASE = SPUtils.getString("base_http", "html5_dm", "");
        }
        if (!isEmpty(SPUtils.getString("base_http", "static_dm", ""))) {
            ApiUrls.STATIC_BASE = SPUtils.getString("base_http", "static_dm", "");
        }
        if (!isEmpty(SPUtils.getString("base_http", "analytics_dm", ""))) {
            ApiUrls.ANALYTICS_BASE = SPUtils.getString("base_http", "analytics_dm", "");
        }
        if (!isEmpty(SPUtils.getString("base_http", "livestreaming_dm", ""))) {
            ApiUrls.LIVESTREAMING_DM = SPUtils.getString("base_http", "livestreaming_dm", "");
        }
        if (!isEmpty(SPUtils.getString("base_http", "livestreaming_ws_dm", ""))) {
            ApiUrls.LIVESTREAMING_WS_DM = SPUtils.getString("base_http", "livestreaming_ws_dm", "");
        }
    }

    public static boolean isEmpty(String str) {
        if (str == null || "".equals(str) || "".equals(str.trim()))
            return true;
        else
            return false;
    }
}
