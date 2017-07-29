package com.bhtc.huajuan.push.activity;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Parcelable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.TextAppearanceSpan;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bhtc.huajuan.push.App;
import com.bhtc.huajuan.push.R;
import com.bhtc.huajuan.push.adapter.LiveInUserAdapter;
import com.bhtc.huajuan.push.adapter.TextListAdapter;
import com.bhtc.huajuan.push.adapter.ViewPagerAdapter;
import com.bhtc.huajuan.push.adapter.WebSocketMsgAdapter;
import com.bhtc.huajuan.push.bean.PushInitDataBean;
import com.bhtc.huajuan.push.bean.UserInfoBean;
import com.bhtc.huajuan.push.bean.WSMessageBean;
import com.bhtc.huajuan.push.dialog.BaseAllDialogF;
import com.bhtc.huajuan.push.fragment.GoodsInfoFragment;
import com.bhtc.huajuan.push.fragment.TextListFragment;
import com.bhtc.huajuan.push.util.AnimatorUtil;
import com.bhtc.huajuan.push.util.GlideManager;
import com.bhtc.huajuan.push.util.NetworkUtil;
import com.bhtc.huajuan.push.util.RunBackText;
import com.bhtc.huajuan.push.util.UIUtils;
import com.bhtc.huajuan.push.websocket.SocketMsgHandler;
import com.bhtc.huajuan.push.websocket.SocketUtil;
import com.pili.pldroid.player.AVOptions;
import com.pili.pldroid.player.PLMediaPlayer;
import com.pili.pldroid.player.widget.PLVideoView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class VideoPlayActivity extends BaseActivity implements PLMediaPlayer.OnErrorListener, View.OnClickListener {

    public static final String PUB_DATA = "pub_data";
    public static final String LIVE_ID = "live_id";
    public static final String STREAM_KEY = "stream_key";
    public static final String VIDEO_PATH = "video_path";

    private PLVideoView mVideoView;
    private String mVideoPath;
    private View loadingView;

    private RecyclerView mWebSocketUser;
    private RecyclerView mWebSocketHJ;
    private WebSocketMsgAdapter mUserAdapter;
    private WebSocketMsgAdapter mHjAdapter;
    private List<WSMessageBean> mMessageListUser = new ArrayList<>();
    private List<WSMessageBean> mMessageListHj = new ArrayList<>();
//    private List<WSMessageBean> mMessageListHjForViewPager = new ArrayList<>();

    private TextView mStartLive;
    private TextView mBuyGoodsMsg;
    private LinearLayout mThreeButton;
    private PushInitDataBean pushInitDataBean;

    private TextView mLiveTitle;
    private ImageView mLiveClose;
    private ImageView mLiveUserIcon;
    private TextView mLiveUserName;
    private TextView mLiveUserNumber;
    private RecyclerView mLiveInUser;
    private RunBackText mNotifyText;
    private LiveInUserAdapter liveInUserAdapter;

    public TabLayout mTabLayout;
    private ViewPager mViewPager;
    private ViewPagerAdapter pagerAdapter;
    public List<Fragment> mListFragment = new ArrayList<>();
    private TextListFragment mFragmentUser;
    private TextListFragment mFragmentHuaJuan;
    private GoodsInfoFragment mFragmentGoods;
    private LinearLayout mUserTextListLayout;
    private RelativeLayout mShowBottom;

    public boolean isClickTran;
    private LinearLayout mLiveToastLayout;
    private TextView mTVTime;
    private TextView mTVTimeBottom;

    private ImageView mShowBottomImage;
    public TextView mPointMsg;
    private String live_id = "";
    private String stream_key = "";

    public boolean isHongRenPush = false;
    private List<WSMessageBean> mUserBuyGoods = new ArrayList<>();
    private int loopPos;
    private Handler messageLooper = new Handler();

    private boolean isShowNextBuy = true;
    private LinearLayoutManager mLinearLayoutManager1;
    private LinearLayoutManager mLinearLayoutManager2;

    private ImageView mLiveUserIconTwo;
    private ImageView mLiveUserIconThree;
    private ImageView mLiveUserIconFour;
    private LinearLayout mNameNumberLayout;

    private boolean isStart = true;
    private Handler showAnnounHandler = new Handler();
    private Handler showAnimatorRight = new Handler();

    private ImageView mShowAnimator;
    private LinearLayout mShowAnimatorlayout;

    private boolean isShowRightMsg = true;

    private RelativeLayout mPushLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_play);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);  //屏幕高亮

        SocketMsgHandler.isLookVideo = true;

        pushInitDataBean = getIntent().getParcelableExtra(PUB_DATA);
        live_id = getIntent().getStringExtra(LIVE_ID);
        mVideoPath = getIntent().getStringExtra(VIDEO_PATH);

        if (pushInitDataBean != null && pushInitDataBean.getLivestreaming() != null) {
            stream_key = pushInitDataBean.getLivestreaming().getStream_key();
        }

        initPlayView();
        initPushUI();
    }

    private void initPushUI() {
        findViewById(R.id.beauti_image).setVisibility(View.GONE);
        findViewById(R.id.jingxiang_image).setVisibility(View.GONE);
        findViewById(R.id.switch_image).setVisibility(View.GONE);
        findViewById(R.id.start_live).setVisibility(View.INVISIBLE);
        findViewById(R.id.three_button).setVisibility(View.VISIBLE);

        mLiveToastLayout = (LinearLayout) findViewById(R.id.live_start_time);
        mTVTime = (TextView) findViewById(R.id.toast_text);
        mTVTimeBottom = (TextView) findViewById(R.id.toast_text_bottom);
        initWebSocket();
    }

    private void initPlayView() {
        mVideoView = (PLVideoView) findViewById(R.id.PLVideoView);
        loadingView = findViewById(R.id.refresh_image);
        mVideoView.setBufferingIndicator(loadingView);
        mVideoView.setDisplayAspectRatio(PLVideoView.ASPECT_RATIO_PAVED_PARENT);
        initOptions();

        mVideoView.setVideoPath(mVideoPath);
        mVideoView.setOnErrorListener(this);
    }

    private void initOptions() {
        AVOptions options = new AVOptions();
        options.setInteger(AVOptions.KEY_MEDIACODEC, AVOptions.MEDIA_CODEC_AUTO);
        options.setInteger(AVOptions.KEY_PREPARE_TIMEOUT, 10 * 1000);
        options.setInteger(AVOptions.KEY_GET_AV_FRAME_TIMEOUT, 10 * 1000);
        options.setInteger(AVOptions.KEY_LIVE_STREAMING, 1);
        options.setInteger(AVOptions.KEY_DELAY_OPTIMIZATION, 1);
        options.setInteger(AVOptions.KEY_CACHE_BUFFER_DURATION, 2000);
        options.setInteger(AVOptions.KEY_MAX_CACHE_BUFFER_DURATION, 4000);
        options.setInteger(AVOptions.KEY_START_ON_PREPARED, 1);
        options.setInteger(AVOptions.KEY_PROBESIZE, 128 * 1024);
        mVideoView.setAVOptions(options);
    }

    @Override
    public boolean onError(PLMediaPlayer plMediaPlayer, int i) {
        boolean isNeedReconnect = false;
        switch (i) {
            case PLMediaPlayer.ERROR_CODE_INVALID_URI://无效的 URL
                UIUtils.showToastSafe("播放地址无效");
                break;
            case PLMediaPlayer.ERROR_CODE_404_NOT_FOUND://播放资源不存在
                UIUtils.showToastSafe("播放资源不存在");
                break;
            case PLMediaPlayer.ERROR_CODE_CONNECTION_REFUSED://服务器拒绝连接
                UIUtils.showToastSafe("服务器拒绝连接");
                break;
            case PLMediaPlayer.ERROR_CODE_CONNECTION_TIMEOUT://连接超时
                UIUtils.showToastSafe("连接超时");
                isNeedReconnect = true;
                break;
            case PLMediaPlayer.ERROR_CODE_EMPTY_PLAYLIST://空的播放列表
                UIUtils.showToastSafe("空的播放列表");
                break;
            case PLMediaPlayer.ERROR_CODE_STREAM_DISCONNECTED://与服务器连接断开
                UIUtils.showToastSafe("与服务器连接断开");
                isNeedReconnect = true;
                break;
            case PLMediaPlayer.ERROR_CODE_IO_ERROR://网络异常
//                查询业务服务器，获知直播是否结束，如果没有结束，则可以尝试做重连
//                判断网络是否断开，如果网络连接是正常的，则可以尝试做重连
                UIUtils.showToastSafe("网络异常");
                isNeedReconnect = true;
                break;
            case PLMediaPlayer.ERROR_CODE_UNAUTHORIZED://未授权，播放一个禁播的流
                UIUtils.showToastSafe("未授权，播放一个禁播的流");
                break;
            case PLMediaPlayer.ERROR_CODE_PREPARE_TIMEOUT://播放器准备超时
                UIUtils.showToastSafe("播放器准备超时");
                isNeedReconnect = true;
                break;
            case PLMediaPlayer.ERROR_CODE_READ_FRAME_TIMEOUT://读取数据超时
                UIUtils.showToastSafe("读取数据超时");
                isNeedReconnect = true;
                break;
            default:
                UIUtils.showToastSafe("unknown error !");
                break;
        }
        // Todo pls handle the error status here, reconnect or call finish()
        if (isNeedReconnect && !isFinishing()) {
            loadingView.setVisibility(View.VISIBLE);
            App.getMainThreadHandler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    sendReconnectMessage();
                }
            }, 5000);
        }
        return true;
    }

    private void sendReconnectMessage() {
        if (NetworkUtil.isNetworkConnected()) {
            UIUtils.showToastSafe(UIUtils.getString(R.string.restart_link));
            if (!SocketUtil.mConnection.isConnected())
                SocketMsgHandler.startSocket();

            mVideoView.setVideoPath(mVideoPath);
            mVideoView.start();
            loadingView.setVisibility(View.GONE);
        } else {
            UIUtils.showToastSafe(R.string.net_error);
        }
    }

    private void initWebSocket() {
        EventBus.getDefault().register(this);

        mPushLayout = (RelativeLayout) findViewById(R.id.pop_layout);
        mShowAnimator = (ImageView) findViewById(R.id.show_animator);
        mShowAnimatorlayout = (LinearLayout) findViewById(R.id.hj_recy_layout);
        mShowBottom = (RelativeLayout) findViewById(R.id.show_bottom);
        mWebSocketHJ = (RecyclerView) findViewById(R.id.websocket_huajuan);
        mWebSocketUser = (RecyclerView) findViewById(R.id.websocket_user);
        mLinearLayoutManager1 = new LinearLayoutManager(this);
        mLinearLayoutManager1.setOrientation(LinearLayoutManager.VERTICAL);
        mWebSocketHJ.setLayoutManager(mLinearLayoutManager1);
        mLinearLayoutManager2 = new LinearLayoutManager(this);
        mLinearLayoutManager2.setOrientation(LinearLayoutManager.VERTICAL);
        mWebSocketUser.setLayoutManager(mLinearLayoutManager2);
        mUserAdapter = new WebSocketMsgAdapter(this, mMessageListUser, WebSocketMsgAdapter.MSG_LEFT);
        mHjAdapter = new WebSocketMsgAdapter(this, mMessageListHj, WebSocketMsgAdapter.MSG_RIGHT);
        mWebSocketHJ.setAdapter(mHjAdapter);
        mWebSocketUser.setAdapter(mUserAdapter);

        mWebSocketUser.setVisibility(View.VISIBLE);

        mVideoView.setOnInfoListener(new PLMediaPlayer.OnInfoListener() {
            @Override
            public boolean onInfo(PLMediaPlayer plMediaPlayer, int i, int i1) {
                if (PLMediaPlayer.MEDIA_INFO_VIDEO_RENDERING_START == i && isStart) {
                    isStart = false;
//                    HttpHelper.startLive(live_id, stream_key, null);
                    if (mFragmentGoods != null) {
                        mFragmentGoods.setGoodsShow();
                    }
                }
                return false;
            }
        });

        SocketMsgHandler.startSocket();

        initPushEvent();
        initData();

        initTextList();
    }

    private void initData() {
        String watchNumber = null;
        if (pushInitDataBean != null) {
            if (pushInitDataBean.getLive() != null) {
                mLiveTitle.setText(pushInitDataBean.getLive().getName());
                watchNumber = pushInitDataBean.getLive().getWatching_number();
            }
            if (pushInitDataBean.getHongren() != null && pushInitDataBean.getHongren().size() != 0) {
                if (pushInitDataBean.getHongren().size() > 1) {
                    mNameNumberLayout.setVisibility(View.GONE);
                    mLiveUserIcon.setVisibility(View.GONE);
                    mLiveUserIconTwo.setVisibility(View.GONE);
                    mLiveUserIconThree.setVisibility(View.GONE);
                    mLiveUserIconFour.setVisibility(View.GONE);
                    for (int i = 0; i < pushInitDataBean.getHongren().size(); i++) {
                        switch (i) {
                            case 0:
                                mLiveUserIcon.setVisibility(View.VISIBLE);
                                GlideManager.getInstance().loadCircleImg(this, pushInitDataBean.getHongren().get(i).getUser_avatar(), mLiveUserIcon);
                                break;
                            case 1:
                                mLiveUserIconTwo.setVisibility(View.VISIBLE);
                                GlideManager.getInstance().loadCircleImg(this, pushInitDataBean.getHongren().get(i).getUser_avatar(), mLiveUserIconTwo);
                                break;
                            case 2:
                                mLiveUserIconThree.setVisibility(View.VISIBLE);
                                GlideManager.getInstance().loadCircleImg(this, pushInitDataBean.getHongren().get(i).getUser_avatar(), mLiveUserIconThree);
                                break;
                            case 3:
                                mLiveUserIconFour.setVisibility(View.VISIBLE);
                                GlideManager.getInstance().loadCircleImg(this, pushInitDataBean.getHongren().get(i).getUser_avatar(), mLiveUserIconFour);
                                break;
                        }
                    }
                } else {
                    mLiveUserIconTwo.setVisibility(View.GONE);
                    mLiveUserIconThree.setVisibility(View.GONE);
                    mLiveUserIconFour.setVisibility(View.GONE);
                    mLiveUserIcon.setVisibility(View.VISIBLE);
                    mNameNumberLayout.setVisibility(View.VISIBLE);

                    UserInfoBean userInfoBean = pushInitDataBean.getHongren().get(0);
                    mLiveUserName.setText(userInfoBean.getUser_name());
                    if (!UIUtils.isEmpty(userInfoBean.getHongren_number())) {
                        mLiveUserNumber.setText("小铺号 " + userInfoBean.getHongren_number());
                    }
                    GlideManager.getInstance().loadCircleImg(this, userInfoBean.getUser_avatar(), mLiveUserIcon);
                }
            }
            if (pushInitDataBean.getWatchingUser() != null) {
                List<UserInfoBean> watchingUser = pushInitDataBean.getWatchingUser();
                LinearLayoutManager linearLayout = new LinearLayoutManager(this);
                linearLayout.setOrientation(LinearLayout.HORIZONTAL);
                mLiveInUser.setLayoutManager(linearLayout);

                liveInUserAdapter = new LiveInUserAdapter(this, watchingUser, watchNumber);
                mLiveInUser.setAdapter(liveInUserAdapter);
            }
        }
    }

    private void initPushEvent() {
        mShowBottomImage = (ImageView) findViewById(R.id.show_bottom_image);
        mStartLive = (TextView) findViewById(R.id.start_live);
        mPointMsg = (TextView) findViewById(R.id.point_msg);
        mBuyGoodsMsg = (TextView) findViewById(R.id.buy_goods_message);
        mThreeButton = (LinearLayout) findViewById(R.id.three_button);

        mLiveTitle = (TextView) findViewById(R.id.live_title);
        mLiveClose = (ImageView) findViewById(R.id.live_close);
        mLiveUserIcon = (ImageView) findViewById(R.id.live_user_header);
        mLiveUserName = (TextView) findViewById(R.id.live_user_name);
        mLiveUserNumber = (TextView) findViewById(R.id.live_user_store_id);
        mLiveInUser = (RecyclerView) findViewById(R.id.live_in_user);
        mNotifyText = (RunBackText) findViewById(R.id.notify_text);
        mLiveUserIconTwo = (ImageView) findViewById(R.id.live_user_header_two);
        mLiveUserIconThree = (ImageView) findViewById(R.id.live_user_header_three);
        mLiveUserIconFour = (ImageView) findViewById(R.id.live_user_header_four);
        mNameNumberLayout = (LinearLayout) findViewById(R.id.live_user_detail_layout);
        mStartLive.setOnClickListener(this);
        mShowBottomImage.setOnClickListener(this);
        mLiveClose.setOnClickListener(this);
        loadingView.setOnClickListener(this);
        mShowAnimator.setOnClickListener(this);
        mShowAnimatorlayout.setOnClickListener(this);
        mPushLayout.setOnClickListener(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        HttpHelper.exitLive(live_id, stream_key, null);
        SocketMsgHandler.stepOutChannelMsg();
        EventBus.getDefault().unregister(this);
        showAnnounHandler.removeCallbacksAndMessages(null);
        messageLooper.removeCallbacksAndMessages(null);
        App.getMainThreadHandler().removeCallbacksAndMessages(null);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(WSMessageBean wsMessageBean) {
        if (wsMessageBean == null || wsMessageBean.getAction_type() == null || wsMessageBean.getAction_data() == null) {
            return;
        }
        switch (wsMessageBean.getAction_type()) {
            case WSMessageBean.STEP_IN_CHANNEL:
            case WSMessageBean.STEP_OUT_CHANNEL:
            case WSMessageBean.MSG:
            case WSMessageBean.FOLLOW:
            case WSMessageBean.GRAB_GOODS_SUCCESS:
            case WSMessageBean.BLOCK:
                userMsg(wsMessageBean);
                break;
            case WSMessageBean.ADMIN_MSG:
                mShowAnimator.setVisibility(View.VISIBLE);
                if (mViewPager != null && mViewPager.getCurrentItem() != 1) {
                    mPointMsg.setVisibility(View.VISIBLE);
                }
                HJMsg(wsMessageBean);
                break;
            case WSMessageBean.SHOW_GOODS:
            case WSMessageBean.HIDDEN_GOODS:
            case WSMessageBean.SHOW_GRAB_BUTTON:
            case WSMessageBean.HIDDEN_GRAB_BUTTON:
                mShowAnimator.setVisibility(View.VISIBLE);
                HJMsg(wsMessageBean);
                break;
            case WSMessageBean.BUY:
                buyMsg(wsMessageBean);
                break;
            case WSMessageBean.ANNOUNCEMENT:
                if (!UIUtils.isEmpty(wsMessageBean.getAction_data().getMsg())) {
                    AnimatorUtil.viewVisible(mNotifyText);
                    mNotifyText.setText(wsMessageBean.getAction_data().getMsg());
                    mNotifyText.init(getWindowManager());
                    mNotifyText.startScroll();
                } else {
                    if (mNotifyText.getVisibility() == View.VISIBLE) {
                        AnimatorUtil.rootViewGone(mNotifyText, null);
                        mNotifyText.stopScroll();
                    }
                }
                showAnnounHandler.removeCallbacksAndMessages(null);
                showAnnounHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        AnimatorUtil.rootViewGone(mNotifyText, null);
                        mNotifyText.stopScroll();
                    }
                }, 100000);
                break;
            case WSMessageBean.HIDDEN_ANNOUNCEMENT:
                if (mNotifyText.getVisibility() == View.VISIBLE) {
                    AnimatorUtil.rootViewGone(mNotifyText, null);
                    mNotifyText.stopScroll();
                    showAnnounHandler.removeCallbacksAndMessages(null);
                }
                break;
        }
    }

    // 接收用户消息
    private void userMsg(WSMessageBean wsMessageBean) {
        if (!wsMessageBean.getAction_type().equals(WSMessageBean.BLOCK) && wsMessageBean.getAction_data().getUser_name() == null) {
            return;
        }
        WSMessageBean.MessageData actionData = wsMessageBean.getAction_data();
        if (WSMessageBean.STEP_OUT_CHANNEL.equals(wsMessageBean.getAction_type())) {
            if (liveInUserAdapter != null) {
                liveInUserAdapter.setWatchNumberRemove(actionData.getWatching_number(), actionData.getWatchingUser());
            }
            return;
        }
        if (WSMessageBean.STEP_IN_CHANNEL.equals(wsMessageBean.getAction_type())) {
            if (liveInUserAdapter != null) {
                liveInUserAdapter.setWatchNumberAdd(actionData.getWatching_number(), actionData.getWatchingUser());
            }
        }
        int positionUser = mMessageListUser.size();
        mMessageListUser.add(positionUser, wsMessageBean);
        mUserAdapter.notifyItemInserted(positionUser);

        if (isClickTran) {
            mWebSocketUser.scrollToPosition(positionUser);
        } else {
            if (mMessageListUser.size() - 3 <= mLinearLayoutManager2.findLastVisibleItemPosition())
                mWebSocketUser.scrollToPosition(positionUser);
        }

        if (mFragmentUser != null) {
            mFragmentUser.setWSMessageBeanData(wsMessageBean);
        }
    }

    // 接收花卷消息
    private void HJMsg(WSMessageBean wsMessageBean) {
        if (!isShowRightMsg) {
            isShowRightMsg = true;
            mShowAnimator.setImageResource(R.drawable.triangle_two);
            ObjectAnimator translationX = ObjectAnimator.ofFloat(mShowAnimatorlayout, "translationX", UIUtils.dp2px(190), 0);
            translationX.setDuration(500).start();
        }
        handleRightAnimator();

        switch (wsMessageBean.getAction_type()) {
            case WSMessageBean.SHOW_GOODS:
                if (mFragmentGoods != null) {
                    mFragmentGoods.mTextListAdapter.setTanchuUI(wsMessageBean.getAction_data().getGoods_id(), false);
                }
                break;
            case WSMessageBean.SHOW_GRAB_BUTTON:
                if (mFragmentGoods != null) {
                    mFragmentGoods.mTextListAdapter.setTanchuUI(wsMessageBean.getAction_data().getGoods_id(), true);
                }
                break;
            case WSMessageBean.HIDDEN_GOODS:
                if (mFragmentGoods != null) {
                    mFragmentGoods.mTextListAdapter.setChehuiUI(wsMessageBean.getAction_data().getGoods_id(), false);
                }
                return;
            case WSMessageBean.HIDDEN_GRAB_BUTTON:
                if (mFragmentGoods != null) {
                    mFragmentGoods.mTextListAdapter.setChehuiUI(wsMessageBean.getAction_data().getGoods_id(), true);
                }
                return;
        }

        int positionHj = mMessageListHj.size();
        mMessageListHj.add(positionHj, wsMessageBean);
        mHjAdapter.notifyItemInserted(positionHj);

        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) mShowAnimator.getLayoutParams();
        if (mMessageListHj.size() > 1 && layoutParams.height != UIUtils.dp2px(101)) {
            layoutParams.height = UIUtils.dp2px(101);
            layoutParams.setMargins(0,UIUtils.dp2px(1),UIUtils.dp2px(3),0);
            mShowAnimator.setLayoutParams(layoutParams);
        }

        if (isClickTran) {
            mWebSocketHJ.scrollToPosition(positionHj);
        } else {
            if (mMessageListHj.size() - 3 <= mLinearLayoutManager1.findLastVisibleItemPosition())
                mWebSocketHJ.scrollToPosition(positionHj);
        }

        if (mFragmentHuaJuan != null) {
            mFragmentHuaJuan.setWSMessageBeanData(wsMessageBean);
        }
    }

    // 接收购买消息
    private void buyMsg(WSMessageBean wsMessageBean) {
        WSMessageBean.MessageData actionData = wsMessageBean.getAction_data();
        if (actionData.getUser_name() == null) {
            return;
        }

//        mBuyGoodsMsg.setVisibility(View.VISIBLE);
//        mUserBuyGoods.add(wsMessageBean);
//
//        if (mUserBuyGoods.size() == 1) {
//            messageLooper(wsMessageBean, actionData);
//        }

        if (isShowNextBuy) {
            isShowNextBuy = false;
            messageLooper.removeCallbacksAndMessages(null);

            String userNameMsg = actionData.getUser_name() + " 购买了" + actionData.getGoods_name();
            SpannableString styledText = new SpannableString(userNameMsg);
            styledText.setSpan(new TextAppearanceSpan(this, R.style.user_name_ffffff), 0, actionData.getUser_name() != null ? actionData.getUser_name().length() : 0, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            mBuyGoodsMsg.setText(styledText, TextView.BufferType.SPANNABLE);

            mBuyGoodsMsg.setVisibility(View.VISIBLE);
            ObjectAnimator translationX = ObjectAnimator.ofFloat(mBuyGoodsMsg, "translationX", -mBuyGoodsMsg.getWidth(), 0);
            translationX.setDuration(500).start();

            if (mFragmentUser != null) {
                mFragmentUser.setBuyGoodsInfo(wsMessageBean);
            }
            if (mFragmentHuaJuan != null) {
                mFragmentHuaJuan.setBuyGoodsInfo(wsMessageBean);
            }
            if (mFragmentGoods != null) {
                mFragmentGoods.setBuyGoodsInfo(wsMessageBean);
            }

            messageLooper.postDelayed(new Runnable() {
                @Override
                public void run() {
                    isShowNextBuy = true;
                }
            }, 2000);

            messageLooper.postDelayed(new Runnable() {
                @Override
                public void run() {
                    ObjectAnimator translationX = ObjectAnimator.ofFloat(mBuyGoodsMsg, "translationX", 0, -mBuyGoodsMsg.getWidth());
                    translationX.setDuration(500).start();
                    translationX.addListener(new Animator.AnimatorListener() {
                        @Override
                        public void onAnimationStart(Animator animation) {
                        }

                        @Override
                        public void onAnimationEnd(Animator animation) {
                            mBuyGoodsMsg.setVisibility(View.INVISIBLE);
                            mFragmentUser.mBuyGoodsInfo.setVisibility(View.GONE);
                            mFragmentHuaJuan.mBuyGoodsInfo.setVisibility(View.GONE);
                            mFragmentGoods.mBuyGoodsInfo.setVisibility(View.GONE);
                        }

                        @Override
                        public void onAnimationCancel(Animator animation) {
                        }

                        @Override
                        public void onAnimationRepeat(Animator animation) {
                        }
                    });
                }
            }, 30000);
        }
    }

//    private void messageLooper(WSMessageBean wsMessageBean, WSMessageBean.MessageData actionData) {
//        String userNameMsg = actionData.getUser_name() + " 购买了" + actionData.getGoods_name();
//        SpannableString styledText = new SpannableString(userNameMsg);
//        styledText.setSpan(new TextAppearanceSpan(this, R.style.user_name_ffffff), 0, actionData.getUser_name() != null ? actionData.getUser_name().length() : 0, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
//        mBuyGoodsMsg.setText(styledText, TextView.BufferType.SPANNABLE);
//
//        if (mFragmentUser != null) {
//            mFragmentUser.setBuyGoodsInfo(wsMessageBean);
//        }
//        if (mFragmentHuaJuan != null) {
//            mFragmentHuaJuan.setBuyGoodsInfo(wsMessageBean);
//        }
//        if (mFragmentGoods != null) {
//            mFragmentGoods.setBuyGoodsInfo(wsMessageBean);
//        }
//
//        messageLooper.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                if (mUserBuyGoods.size() <= 1) {
//                    loopPos = 0;
//                    mUserBuyGoods.clear();
//                    mBuyGoodsMsg.setVisibility(View.GONE);
//                    mFragmentUser.mBuyGoodsInfo.setVisibility(View.GONE);
//                    mFragmentHuaJuan.mBuyGoodsInfo.setVisibility(View.GONE);
//                    mFragmentGoods.mBuyGoodsInfo.setVisibility(View.GONE);
//                } else {
//                    mUserBuyGoods.remove(0);
//                    messageLooper(mUserBuyGoods.get(loopPos), mUserBuyGoods.get(loopPos).getAction_data());
//                }
//            }
//        }, 30000);
//    }

    //初始化半屏聊天消息
    private void initTextList() {
        mTabLayout = (TabLayout) findViewById(R.id.text_tab);
        mViewPager = (ViewPager) findViewById(R.id.text_viewpager);
        mUserTextListLayout = (LinearLayout) findViewById(R.id.ll_user_text_layout);

        mListFragment.clear();

        Bundle bundle = new Bundle();
        bundle.putInt(TextListFragment.TYPE, TextListAdapter.USER);
        bundle.putParcelableArrayList(TextListFragment.LIST, (ArrayList<? extends Parcelable>) mMessageListUser);
        mFragmentUser = TextListFragment.newInstance(bundle);
        Bundle bundle1 = new Bundle();
        bundle1.putInt(TextListFragment.TYPE, TextListAdapter.HUAJUAN);
        bundle1.putParcelableArrayList(TextListFragment.LIST, (ArrayList<? extends Parcelable>) mMessageListHj);
        mFragmentHuaJuan = TextListFragment.newInstance(bundle1);
        Bundle bundle2 = new Bundle();
        bundle2.putString(LIVE_ID, live_id);
        bundle2.putString(STREAM_KEY, stream_key);
        mFragmentGoods = GoodsInfoFragment.newInstance(bundle2);
        mListFragment.add(mFragmentUser);
        mListFragment.add(mFragmentHuaJuan);
        mListFragment.add(mFragmentGoods);
        pagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(), mListFragment);
        mViewPager.setAdapter(pagerAdapter);
        mTabLayout.setupWithViewPager(mViewPager);
        mViewPager.setOffscreenPageLimit(3);

        try {
            Class<?> tablayout = mTabLayout.getClass();
            Field tabStrip = tablayout.getDeclaredField("mTabStrip");
            tabStrip.setAccessible(true);
            LinearLayout ll_tab = (LinearLayout) tabStrip.get(mTabLayout);
            for (int i = 0; i < ll_tab.getChildCount(); i++) {
                View child = ll_tab.getChildAt(i);
                child.setPadding(0, 0, 0, 0);
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, 1);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                    params.setMarginStart(UIUtils.dp2px(30));
                    params.setMarginEnd(UIUtils.dp2px(30));
                }
                child.setLayoutParams(params);
                child.invalidate();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.live_close:
                showDialog();
                break;
            case R.id.show_bottom_image:
                if (!isClickTran) {
                    isClickTran = true;
                    mShowBottomImage.setImageResource(R.drawable.show_bottom);
                    mShowBottom.setVisibility(View.GONE);
                    mVideoView.setDisplayAspectRatio(PLVideoView.ASPECT_RATIO_FIT_PARENT);
                    ValueAnimator anim = ValueAnimator.ofInt(-UIUtils.dp2px(444), 0);
                    anim.setDuration(130);
                    anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                        @Override
                        public void onAnimationUpdate(ValueAnimator animation) {
                            int currentValue = (int) animation.getAnimatedValue();
                            RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) mUserTextListLayout.getLayoutParams();
                            layoutParams.bottomMargin = currentValue;
                            mUserTextListLayout.setLayoutParams(layoutParams);
                        }
                    });
                    anim.start();
                } else {
                    isClickTran = false;
                    mShowBottomImage.setImageResource(R.drawable.show_bottom_null_big);
                    ValueAnimator anim = ValueAnimator.ofInt(0, -UIUtils.dp2px(444));
                    anim.setDuration(130);
                    anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                        @Override
                        public void onAnimationUpdate(ValueAnimator animation) {
                            int currentValue = (int) animation.getAnimatedValue();
                            RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) mUserTextListLayout.getLayoutParams();
                            layoutParams.bottomMargin = currentValue;
                            mUserTextListLayout.setLayoutParams(layoutParams);

                            if (currentValue == -UIUtils.dp2px(444)) {
                                mShowBottom.setVisibility(View.VISIBLE);
                                mVideoView.setDisplayAspectRatio(PLVideoView.ASPECT_RATIO_PAVED_PARENT);
                            }
                        }
                    });
                    anim.start();
                }
                break;
            case R.id.refresh_image:
                sendReconnectMessage();
                break;
            case R.id.hj_recy_layout:
                if (isShowRightMsg) {
                    isShowRightMsg = false;
                    mShowAnimator.setImageResource(R.drawable.triangle);
                    ObjectAnimator translationX = ObjectAnimator.ofFloat(mShowAnimatorlayout, "translationX", 0, UIUtils.dp2px(190));
                    translationX.setDuration(500).start();
                } else {
                    isShowRightMsg = true;
                    mShowAnimator.setImageResource(R.drawable.triangle_two);
                    ObjectAnimator translationX = ObjectAnimator.ofFloat(mShowAnimatorlayout, "translationX", UIUtils.dp2px(190), 0);
                    translationX.setDuration(500).start();

                    handleRightAnimator();
                }
                break;
            case R.id.pop_layout:
                if (!isClickTran) {
                    return;
                }
                isClickTran = false;
                mShowBottomImage.setImageResource(R.drawable.show_bottom_null_big);
                ValueAnimator anim = ValueAnimator.ofInt(0, -UIUtils.dp2px(444));
                anim.setDuration(130);
                anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {
                        int currentValue = (int) animation.getAnimatedValue();
                        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) mUserTextListLayout.getLayoutParams();
                        layoutParams.bottomMargin = currentValue;
                        mUserTextListLayout.setLayoutParams(layoutParams);

                        if (currentValue == -UIUtils.dp2px(444)) {
                            mShowBottom.setVisibility(View.VISIBLE);
                            mVideoView.setDisplayAspectRatio(PLVideoView.ASPECT_RATIO_PAVED_PARENT);
                        }
                    }
                });
                anim.start();
                break;
        }
    }

    private void handleRightAnimator() {
        showAnimatorRight.removeCallbacksAndMessages(null);
        showAnimatorRight.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (isShowRightMsg) {
                    isShowRightMsg = false;
                    mShowAnimator.setImageResource(0);
                    mShowAnimator.setImageResource(R.drawable.triangle);
                    ObjectAnimator translationX = ObjectAnimator.ofFloat(mShowAnimatorlayout, "translationX", 0, UIUtils.dp2px(190));
                    translationX.setDuration(500).start();
                }
            }
        },10000);
    }

    private void showDialog() {
        BaseAllDialogF baseAllDialogF = new BaseAllDialogF()
                .setContent(UIUtils.getString(R.string.is_finish_look_video))
                .setCancelTxt(UIUtils.getString(R.string.confirm), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                })
                .setConfirmTxt(UIUtils.getString(R.string.cancel), null);
        baseAllDialogF.setCancelable(false);
        baseAllDialogF.show(VideoPlayActivity.this.getSupportFragmentManager(), "FACE");
    }

    @Override
    public void onBackPressed() {
        showDialog();
    }
}
