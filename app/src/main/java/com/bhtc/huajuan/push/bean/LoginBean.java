package com.bhtc.huajuan.push.bean;

import java.io.Serializable;

/**
 * Created by qkk on 2016/4/22.
 * 登录以后获取的值 登录的接口传这个bean
 */
public class LoginBean extends BaseBean implements Serializable {

    private String logintoken;
    private UserInfoBean userInfo;

    public String getLogintoken() {
        return logintoken;
    }

    public void setLogintoken(String logintoken) {
        this.logintoken = logintoken;
    }

    public UserInfoBean getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UserInfoBean userInfo) {
        this.userInfo = userInfo;
    }
}
