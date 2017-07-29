package com.bhtc.huajuan.push.util;

import android.webkit.JavascriptInterface;

import com.bhtc.huajuan.push.activity.LoginActivity;

import org.json.JSONObject;

/**
 * Created by forge on 2016/5/18.
 */
public class SubjectInterface {

    private LoginActivity loginActivity;
    public SubjectInterface(LoginActivity loginActivity) {
        this.loginActivity = loginActivity;
    }

    @JavascriptInterface
    public void postMessage(String str) {
        try {
            JSONObject jsonObject = new JSONObject(str);
            String method = (String) jsonObject.get("method");
            JSONObject params = null;
            try{
                params = ((JSONObject) jsonObject.get("params"));
            }catch (Exception e){
                e.printStackTrace();
                params = new JSONObject();
            }
            switch (method){
                case "isClickCaptBtn":  //人机验证逻辑：点击
                    break;
                case "loginVerificationSuccess":  //人机验证逻辑：成功
                    loginActivity.validata(true);
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
