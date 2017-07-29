package com.bhtc.huajuan.push.adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.TextAppearanceSpan;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bhtc.huajuan.push.R;
import com.bhtc.huajuan.push.bean.WSMessageBean;
import com.bhtc.huajuan.push.util.GlideManager;
import com.bhtc.huajuan.push.util.UIUtils;

import java.util.List;

/**
 * Created by kouxiongfei on 2017/5/18.
 */

public class TextListAdapter extends RecyclerView.Adapter {

    public static int USER = 1;
    public static int HUAJUAN = 2;
    private int mCurrentType;
    private Context mContext;
    private List<WSMessageBean> messageList;

    private int TEXT = 3;
    private int GOODS = 4;

    public TextListAdapter(Context context, int type, List<WSMessageBean> messageList) {
        this.mCurrentType = type;
        this.mContext = context;
        this.messageList = messageList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TEXT) {
            return new MyViewHolder(UIUtils.inflate(mContext, R.layout.text_list_item));
        } else {
            return new MyGoodsHolder(UIUtils.inflate(mContext, R.layout.text_list_item_show_goods));
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (messageList.get(position).getAction_type().equals(WSMessageBean.SHOW_GOODS) || messageList.get(position).getAction_type().equals(WSMessageBean.SHOW_GRAB_BUTTON)) {
            return GOODS;
        }
        return TEXT;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (getItemViewType(position) == TEXT) {
            MyViewHolder viewHolder = (MyViewHolder) holder;
            if (messageList == null || messageList.get(position) == null) {
                return;
            }
            WSMessageBean wsMessageBean = messageList.get(position);
            WSMessageBean.MessageData actionData = wsMessageBean.getAction_data();
            viewHolder.mName.setText(actionData.getUser_name());
            if (mCurrentType == USER) {
                viewHolder.mName.setTextColor(Color.parseColor("#ff737e"));
                switch (wsMessageBean.getAction_type()) {
                    case WSMessageBean.MSG:
                        viewHolder.mName.setText(actionData.getUser_name() + (UIUtils.isEmpty(actionData.getTo_user_name()) ? "" : " @" + actionData.getTo_user_name()));
                        viewHolder.mText.setTextColor(Color.parseColor("#000000"));
                        viewHolder.mText.setText(actionData.getMsg());
                        break;
                    case WSMessageBean.BLOCK:
                        viewHolder.mName.setText(actionData.getBlock_user_name());
                        viewHolder.mText.setTextColor(Color.parseColor("#000000"));
                        viewHolder.mText.setText(actionData.getMsg());
                        break;
                    case WSMessageBean.FOLLOW:
                        viewHolder.mText.setTextColor(Color.parseColor("#a9a9a9"));
                        String text = "关注了主播 " + actionData.getFuname() + " 的小铺";

                        SpannableString styledTextIn = new SpannableString(text);
                        styledTextIn.setSpan(new TextAppearanceSpan(mContext, R.style.user_name_ff737e), text.length() - actionData.getFuname().length() - 4, text.length() - 4, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                        viewHolder.mText.setText(styledTextIn, TextView.BufferType.SPANNABLE);
                        break;
                    case WSMessageBean.STEP_IN_CHANNEL:
                        String userNum = "";
                        if (!UIUtils.isEmpty(actionData.getCur_user_num())) {
                            try {
                                int cur_user_num = Integer.parseInt(actionData.getCur_user_num());
                                if (cur_user_num > 1) {
                                    userNum = "等" + cur_user_num + "人";
                                } else {
                                    userNum = "";
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                        viewHolder.mText.setTextColor(Color.parseColor("#a9a9a9"));
                        viewHolder.mText.setText(userNum + "进入了直播间。");
                        break;
                }
            } else if (mCurrentType == HUAJUAN) {
                viewHolder.mName.setTextColor(Color.parseColor("#4d4d4d"));
                viewHolder.mText.setTextColor(Color.parseColor("#4d4d4d"));
                viewHolder.mText.setText(actionData.getMsg());
            }
        } else {
            MyGoodsHolder viewHolder = (MyGoodsHolder) holder;
            if (messageList == null || messageList.get(position) == null) {
                return;
            }
            WSMessageBean wsMessageBean = messageList.get(position);
            if (mCurrentType == HUAJUAN) {
                GlideManager.getInstance().loadImage(mContext, wsMessageBean.getAction_data().getGoods_image(), viewHolder.mGoodsImage);
                viewHolder.mGoodsName.setText(wsMessageBean.getAction_data().getGoods_name());
                viewHolder.mGoodsPrice.setText("￥" + wsMessageBean.getAction_data().getGoods_price());
                viewHolder.mGoodsMarketPrice.setText("￥" + wsMessageBean.getAction_data().getGoods_marketprice());
                viewHolder.mGoodsMarketPrice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG | Paint.ANTI_ALIAS_FLAG);

                viewHolder.mGoodsQiang.setVisibility(wsMessageBean.getAction_type().equals(WSMessageBean.SHOW_GRAB_BUTTON) ? View.VISIBLE : View.GONE);
                viewHolder.mGoodsMarketPrice.setVisibility(wsMessageBean.getAction_type().equals(WSMessageBean.SHOW_GRAB_BUTTON) ? View.GONE : View.VISIBLE);
            }
        }
    }

    @Override
    public int getItemCount() {
        return messageList == null ? 0 : messageList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView mName;
        private TextView mText;

        public MyViewHolder(View itemView) {
            super(itemView);
            mName = (TextView) itemView.findViewById(R.id.user_name);
            mText = (TextView) itemView.findViewById(R.id.user_text);
        }
    }

    class MyGoodsHolder extends RecyclerView.ViewHolder {

        private TextView mGoodsName;
        private TextView mGoodsPrice;
        private TextView mGoodsMarketPrice;
        private ImageView mGoodsImage;
        private ImageView mGoodsQiang;

        public MyGoodsHolder(View itemView) {
            super(itemView);
            mGoodsName = (TextView) itemView.findViewById(R.id.goods_name);
            mGoodsPrice = (TextView) itemView.findViewById(R.id.goods_price);
            mGoodsMarketPrice = (TextView) itemView.findViewById(R.id.goods_market_price);
            mGoodsImage = (ImageView) itemView.findViewById(R.id.goods_image);
            mGoodsQiang = (ImageView) itemView.findViewById(R.id.goods_image_qiang);
        }
    }
}
