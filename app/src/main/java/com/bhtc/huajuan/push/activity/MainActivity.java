package com.bhtc.huajuan.push.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;

import com.bhtc.huajuan.push.App;
import com.bhtc.huajuan.push.R;
import com.bhtc.huajuan.push.adapter.WebSocketMsgAdapter;
import com.bhtc.huajuan.push.bean.WSMessageBean;
import com.bhtc.huajuan.push.util.DividerItemDecoration;
import com.bhtc.huajuan.push.websocket.SocketMsgHandler;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity {

    private RecyclerView mWebSocketUser;
    private RecyclerView mWebSocketHJ;
    private WebSocketMsgAdapter mUserAdapter;
    private WebSocketMsgAdapter mHjAdapter;
    private List<WSMessageBean> mMessageListUser = new ArrayList<>();
    private List<WSMessageBean> mMessageListHj = new ArrayList<>();

    private EditText input_number;
    private EditText block_name;

    private void initWebSocket() {
        EventBus.getDefault().register(this);
        mWebSocketHJ = (RecyclerView) findViewById(R.id.websocket_huajuan);
        mWebSocketUser = (RecyclerView) findViewById(R.id.websocket_user);
        LinearLayoutManager mLinearLayoutManager1 = new LinearLayoutManager(this);
        mLinearLayoutManager1.setOrientation(LinearLayoutManager.VERTICAL);
        mWebSocketHJ.setLayoutManager(mLinearLayoutManager1);
        LinearLayoutManager mLinearLayoutManager2 = new LinearLayoutManager(this);
        mLinearLayoutManager2.setOrientation(LinearLayoutManager.VERTICAL);
        mWebSocketUser.setLayoutManager(mLinearLayoutManager2);
        mUserAdapter = new WebSocketMsgAdapter(this, mMessageListUser, WebSocketMsgAdapter.MSG_LEFT);
        mHjAdapter = new WebSocketMsgAdapter(this, mMessageListHj, WebSocketMsgAdapter.MSG_RIGHT);
        mWebSocketHJ.setAdapter(mHjAdapter);
        mWebSocketUser.setAdapter(mUserAdapter);
        mWebSocketHJ.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST));
        mWebSocketUser.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(WSMessageBean wsMessageBean) {
        if (wsMessageBean == null || wsMessageBean.getAction_type() == null || wsMessageBean.getAction_data() == null) {
            return;
        }
        switch (wsMessageBean.getAction_type()) {
            case WSMessageBean.STEP_IN_CHANNEL:
            case WSMessageBean.MSG:
            case WSMessageBean.FOLLOW:
            case WSMessageBean.GRAB_GOODS_SUCCESS:
                if (wsMessageBean.getAction_data().getUser_name() == null) {
                    return;
                }
                int positionUser = mMessageListUser.size();
                mMessageListUser.add(positionUser, wsMessageBean);
                mUserAdapter.notifyItemInserted(positionUser);
                mWebSocketUser.scrollToPosition(positionUser);
                break;
            case WSMessageBean.ADMIN_MSG:
            case WSMessageBean.SHOW_GOODS:
                int positionHj = mMessageListHj.size();
                mMessageListHj.add(positionHj, wsMessageBean);
                mHjAdapter.notifyItemInserted(positionHj);
                mWebSocketHJ.scrollToPosition(positionHj);
                break;
            case WSMessageBean.BUY:
                break;
            case WSMessageBean.ANNOUNCEMENT:
                break;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        App.getMainThreadHandler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(MainActivity.this, LoginActivity.class));
                finish();
            }
        }, 1000);

//        input_number = (EditText) findViewById(R.id.input_number);
//
//        initEvent();
//
//        SocketMsgHandler.startSocket();
//        if (UIUtils.isEmpty(SPDataUtil.getUserToken())) {
//            startActivity(new Intent(this, LoginActivity.class));
//        }

//        initWebSocket();
    }

    private void initEvent() {
        findViewById(R.id.start).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SocketMsgHandler.sendMsgNotify("完美 android ios 永无bug～～～nice ！！！");
            }
        });

        findViewById(R.id.websocket).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (input_number.getText() != null) {
                    WSMessageBean.CHANNEL_TESTING = input_number.getText().toString().trim();
                }
                SocketMsgHandler.startSocket();
            }
        });

        findViewById(R.id.msg).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SocketMsgHandler.sendMsg("完美 android ios 永无bug～～～nice ！！！");
            }
        });

        findViewById(R.id.aite).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SocketMsgHandler.sendMsgWithUser("完美 android ios 永无bug～～～nice ！！！", "0", "96067500479085", "飞哥");
            }
        });

        findViewById(R.id.msghj).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SocketMsgHandler.sendMsgHj("完美 android ios 永无bug～～～nice ！！！");
            }
        });

        findViewById(R.id.output).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SocketMsgHandler.stepOutChannelMsg();
            }
        });

//        findViewById(R.id.msggoods_hongren).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                SocketMsgHandler.sendGoodsHongRen("1001090","玛贝拉 蓝莓脱毛膏","100","120","130","1","http://static.huajuanmall.com/images/goods/o_1b9nmsepq11te1u813sh1b9hfked.jpg?imageView2/2/w/800/h/800/format/webp/");
//            }
//        });

        findViewById(R.id.msggoods).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SocketMsgHandler.sendGoods("1001090", "玛贝拉 蓝莓脱毛膏", "100", "120", "130", "1", "http://static.huajuanmall.com/images/goods/o_1b9nmsepq11te1u813sh1b9hfked.jpg?imageView2/2/w/800/h/800/format/webp/", "1");
            }
        });

        final int[] i = {1};
        findViewById(R.id.buygoods).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SocketMsgHandler.buyMsg("1001090", "玛贝拉 蓝莓脱毛膏==" + i[0], "1");
                i[0]++;
            }
        });

        findViewById(R.id.concern).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SocketMsgHandler.followMsg("96029699471632", "孙一萌");
            }
        });

        findViewById(R.id.qiang_show).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                SocketMsgHandler.qiangMsg("1001090");
            }
        });

        findViewById(R.id.qiang_hidden).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                SocketMsgHandler.hiddenQiangMsg("1001090");
            }
        });

        findViewById(R.id.not_take).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                String uid = block_name.getText().toString().trim();
                SocketMsgHandler.notSpeak("96067500479085");
            }
        });
    }


}
