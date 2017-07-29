package com.bhtc.huajuan.push.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.TextAppearanceSpan;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bhtc.huajuan.push.R;
import com.bhtc.huajuan.push.bean.WSMessageBean;
import com.bhtc.huajuan.push.util.GlideManager;
import com.bhtc.huajuan.push.util.UIUtils;

import java.util.List;

/**
 * Created by kouxiongfei on 2017/5/16.
 */

public class WebSocketMsgAdapter extends RecyclerView.Adapter {
    private final int SEND_MESSAGE = 1;   //普通消息
    private final int STEP_IN_CHANNEL = 2;   //进入直播间 关注
    private final int GOODS_INFO = 3;    //商品信息

    public static final int MSG_LEFT = 11;     //左边用户消息
    public static final int MSG_RIGHT = 12;    //右边客服消息

    private int msgLeftOrRight;
    private Activity activity;
    private List<WSMessageBean> messageList;

    public WebSocketMsgAdapter(Activity activity, List<WSMessageBean> messageList, int msgLeftOrRight) {
        this.activity = activity;
        this.messageList = messageList;
        this.msgLeftOrRight = msgLeftOrRight;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case SEND_MESSAGE:
                return new SendMsgHolder(UIUtils.inflate(activity, R.layout.send_message_item));
            case STEP_IN_CHANNEL:
                return new StepInChannelOrConcernHolder(UIUtils.inflate(activity, R.layout.set_in_channel_item));
            case GOODS_INFO:
                return new GoodsInfoHolder(UIUtils.inflate(activity, R.layout.goods_info_layout));
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        WSMessageBean wsMessageBean = messageList.get(position);
        if (wsMessageBean == null || wsMessageBean.getAction_data() == null) {
            return;
        }
//        WSMessageBean.MessageData actionData = wsMessageBean.getAction_data();
        switch (getItemViewType(position)) {
            case SEND_MESSAGE:
                sendMsgData((SendMsgHolder) holder, wsMessageBean);
                break;
            case STEP_IN_CHANNEL:
                stepInOrConcernData((StepInChannelOrConcernHolder) holder, wsMessageBean);
                break;
            case GOODS_INFO:
                goodsData((GoodsInfoHolder) holder, wsMessageBean);
                break;
        }
    }

    // 商品消息
    private void goodsData(GoodsInfoHolder goodsInfoHolder, WSMessageBean wsMessageBean) {
        WSMessageBean.MessageData actionData = wsMessageBean.getAction_data();
        GlideManager.getInstance().loadImage(activity, actionData.getGoods_image(), goodsInfoHolder.mGoodsImage);
        goodsInfoHolder.mGoodsName.setText(actionData.getGoods_name());
        goodsInfoHolder.mGoodsPrice.setText("￥" + actionData.getGoods_price());

        goodsInfoHolder.mGoodsQiang.setVisibility(wsMessageBean.getAction_type().equals(WSMessageBean.SHOW_GRAB_BUTTON) ? View.VISIBLE : View.GONE);
    }

