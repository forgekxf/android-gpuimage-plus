package com.bhtc.huajuan.push.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bhtc.huajuan.push.App;
import com.bhtc.huajuan.push.R;
import com.bhtc.huajuan.push.bean.LoginBean;
import com.bhtc.huajuan.push.bean.ValidateBean;
import com.bhtc.huajuan.push.util.ApiUrls;
import com.bhtc.huajuan.push.util.HttpConfig;
import com.bhtc.huajuan.push.util.HttpHelper;
import com.bhtc.huajuan.push.util.MyCallBack;
import com.bhtc.huajuan.push.util.PermissionsChecker;
import com.bhtc.huajuan.push.util.SPDataUtil;
import com.bhtc.huajuan.push.util.SubjectInterface;
import com.bhtc.huajuan.push.util.UIUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import okhttp3.Call;

public class LoginActivity extends BaseActivity implements View.OnClickListener {

    private EditText mEtRegPhoneNumber;
    private EditText mEtRegCode;
    private TextView mTvRegSendCode;
    private TextView mTvLogin;
    private TextView user_http;

    final int SEND_CODE_INTERVAL = 60;

    private WebView mWebView;
    private SubjectInterface subjectInterface;

    private String mValidateUrl;
    private LinearLayout mLoginBg;


    private PermissionsChecker mPermissionsChecker;
    private static final int PERMISSION_REQUEST_CODE = 0; // 系统权限管理页面的参数
    private static final String PACKAGE_URL_SCHEME = "package:"; // 方案
    private AlertDialog.Builder builder;
    static final String[] PERMISSIONS = new String[]{
            "android.permission.WRITE_EXTERNAL_STORAGE",
            "android.permission.RECORD_AUDIO",
            "android.permission.CAMERA",
            "android.permission.INTERNET"
    };
    private String TAG = "LoginActivity";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mPermissionsChecker = new PermissionsChecker(this);
        initView();
    }

    protected void initView() {
        subjectInterface = new SubjectInterface(this);
        assignViews();
        initEvent();
    }

    public void validata(boolean success) {
        if (success) {
            mWebView.setVisibility(View.VISIBLE);
            mTvLogin.setBackgroundResource(R.drawable.btn_go_money);
            mTvLogin.setEnabled(true);
            mTvLogin.setClickable(true);

            mTvRegSendCode.setEnabled(true);
            mTvRegSendCode.setClickable(true);
            mTvRegSendCode.setTextColor(UIUtils.getColor(R.color.red_FF737E));
            mTvRegSendCode.setBackgroundResource(R.drawable.bg_transparent_ff737e_4);
            mTvRegSendCode.setText(UIUtils.getString(R.string.send_code));
        }
    }

    protected void initEvent() {
        mTvRegSendCode.setOnClickListener(this);
        mTvLogin.setOnClickListener(this);
        user_http.setOnClickListener(this);
    }

    private void assignViews() {
        mWebView = (WebView) findViewById(R.id.login_dialog_wb);
        mEtRegPhoneNumber = (EditText) findViewById(R.id.et_reg_phone_number);
        mEtRegCode = (EditText) findViewById(R.id.et_reg_code);
        mTvRegSendCode = (TextView) findViewById(R.id.tv_reg_send_code);
        mTvLogin = (TextView) findViewById(R.id.tv_login);
        user_http = (TextView) findViewById(R.id.user_http);
        mLoginBg = (LinearLayout) findViewById(R.id.login_bg);
    }

    private int restSecond = SEND_CODE_INTERVAL;

    @Override
    public void onClick(View v) {
        String phoneNum = mEtRegPhoneNumber.getText().toString().trim();
        String code = mEtRegCode.getText().toString().trim();
        switch (v.getId()) {
            case R.id.tv_reg_send_code:
//                if (checkPhoneNum(phoneNum)) {
//                    sendCode();
//                }
//                startActivity(new Intent(this, MainActivity.class));
                break;
            case R.id.tv_login:
                if (mPermissionsChecker.lacksPermissions(PERMISSIONS)) {
                    requestPermissions(PERMISSIONS);
                    return;
                }
                if (checkCanLogin(phoneNum, code, mEtRegPhoneNumber, mEtRegCode)) {
                    doMobileLogin(phoneNum, code);
                }
                break;
            case R.id.user_http:
                startActivity(new Intent(this, ProtocolActivity.class));
                break;
        }
    }

    /**
     * 进行手机和验证码登录
     *
     * @param phoneNum
     * @param code
     */
    private void doMobileLogin(String phoneNum, String code) {
        HttpHelper.doMobileLogin("hj", phoneNum, code, "", new MyCallBack<LoginBean>(LoginBean.class, this) {
            @Override
            public void onError(Call call, Exception e) {
                UIUtils.showToastSafe(LoginActivity.this.getResources().getString(R.string.net_error));
            }

            @Override
            public void onResponse(LoginBean loginBean) {
                if (loginBean != null && loginBean.success()) {
                    SPDataUtil.saveUserInfo(loginBean);
                    initPushData();
                } else if (loginBean != null) {
                    switch (loginBean.getCode()) {
                        case HttpConfig.ResultCode.CODE_ERROR_16:
                            UIUtils.showToastSafe(R.string.err_code);
                            break;
                        default:
                            UIUtils.showToastSafe(loginBean.getInfo());
                    }
                } else {
                    UIUtils.showToastSafe(R.string.date_error);
                }
            }
        });
    }

    private void initPushData() {
        startActivity(new Intent(this,ReserveListActivity.class));
    }

    /**
     * 发送手机验证码
     */
    private void sendCode() {
        String phone = mEtRegPhoneNumber.getText().toString().trim();
        HttpHelper.getMobileLoginCode(phone, new MyCallBack<ValidateBean>(ValidateBean.class, this) {
            @Override
            public void onError(Call call, Exception e) {
                UIUtils.showToastSafe(R.string.net_error);
            }

            @Override
            public void onResponse(ValidateBean baseBean) {
                if (baseBean.success()) {
                    restSecond = 60;
                    final String defaultString = mTvRegSendCode.getText().toString();
                    mTvRegSendCode.setEnabled(false);
                    mTvRegSendCode.setClickable(false);
                    mTvRegSendCode.setTextColor(UIUtils.getColor(R.color.divide_999999));
                    mTvRegSendCode.setBackgroundResource(R.drawable.bg_grayf4f4f4_bfbfbf_4);
                    mTvRegSendCode.setText(String.format(UIUtils.getString(R.string.reg_rest_code), restSecond));
                    App.getMainThreadHandler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            restSecond--;
                            mTvRegSendCode.setText(String.format(UIUtils.getString(R.string.reg_rest_code), restSecond));
                            if (restSecond == 0) {
                                mTvRegSendCode.setEnabled(true);
                                mTvRegSendCode.setClickable(true);
                                mTvRegSendCode.setTextColor(UIUtils.getColor(R.color.red_FF737E));
                                mTvRegSendCode.setBackgroundResource(R.drawable.bg_transparent_ff737e_4);
                                mTvRegSendCode.setText(defaultString);
                                App.getMainThreadHandler().removeCallbacks(this);
                            } else {
                                App.getMainThreadHandler().postDelayed(this, 1000);
                            }
                        }
                    }, 1000);
                } else {
                    switch (baseBean.getCode()) {
                        case HttpConfig.ResultCode.CODE_TIME_OUT:
                            UIUtils.showToastSafe(baseBean.getInfo());
                            break;
                        case HttpConfig.ResultCode.PARAMS_ERROR_03:
                            UIUtils.showToastSafe(baseBean.getInfo());
                            break;
                        case HttpConfig.ResultCode.SEND_CODE_FAILURE_15:
                            UIUtils.showToastSafe(baseBean.getInfo());
                            restSecond = 60;
                            final String defaultString = mTvRegSendCode.getText().toString();
                            mTvRegSendCode.setEnabled(false);
                            mTvRegSendCode.setClickable(false);
                            mTvRegSendCode.setTextColor(UIUtils.getColor(R.color.divide_999999));
                            mTvRegSendCode.setBackgroundResource(R.drawable.bg_grayf4f4f4_bfbfbf_4);
                            mTvRegSendCode.setText(String.format(UIUtils.getString(R.string.reg_rest_code), restSecond));
                            App.getMainThreadHandler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    restSecond--;
                                    mTvRegSendCode.setText(String.format(UIUtils.getString(R.string.reg_rest_code), restSecond));
                                    if (restSecond == 0) {
                                        mTvRegSendCode.setEnabled(true);
                                        mTvRegSendCode.setClickable(true);
                                        mTvRegSendCode.setTextColor(UIUtils.getColor(R.color.red_FF737E));
                                        mTvRegSendCode.setBackgroundResource(R.drawable.bg_transparent_ff737e_4);
                                        mTvRegSendCode.setText(UIUtils.getString(R.string.reg_rest_code_error));
                                        App.getMainThreadHandler().removeCallbacks(this);
                                    } else {
                                        App.getMainThreadHandler().postDelayed(this, 1000);
                                    }
                                }
                            }, 1000);
                            break;
                        case HttpConfig.ResultCode.CODE_ERROR_10042:
                            if (!UIUtils.isEmpty(baseBean.getValidate_url())) {
                                mValidateUrl = baseBean.getValidate_url();
                                checkValidateCode();
                            }
                            break;
                    }
                }
            }
        });
    }

    private void checkValidateCode() {
        sendCodeClickFalse();
        mWebView.setVisibility(View.VISIBLE);
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.addJavascriptInterface(subjectInterface, "SubjectInterface");
        mWebView.loadUrl(ApiUrls.getHtml5BaseUrl(mValidateUrl));
        Log.e("url::::::", ApiUrls.getHtml5BaseUrl(mValidateUrl));
        mTvLogin.setBackgroundResource(R.drawable.btn_check_code);
        mTvLogin.setEnabled(false);
        mTvLogin.setClickable(false);
    }

    private void sendCodeClickFalse() {
        mTvRegSendCode.setEnabled(false);
        mTvRegSendCode.setClickable(false);
        mTvRegSendCode.setTextColor(Color.parseColor("#999999"));
        mTvRegSendCode.setBackgroundResource(R.drawable.bg_grayf4f4f4_bfbfbf_4);
        mTvRegSendCode.setText(UIUtils.getString(R.string.send_code));
    }

    public boolean checkPhoneNum(String phone) {
        if (TextUtils.isEmpty(phone)) {
            Toast.makeText(this, getResources().getString(R.string.tip_fill_phone_num), Toast.LENGTH_SHORT).show();
            return false;
        }
        if (!isMobileNO(phone)) {
            Toast.makeText(this, getResources().getString(R.string.tip_phone_num_err_format), Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    public boolean checkCanLogin(String phone, String code, android.widget.TextView mPhone, android.widget.TextView mCode) {
        if (TextUtils.isEmpty(phone)) {
            Toast.makeText(this, getResources().getString(R.string.tip_fill_phone_num), Toast.LENGTH_SHORT).show();
            return false;
        }
        if (TextUtils.isEmpty(code)) {
            Toast.makeText(this, getResources().getString(R.string.tip_fill_code), Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    public static boolean isMobileNO(String mobiles) {
        boolean flag = false;
        try {
            Pattern p = Pattern.compile("^1\\d{10}$");
            Matcher m = p.matcher(mobiles);
            flag = m.matches();
        } catch (Exception e) {
            flag = false;
        }
        return flag;
    }


    private void requestPermissions(String... permissions) {
        ActivityCompat.requestPermissions(this, permissions, PERMISSION_REQUEST_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode != PERMISSION_REQUEST_CODE && hasAllPermissionsGranted(grantResults)) {
            showMissingPermissionDialog();
        }
    }

    private boolean hasAllPermissionsGranted(@NonNull int[] grantResults) {
        for (int grantResult : grantResults) {
            if (grantResult == PackageManager.PERMISSION_DENIED) {
                return false;
            }
        }
        return true;
    }

    private void showMissingPermissionDialog() {
        builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.help);
        builder.setCancelable(false);
        builder.setMessage(getResources().getString(R.string.check_per));
        builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.setPositiveButton(R.string.settings_per, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                startAppSettings();
            }
        });
        builder.show();
    }

    private void startAppSettings() {
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        intent.setData(Uri.parse(PACKAGE_URL_SCHEME + getPackageName()));
        startActivity(intent);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mPermissionsChecker.lacksPermissions(PERMISSIONS)) {
            requestPermissions(PERMISSIONS); // 请求权限
        }
    }
}
