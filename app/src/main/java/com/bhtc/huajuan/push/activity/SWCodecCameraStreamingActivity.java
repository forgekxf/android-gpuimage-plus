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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
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
import com.bhtc.huajuan.push.rtmp.CameraPreviewFrameView;
import com.bhtc.huajuan.push.util.AnimatorUtil;
import com.bhtc.huajuan.push.util.GlideManager;
import com.bhtc.huajuan.push.util.HttpHelper;
import com.bhtc.huajuan.push.util.RunBackText;
import com.bhtc.huajuan.push.util.TimeCount;
import com.bhtc.huajuan.push.util.UIUtils;
import com.bhtc.huajuan.push.websocket.SocketMsgHandler;
import com.bhtc.huajuan.push.websocket.SocketUtil;
import com.qiniu.pili.droid.streaming.AVCodecType;
import com.qiniu.pili.droid.streaming.CameraStreamingSetting;
import com.qiniu.pili.droid.streaming.MediaStreamingManager;
import com.qiniu.pili.droid.streaming.widget.AspectFrameLayout;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by jerikc on 15/10/29.
 */
public class SWCodecCameraStreamingActivity extends StreamingBaseActivity implements View.OnClickListener, SeekBar.OnSeekBarChangeListener {

    public static final String PUB_DATA = "pub_data";
    public static final String PUB_URL = "pub_url";
    public static final String LIVE_ID = "live_id";
    public static final String STREAM_KEY = "stream_key";

    private RecyclerView mWebSocketUser;
    private RecyclerView mWebSocketHJ;
    private WebSocketMsgAdapter mUserAdapter;
    private WebSocketMsgAdapter mHjAdapter;
    private List<WSMessageBean> mMessageListUser = new ArrayList<>();
    private List<WSMessageBean> mMessageListHj = new ArrayList<>();

    private TextView mBuyGoodsMsg;
    private ImageView mBuautiImage;
    private ImageView mJingXiangImage;
    private ImageView mRotateImage;
    private LinearLayout mThreeButton;
    private PushInitDataBean pushInitDataBean;

    private TextView mLiveTitle;
    private ImageView mLiveClose;
    private ImageView mLiveUserIcon;
    private ImageView mLiveUserIconTwo;
    private ImageView mLiveUserIconThree;
    private ImageView mLiveUserIconFour;
    private LinearLayout mNameNumberLayout;
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
    private AspectFrameLayout afl;
    private CameraPreviewFrameView cameraPreviewFrameView;
    private RelativeLayout mShowBottom;
    private RelativeLayout mPushLayout;

    public boolean isClickTran;
    private LinearLayout mLiveToastLayout;
    private TextView mTVTime;
    private TextView mTVTimeBottom;
    private TimeCount clock;

    private ImageView mShowBottomImage;
    public TextView mPointMsg;
    private String live_id = "";
    private String stream_key = "";
    public boolean isHongRenPush = false;
    private boolean isSwitch = true;
    private Handler messageLooper = new Handler();

    private boolean mTimeOut;
    private boolean isShowNextBuy = true;
    private LinearLayoutManager mLinearLayoutManager1;
    private LinearLayoutManager mLinearLayoutManager2;

    private Handler showAnnounHandler = new Handler();
    private Handler showAnimator = new Handler();
    private Handler showAnimatorRight = new Handler();

    private boolean isBack = false;

    private ImageView mShowAnimator;
    private LinearLayout mShowAnimatorlayout;

    private boolean isShowRightMsg = true;

    private boolean seekZero = false;
    private int beautiful = 0;
    private int white = 0;
    private int red = 0;
    private String TAG = "BEAUTIFUL";

    private RelativeLayout mBeautiLayoutId;
    private TextView mCancelBeauti;
    private TextView mRemindBeauti;
    private TextView mCancelBeautilayout;
    private TextView tv_pink;
    private TextView tv_redden;
    private TextView tv_soften;
    private TextView tv_whiten;

    private TextView tv_filter_one;
    private TextView tv_filter_two;
    private TextView tv_filter_three;

    private SeekBar m_Seekpink;
    private SeekBar m_Seekwhiten;
    private SeekBar m_Seekredden;
    private SeekBar m_Seeksoften;

