package com.bhtc.huajuan.push.activity;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AbsListView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bhtc.huajuan.push.App;
import com.bhtc.huajuan.push.R;
import com.bhtc.huajuan.push.adapter.ReserveAdapter;
import com.bhtc.huajuan.push.bean.LiveBean;
import com.bhtc.huajuan.push.bean.PublishBean;
import com.bhtc.huajuan.push.bean.PushInitDataBean;
import com.bhtc.huajuan.push.bean.ReserveListBean;
import com.bhtc.huajuan.push.bean.WSMessageBean;
import com.bhtc.huajuan.push.util.DividerItemDecoration;
import com.bhtc.huajuan.push.util.HttpHelper;
import com.bhtc.huajuan.push.util.MyCallBack;
import com.bhtc.huajuan.push.util.TimeCount;
import com.bhtc.huajuan.push.util.UIUtils;
import com.bhtc.huajuan.push.websocket.SocketMsgHandler;
import com.chanven.lib.cptr.PtrClassicFrameLayout;
import com.chanven.lib.cptr.PtrFrameLayout;
import com.chanven.lib.cptr.PtrHandler;
import com.chanven.lib.cptr.loadmore.OnLoadMoreListener;
import com.chanven.lib.cptr.recyclerview.RecyclerAdapterWithHF;

import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;

public class ReserveListActivity extends BaseActivity implements PtrHandler, OnLoadMoreListener, View.OnClickListener {

    private PtrClassicFrameLayout ptrClassicFrameLayout;
    private RecyclerAdapterWithHF mAdapter;
    public RecyclerView mGoodsClassRecycler;
    private LinearLayoutManager mLinearLayoutManager;
    private ReserveAdapter mReserveAdapter;

    private ImageView mIvEyes;
    private List<LiveBean> lives = new ArrayList<>();
    //刷新加载状态
    public int REFRESH_TO_LOAD_TYPE = 0;
    public int NOT_TYPE = 0;
    public int REFRESH = 1;
    public int LOADMORE = 2;

    private TextView mBackImage;
    private String TAG = "ReserveListActivity";
    public Map<Integer, TimeCount> timeCountList = new HashMap<>();

