package com.bhtc.huajuan.push.adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bhtc.huajuan.push.R;
import com.bhtc.huajuan.push.bean.LiveGoodsBean;
import com.bhtc.huajuan.push.bean.LiveItemBean;
import com.bhtc.huajuan.push.fragment.GoodsInfoFragment;
import com.bhtc.huajuan.push.util.GlideManager;
import com.bhtc.huajuan.push.util.UIUtils;

import java.util.List;

/**
 * Created by kouxiongfei on 2017/5/19.
 */

public class GoodsFragAdapter extends RecyclerView.Adapter {
    private LiveGoodsBean mLiveGoodsBean;
    private GoodsInfoFragment mGoodsInfoFragment;
    private Context context;

    public GoodsFragAdapter(Context context, LiveGoodsBean mLiveGoodsBean, GoodsInfoFragment goodsInfoFragment) {
        this.context = context;
        this.mLiveGoodsBean = mLiveGoodsBean;
        this.mGoodsInfoFragment = goodsInfoFragment;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHolder(UIUtils.inflate(context, R.layout.goods_frag_item));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (mLiveGoodsBean == null || mLiveGoodsBean.getGoods_info() == null) {
            return;
        }
        List<LiveItemBean> liveGoods = mLiveGoodsBean.getGoods_info();

        if (liveGoods.get(position) == null) {
            return;
        }
        LiveItemBean goods = liveGoods.get(position);
        MyViewHolder myViewHolder = (MyViewHolder) holder;

        GlideManager.getInstance().loadImage(context, goods.getGoods_image(), myViewHolder.mGoodsImage);
        myViewHolder.mGoodsName.setText(goods.getGoods_name());
        myViewHolder.mGoodsDesc.setText(goods.getGoods_desc());
        myViewHolder.mGoodsPrice.setText("花卷价：" + goods.getGoods_price());
        myViewHolder.mGoodsMarket.setText("市场价：" + goods.getGoods_marketprice());
        myViewHolder.mGoodsMarket.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG | Paint.ANTI_ALIAS_FLAG);
        myViewHolder.mGoodsSend.setOnClickListener(mGoodsInfoFragment);
        myViewHolder.mGoodsSend.setTag(R.id.item_position, goods);

        myViewHolder.mBigQiang.setVisibility(goods.isQiang() ? View.VISIBLE : View.GONE);
        myViewHolder.mSmallQiang.setVisibility(goods.isQiang() ? View.VISIBLE : View.GONE);

        if ("0".equals(goods.getGoods_state())) {
            myViewHolder.mGoodsImageState.setVisibility(View.VISIBLE);
            myViewHolder.mGoodsImageState.setBackgroundResource(R.drawable.icon_sold_out);
        } else {
            myViewHolder.mGoodsImageState.setVisibility(View.GONE);
            if ("0".equals(goods.getGoods_stock())) {
                myViewHolder.mGoodsImageState.setVisibility(View.VISIBLE);
                myViewHolder.mGoodsImageState.setBackgroundResource(R.drawable.icon_sale_out);
            }
        }