    private SeekBar m_SeekFilterOne;
    private SeekBar m_SeekFilterTwo;
    private SeekBar m_SeekFilterThree;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SocketMsgHandler.isLookVideo = false;
        SocketMsgHandler.startSocket();

        ininCamera360();
        mLiveToastLayout = (LinearLayout) findViewById(R.id.live_start_time);
        mPushLayout = (RelativeLayout) findViewById(R.id.push_layout);
        mTVTime = (TextView) findViewById(R.id.toast_text);
        mTVTimeBottom = (TextView) findViewById(R.id.toast_text_bottom);
        afl = (AspectFrameLayout) findViewById(R.id.cameraPreview_afl);
        afl.setShowMode(AspectFrameLayout.SHOW_MODE.FULL);
        cameraPreviewFrameView = (CameraPreviewFrameView) findViewById(R.id.cameraPreview_surfaceView);
        cameraPreviewFrameView.setListener(this);

        mMediaStreamingManager = new MediaStreamingManager(this, afl, cameraPreviewFrameView, AVCodecType.SW_VIDEO_WITH_SW_AUDIO_CODEC); // sw codec
        mMediaStreamingManager.prepare(mCameraStreamingSetting, mMicrophoneStreamingSetting, null, mProfile);
//        mMediaStreamingManager.prepare(mCameraStreamingSetting, mMicrophoneStreamingSetting, watermarksetting, mProfile);

        mMediaStreamingManager.setNativeLoggingEnabled(false);
        mMediaStreamingManager.setStreamingStateListener(this);
        mMediaStreamingManager.setSurfaceTextureCallback(this);
        mMediaStreamingManager.setStreamingSessionListener(this);
        mMediaStreamingManager.setStreamStatusCallback(this);
        mMediaStreamingManager.setAudioSourceCallback(this);
        mMediaStreamingManager.setStreamingPreviewCallback(this);
        setFocusAreaIndicator();

        pushInitDataBean = getIntent().getParcelableExtra(PUB_DATA);
        live_id = getIntent().getStringExtra(LIVE_ID);

        if (pushInitDataBean != null && pushInitDataBean.getLivestreaming() != null) {
            stream_key = pushInitDataBean.getLivestreaming().getStream_key();
        }