    private EditText mBitVideo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reserve_list);

        initView();
    }

    private void initView() {
        mBitVideo = (EditText) findViewById(R.id.bit_video);
        mBitVideo.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                try {
                    StreamingBaseActivity.BIT_VIDEO = Integer.parseInt(s.toString());
                }catch (Exception e){
                    e.printStackTrace();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        mBackImage = (TextView) findViewById(R.id.back_image);
        mBackImage.setOnClickListener(this);

        mGoodsClassRecycler = (RecyclerView) findViewById(R.id.swipe_target);
        mLinearLayoutManager = new LinearLayoutManager(this);
        mLinearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mGoodsClassRecycler.setLayoutManager(mLinearLayoutManager);
        mReserveAdapter = new ReserveAdapter(this, lives);
        mAdapter = new RecyclerAdapterWithHF(mReserveAdapter, this);
        mGoodsClassRecycler.setAdapter(mAdapter);
        mGoodsClassRecycler.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST));
        mGoodsClassRecycler.setItemAnimator(new DefaultItemAnimator());

        ptrClassicFrameLayout = (PtrClassicFrameLayout) findViewById(R.id.recycler_goods_class);
        ptrClassicFrameLayout.setHeaderView(getHeaderView(ptrClassicFrameLayout));
        ptrClassicFrameLayout.setPtrHandler(this);
        ptrClassicFrameLayout.setOnLoadMoreListener(this);

        ptrClassicFrameLayout.postDelayed(new Runnable() {
            @Override
            public void run() {
                REFRESH_TO_LOAD_TYPE = REFRESH;
                ptrClassicFrameLayout.autoRefresh(true);
            }
        }, 150);

    }

    @Override
    public void onRefreshBegin(PtrFrameLayout frame) {
        REFRESH_TO_LOAD_TYPE = REFRESH;
        loadData(0);
    }

    @Override
    public void loadMore() {
        REFRESH_TO_LOAD_TYPE = LOADMORE;
        loadData(lives.size());
    }

    private void loadData(final int offset) {
        HttpHelper.getReserveList(offset, new MyCallBack<ReserveListBean>(ReserveListBean.class, this, false) {
            @Override
            public void onResponse(ReserveListBean reserveListBean) {
                stopLoadingAnim(ptrClassicFrameLayout);
                if (reserveListBean != null && reserveListBean.success()) {
                    Log.i("okhttp", reserveListBean.toString());
                    if (offset == 0) {
                        for (TimeCount value : timeCountList.values()) {
                            if (value != null) {
                                value.cancel();
                            }
                        }
                        timeCountList.clear();
                        lives.clear();
                        if (reserveListBean.getCur_live() != null && reserveListBean.getCur_live().getUser() != null) {
                            lives.add(reserveListBean.getCur_live());
                            mReserveAdapter.havaStart = true;
                        } else {
                            mReserveAdapter.havaStart = false;
                        }
                    }

                    if (reserveListBean.getLives() != null && reserveListBean.getLives().size() != 0) {
                        int position = lives.size();
                        lives.addAll(reserveListBean.getLives());
                        if (offset == 0) {
                            mReserveAdapter.notifyDataSetChanged();
                        } else {
                            mReserveAdapter.notifyItemInserted(position);
                        }
                    } else {
                        if (offset == 0) {
                            mReserveAdapter.notifyDataSetChanged();
                        }
                    }
                } else {
                    UIUtils.showToastSafe(R.string.date_error);
                }
            }

            @Override
            public void onError(Call call, Exception e) {
                super.onError(call, e);
                stopLoadingAnim(ptrClassicFrameLayout);
            }
        });
    }

    @Override
    public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
        return !canChildScrollUp(content);
    }

    public static boolean canChildScrollUp(View view) {
        if (android.os.Build.VERSION.SDK_INT < 14) {
            if (view instanceof AbsListView) {
                final AbsListView absListView = (AbsListView) view;
                return absListView.getChildCount() > 0
                        && (absListView.getFirstVisiblePosition() > 0 || absListView.getChildAt(0).getTop() <
                        absListView
                                .getPaddingTop());
            } else {
                return ViewCompat.canScrollVertically(view, -1) || view.getScrollY() > 0;
            }
        } else {
            return ViewCompat.canScrollVertically(view, -1);
        }
    }

    //停止加载动画
    public void stopLoadingAnim(PtrClassicFrameLayout ptrClassicFrameLayout) {
        if (REFRESH_TO_LOAD_TYPE == REFRESH) {
            ptrClassicFrameLayout.refreshComplete();
            ptrClassicFrameLayout.setLoadMoreEnable(true);
        } else if (REFRESH_TO_LOAD_TYPE == LOADMORE) {
            ptrClassicFrameLayout.loadMoreComplete(true);
        }
        REFRESH_TO_LOAD_TYPE = NOT_TYPE;
    }

    //获取下拉刷新的headrView
    public View getHeaderView(PtrClassicFrameLayout ptrClassicFrameLayout) {
        View inflate = LayoutInflater.from(this).inflate(R.layout.layout_classic_footer, ptrClassicFrameLayout, false);
        mIvEyes = (ImageView) inflate.findViewById(R.id.loading_eyes_tran);
        float right = mIvEyes.getTranslationX();
        ObjectAnimator objRight = ObjectAnimator.ofFloat(mIvEyes, "translationX", right, right + UIUtils.dp2px(1.48f), right);
        objRight.setDuration(2500);
        objRight.setRepeatMode(ValueAnimator.RESTART);
        objRight.setRepeatCount(ValueAnimator.INFINITE);
        objRight.start();
        return inflate;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back_image:
                finish();
                break;
            case R.id.look_video:
                if (v.getTag(R.id.item_position) instanceof LiveBean) {
                    LiveBean liveBean = (LiveBean) v.getTag(R.id.item_position);
                    if (liveBean != null) {
                        if ("1".equals(liveBean.getStatus())) {
                            getPlayURL(liveBean.getId());
                        }
                    }
                }
                break;
            case R.id.start_push:
                if (v.getTag(R.id.item_position) instanceof LiveBean) {
                    final LiveBean liveBean = (LiveBean) v.getTag(R.id.item_position);
                    if (liveBean != null) {
                        if ("0".equals(liveBean.getStatus())) {
                            getPushURL(liveBean.getId());
                        } else if ("4".equals(liveBean.getStatus())) {
                            getPushURL(liveBean.getId());
                        }
                    }
                }
                break;
        }
    }

    private void getPushURL(final String liveId) {
        HttpHelper.getPublishInitdata(liveId, new MyCallBack<PushInitDataBean>(PushInitDataBean.class, ReserveListActivity.this) {
            @Override
            public void onResponse(final PushInitDataBean pushInitDataBean) {
                if (pushInitDataBean != null && pushInitDataBean.success()) {
                    Log.i(TAG, pushInitDataBean.toString());
                    if (pushInitDataBean.getLive() != null) {
                        SocketMsgHandler.MIN_SECOND = (int) Double.parseDouble(pushInitDataBean.getLive().getMin_second());
                        SocketMsgHandler.MAX_SECOND = (int) Double.parseDouble(pushInitDataBean.getLive().getMax_second());
                    }
                    PublishBean livestreaming = pushInitDataBean.getLivestreaming();
                    if (livestreaming != null) {
                        WSMessageBean.CHANNEL_TESTING = livestreaming.getStream_key();
                        String stream_key = livestreaming.getRtmp_publish_url();
                        URI u;
                        try {
                            u = new URI(stream_key);
                        } catch (Exception e) {
                            UIUtils.showToastSafe(UIUtils.getString(R.string.push_link_null));
                            e.printStackTrace();
                            return;
                        }
                        final String publishUrl = u.toString();
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                Intent intent = new Intent(ReserveListActivity.this, SWCodecCameraStreamingActivity.class);
                                intent.putExtra(SWCodecCameraStreamingActivity.PUB_URL, publishUrl);
                                intent.putExtra(SWCodecCameraStreamingActivity.PUB_DATA, (Parcelable) pushInitDataBean);
                                intent.putExtra(SWCodecCameraStreamingActivity.LIVE_ID, liveId);
                                startActivity(intent);
                            }
                        }).start();
                    }
                } else {
                    UIUtils.showToastSafe(R.string.date_error);
                }
            }
        });
    }

    private void getPlayURL(final String liveId) {
        HttpHelper.getPublishInitdata(liveId, new MyCallBack<PushInitDataBean>(PushInitDataBean.class, ReserveListActivity.this) {
            @Override
            public void onResponse(final PushInitDataBean pushInitDataBean) {
                if (pushInitDataBean != null && pushInitDataBean.success()) {
                    Log.i(TAG, pushInitDataBean.toString());
                    PublishBean livestreaming = pushInitDataBean.getLivestreaming();
                    if (livestreaming != null) {
                        WSMessageBean.CHANNEL_TESTING = livestreaming.getStream_key();
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                if (pushInitDataBean.getLive() != null) {
                                    SocketMsgHandler.MIN_SECOND = (int) Double.parseDouble(pushInitDataBean.getLive().getMin_second());
                                    SocketMsgHandler.MAX_SECOND = (int) Double.parseDouble(pushInitDataBean.getLive().getMax_second());

                                    long currentTime = System.currentTimeMillis() / 1000;
                                    if (!UIUtils.isEmpty(pushInitDataBean.getLive().getPlan_start_time()) && Double.parseDouble(pushInitDataBean.getLive().getPlan_start_time()) <= currentTime) {
                                        Intent intent = new Intent(ReserveListActivity.this, VideoPlayActivity.class);
                                        intent.putExtra(VideoPlayActivity.VIDEO_PATH, pushInitDataBean.getLivestreaming().getRtmp_play_url());
                                        intent.putExtra(VideoPlayActivity.PUB_DATA, (Parcelable) pushInitDataBean);
                                        intent.putExtra(VideoPlayActivity.LIVE_ID, liveId);
                                        startActivity(intent);
                                    } else {
                                        App.getMainThreadHandler().post(new Runnable() {
                                            @Override
                                            public void run() {
                                                UIUtils.showToastSafe(R.string.not_time_out);
                                            }
                                        });
                                    }
                                } else {
                                    App.getMainThreadHandler().post(new Runnable() {
                                        @Override
                                        public void run() {
                                            UIUtils.showToastSafe(R.string.date_error);
                                        }
                                    });
                                }
                            }
                        }).start();
                    }
                } else {
                    UIUtils.showToastSafe(R.string.date_error);
                }
            }
        });
    }
}