        if (goods.getPop_show() == 0) {
            if (mGoodsInfoFragment.goodsNotShow) {
                myViewHolder.mGoodsSend.setBackgroundResource(R.drawable.bg_white);
                myViewHolder.mGoodsSend.setTextColor(Color.parseColor("#ffffff"));
                myViewHolder.mGoodsSend.setText(UIUtils.getString(R.string.out_send));
            } else {
                myViewHolder.mGoodsSend.setBackgroundResource(R.drawable.bg_red_4);
                myViewHolder.mGoodsSend.setTextColor(Color.parseColor("#ffffff"));
                myViewHolder.mGoodsSend.setText(UIUtils.getString(R.string.out_send));
            }
        } else {
            myViewHolder.mGoodsSend.setBackgroundResource(R.drawable.bg_transparent_ff737e_4);
            myViewHolder.mGoodsSend.setTextColor(Color.parseColor("#ff737e"));
            myViewHolder.mGoodsSend.setText(UIUtils.getString(R.string.che_hui));
        }

//        if (mGoodsInfoFragment.getActivity() != null) {
//            if (mGoodsInfoFragment.getActivity() instanceof SWCodecCameraStreamingActivity){
//                for (int i = 0; i < ((SWCodecCameraStreamingActivity) mGoodsInfoFragment.getActivity()).sendGoodsId.size(); i++) {
//                    if (((SWCodecCameraStreamingActivity) mGoodsInfoFragment.getActivity()).sendGoodsId.get(i).equals(goods.getGoods_id())) {
//                        myViewHolder.mGoodsSend.setBackgroundResource(R.drawable.bg_transparent_ff737e_4);
//                        myViewHolder.mGoodsSend.setTextColor(Color.parseColor("#ff737e"));
//                        myViewHolder.mGoodsSend.setText(UIUtils.getString(R.string.che_hui));
//                        break;
//                    }
//                }
//            }else {
//                for (int i = 0; i < ((VideoPlayActivity) mGoodsInfoFragment.getActivity()).sendGoodsId.size(); i++) {
//                    if (((VideoPlayActivity) mGoodsInfoFragment.getActivity()).sendGoodsId.get(i).equals(goods.getGoods_id())) {
//                        myViewHolder.mGoodsSend.setBackgroundResource(R.drawable.bg_transparent_ff737e_4);
//                        myViewHolder.mGoodsSend.setTextColor(Color.parseColor("#ff737e"));
//                        myViewHolder.mGoodsSend.setText(UIUtils.getString(R.string.che_hui));
//                        break;
//                    }
//                }
//            }
//        }
    }

    @Override
    public int getItemCount() {
        if (mLiveGoodsBean != null && mLiveGoodsBean.getGoods_info() != null) {
            return mLiveGoodsBean.getGoods_info().size();
        }
        return 0;
    }

    public void setTanchuUI(String goods_id, boolean isQiang) {
        if (mLiveGoodsBean != null && mLiveGoodsBean.getGoods_info() != null) {
            for (int i = 0; i < mLiveGoodsBean.getGoods_info().size(); i++) {
                if (mLiveGoodsBean.getGoods_info().get(i).getGoods_id().equals(goods_id)) {
                    if (isQiang) {
                        if (mLiveGoodsBean.getGoods_info().get(i).isQiang()) {
                            mLiveGoodsBean.getGoods_info().get(i).setPop_show(1);
                            notifyItemChanged(i);
                            break;
                        }
                    } else {
                        if (!mLiveGoodsBean.getGoods_info().get(i).isQiang()) {
                            mLiveGoodsBean.getGoods_info().get(i).setPop_show(1);
                            notifyItemChanged(i);
                            break;
                        }
                    }
                }
            }
        }
    }

    public void setChehuiUI(String goods_id, boolean isQiang) {
        if (mLiveGoodsBean != null && mLiveGoodsBean.getGoods_info() != null) {
            for (int i = 0; i < mLiveGoodsBean.getGoods_info().size(); i++) {
                if (mLiveGoodsBean.getGoods_info().get(i).getGoods_id().equals(goods_id)) {
                    if (isQiang) {
                        if (mLiveGoodsBean.getGoods_info().get(i).isQiang()) {
                            mLiveGoodsBean.getGoods_info().get(i).setPop_show(0);
                            notifyItemChanged(i);
                            break;
                        }
                    } else {
                        if (!mLiveGoodsBean.getGoods_info().get(i).isQiang()) {
                            mLiveGoodsBean.getGoods_info().get(i).setPop_show(0);
                            notifyItemChanged(i);
                            break;
                        }
                    }
                }
            }
        }
    }

    public void setData(LiveGoodsBean liveGoodsBean) {
        this.mLiveGoodsBean = liveGoodsBean;
        if (mLiveGoodsBean != null && mLiveGoodsBean.getGrab_info() != null && !UIUtils.isEmpty(mLiveGoodsBean.getGrab_info().getGoods_id())) {
            mLiveGoodsBean.getGrab_info().setQiang(true);
            if (mLiveGoodsBean.getGoods_info() != null)
                mLiveGoodsBean.getGoods_info().add(0, mLiveGoodsBean.getGrab_info());
        }
        notifyDataSetChanged();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        private ImageView mGoodsImage;
        private ImageView mGoodsImageState;
        private ImageView mBigQiang;
        private ImageView mSmallQiang;
        private TextView mGoodsName;
        private TextView mGoodsDesc;
        private TextView mGoodsPrice;
        private TextView mGoodsMarket;
        private TextView mGoodsSend;

        public MyViewHolder(View itemView) {
            super(itemView);
            mGoodsImage = (ImageView) itemView.findViewById(R.id.goods_image);
            mBigQiang = (ImageView) itemView.findViewById(R.id.goods_image_qiang);
            mSmallQiang = (ImageView) itemView.findViewById(R.id.goods_qiang);
            mGoodsImageState = (ImageView) itemView.findViewById(R.id.goods_image_state);
            mGoodsName = (TextView) itemView.findViewById(R.id.goods_name);
            mGoodsDesc = (TextView) itemView.findViewById(R.id.goods_desc);
            mGoodsPrice = (TextView) itemView.findViewById(R.id.goods_price);
            mGoodsMarket = (TextView) itemView.findViewById(R.id.goods_market);
            mGoodsSend = (TextView) itemView.findViewById(R.id.goods_send);
        }
    }
}
