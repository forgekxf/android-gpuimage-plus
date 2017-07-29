package com.bhtc.huajuan.push.bean;

import com.bhtc.huajuan.push.util.HttpConfig;

import java.io.Serializable;

/**
 * Created by kouxiongfei on 2017/5/12.
 */

public class BaseBean implements Serializable {
    protected int code;
    protected String info;

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

    public boolean success() {
        return code == HttpConfig.ResultCode.SUCCESS;
    }

    @Override
    public String toString() {
        return "BaseBean{" +
                "code=" + code +
                ", info='" + info + '\'' +
                '}';
    }
}
