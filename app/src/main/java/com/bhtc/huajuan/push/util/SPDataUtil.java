package com.bhtc.huajuan.push.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import com.bhtc.huajuan.push.App;
import com.bhtc.huajuan.push.bean.LoginBean;
import com.bhtc.huajuan.push.bean.UserInfoBean;


/**
 * 保存、取出数据
 * <P/>Created by Administrator on 2016/10/26.
 */
public class SPDataUtil {

    /**
     * 取出个人信息
     */
    public static String getUserToken(){
        SharedPreferences sp = App.App.getSharedPreferences(Constants.SpName.USER_INFO, Context.MODE_PRIVATE);
        return sp.getString(Constants.SpKeyUserInfo.LOGIN_TOKEN, "");
    }


    /**
     * 保存个人信息到本地sp(一般是登录以后)
     */
    public static void saveUserInfo(LoginBean loginBean) {

        SharedPreferences sp = App.App.getSharedPreferences(Constants.SpName.USER_INFO, Context.MODE_PRIVATE);

        SharedPreferences.Editor edit = sp.edit();

        UserInfoBean userInfo = loginBean.getUserInfo();
        //存储个人信息
        if (userInfo != null) {
            edit.putString(Constants.SpKeyUserInfo.ADD_TIME, userInfo.getAdd_time());
            edit.putString(Constants.SpKeyUserInfo.CLIENT_ID, userInfo.getClient_id());
            edit.putString(Constants.SpKeyUserInfo.IS_ALLOWTALK, userInfo.getIs_allowtalk());
            edit.putString(Constants.SpKeyUserInfo.IS_BUY, userInfo.getIs_buy());
            edit.putString(Constants.SpKeyUserInfo.IS_HONGREN, userInfo.getIs_hongren());
            edit.putString(Constants.SpKeyUserInfo.LAST_UPDATE_TIME, userInfo.getLast_update_time());
            edit.putString(Constants.SpKeyUserInfo.LOGIN_DEV, userInfo.getLogin_dev());
            edit.putString(Constants.SpKeyUserInfo.LOGIN_PLATFORM, userInfo.getLogin_platform());
            edit.putString(Constants.SpKeyUserInfo.UID, userInfo.getUid());
            edit.putString(Constants.SpKeyUserInfo.USER_AREAID, userInfo.getUser_areaid());
            edit.putString(Constants.SpKeyUserInfo.USER_AVATAR, userInfo.getUser_avatar());
            edit.putString(Constants.SpKeyUserInfo.USER_CITYID, userInfo.getUser_cityid());
            edit.putString(Constants.SpKeyUserInfo.USER_LOGIN_IP, userInfo.getUser_login_ip());
            edit.putString(Constants.SpKeyUserInfo.USER_LOGIN_NUM, userInfo.getUser_login_num());
            edit.putString(Constants.SpKeyUserInfo.USER_LOGIN_TIME, userInfo.getUser_login_time());
            edit.putString(Constants.SpKeyUserInfo.USER_MOBILE, userInfo.getUser_mobile());
            edit.putString(Constants.SpKeyUserInfo.USER_NAME, userInfo.getUser_name());
            edit.putString(Constants.SpKeyUserInfo.USER_OLD_LOGIN_IP, userInfo.getUser_old_login_ip());
            edit.putString(Constants.SpKeyUserInfo.USER_OLD_LOGIN_TIME, userInfo.getUser_old_login_time());
            edit.putString(Constants.SpKeyUserInfo.USER_PROVINCEID, userInfo.getUser_provinceid());
            edit.putString(Constants.SpKeyUserInfo.USER_CONSTELLATION, userInfo.getConstellation());
            edit.putString(Constants.SpKeyUserInfo.USER_SEX, userInfo.getUser_sex());
            edit.putString(Constants.SpKeyUserInfo.USER_AGE, userInfo.getAge());
            edit.putString(Constants.SpKeyUserInfo.USER_STATE, userInfo.getUser_state());
            edit.putString(Constants.SpKeyUserInfo.USER_TRUENAME, userInfo.getUser_truename());
            edit.putString(Constants.SpKeyUserInfo.USER_BIRTHDAY, userInfo.getUser_birthday());
            edit.putInt(Constants.SpKeyUserInfo.IS_REG_EASEMOB, userInfo.getIs_reg_easemob());
            edit.putString(Constants.SpKeyUserInfo.USER_TYPE, userInfo.getUser_type());
        }
        //存储logintoken
        if (!TextUtils.isEmpty(loginBean.getLogintoken())) {
            edit.putString(Constants.SpKeyUserInfo.LOGIN_TOKEN, loginBean.getLogintoken());
        }

        edit.apply();

    }

}
