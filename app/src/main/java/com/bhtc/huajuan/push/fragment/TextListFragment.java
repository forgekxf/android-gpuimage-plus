package com.bhtc.huajuan.push.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bhtc.huajuan.push.R;
import com.bhtc.huajuan.push.activity.SWCodecCameraStreamingActivity;
import com.bhtc.huajuan.push.activity.VideoPlayActivity;
import com.bhtc.huajuan.push.adapter.TextListAdapter;
import com.bhtc.huajuan.push.bean.WSMessageBean;

import java.util.List;

/**
 * Created by kouxiongfei on 2017/5/18.
 */

public class TextListFragment extends Fragment {
    private View mContentView;
    private Context context;

    private RecyclerView mTextRecyclerView;
    public TextListAdapter mTextListAdapter;
    private List<WSMessageBean> messageList;
    private int mCurrentType;

    public static String TYPE = "type";
    public static String LIST = "list";

    public LinearLayout mBuyGoodsInfo;
    private RelativeLayout mShowGoodsInfo;
    private TextView mUserName;
    private TextView mUserBuyGoods;
    private TextView mGoodsName;
    private TextView mGoodsPrice;
    private TextView mGoodsMarketPrice;
    private ImageView mGoodsImage;
    private String showGoodsId = "";
    private LinearLayoutManager mLinearLayoutManager2;

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser && getActivity() != null) {
            if (getActivity() instanceof SWCodecCameraStreamingActivity) {
                ((SWCodecCameraStreamingActivity) getActivity()).mPointMsg.setVisibility(View.GONE);
            } else {
                ((VideoPlayActivity) getActivity()).mPointMsg.setVisibility(View.GONE);
            }
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mContentView = LayoutInflater.from(context).inflate(R.layout.text_list_fragment, container, false);
        initView();
        return mContentView;
    }

    private void initView() {
        Bundle bundle = getArguments();
        if (bundle != null) {
            messageList = bundle.getParcelableArrayList(LIST);
            mCurrentType = bundle.getInt(TYPE);
        }

        mBuyGoodsInfo = (LinearLayout) mContentView.findViewById(R.id.user_buy_goods_layout);
        mShowGoodsInfo = (RelativeLayout) mContentView.findViewById(R.id.goods_info_layout);
        mUserName = (TextView) mContentView.findViewById(R.id.user_name);
        mUserBuyGoods = (TextView) mContentView.findViewById(R.id.user_buy_goods);
        mGoodsName = (TextView) mContentView.findViewById(R.id.goods_name);
        mGoodsPrice = (TextView) mContentView.findViewById(R.id.goods_price);
        mGoodsMarketPrice = (TextView) mContentView.findViewById(R.id.goods_market_price);
        mGoodsImage = (ImageView) mContentView.findViewById(R.id.goods_image);

        mTextRecyclerView = (RecyclerView) mContentView.findViewById(R.id.text_recycler_view);
        mLinearLayoutManager2 = new LinearLayoutManager(context);
        mLinearLayoutManager2.setOrientation(LinearLayoutManager.VERTICAL);
        mTextRecyclerView.setLayoutManager(mLinearLayoutManager2);
        mTextListAdapter = new TextListAdapter(context, mCurrentType, messageList);
        mTextRecyclerView.setAdapter(mTextListAdapter);
    }

    public static TextListFragment newInstance(Bundle args) {
        TextListFragment searchOneFragment = new TextListFragment();
        searchOneFragment.setArguments(args);
        return searchOneFragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }

    public void setWSMessageBeanData(WSMessageBean wsMessageBean) {
        showGoodsId = wsMessageBean.getAction_data().getGoods_id();

        if (messageList == null || mTextListAdapter == null || mTextRecyclerView == null) {
            return;
        }

        if (WSMessageBean.HIDDEN_GOODS.equals(wsMessageBean.getAction_type()) || wsMessageBean.getAction_type().equals(WSMessageBean.HIDDEN_GRAB_BUTTON)) {
            for (int i = 0; i < messageList.size(); i++) {
                if (showGoodsId.equals(messageList.get(i).getAction_data().getGoods_id())) {
                    messageList.remove(i);
                    mTextListAdapter.notifyItemRemoved(i);
                    return;
                }
            }
        }

        int positionHj = messageList.size();
        mTextListAdapter.notifyItemInserted(positionHj-1);

        if (getActivity() instanceof SWCodecCameraStreamingActivity) {
            if (((SWCodecCameraStreamingActivity) getActivity()).isClickTran){
                if (messageList.size() - 3 <= mLinearLayoutManager2.findLastVisibleItemPosition())
                    mTextRecyclerView.scrollToPosition(positionHj-1);
            }else {
                mTextRecyclerView.scrollToPosition(positionHj-1);
            }
        } else {
            if (((VideoPlayActivity) getActivity()).isClickTran){
                if (messageList.size() - 3 <= mLinearLayoutManager2.findLastVisibleItemPosition())
                    mTextRecyclerView.scrollToPosition(positionHj-1);
            }else {
                mTextRecyclerView.scrollToPosition(positionHj-1);
            }
        }
    }

    public void setBuyGoodsInfo(WSMessageBean wsMessageBean) {
        mBuyGoodsInfo.setVisibility(View.VISIBLE);
        mUserName.setText(wsMessageBean.getAction_data().getUser_name());
        mUserBuyGoods.setText("购买了" + wsMessageBean.getAction_data().getGoods_name());
    }
}
