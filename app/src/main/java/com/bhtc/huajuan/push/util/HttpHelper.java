package com.bhtc.huajuan.push.util;

import android.provider.Settings;
import android.text.TextUtils;
import android.webkit.WebView;

import com.bhtc.huajuan.push.App;
import com.bhtc.huajuan.push.BuildConfig;
import com.bhtc.huajuan.push.bean.BaseBean;
import com.bhtc.huajuan.push.bean.LiveGoodsBean;
import com.bhtc.huajuan.push.bean.PushInitDataBean;
import com.bhtc.huajuan.push.bean.ReserveListBean;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.builder.PostFormBuilder;
import com.zhy.http.okhttp.callback.Callback;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Headers;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;

/**
 * Created by kouxiongfei on 2017/5/13.
 */

public class HttpHelper {

    static {
        OkHttpUtils.getInstance(providerOkhttpClient());
    }

    private static OkHttpClient providerOkhttpClient() {

        //日志打印的logger
        HttpLoggingInterceptor logger = new HttpLoggingInterceptor();
        logger.setLevel(HttpLoggingInterceptor.Level.BODY);

        return new OkHttpClient()
                .newBuilder()
                .connectTimeout(12, TimeUnit.SECONDS)//网络超时
                .addInterceptor(new CommonHeaderInterceptor())
                .addInterceptor(logger)
                .build();
    }

    /**
     * 给所有的接口添加上通用的header
     */
    private static class CommonHeaderInterceptor implements Interceptor {
        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request();
            Map<String, String> commonHeaders = getCommonHeaders();
            request = request.newBuilder().headers(Headers.of(commonHeaders)).build();
            return chain.proceed(request);
        }
    }

    private static String WEBVIEW_UA = new WebView(App.App).getSettings().getUserAgentString();

    public static Map<String, String> getCommonHeaders() {
        String loginToken = SPUtils.getString(Constants.SpName.USER_INFO, Constants.SpKeyUserInfo.LOGIN_TOKEN, "");
        HashMap<String, String> headers = new HashMap<>();
        headers.put(HttpConfig.HeaderKeyParams.X_REQUESTED_WITH, HttpConfig.HeaderValueParams.XML_HTTP_REQUEST);
        headers.put(HttpConfig.HeaderKeyParams.SERVICE, HttpConfig.HeaderValueParams.HJAPP_ANDROID);
        headers.put(HttpConfig.HeaderKeyParams.USER_AGENT, WEBVIEW_UA);

        headers.put(HttpConfig.HeaderKeyParams.VERSION_NAME, "1.0");

        if (!TextUtils.isEmpty(loginToken)) {
            headers.put(HttpConfig.HeaderKeyParams.COOKIE, String.format(HttpConfig.HeaderValueParams.LOGIN_TOKEN, loginToken));
        }
        if (BuildConfig.DEBUG) {
            headers.put(HttpConfig.HeaderKeyParams.VM, HttpConfig.HeaderValueParams.DEBUG_VM);
        } else {
            headers.put(HttpConfig.HeaderKeyParams.VM, ApiUrls.VM);
        }
        return headers;
    }

    /**
     * 获取手机的登录验证码
     *
     * @param phone
     * @param callback
     */
    public static void getMobileLoginCode(String phone, Callback callback) {
        OkHttpUtils.post().url(ApiUrls.getBaseUrl(ApiUrls.GET_MOBILE_LOGIN_CODE)).headers(getCommonHeaders()).addParams(HttpConfig.KeyParmas.MOBILE, phone).build().execute(callback);
    }

    /**
     * 直接用手机号进行登录
     *
     * @param phone
     * @param callback
     */
    public static void doMobileLogin(String channel, String phone, String code, String clientId, Callback callback) {
        PostFormBuilder postFormBuilder = OkHttpUtils.post().url(ApiUrls.getBaseUrl(ApiUrls.DO_MOBILE_LOGIN)).headers(getCommonHeaders());
        postFormBuilder.addParams(HttpConfig.KeyParmas.MOBILE, phone);
        postFormBuilder.addParams(HttpConfig.KeyParmas.CODE, code);
        postFormBuilder.addParams(HttpConfig.KeyParmas.DEVICE, Settings.Secure.getString(App.getInstance().getContentResolver(), Settings.Secure.ANDROID_ID));
//        postFormBuilder.addParams("last_feed_time", SPUtils.getInt(Constants.SpName.USER_INFO, Constants.SpKeyUserInfo.LAST_FEED_TIME, 0) + "");//全局变量需要参数来得到 newFeed 的值
//        if (!UIUtils.isEmpty(clientId))
//            postFormBuilder.addParams(HttpConfig.KeyParmas.CLIENT_ID, clientId);
        postFormBuilder.build().execute(callback);
    }

