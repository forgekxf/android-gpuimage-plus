package com.bhtc.huajuan.push.fragment;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bhtc.huajuan.push.R;
import com.bhtc.huajuan.push.activity.SWCodecCameraStreamingActivity;
import com.bhtc.huajuan.push.activity.VideoPlayActivity;
import com.bhtc.huajuan.push.adapter.GoodsFragAdapter;
import com.bhtc.huajuan.push.bean.LiveGoodsBean;
import com.bhtc.huajuan.push.bean.LiveItemBean;
import com.bhtc.huajuan.push.bean.WSMessageBean;
import com.bhtc.huajuan.push.util.HttpHelper;
import com.bhtc.huajuan.push.util.MyCallBack;
import com.bhtc.huajuan.push.util.UIUtils;
import com.bhtc.huajuan.push.websocket.SocketMsgHandler;
import com.chanven.lib.cptr.PtrClassicFrameLayout;
import com.chanven.lib.cptr.PtrFrameLayout;
import com.chanven.lib.cptr.PtrHandler;
import com.chanven.lib.cptr.recyclerview.RecyclerAdapterWithHF;

import okhttp3.Call;

/**
 * Created by kouxiongfei on 2017/5/19.
 */

public class GoodsInfoFragment extends Fragment implements View.OnClickListener, PtrHandler {
    private View mContentView;
    private Context context;
    private PtrClassicFrameLayout ptrClassicFrameLayout;
    private RecyclerAdapterWithHF mAdapter;
    private RecyclerView mGoodsList;
    public GoodsFragAdapter mTextListAdapter;
    private LiveGoodsBean mLiveGoodsBean;
    private String live_id = "";
    private String stream_key = "";

    public LinearLayout mBuyGoodsInfo;
    private TextView mUserName;
    private TextView mUserBuyGoods;

    public boolean goodsNotShow = true;

    //刷新加载状态
    public int REFRESH_TO_LOAD_TYPE = 0;
    public int NOT_TYPE = 0;
    public int REFRESH = 1;
    public int LOADMORE = 2;

    public void setGoodsShow() {
        goodsNotShow = false;
        mTextListAdapter.notifyDataSetChanged();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mContentView = LayoutInflater.from(context).inflate(R.layout.goods_info_fragment, container, false);
        initView();
        return mContentView;
    }

    public static GoodsInfoFragment newInstance(Bundle args) {
        GoodsInfoFragment searchOneFragment = new GoodsInfoFragment();
        searchOneFragment.setArguments(args);
        return searchOneFragment;
    }

    private void initView() {
        Bundle bundle = getArguments();
        if (bundle != null) {
            live_id = bundle.getString(SWCodecCameraStreamingActivity.LIVE_ID);
            stream_key = bundle.getString(SWCodecCameraStreamingActivity.STREAM_KEY);
        }
        mGoodsList = (RecyclerView) mContentView.findViewById(R.id.goods_info_fragment_recy);
        LinearLayoutManager mLinearLayoutManager2 = new LinearLayoutManager(context);
        mLinearLayoutManager2.setOrientation(LinearLayoutManager.VERTICAL);
        mGoodsList.setLayoutManager(mLinearLayoutManager2);
        mTextListAdapter = new GoodsFragAdapter(context, mLiveGoodsBean, this);
        mGoodsList.setAdapter(mTextListAdapter);

        ptrClassicFrameLayout = (PtrClassicFrameLayout) mContentView.findViewById(R.id.recycler_goods_class);
        ptrClassicFrameLayout.setHeaderView(getHeaderView(ptrClassicFrameLayout));
        ptrClassicFrameLayout.setPtrHandler(this);

        mBuyGoodsInfo = (LinearLayout) mContentView.findViewById(R.id.user_buy_goods_layout);
        mUserName = (TextView) mContentView.findViewById(R.id.user_name);
        mUserBuyGoods = (TextView) mContentView.findViewById(R.id.user_buy_goods);

        initData();
    }

