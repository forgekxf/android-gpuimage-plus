package com.bhtc.huajuan.push.bean;

import java.io.Serializable;

/**
 * 获取主域名
 */
public class HttpAddressBean implements Serializable {
    private String html5_dm;
    private String static_dm;
    private String analytics_dm;
    private String app_dm;
    private String livestreaming_dm;
    private String livestreaming_ws_dm;
    private String expire_time;
    private int code;
    private String info;

    @Override
    public String toString() {
        return "HttpAddressBean{" +
                "html5_dm='" + html5_dm + '\'' +
                ", static_dm='" + static_dm + '\'' +
                ", analytics_dm='" + analytics_dm + '\'' +
                ", app_dm='" + app_dm + '\'' +
                ", livestreaming_dm='" + livestreaming_dm + '\'' +
                ", livestreaming_ws_dm='" + livestreaming_ws_dm + '\'' +
                ", expire_time='" + expire_time + '\'' +
                ", code=" + code +
                ", info='" + info + '\'' +
                '}';
    }

    public String getLivestreaming_ws_dm() {
        return livestreaming_ws_dm;
    }

    public void setLivestreaming_ws_dm(String livestreaming_ws_dm) {
        this.livestreaming_ws_dm = livestreaming_ws_dm;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getLivestreaming_dm() {
        return livestreaming_dm;
    }

    public void setLivestreaming_dm(String livestreaming_dm) {
        this.livestreaming_dm = livestreaming_dm;
    }

    public String getHtml5_dm() {
        return html5_dm;
    }

    public void setHtml5_dm(String html5_dm) {
        this.html5_dm = html5_dm;
    }

    public String getStatic_dm() {
        return static_dm;
    }

    public void setStatic_dm(String static_dm) {
        this.static_dm = static_dm;
    }

    public String getAnalytics_dm() {
        return analytics_dm;
    }

    public void setAnalytics_dm(String analytics_dm) {
        this.analytics_dm = analytics_dm;
    }

    public String getApp_dm() {
        return app_dm;
    }

    public void setApp_dm(String app_dm) {
        this.app_dm = app_dm;
    }

    public String getExpire_time() {
        return expire_time;
    }

    public void setExpire_time(String expire_time) {
        this.expire_time = expire_time;
    }
}
