package com.bhtc.huajuan.push.util;

import android.app.Activity;
import android.content.Context;

import com.bhtc.huajuan.push.R;
import com.google.gson.GsonBuilder;
import com.zhy.http.okhttp.callback.Callback;

import okhttp3.Call;
import okhttp3.Request;
import okhttp3.Response;


/**
 * Created by qkk on 2016/4/19.
 */
public abstract class MyCallBack<T> extends Callback<T> {

    /**
     * application的Context的无法创建对话框的
     */
    private Context mContext;
    private boolean isShowDialog;
    private Class<T> mTargetClazz;

    /**
     * 传入的context必须 具有token，application的token是不可以的
     *
     * @param mContext
     * @param isShowDialog
     */
    public MyCallBack(Class<T> targetClazz, Context mContext, boolean isShowDialog) {
        this.mContext = mContext;
        this.isShowDialog = isShowDialog;
        this.mTargetClazz = targetClazz;
    }

    public MyCallBack(Class<T> targetClazz, Context mContext) {
        this(targetClazz, mContext, true);
    }

    private LoadingDialogFragment progressDialog;

    @Override
    public T parseNetworkResponse(Response response) throws Exception {
        return new GsonBuilder().create().fromJson(response.body().string(), mTargetClazz);
    }

    @Override
    public void onError(Call call, Exception e) {
        e.printStackTrace();
        UIUtils.showToastSafe(R.string.net_error);
    }

    @Override
    public void onBefore(Request request) {
        if (isShowDialog && mContext != null) {
            if (progressDialog == null) {
                progressDialog = new LoadingDialogFragment(mContext, R.style.add_dialog);
            }
            if (mContext instanceof Activity && !((Activity)mContext).isFinishing()) {
                progressDialog.show();
            }
        }
        super.onBefore(request);
    }

    @Override
    public void onAfter() {
        try {
            if (progressDialog != null && progressDialog.isShowing()) {
                progressDialog.dismiss();
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
        super.onAfter();
    }
}