    private void initData() {
        HttpHelper.getLiveGoods(stream_key, live_id, new MyCallBack<LiveGoodsBean>(LiveGoodsBean.class, context,false) {
            @Override
            public void onResponse(LiveGoodsBean liveGoodsBean) {
                stopLoadingAnim(ptrClassicFrameLayout);
                if (liveGoodsBean != null && liveGoodsBean.success()) {
                    mTextListAdapter.setData(liveGoodsBean);
                } else {
                    UIUtils.showToastSafe(R.string.date_error);
                }
            }

            @Override
            public void onError(Call call, Exception e) {
                super.onError(call, e);
                e.printStackTrace();
                stopLoadingAnim(ptrClassicFrameLayout);
            }
        });
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.goods_send:
                if (v.getTag(R.id.item_position) instanceof LiveItemBean) {
                    if (getActivity() instanceof SWCodecCameraStreamingActivity) {
                        ((SWCodecCameraStreamingActivity) getActivity()).isHongRenPush = true;
                    } else if (getActivity() instanceof VideoPlayActivity) {
                        ((VideoPlayActivity) getActivity()).isHongRenPush = true;
                    }

                    LiveItemBean goods = (LiveItemBean) v.getTag(R.id.item_position);
                    if (goods == null){
                        return;
                    }
                    if (goods.isQiang()){
                        if (((TextView) v).getText().equals(UIUtils.getString(R.string.out_send))) {
                            if (goodsNotShow) {
                                return;
                            }
                            SocketMsgHandler.qiangMsg(goods.getGoods_id(), goods.getLimit(),goods.getGoods_name(),goods.getGoods_image(),goods.getDiscount(), goods.getSpec_price_id(),goods.getGoods_price(),goods.getGoods_marketprice(),goods.getGoods_desc(),goods.getGoods_state(),goods.getGoods_stock(),goods.getSpec_images());
                        } else {
                            SocketMsgHandler.hiddenQiangMsg(goods.getGoods_id(), goods.getLimit(),goods.getGoods_name(),goods.getGoods_image(),goods.getDiscount(), goods.getSpec_price_id(),goods.getGoods_price(),goods.getGoods_marketprice(),goods.getGoods_desc());
                        }
                    }else {
                        if (((TextView) v).getText().equals(UIUtils.getString(R.string.out_send))) {
                            if (goodsNotShow) {
                                return;
                            }
                            SocketMsgHandler.sendGoods(goods.getGoods_id(), goods.getGoods_name(), goods.getGoods_price(), goods.getGoods_marketprice(), goods.getGoods_hj_price(), goods.getGoods_stock(), goods.getGoods_image(), goods.getGoods_state());
                        } else {
                            SocketMsgHandler.hiddenGoods(goods.getGoods_id(), goods.getGoods_name(), goods.getGoods_price(), goods.getGoods_marketprice(), goods.getGoods_hj_price(), goods.getGoods_stock(), goods.getGoods_image());
                        }
                    }
                }
                break;
        }
    }

    public void setBuyGoodsInfo(WSMessageBean wsMessageBean) {
        mBuyGoodsInfo.setVisibility(View.VISIBLE);
        mUserName.setText(wsMessageBean.getAction_data().getUser_name());
        mUserBuyGoods.setText("购买了" + wsMessageBean.getAction_data().getGoods_name());
    }

    //获取下拉刷新的headrView
    public View getHeaderView(PtrClassicFrameLayout ptrClassicFrameLayout) {
        View inflate = LayoutInflater.from(context).inflate(R.layout.layout_classic_footer, ptrClassicFrameLayout, false);
        ImageView mIvEyes = (ImageView) inflate.findViewById(R.id.loading_eyes_tran);
        float right = mIvEyes.getTranslationX();
        ObjectAnimator objRight = ObjectAnimator.ofFloat(mIvEyes, "translationX", right, right + UIUtils.dp2px(1.48f), right);
        objRight.setDuration(2500);
        objRight.setRepeatMode(ValueAnimator.RESTART);
        objRight.setRepeatCount(ValueAnimator.INFINITE);
        objRight.start();
        return inflate;
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
        } else if (REFRESH_TO_LOAD_TYPE == LOADMORE) {
        }
        REFRESH_TO_LOAD_TYPE = NOT_TYPE;
    }

    @Override
    public void onRefreshBegin(PtrFrameLayout frame) {
        REFRESH_TO_LOAD_TYPE = REFRESH;
        initData();
    }
}