//    /**
//     * 获取推流地址
//     */
//    public static void getPublishUrl(MyCallBack<PublishBean> callback) {
//        OkHttpUtils.get().url(ApiUrls.getLivestreaming_dm(ApiUrls.PUBLISH_URL)).headers(getCommonHeaders()).build().execute(callback);
//    }

    /**
     * 直播初始化
     */
    public static void getPublishInitdata(String liveId, MyCallBack<PushInitDataBean> callback) {
        OkHttpUtils.get().url(ApiUrls.getBaseUrl(ApiUrls.PUBLISH_INIT)).headers(getCommonHeaders())
                .addParams("client", Settings.Secure.getString(App.getInstance().getContentResolver(), Settings.Secure.ANDROID_ID))
                .addParams("live_id", liveId)
                .build().execute(callback);
    }

    /**
     * 直播红人端初始化
     */
    public static void getPublishLivehr(MyCallBack<PushInitDataBean> callback) {
        OkHttpUtils.get().url(ApiUrls.getBaseUrl(ApiUrls.PUBLISH_LIVEHR)).headers(getCommonHeaders()).build().execute(callback);
    }

    /**
     * 开始直播
     */
    public static void startLive(String liveId, String streamKey, MyCallBack<BaseBean> callback) {
        OkHttpUtils.get().url(ApiUrls.getBaseUrl(ApiUrls.START_LIVE)).headers(getCommonHeaders())
                .addParams("client", Settings.Secure.getString(App.getInstance().getContentResolver(), Settings.Secure.ANDROID_ID))
                .addParams("live_id", liveId)
                .addParams("stream_key", streamKey)
                .build().execute(callback);
    }

    /**
     * 结束直播
     */
    public static void exitLive(String liveId, String streamKey, MyCallBack<BaseBean> callback) {
        OkHttpUtils.get().url(ApiUrls.getBaseUrl(ApiUrls.EXIT_LIVE)).headers(getCommonHeaders()).addParams("live_id", liveId).addParams("stream_key", streamKey).build().execute(callback);
    }

    /**
     * 获取直播关联商品列表
     */
    public static void getLiveGoods(String stream_key,String liveId, MyCallBack<LiveGoodsBean> callback) {
        OkHttpUtils.get().url(ApiUrls.getBaseUrl(ApiUrls.GET_LIVE_GOODS)).headers(getCommonHeaders()).addParams("live_id", liveId).addParams("stream_key", stream_key).build().execute(callback);
    }

    /**
     * 获取红人预约直播列表
     */
    public static void getReserveList(int offset, MyCallBack<ReserveListBean> callback) {
        OkHttpUtils.get().url(ApiUrls.getBaseUrl(ApiUrls.GET_LIVE_RESERVE)).headers(getCommonHeaders())
                .addParams("client", Settings.Secure.getString(App.getInstance().getContentResolver(), Settings.Secure.ANDROID_ID))
                .addParams("offset", offset + "")
                .build().execute(callback);
    }

    /**
     * 获取各个主域名
     *
     * @param callback
     */
    public static void getHttpBase(Callback callback) {
        OkHttpUtils.get().url(ApiUrls.BASE_HTTP)
                .headers(getCommonHeaders())
                .build().execute(callback);
    }

}
