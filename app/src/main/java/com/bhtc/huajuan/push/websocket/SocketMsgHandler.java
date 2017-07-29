package com.bhtc.huajuan.push.websocket;

import android.os.Handler;
import android.os.Looper;
import android.provider.Settings;
import android.util.Log;

import com.bhtc.huajuan.push.App;
import com.bhtc.huajuan.push.R;
import com.bhtc.huajuan.push.bean.WSMessageBean;
import com.bhtc.huajuan.push.util.Constants;
import com.bhtc.huajuan.push.util.SPDataUtil;
import com.bhtc.huajuan.push.util.SPUtils;
import com.bhtc.huajuan.push.util.UIUtils;
import com.google.gson.GsonBuilder;

import org.greenrobot.eventbus.EventBus;

import java.util.Random;

/**
 * WebSocket 消息处理
 */
public class SocketMsgHandler {
    private static String TAG = "SocketMsgHandler";
    private static Random r = new Random();
    public static int pingCount = 0;    //ping无反应次数
    public static boolean liveClose = false;  //判断用户退出
    public static int MIN_SECOND = 60;
    public static int MAX_SECOND = 70;
    public static int delayeTime = 2000;
    public static boolean isLookVideo = false;

    public static int stepInChannelTime;  //进入直播间
    private static Handler mStepInChannelHandler = new Handler(Looper.getMainLooper());

