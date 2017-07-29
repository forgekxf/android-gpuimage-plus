package com.bhtc.huajuan.push.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.bhtc.huajuan.push.App;


/**
 * Created by qkk on 2016/4/27.
 * 获取当前的网络状态
 */
public class NetworkUtil {

    public static int TYPE_WIFI = 1;
    public static int TYPE_MOBILE = 2;
    public static int TYPE_NOT_CONNECTED = 0;

    public static int getConnectivityStatus() {
        ConnectivityManager cm = (ConnectivityManager) App.App.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetWork = cm.getActiveNetworkInfo();

        if (null != activeNetWork) {
            if (activeNetWork.getType() == ConnectivityManager.TYPE_WIFI) {
                return TYPE_WIFI;
            }

            if (activeNetWork.getType() == ConnectivityManager.TYPE_MOBILE) {
                return TYPE_MOBILE;
            }
        }
        return TYPE_NOT_CONNECTED;
    }

    public static String getConnectivityStatusString() {
        int conn = NetworkUtil.getConnectivityStatus();
        String status = null;
        if (conn == NetworkUtil.TYPE_WIFI) {
            status = "Wifi enabled";
        } else if (conn == NetworkUtil.TYPE_MOBILE) {
            status = "Mobile data enabled";
        } else if (conn == NetworkUtil.TYPE_NOT_CONNECTED) {
            status = "Not connected to Internet";
        }
        return status;
    }

    public static boolean isNetworkConnected() {
        return TYPE_NOT_CONNECTED != getConnectivityStatus();
    }

}
