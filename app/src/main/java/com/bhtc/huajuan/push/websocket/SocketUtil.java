package com.bhtc.huajuan.push.websocket;

import android.util.Log;

import com.bhtc.huajuan.push.App;
import com.bhtc.huajuan.push.R;
import com.bhtc.huajuan.push.util.ApiUrls;
import com.bhtc.huajuan.push.util.SPDataUtil;
import com.bhtc.huajuan.push.util.UIUtils;

import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.List;

import de.tavendo.autobahn.WebSocket;
import de.tavendo.autobahn.WebSocketConnection;
import de.tavendo.autobahn.WebSocketException;
import de.tavendo.autobahn.WebSocketOptions;


/**
 * http://blog.csdn.net/luyouxin/article/details/51030317
 */

public class SocketUtil {

    public static final WebSocketConnection mConnection = new WebSocketConnection();
    public final static String TAG = "SocketUtil";
    private static String wsUrl;
    private static List<BasicNameValuePair> headers = new ArrayList<>();

    public static void start() {
        wsUrl = UIUtils.isEmpty(SPDataUtil.getUserToken()) ? ApiUrls.getLivestreaming_ws_dm("") : ApiUrls.getLivestreaming_ws_dm("/logintoken/" + SPDataUtil.getUserToken());
        headers.add(new BasicNameValuePair("origin", ApiUrls.getLivestreaming_dm("")));
        try {
            mConnection.connect(wsUrl, null, new WebSocket.ConnectionHandler() {
                @Override
                public void onOpen() {
                    Log.i(TAG, "Connected：" + wsUrl);
                    SocketMsgHandler.stepInChannelMsg();  //进入直播间
                    SocketMsgHandler.startLoopStepInChannel();  //开始循环发消息，防止进入失败
                }

                @Override
                public void onClose(int code, String reason) {
                    Log.i(TAG, "Connection lost");
                    SocketMsgHandler.stopLoopStepInChannel();
                    if (!SocketMsgHandler.liveClose){
                        UIUtils.showToastSafe(UIUtils.getString(R.string.message_restart));
                        SocketMsgHandler.reStartSocket();
                    }else {
                        App.getMainThreadHandler().removeCallbacksAndMessages(null);
                    }
                }

                @Override
                public void onTextMessage(String payload) {
                    Log.i(TAG, "onTextMessage：" + payload);
                    SocketMsgHandler.handleMessage(payload);
                }

                @Override
                public void onRawTextMessage(byte[] payload) {
                    Log.i(TAG, "onRawTextMessage.");
                }

                @Override
                public void onBinaryMessage(byte[] payload) {
                    Log.i(TAG, "onBinaryMessage.");
                }
            }, new WebSocketOptions(), headers);
        } catch (WebSocketException e) {
            Log.d(TAG, e.toString());
        }
    }
}
