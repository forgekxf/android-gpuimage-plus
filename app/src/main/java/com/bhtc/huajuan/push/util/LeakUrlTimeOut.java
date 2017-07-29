package com.bhtc.huajuan.push.util;

import android.content.Context;

import com.bhtc.huajuan.push.bean.HttpAddressBean;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;

import okhttp3.Call;

/**
 * Created by kouxiongfei on 2017/5/12.
 */

public class LeakUrlTimeOut {

    public static void initHttp(Context mContext) {
        //获取各个主域名
        //获取有效期
        Long mExpireTime = SPUtils.getLong("base_http", "expire_time", 0);
        if (mExpireTime != 0) {
            //判断是否过期
            if (mExpireTime < System.currentTimeMillis() / 1000) {
                initBaseHttp(mContext);
            } else {
                userBeforeAddress();
            }
        } else {
            initBaseHttp(mContext);
        }
    }

    private static void initBaseHttp(Context mContext) {
        getHttpBase(new MyCallBack<HttpAddressBean>(HttpAddressBean.class, mContext) {
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

    public static void getHttpBase(Callback callback) {
        OkHttpUtils.get().url(ApiUrls.BASE_HTTP)
                .build().execute(callback);
    }

//    public static Map<String, String> getCommonHeaders() {
//        String loginToken = SPUtils.getString(Constants.SpName.USER_INFO, Constants.SpKeyUserInfo.LOGIN_TOKEN, "");
//        HashMap<String, String> headers = new HashMap<>();
//        headers.put(HttpConfig.HeaderKeyParams.X_REQUESTED_WITH, HttpConfig.HeaderValueParams.XML_HTTP_REQUEST);
//        headers.put(HttpConfig.HeaderKeyParams.SERVICE, HttpConfig.HeaderValueParams.HJAPP_ANDROID);
//        headers.put(HttpConfig.HeaderKeyParams.USER_AGENT, WEBVIEW_UA);
//
//        if (!TextUtils.isEmpty(loginToken)) {
//            headers.put(HttpConfig.HeaderKeyParams.COOKIE, String.format(HttpConfig.HeaderValueParams.LOGIN_TOKEN, loginToken));
//        }
//        if (BuildConfig.DEBUG) {
//            headers.put(HttpConfig.HeaderKeyParams.VM, HttpConfig.HeaderValueParams.DEBUG_VM);
//        } else {
////            headers.put(HttpConfig.HeaderKeyParams.VM, HttpConfig.HeaderValueParams.VM);
//            headers.put(HttpConfig.HeaderKeyParams.VM, ApiUrls.VM);
//        }
//        return headers;
//    }
}