        if (pushInitDataBean != null && pushInitDataBean.getLive() != null) {
            final String planStartTime = pushInitDataBean.getLive().getPlan_start_time();
            long currentTime = System.currentTimeMillis() / 1000;
            if (!UIUtils.isEmpty(planStartTime) && Double.parseDouble(planStartTime) > currentTime) {
                mLiveToastLayout.setVisibility(View.VISIBLE);
                AnimatorUtil.tranAnimator(mLiveToastLayout);
                clock = new TimeCount(SWCodecCameraStreamingActivity.this, mTVTime, (long) Double.parseDouble(planStartTime) * 1000 - System.currentTimeMillis(), 1000l);
                clock.setText(UIUtils.getString(R.string.start_live_time));
                clock.start();
                clock.setOnFinishClickListerner(new TimeCount.onFinishClickListerner() {
                    @Override
                    public void onClick() {
                        mTimeOut = true;
                        mTVTime.setVisibility(View.VISIBLE);
                        mTVTime.setText(UIUtils.getString(R.string.time_out));
                        mTVTimeBottom.setText(UIUtils.getString(R.string.please_start_live));
                    }
                });
            } else {
                mTimeOut = true;
                mLiveToastLayout.setVisibility(View.VISIBLE);
                AnimatorUtil.tranAnimator(mLiveToastLayout);
                mTVTime.setText(UIUtils.getString(R.string.time_out));
                mTVTimeBottom.setText(UIUtils.getString(R.string.please_start_live));
            }
        }
        initWebSocket();
    }

    private void ininCamera360() {

        tv_filter_one = (TextView) findViewById(R.id.filter_one_value);
        tv_filter_two = (TextView) findViewById(R.id.filter_two_value);
        tv_filter_three = (TextView) findViewById(R.id.filter_three_value);

        m_SeekFilterOne = (SeekBar) findViewById(R.id.seek_filter_one);
        m_SeekFilterOne.setOnSeekBarChangeListener(this);
        m_SeekFilterOne.setProgress((int) (mFilterOne * 100));
        tv_filter_one.setText(String.valueOf(mFilterOne));

        m_SeekFilterTwo = (SeekBar) findViewById(R.id.seek_filter_two);
        m_SeekFilterTwo.setOnSeekBarChangeListener(this);
        m_SeekFilterTwo.setProgress((int) (mFilterTwo * 100));
        tv_filter_two.setText(String.valueOf(mFilterTwo));

        m_SeekFilterThree = (SeekBar) findViewById(R.id.seek_filter_three);
        m_SeekFilterThree.setOnSeekBarChangeListener(this);
        m_SeekFilterThree.setProgress((int) (mFilterThree * 100));
        tv_filter_three.setText(String.valueOf(mFilterThree));

        tv_pink = (TextView) findViewById(R.id.pink_value);
        tv_whiten = (TextView) findViewById(R.id.whiten_value);
        tv_redden = (TextView) findViewById(R.id.redden_value);
        tv_soften = (TextView) findViewById(R.id.soften_value);

        mBeautiLayoutId = (RelativeLayout) findViewById(R.id.beauti_layout_id);
        mCancelBeauti = (TextView) findViewById(R.id.cancel_beauti);
        mRemindBeauti = (TextView) findViewById(R.id.remind_beauti);
        mCancelBeautilayout = (TextView) findViewById(R.id.cancel_beauti_layout);
        mCancelBeauti.setOnClickListener(this);
        mRemindBeauti.setOnClickListener(this);
        mCancelBeautilayout.setOnClickListener(this);

        m_Seekpink = (SeekBar) findViewById(R.id.seek_pink);
        m_Seekpink.setOnSeekBarChangeListener(this);
        m_Seekpink.setProgress((int) (mPinkValue * 100));
        tv_pink.setText(String.valueOf(mPinkValue));

        m_Seekwhiten = (SeekBar) findViewById(R.id.seek_whiten);
        m_Seekwhiten.setOnSeekBarChangeListener(this);
        m_Seekwhiten.setProgress((int) (mWhitenValue * 100));
        tv_whiten.setText(String.valueOf(mWhitenValue));

        m_Seekredden = (SeekBar) findViewById(R.id.seek_redden);
        m_Seekredden.setOnSeekBarChangeListener(this);
        m_Seekredden.setProgress((int) (mReddenValue * 100));
        tv_redden.setText(String.valueOf(mReddenValue));


        m_Seeksoften = (SeekBar) findViewById(R.id.seek_soften);
        m_Seeksoften.setOnSeekBarChangeListener(this);
        m_Seeksoften.setProgress(mSoftenValue);
        tv_soften.setText(String.valueOf(mSoftenValue));

    }

    private void initWebSocket() {
        EventBus.getDefault().register(this);

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

        mWebSocketUser.setVisibility(View.INVISIBLE);

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

        mPointMsg = (TextView) findViewById(R.id.point_msg);
        mBuyGoodsMsg = (TextView) findViewById(R.id.buy_goods_message);
        mBuautiImage = (ImageView) findViewById(R.id.beauti_image);
        mJingXiangImage = (ImageView) findViewById(R.id.jingxiang_image);
        mRotateImage = (ImageView) findViewById(R.id.switch_image);
        mThreeButton = (LinearLayout) findViewById(R.id.three_button);
        mThreeButton.setVisibility(View.VISIBLE);

        mLiveTitle = (TextView) findViewById(R.id.live_title);
        mLiveClose = (ImageView) findViewById(R.id.live_close);
        mLiveUserIcon = (ImageView) findViewById(R.id.live_user_header);
        mLiveUserIconTwo = (ImageView) findViewById(R.id.live_user_header_two);
        mLiveUserIconThree = (ImageView) findViewById(R.id.live_user_header_three);
        mLiveUserIconFour = (ImageView) findViewById(R.id.live_user_header_four);
        mNameNumberLayout = (LinearLayout) findViewById(R.id.live_user_detail_layout);
        mLiveUserName = (TextView) findViewById(R.id.live_user_name);
        mLiveUserNumber = (TextView) findViewById(R.id.live_user_store_id);
        mLiveInUser = (RecyclerView) findViewById(R.id.live_in_user);
        mNotifyText = (RunBackText) findViewById(R.id.notify_text);

        mNotifyText.setText("1");
        mNotifyText.init(getWindowManager());
        mNotifyText.startScroll();
        mNotifyText.setVisibility(View.INVISIBLE);

        mStartLive.setOnClickListener(this);
        mShowBottomImage.setOnClickListener(this);
        mBuautiImage.setOnClickListener(this);
        mJingXiangImage.setOnClickListener(this);
        mRotateImage.setOnClickListener(this);
        mLiveClose.setOnClickListener(this);
        mRefreshImage.setOnClickListener(this);
        mShowAnimator.setOnClickListener(this);
        mShowAnimatorlayout.setOnClickListener(this);
        mPushLayout.setOnClickListener(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mLiveToastLayout.getVisibility() != View.VISIBLE) {
            HttpHelper.exitLive(live_id, stream_key, null);
            stopStreaming();
        }
        SocketMsgHandler.stepOutChannelMsg();
        EventBus.getDefault().unregister(this);
        mMediaStreamingManager.pause();
        mMediaStreamingManager.destroy();
        messageLooper.removeCallbacksAndMessages(null);
        showAnnounHandler.removeCallbacksAndMessages(null);
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
            layoutParams.setMargins(0, UIUtils.dp2px(1), UIUtils.dp2px(3), 0);
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
            case R.id.start_live:
                if (mTimeOut) {
                    if (mFragmentGoods != null) {
                        mFragmentGoods.setGoodsShow();
                    }
                    mStartLive.setVisibility(View.GONE);
                    mWebSocketUser.setVisibility(View.VISIBLE);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        AnimatorUtil.rootViewGone(mLiveToastLayout, new AnimatorUtil.AnimatorCallBack() {
                            @Override
                            public void callBack() {
                                if (clock != null) {
                                    clock.cancel();
                                    clock = null;
                                }
                                HttpHelper.startLive(live_id, stream_key, null);
                                startStreaming();
                            }
                        });
                    } else {
                        mLiveToastLayout.setVisibility(View.GONE);
                        if (clock != null) {
                            clock.cancel();
                            clock = null;
                        }
                        HttpHelper.startLive(live_id, stream_key, null);
                        startStreaming();
                    }
                } else {
                    UIUtils.showToastSafe(R.string.not_time_out);
                }
                break;