    /**
     * 循环发进入直播间消息
     */
    public static void startLoopStepInChannel() {
        if (isLookVideo) {
            return;
        }
        stepInChannelTime += 4;
        if (stepInChannelTime > 8) {
            stepInChannelTime = 4;
        }
        mStepInChannelHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                SocketMsgHandler.stepInChannelMsg();
                startLoopStepInChannel();
            }
        }, stepInChannelTime * 1000);
    }

    /**
     * 停止循环发进入直播间消息
     */
    public static void stopLoopStepInChannel() {
        if (isLookVideo) {
            return;
        }
        mStepInChannelHandler.removeCallbacksAndMessages(null);
    }

    /**
     * 链接websocket
     */
    public static void startSocket() {
        SocketUtil.start();
    }

    /**
     * 非正常断开后重新链接websocket
     */
    public static void reStartSocket() {
        if (delayeTime >= delayeTime * 16) {
            delayeTime = 2000;
        }
        App.getMainThreadHandler().postDelayed(new Runnable() {
            @Override
            public void run() {
                delayeTime *= 2;
                startSocket();
            }
        }, delayeTime);
    }

    /**
     * 断开websocket
     */
    public static void stopSocket() {
        if (SocketUtil.mConnection.isConnected()) {
            App.getMainThreadHandler().removeCallbacksAndMessages(null);
            SocketUtil.mConnection.disconnect();
        }
    }

    /**
     * 重启websocket消息
     */
    public static boolean reStart(WSMessageBean wsMessageBean) {
        if (wsMessageBean.reStart()) {
            stopSocket();
            if (delayeTime >= delayeTime * 16) {
                delayeTime = 2000;
            }
            App.getMainThreadHandler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    delayeTime *= 2;
                    startSocket();
                }
            }, delayeTime);
            return true;
        }
        return false;
    }

    /**
     * 处理接收到的消息
     */
    public static void handleMessage(String message) {
        try {
            WSMessageBean wsMessageBean = new GsonBuilder().create().fromJson(message, WSMessageBean.class);

            if (wsMessageBean != null && wsMessageBean.channelNotExist()) {  //判断频道不存在
                if (liveClose) {
                    stopSocket();
                    return;
                }
                UIUtils.showToastSafe(R.string.channel_not_exist);
                return;
            }
            if (liveClose) {
                stopSocket();
                return;
            }
            pingCount = 0;
            if (wsMessageBean != null && !reStart(wsMessageBean) && wsMessageBean.getCode() != WSMessageBean.PING_SUCCESS) {
                stopLoopStepInChannel();
                EventBus.getDefault().post(wsMessageBean);
            }
            pingMsg();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 发送操作
     */
    private static void sendMessage(String toJson) {
        try {
            SocketUtil.mConnection.sendTextMessage(toJson);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 发送普通消息
     */
    public static void sendMsg(String message) {
        WSMessageBean messageBean = new WSMessageBean();
        WSMessageBean.MessageData data = new WSMessageBean.MessageData();
        data.setMsg(message);
        messageBean.setAction_data(data);
        messageBean.setAction_type(WSMessageBean.MSG);
        messageBean.setChannel(WSMessageBean.CHANNEL_TESTING);
        messageBean.setLogintoken(SPDataUtil.getUserToken());
        String toJson = new GsonBuilder().create().toJson(messageBean);
        Log.i(TAG, toJson);
        sendMessage(toJson);
    }

    /**
     * 进入直播间消息
     */
    public static void stepInChannelMsg() {
        delayeTime = 2000;
        WSMessageBean messageBean = new WSMessageBean();
        messageBean.setAction_type(WSMessageBean.STEP_IN_CHANNEL);
        messageBean.setChannel(WSMessageBean.CHANNEL_TESTING);
        messageBean.setLogintoken(SPDataUtil.getUserToken());
        WSMessageBean.MessageData messageData = new WSMessageBean.MessageData();
        if (isLookVideo) {
            messageData.setHongren_look(1);
        }
        messageData.setClient(Settings.Secure.getString(App.getInstance().getContentResolver(), Settings.Secure.ANDROID_ID));
        messageData.setUid(SPUtils.getString(Constants.SpName.USER_INFO, Constants.SpKeyUserInfo.UID, ""));
        messageData.setUser_name(SPUtils.getString(Constants.SpName.USER_INFO, Constants.SpKeyUserInfo.USER_NAME, ""));
        messageData.setUser_avatar(SPUtils.getString(Constants.SpName.USER_INFO, Constants.SpKeyUserInfo.USER_AVATAR, ""));
        messageBean.setAction_data(messageData);
        String toJson = new GsonBuilder().create().toJson(messageBean);
        Log.i(TAG, toJson);
        sendMessage(toJson);
        liveClose = false;
        pingMsg();  //进入直播间开始ping
    }

    /**
     * 退出直播间消息
     */
    public static void stepOutChannelMsg() {
        WSMessageBean messageBean = new WSMessageBean();
        messageBean.setAction_type(WSMessageBean.STEP_OUT_CHANNEL);
        messageBean.setChannel(WSMessageBean.CHANNEL_TESTING);
        messageBean.setLogintoken(SPDataUtil.getUserToken());
        String toJson = new GsonBuilder().create().toJson(messageBean);
        Log.i(TAG, toJson);
        liveClose = true;
        sendMessage(toJson);

        stopLoopStepInChannel();

        App.getMainThreadHandler().postDelayed(new Runnable() {
            @Override
            public void run() {
                stopSocket();  //防止没有接收到结束直播的消息
            }
        }, 2000);
    }

//    /**
//     * 结束直播
//     */
//    public static void closeLiveMsg() {
//        WSMessageBean messageBean = new WSMessageBean();
//        messageBean.setAction_type(WSMessageBean.STEP_OUT_HONGREN_MAIN);
//        messageBean.setChannel(WSMessageBean.CHANNEL_TESTING);
//        messageBean.setLogintoken(SPDataUtil.getUserToken());
//        String toJson = new GsonBuilder().create().toJson(messageBean);
//        Log.i(TAG, toJson);
//        sendMessage(toJson);
//    }

    /**
     * 购买消息
     */
    public static void buyMsg(String goodsId, String goodsName, String cartId) {
        WSMessageBean messageBean = new WSMessageBean();
        WSMessageBean.MessageData data = new WSMessageBean.MessageData();
        data.setGoods_id(goodsId);
        data.setGoods_name(goodsName);
        data.setCart_id(cartId);
        messageBean.setAction_data(data);
        messageBean.setAction_type(WSMessageBean.BUY);
        messageBean.setChannel(WSMessageBean.CHANNEL_TESTING);
        messageBean.setLogintoken(SPDataUtil.getUserToken());
        String toJson = new GsonBuilder().create().toJson(messageBean);
        Log.i(TAG, toJson);
        sendMessage(toJson);
    }

    /**
     * 抢字消息
     */
    public static void qiangMsg(String goodsId, String limit, String name, String image, String discount, String spec_price_id, String price, String marketParce, String desc,String state,String stock,String spec_images) {
        WSMessageBean messageBean = new WSMessageBean();
        WSMessageBean.MessageData data = new WSMessageBean.MessageData();
        data.setGoods_id(goodsId);
        data.setLimit(limit);
        data.setGoods_name(name);
        data.setGoods_desc(desc);
        data.setGoods_image(image);
        data.setDiscount(discount);
        data.setSpec_images(spec_images);
        data.setGoods_state(state);
        data.setGoods_stock(stock);
        data.setSpec_price_id(spec_price_id);
        data.setGoods_price(price);
        data.setGoods_marketprice(marketParce);
        messageBean.setAction_data(data);
        messageBean.setAction_type(WSMessageBean.SHOW_GRAB_BUTTON);
        messageBean.setChannel(WSMessageBean.CHANNEL_TESTING);
        messageBean.setLogintoken(SPDataUtil.getUserToken());
        String toJson = new GsonBuilder().create().toJson(messageBean);
        Log.i(TAG, toJson);
        sendMessage(toJson);
    }

    /**
     * 隐藏抢字消息
     */
    public static void hiddenQiangMsg(String goodsId, String limit, String name, String image, String discount, String spec_price_id, String price, String marketParce, String desc) {
        WSMessageBean messageBean = new WSMessageBean();
        WSMessageBean.MessageData data = new WSMessageBean.MessageData();
        data.setGoods_id(goodsId);
        data.setLimit(limit);
        data.setGoods_desc(desc);
        data.setGoods_name(name);
        data.setGoods_image(image);
        data.setDiscount(discount);
        data.setGoods_price(price);
        data.setSpec_price_id(spec_price_id);
        data.setGoods_marketprice(marketParce);
        messageBean.setAction_data(data);
        messageBean.setAction_type(WSMessageBean.HIDDEN_GRAB_BUTTON);
        messageBean.setChannel(WSMessageBean.CHANNEL_TESTING);
        messageBean.setLogintoken(SPDataUtil.getUserToken());
        String toJson = new GsonBuilder().create().toJson(messageBean);
        Log.i(TAG, toJson);
        sendMessage(toJson);
    }

    /**
     * 禁言
     */
    public static void notSpeak(String uid) {
        WSMessageBean messageBean = new WSMessageBean();
        WSMessageBean.MessageData data = new WSMessageBean.MessageData();
        data.setBlock_uid(uid);
        data.setBlock_user_name("禁言");
        data.setExpires("30");
        messageBean.setAction_data(data);
        messageBean.setAction_type(WSMessageBean.BLOCK);
        messageBean.setChannel(WSMessageBean.CHANNEL_TESTING);
        messageBean.setLogintoken(SPDataUtil.getUserToken());
        String toJson = new GsonBuilder().create().toJson(messageBean);
        Log.i(TAG, toJson);
        sendMessage(toJson);
    }

    /**
     * 关注消息
     *
     * @param uid
     * @param userName
     */
    public static void followMsg(String uid, String userName) {
        WSMessageBean messageBean = new WSMessageBean();
        WSMessageBean.MessageData data = new WSMessageBean.MessageData();
        data.setFuid(uid);
        data.setFuname(userName);
        messageBean.setAction_data(data);
        messageBean.setAction_type(WSMessageBean.FOLLOW);
        messageBean.setChannel(WSMessageBean.CHANNEL_TESTING);
        messageBean.setLogintoken(SPDataUtil.getUserToken());
        String toJson = new GsonBuilder().create().toJson(messageBean);
        Log.i(TAG, toJson);
        sendMessage(toJson);
    }

    /**
     * 客服发消息
     */
    public static void sendMsgHj(String message) {
        WSMessageBean messageBean = new WSMessageBean();
        WSMessageBean.MessageData data = new WSMessageBean.MessageData();
        data.setMsg(message);
        messageBean.setAction_data(data);
        messageBean.setAction_type(WSMessageBean.ADMIN_MSG);
        messageBean.setChannel(WSMessageBean.CHANNEL_TESTING);
        messageBean.setLogintoken(SPDataUtil.getUserToken());
        String toJson = new GsonBuilder().create().toJson(messageBean);
        Log.i(TAG, toJson);
        sendMessage(toJson);
    }

    /**
     * 公告
     */
    public static void sendMsgNotify(String message) {
        WSMessageBean messageBean = new WSMessageBean();
        WSMessageBean.MessageData data = new WSMessageBean.MessageData();
        data.setMsg(message);
        messageBean.setAction_data(data);
        messageBean.setAction_type(WSMessageBean.ANNOUNCEMENT);
        messageBean.setChannel(WSMessageBean.CHANNEL_TESTING);
        messageBean.setLogintoken(SPDataUtil.getUserToken());
        String toJson = new GsonBuilder().create().toJson(messageBean);
        Log.i(TAG, toJson);
        sendMessage(toJson);
    }

    // 红人推送商品
    public static void sendGoodsHongRen(String goods_id, String goods_name, String goods_price, String goods_marketprice, String goods_hj_price, String goods_stock, String goods_image) {
        WSMessageBean messageBean = new WSMessageBean();
        WSMessageBean.MessageData data = new WSMessageBean.MessageData();
        data.setGoods_id(goods_id);
        data.setGoods_name(goods_name);
        data.setGoods_price(goods_price);
        data.setGoods_marketprice(goods_marketprice);
        data.setGoods_hj_price(goods_hj_price);
        data.setGoods_stock(goods_stock);
        data.setGoods_image(goods_image);
        messageBean.setAction_data(data);
        messageBean.setAction_type(WSMessageBean.SHOW_GOODS_HONGREN);
        messageBean.setChannel(WSMessageBean.CHANNEL_TESTING);
        messageBean.setLogintoken(SPDataUtil.getUserToken());
        String toJson = new GsonBuilder().create().toJson(messageBean);
        Log.i(TAG, toJson);
        sendMessage(toJson);
    }

    /**
     * 推送商品
     */
    public static void sendGoods(String goods_id, String goods_name, String goods_price, String goods_marketprice, String goods_hj_price, String goods_stock, String goods_image, String goods_state) {
        WSMessageBean messageBean = new WSMessageBean();
        WSMessageBean.MessageData data = new WSMessageBean.MessageData();
        data.setGoods_id(goods_id);
        data.setGoods_name(goods_name);
        data.setGoods_price(goods_price);
        data.setGoods_marketprice(goods_marketprice);
        data.setGoods_hj_price(goods_hj_price);
        data.setGoods_stock(goods_stock);
        data.setGoods_image(goods_image);
        data.setGoods_state(goods_state);
        messageBean.setAction_data(data);
        messageBean.setAction_type(WSMessageBean.SHOW_GOODS);
        messageBean.setChannel(WSMessageBean.CHANNEL_TESTING);
        messageBean.setLogintoken(SPDataUtil.getUserToken());
        String toJson = new GsonBuilder().create().toJson(messageBean);
        Log.i(TAG, toJson);
        sendMessage(toJson);
    }

    /**
     * 观看显示以弹出商品
     */
    public static void sendGoodsForVideoPlay(String goods_id, String goods_name, String goods_price, String goods_marketprice, String goods_hj_price, String goods_stock, String goods_image, String goods_state) {
        WSMessageBean messageBean = new WSMessageBean();
        WSMessageBean.MessageData data = new WSMessageBean.MessageData();
        data.setGoods_id(goods_id);
        data.setGoods_name(goods_name);
        data.setGoods_price(goods_price);
        data.setGoods_marketprice(goods_marketprice);
        data.setGoods_hj_price(goods_hj_price);
        data.setGoods_stock(goods_stock);
        data.setGoods_image(goods_image);
        data.setGoods_state(goods_state);
        messageBean.setAction_data(data);
        messageBean.setAction_type(WSMessageBean.SHOW_GOODS);
        messageBean.setChannel(WSMessageBean.CHANNEL_TESTING);
        messageBean.setLogintoken(SPDataUtil.getUserToken());
        EventBus.getDefault().post(messageBean);
    }

    /**
     * 隐藏商品
     */
    public static void hiddenGoods(String goods_id, String goods_name, String goods_price, String goods_marketprice, String goods_hj_price, String goods_stock, String goods_image) {
        WSMessageBean messageBean = new WSMessageBean();
        WSMessageBean.MessageData data = new WSMessageBean.MessageData();
        data.setGoods_id(goods_id);
        data.setGoods_name(goods_name);
        data.setGoods_price(goods_price);
        data.setGoods_marketprice(goods_marketprice);
        data.setGoods_hj_price(goods_hj_price);
        data.setGoods_stock(goods_stock);
        data.setGoods_image(goods_image);
        messageBean.setAction_data(data);
        messageBean.setAction_type(WSMessageBean.HIDDEN_GOODS);
        messageBean.setChannel(WSMessageBean.CHANNEL_TESTING);
        messageBean.setLogintoken(SPDataUtil.getUserToken());
        String toJson = new GsonBuilder().create().toJson(messageBean);
        Log.i(TAG, toJson);
        sendMessage(toJson);
    }

    /**
     * 发送@消息
     */
    public static void sendMsgWithUser(String message, String parent_id, String to_uid, String to_name) {
        WSMessageBean messageBean = new WSMessageBean();
        WSMessageBean.MessageData data = new WSMessageBean.MessageData();
        data.setParent_id(parent_id);
        data.setTo_uid(to_uid);
        data.setTo_user_name(to_name);
        data.setMsg(message);
        messageBean.setAction_data(data);
        messageBean.setAction_type(WSMessageBean.MSG);
        messageBean.setChannel(WSMessageBean.CHANNEL_TESTING);
        messageBean.setLogintoken(SPDataUtil.getUserToken());
        String toJson = new GsonBuilder().create().toJson(messageBean);
        Log.i(TAG, toJson);
        sendMessage(toJson);
    }

    /**
     * ping
     */
    public static void pingMsg() {
        try {
            int waitTime = (r.nextInt(MIN_SECOND) + (MAX_SECOND - MIN_SECOND)) * 1000;
            if (waitTime <= 0) {
                waitTime = 60000;
            }
            App.getMainThreadHandler().removeCallbacksAndMessages(null);
            App.getMainThreadHandler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    WSMessageBean messageBean = new WSMessageBean();
                    messageBean.setAction_type(WSMessageBean.PING);
                    String toJson = new GsonBuilder().create().toJson(messageBean);
                    Log.i(TAG, toJson);
                    sendMessage(toJson);

                    restartPing();
                }
            }, waitTime);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * ping后等待两秒如果没反应 继续ping 执行三次没反应 断开重连
     */
    private static void restartPing() {
        if (pingCount == 3) {
            pingCount = 0;
            stopSocket();
            startSocket();
            return;
        }
        App.getMainThreadHandler().postDelayed(new Runnable() {
            @Override
            public void run() {
                WSMessageBean messageBean = new WSMessageBean();
                messageBean.setAction_type(WSMessageBean.PING);
                String toJson = new GsonBuilder().create().toJson(messageBean);
                Log.i(TAG, toJson);
                sendMessage(toJson);
                pingCount++;
                restartPing();
            }
        }, 2000);
    }

}