    // 进入直播间或关注消息
    private void stepInOrConcernData(StepInChannelOrConcernHolder stepInChannelOrConcernHolder, WSMessageBean wsMessageBean) {
        WSMessageBean.MessageData action_data = wsMessageBean.getAction_data();
        if (WSMessageBean.STEP_IN_CHANNEL.equals(wsMessageBean.getAction_type())) {

            String userNum = "";
            if (!UIUtils.isEmpty(action_data.getCur_user_num())) {
                try {
                    int cur_user_num = Integer.parseInt(action_data.getCur_user_num());
                    if (cur_user_num > 1) {
                        userNum = "等" + cur_user_num + "人";
                    } else {
                        userNum = "";
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            String userNameStepIn = action_data.getUser_name() + " " + userNum + "进入了直播间。";
            SpannableString styledTextIn = new SpannableString(userNameStepIn);
            styledTextIn.setSpan(new TextAppearanceSpan(activity, R.style.user_name_ffffff), 0, action_data.getUser_name() != null ? action_data.getUser_name().length() : 0, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            stepInChannelOrConcernHolder.mUserNameMsg.setText(styledTextIn, TextView.BufferType.SPANNABLE);
        } else if (WSMessageBean.FOLLOW.equals(wsMessageBean.getAction_type())) {
            String userNameStepIn = action_data.getUser_name() + " 关注了主播 " + action_data.getFuname() + " 的小铺";
            SpannableString styledTextIn = new SpannableString(userNameStepIn);
            styledTextIn.setSpan(new TextAppearanceSpan(activity, R.style.user_name_ffffff), 0, action_data.getUser_name() != null ? action_data.getUser_name().length() : 0, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            styledTextIn.setSpan(new TextAppearanceSpan(activity, R.style.user_name_ffffff), userNameStepIn.length() - action_data.getFuname().length() - 4, userNameStepIn.length() - 4, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            stepInChannelOrConcernHolder.mUserNameMsg.setText(styledTextIn, TextView.BufferType.SPANNABLE);
        }
    }

    // 用户或客服发送的消息
    private void sendMsgData(SendMsgHolder sendMsgHolder, WSMessageBean wsMessageBean) {
        WSMessageBean.MessageData actionData = wsMessageBean.getAction_data();
        if (msgLeftOrRight == MSG_LEFT) {
            sendMsgHolder.mHJName.setVisibility(View.GONE);
            LinearLayout.LayoutParams layoutParams1 = (LinearLayout.LayoutParams) sendMsgHolder.mUserMsgLayout.getLayoutParams();
            layoutParams1.gravity = Gravity.LEFT;
            layoutParams1.width = LinearLayout.LayoutParams.WRAP_CONTENT;
            layoutParams1.height = LinearLayout.LayoutParams.WRAP_CONTENT;
            sendMsgHolder.mUserMsgLayout.setLayoutParams(layoutParams1);

            sendMsgHolder.mUserMsgLayout.setBackgroundDrawable(UIUtils.getDrawable(R.drawable.msg_default_left));
            LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) sendMsgHolder.mUserNameMsg.getLayoutParams();
            layoutParams.topMargin = UIUtils.dp2px(8);
            sendMsgHolder.mUserNameMsg.setLayoutParams(layoutParams);
            String userName = "";
            if (UIUtils.isEmpty(actionData.getTo_user_name())) {
                userName = !wsMessageBean.getAction_type().equals(WSMessageBean.BLOCK) ? actionData.getUser_name() : actionData.getBlock_user_name();
            } else {
                userName = !wsMessageBean.getAction_type().equals(WSMessageBean.BLOCK) ? actionData.getUser_name() + " @" + actionData.getTo_user_name() : actionData.getBlock_user_name();
            }
            String userNameMsg = userName + " " + actionData.getMsg();
            SpannableString styledText = new SpannableString(userNameMsg);
            styledText.setSpan(new TextAppearanceSpan(activity, R.style.user_name_ff737e), 0, userName.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            sendMsgHolder.mUserNameMsg.setText(styledText, TextView.BufferType.SPANNABLE);
            sendMsgHolder.mUserNameMsg.setTextSize(12f);

        } else {
            sendMsgHolder.mHJName.setVisibility(View.GONE);
//            sendMsgHolder.mHJName.setVisibility(View.VISIBLE);
            LinearLayout.LayoutParams layoutParams1 = (LinearLayout.LayoutParams) sendMsgHolder.mUserMsgLayout.getLayoutParams();
            layoutParams1.gravity = Gravity.RIGHT;
            layoutParams1.width = UIUtils.dp2px(200);
            layoutParams1.height = UIUtils.dp2px(50);
            sendMsgHolder.mUserMsgLayout.setLayoutParams(layoutParams1);
            sendMsgHolder.mUserMsgLayout.setBackgroundDrawable(UIUtils.getDrawable(R.drawable.msg_default_right));
            LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) sendMsgHolder.mUserNameMsg.getLayoutParams();
//            layoutParams.topMargin = UIUtils.dp2px(2);
            layoutParams.topMargin = UIUtils.dp2px(8);
            sendMsgHolder.mUserNameMsg.setLayoutParams(layoutParams);
            sendMsgHolder.mUserNameMsg.setText(actionData.getMsg());
//            sendMsgHolder.mHJName.setText(actionData.getUser_name() + "：");
            sendMsgHolder.mUserNameMsg.setTextSize(15f);

            sendMsgHolder.mUserNameMsg.setGravity(Gravity.CENTER_VERTICAL);
            sendMsgHolder.mUserNameMsg.setEllipsize(TextUtils.TruncateAt.END);
            sendMsgHolder.mUserNameMsg.setLines(2);
        }
    }

    @Override
    public int getItemCount() {
        return messageList.size();
    }

    @Override
    public int getItemViewType(int position) {
        switch (messageList.get(position).getAction_type()) {
            case WSMessageBean.STEP_IN_CHANNEL:
            case WSMessageBean.STEP_OUT_CHANNEL:
            case WSMessageBean.FOLLOW:
                return STEP_IN_CHANNEL;
            case WSMessageBean.MSG:
            case WSMessageBean.ADMIN_MSG:
            case WSMessageBean.BLOCK:
                return SEND_MESSAGE;
            case WSMessageBean.SHOW_GOODS:
            case WSMessageBean.SHOW_GRAB_BUTTON:
                return GOODS_INFO;
        }
        return SEND_MESSAGE;
    }

    class SendMsgHolder extends RecyclerView.ViewHolder {

        private TextView mHJName;
        private TextView mUserNameMsg;
        private LinearLayout mUserMsgLayout;

        public SendMsgHolder(View itemView) {
            super(itemView);
            mHJName = (TextView) itemView.findViewById(R.id.hj_name);
            mUserNameMsg = (TextView) itemView.findViewById(R.id.user_name_msg);
            mUserMsgLayout = (LinearLayout) itemView.findViewById(R.id.user_msg_layout);
        }
    }

    class StepInChannelOrConcernHolder extends RecyclerView.ViewHolder {

        private TextView mUserNameMsg;

        public StepInChannelOrConcernHolder(View itemView) {
            super(itemView);
            mUserNameMsg = (TextView) itemView.findViewById(R.id.user_name_step_in);
        }
    }

    class GoodsInfoHolder extends RecyclerView.ViewHolder {

        private TextView mGoodsName;
        private TextView mGoodsPrice;
        private ImageView mGoodsImage;
        private ImageView mGoodsQiang;

        public GoodsInfoHolder(View itemView) {
            super(itemView);
            mGoodsName = (TextView) itemView.findViewById(R.id.goods_name);
            mGoodsPrice = (TextView) itemView.findViewById(R.id.goods_price);
            mGoodsImage = (ImageView) itemView.findViewById(R.id.goods_image);
            mGoodsQiang = (ImageView) itemView.findViewById(R.id.qiang_image);
        }
    }
}