//            case R.id.beauti_image:
//                mBeautiLayoutId.setVisibility(View.VISIBLE);
//                ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(mBeautiLayoutId, "translationY", 0, -UIUtils.dp2px(230));
//                objectAnimator.setDuration(300).start();
//                break;
            case R.id.jingxiang_image:
                mIsPreviewMirror = !mIsPreviewMirror;
                mMediaStreamingManager.setPreviewMirror(mIsPreviewMirror);

                if (isSwitch) {
                    isSwitch = false;
                    mIsEncodingMirror = !mIsEncodingMirror;
                    mMediaStreamingManager.setEncodingMirror(mIsEncodingMirror);
                }
                mIsEncodingMirror = !mIsEncodingMirror;
                mMediaStreamingManager.setEncodingMirror(mIsEncodingMirror);

                mJingXiangImage.setImageResource(mIsPreviewMirror ? (isClickTran ? R.drawable.jingxiang_camera : R.drawable.jingxiang_camera_big) : R.drawable.jingxiang_camera_open);
                break;
            case R.id.switch_image:
                isSwitch = true;

                mHandler.removeCallbacks(mSwitcher);
                mHandler.postDelayed(mSwitcher, 100);

                isBack = !isBack;
                if (isBack) {
                    mRotateImage.setImageResource(R.drawable.rotate_camera_open);
                } else {
                    mRotateImage.setImageResource(isClickTran ? R.drawable.rotate_camera : R.drawable.rotate_camera_big);
                }
                break;
            case R.id.live_close:
                showDialog();
                break;
            case R.id.show_bottom_image:
                mThreeButton.setVisibility(View.INVISIBLE);
                if (!isClickTran) {
                    isClickTran = true;
                    mShowBottomImage.setImageResource(R.drawable.show_bottom);
                    mShowBottom.setVisibility(View.GONE);

                    ValueAnimator anim = ValueAnimator.ofInt(-UIUtils.dp2px(444), 0);
                    anim.setDuration(150);
                    anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                        @Override
                        public void onAnimationUpdate(ValueAnimator animation) {
                            int currentValue = (int) animation.getAnimatedValue();
                            RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) mUserTextListLayout.getLayoutParams();
                            layoutParams.bottomMargin = currentValue;
                            mUserTextListLayout.setLayoutParams(layoutParams);

                            if (currentValue == 0) {
                                showAnimator.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        afl.setShowMode(AspectFrameLayout.SHOW_MODE.REAL);
                                        RelativeLayout.LayoutParams layoutParams1 = (RelativeLayout.LayoutParams) mPushLayout.getLayoutParams();
                                        layoutParams1.addRule(RelativeLayout.ABOVE, R.id.ll_user_text_layout);
                                        mPushLayout.setLayoutParams(layoutParams1);
                                        startHorizontal();
                                        AnimatorUtil.viewVisible(mThreeButton);
                                    }
                                }, 50);
                            }
                        }
                    });
                    anim.start();
                } else {
                    isClickTran = false;
                    RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) mPushLayout.getLayoutParams();
                    layoutParams.removeRule(RelativeLayout.ABOVE);
                    mPushLayout.setLayoutParams(layoutParams);
                    afl.setShowMode(AspectFrameLayout.SHOW_MODE.FULL);

                    mShowBottomImage.setImageResource(R.drawable.show_bottom_null_big);
                    showAnimator.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            ValueAnimator anim = ValueAnimator.ofInt(0, -UIUtils.dp2px(444));
                            anim.setDuration(150);
                            anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                                @Override
                                public void onAnimationUpdate(ValueAnimator animation) {
                                    int currentValue = (int) animation.getAnimatedValue();
                                    RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) mUserTextListLayout.getLayoutParams();
                                    layoutParams.bottomMargin = currentValue;
                                    mUserTextListLayout.setLayoutParams(layoutParams);

                                    if (currentValue == -UIUtils.dp2px(444)) {
                                        showAnimator.postDelayed(new Runnable() {
                                            @Override
                                            public void run() {
                                                mShowBottom.setVisibility(View.VISIBLE);
                                                startVertical();
                                                AnimatorUtil.viewVisible(mThreeButton);
                                            }
                                        }, 50);
                                    }
                                }
                            });
                            anim.start();
                        }
                    }, 50);
                }

                if (!isBack) {
                    mRotateImage.setImageResource(isClickTran ? R.drawable.rotate_camera : R.drawable.rotate_camera_big);
                }
                if (mIsPreviewMirror) {
                    mJingXiangImage.setImageResource(isClickTran ? R.drawable.jingxiang_camera : R.drawable.jingxiang_camera_big);
                }
                break;
            case R.id.refresh_image:
                UIUtils.showToastSafe(UIUtils.getString(R.string.link_restart));
                if (!SocketUtil.mConnection.isConnected())
                    SocketMsgHandler.startSocket();

                startStreamingInternal();
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
            case R.id.cancel_beauti_layout:
                ObjectAnimator objectAnimator1 = ObjectAnimator.ofFloat(mBeautiLayoutId, "translationY", -UIUtils.dp2px(230), 0);
                objectAnimator1.setDuration(300).start();
                objectAnimator1.addListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {
                        mBeautiLayoutId.setVisibility(View.GONE);
                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {

                    }

                    @Override
                    public void onAnimationRepeat(Animator animation) {

                    }
                });
                break;
            case R.id.remind_beauti:
                mSkinChange = true;
                mSoftenChange = true;
                mPinkValue = mPinkValueDefault;
                mWhitenValue = mWhitenValueDefault;
                mReddenValue = mReddenValueDefault;
                mSoftenValue = mSoftenValueDefault;
                mFilterOne = mFilterOneDefault;
                mFilterTwo = mFilterTwoDefault;
                mFilterThree = mFilterThreeDefault;

                tv_pink.setText(String.valueOf(mPinkValue));
                tv_whiten.setText(String.valueOf(mWhitenValue));
                tv_redden.setText(String.valueOf(mReddenValue));
                tv_soften.setText(String.valueOf(mSoftenValue));
                tv_filter_one.setText(String.valueOf(mFilterOne));
                tv_filter_two.setText(String.valueOf(mFilterTwo));
                tv_filter_three.setText(String.valueOf(mFilterThree));

                m_Seekpink.setProgress((int) (mPinkValue * 100));
                m_Seekwhiten.setProgress((int) (mWhitenValue * 100));
                m_Seekredden.setProgress((int) (mReddenValue * 100));
                m_Seeksoften.setProgress(mSoftenValue);
                m_SeekFilterOne.setProgress((int) (mFilterOne * 100));
                m_SeekFilterTwo.setProgress((int) (mFilterTwo * 100));
                m_SeekFilterThree.setProgress((int) (mFilterThree * 100));

                break;
            case R.id.cancel_beauti:
                mSkinChange = true;
                mSoftenChange = true;
                mPinkValue = 0f;
                mWhitenValue = 0f;
                mReddenValue = 0f;
                mSoftenValue = 0;
                mFilterOne = 0f;
                mFilterTwo = 0f;
                mFilterThree = 0f;

                tv_filter_one.setText(String.valueOf(mFilterOne));
                tv_filter_two.setText(String.valueOf(mFilterOne));
                tv_filter_three.setText(String.valueOf(mFilterOne));
                tv_pink.setText(String.valueOf(mPinkValue));
                tv_redden.setText(String.valueOf(mPinkValue));
                tv_whiten.setText(String.valueOf(mPinkValue));
                tv_soften.setText(String.valueOf(mSoftenValue));

                m_SeekFilterOne.setProgress(0);
                m_SeekFilterTwo.setProgress(0);
                m_SeekFilterThree.setProgress(0);
                m_Seekpink.setProgress(0);
                m_Seekwhiten.setProgress(0);
                m_Seekredden.setProgress(0);
                m_Seeksoften.setProgress(0);
                break;
            case R.id.push_layout:
                mBeautiLayoutId.setVisibility(View.GONE);

                mThreeButton.setVisibility(View.INVISIBLE);
                isClickTran = false;
                RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) mPushLayout.getLayoutParams();
                layoutParams.removeRule(RelativeLayout.ABOVE);
                mPushLayout.setLayoutParams(layoutParams);
                afl.setShowMode(AspectFrameLayout.SHOW_MODE.FULL);

                mShowBottomImage.setImageResource(R.drawable.show_bottom_null_big);
                showAnimator.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        ValueAnimator anim = ValueAnimator.ofInt(0, -UIUtils.dp2px(444));
                        anim.setDuration(150);
                        anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                            @Override
                            public void onAnimationUpdate(ValueAnimator animation) {
                                int currentValue = (int) animation.getAnimatedValue();
                                RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) mUserTextListLayout.getLayoutParams();
                                layoutParams.bottomMargin = currentValue;
                                mUserTextListLayout.setLayoutParams(layoutParams);

                                if (currentValue == -UIUtils.dp2px(444)) {
                                    showAnimator.postDelayed(new Runnable() {
                                        @Override
                                        public void run() {
                                            mShowBottom.setVisibility(View.VISIBLE);
                                            startVertical();
                                            AnimatorUtil.viewVisible(mThreeButton);
                                        }
                                    }, 50);
                                }
                            }
                        });
                        anim.start();
                    }
                }, 50);

                if (!isBack) {
                    mRotateImage.setImageResource(isClickTran ? R.drawable.rotate_camera : R.drawable.rotate_camera_big);
                }
                if (mIsPreviewMirror) {
                    mJingXiangImage.setImageResource(isClickTran ? R.drawable.jingxiang_camera : R.drawable.jingxiang_camera_big);
                }
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
        }, 10000);
    }

    private void showDialog() {
        BaseAllDialogF baseAllDialogF = new BaseAllDialogF()
                .setContent(UIUtils.getString(R.string.is_finish))
                .setCancelTxt(UIUtils.getString(R.string.confirm), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                })
                .setConfirmTxt(UIUtils.getString(R.string.cancel), null);
        baseAllDialogF.setCancelable(false);
        baseAllDialogF.show(SWCodecCameraStreamingActivity.this.getSupportFragmentManager(), "FACE");
    }

    @Override
    public void onBackPressed() {
        showDialog();
    }

    private void startHorizontal() {
        mThreeButton.setOrientation(LinearLayout.HORIZONTAL);
        setHorizontal(mShowBottomImage, 7);
        setHorizontal(mBuautiImage, 7);
        setHorizontal(mJingXiangImage, 7);
        setHorizontal(mRotateImage, 0);
    }

    private void setHorizontal(View view, int right) {
        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) view.getLayoutParams();
        layoutParams.topMargin = 0;
        layoutParams.rightMargin = UIUtils.dp2px(right);
        view.setLayoutParams(layoutParams);
    }

    private void startVertical() {
        mThreeButton.setOrientation(LinearLayout.VERTICAL);
        setVertical(mShowBottomImage, 0);
        setVertical(mBuautiImage, 9);
        setVertical(mJingXiangImage, 9);
        setVertical(mRotateImage, 9);
    }

    private void setVertical(View view, int top) {
        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) view.getLayoutParams();
        layoutParams.rightMargin = 0;
        layoutParams.topMargin = UIUtils.dp2px(top);
        view.setLayoutParams(layoutParams);
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        switch (seekBar.getId()) {
            case R.id.seek_pink:
                mPinkValue = progress / 100f;
                mSkinChange = true;
                tv_pink.setText(String.valueOf(mPinkValue));
                break;
            case R.id.seek_whiten:
                mWhitenValue = progress / 100f;
                mSkinChange = true;
                tv_whiten.setText(String.valueOf(mWhitenValue));
                break;
            case R.id.seek_redden:
                mReddenValue = progress / 100f;
                mSkinChange = true;
                tv_redden.setText(String.valueOf(mReddenValue));
                break;
            case R.id.seek_soften:
                mSoftenValue = progress;
                mSoftenChange = true;
                tv_soften.setText(String.valueOf(mSoftenValue));
                break;
            case R.id.seek_filter_one:
                mFilterOne = progress / 100f;
                tv_filter_one.setText(String.valueOf(mFilterOne));
                if (mCameraStreamingSetting == null || mMediaStreamingManager == null) {
                    return;
                }
                CameraStreamingSetting.FaceBeautySetting fbSetting = mCameraStreamingSetting.getFaceBeautySetting();
                fbSetting.whiten = mFilterOne;
                fbSetting.redden = mFilterTwo;
                fbSetting.beautyLevel = mFilterThree;
                mMediaStreamingManager.updateFaceBeautySetting(fbSetting);
                break;
            case R.id.seek_filter_two:
                mFilterTwo = progress / 100f;
                tv_filter_two.setText(String.valueOf(mFilterTwo));
                if (mCameraStreamingSetting == null || mMediaStreamingManager == null) {
                    return;
                }
                CameraStreamingSetting.FaceBeautySetting fbSetting1 = mCameraStreamingSetting.getFaceBeautySetting();
                fbSetting1.whiten = mFilterOne;
                fbSetting1.redden = mFilterTwo;
                fbSetting1.beautyLevel = mFilterThree;
                mMediaStreamingManager.updateFaceBeautySetting(fbSetting1);
                break;
            case R.id.seek_filter_three:
                mFilterThree = progress / 100f;
                tv_filter_three.setText(String.valueOf(mFilterThree));
                if (mCameraStreamingSetting == null || mMediaStreamingManager == null) {
                    return;
                }
                CameraStreamingSetting.FaceBeautySetting fbSetting2 = mCameraStreamingSetting.getFaceBeautySetting();
                fbSetting2.whiten = mFilterOne;
                fbSetting2.redden = mFilterTwo;
                if (mFilterThree == 0) {
                    mFilterThree = 0.01f;
                }
                fbSetting2.beautyLevel = mFilterThree;
                mMediaStreamingManager.updateFaceBeautySetting(fbSetting2);
                break;
        }
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }
}
