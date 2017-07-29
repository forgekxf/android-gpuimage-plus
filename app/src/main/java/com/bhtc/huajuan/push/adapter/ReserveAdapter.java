package com.bhtc.huajuan.push.adapter;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bhtc.huajuan.push.R;
import com.bhtc.huajuan.push.activity.ReserveListActivity;
import com.bhtc.huajuan.push.bean.LiveBean;
import com.bhtc.huajuan.push.util.GlideManager;
import com.bhtc.huajuan.push.util.TimeCount;
import com.bhtc.huajuan.push.util.UIUtils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by kouxiongfei on 2017/6/5.
 */

public class ReserveAdapter extends RecyclerView.Adapter {

    private List<LiveBean> lives;
    private ReserveListActivity reserveListActivity;
    public boolean havaStart;

    public ReserveAdapter(ReserveListActivity reserveListActivity, List<LiveBean> lives) {
        this.reserveListActivity = reserveListActivity;
        this.lives = lives;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new Holder(UIUtils.inflate(reserveListActivity, R.layout.live_frag_item));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Holder viewHolder = (Holder) holder;
        if (lives.size() == 0 || lives.get(position) == null) {
            return;
        }
        LiveBean liveBean = lives.get(position);
        if (liveBean == null) {
            return;
        }

        viewHolder.mTitleTime.setBackgroundColor(Color.parseColor("#ff737e"));
        viewHolder.mTitleTime.setTextColor(Color.parseColor("#ffffff"));
        viewHolder.mPushLayout.setVisibility(View.VISIBLE);

        viewHolder.mLookVideo.setOnClickListener(reserveListActivity);
        viewHolder.mLookVideo.setTag(R.id.item_position, liveBean);

        viewHolder.mStartPush.setOnClickListener(reserveListActivity);
        viewHolder.mStartPush.setTag(R.id.item_position, liveBean);

        viewHolder.mPushLayout.setVisibility(View.VISIBLE);
        switch (liveBean.getStatus()) {
            case "0":
                viewHolder.mLookVideo.setBackgroundResource(R.drawable.login_icon_not_click);
                viewHolder.mStartPush.setBackgroundResource(R.drawable.login_icon);
                viewHolder.mStartPush.setText(UIUtils.getString(R.string.ive_start_push));
                viewHolder.mLookVideo.setTextColor(Color.parseColor("#d8d8d8"));
                viewHolder.mStartPush.setTextColor(Color.parseColor("#ff737e"));
                break;
            case "1":
                viewHolder.mLookVideo.setBackgroundResource(R.drawable.login_icon);
                viewHolder.mStartPush.setBackgroundResource(R.drawable.login_icon_not_click);
                viewHolder.mStartPush.setText(UIUtils.getString(R.string.ive_start_push));
                viewHolder.mLookVideo.setTextColor(Color.parseColor("#ff737e"));
                viewHolder.mStartPush.setTextColor(Color.parseColor("#d8d8d8"));
                break;
            case "2":
                viewHolder.mLookVideo.setBackgroundResource(R.drawable.login_icon_not_click);
                viewHolder.mStartPush.setBackgroundResource(R.drawable.login_icon_not_click);
                viewHolder.mStartPush.setText(UIUtils.getString(R.string.ive_start_push));
                viewHolder.mLookVideo.setTextColor(Color.parseColor("#d8d8d8"));
                viewHolder.mStartPush.setTextColor(Color.parseColor("#d8d8d8"));
                break;
            case "4":
                viewHolder.mLookVideo.setBackgroundResource(R.drawable.login_icon_not_click);
                viewHolder.mStartPush.setBackgroundResource(R.drawable.login_icon);
                viewHolder.mStartPush.setText(UIUtils.getString(R.string.ive_restart_push));
                viewHolder.mLookVideo.setTextColor(Color.parseColor("#d8d8d8"));
                viewHolder.mStartPush.setTextColor(Color.parseColor("#ff737e"));
                break;
            case "-1":
                viewHolder.mPushLayout.setVisibility(View.GONE);
                break;
        }

        viewHolder.mLookVideo.setOnClickListener(reserveListActivity);
        viewHolder.mLookVideo.setTag(R.id.item_position, liveBean);

        viewHolder.mStartPush.setOnClickListener(reserveListActivity);
        viewHolder.mStartPush.setTag(R.id.item_position, liveBean);

        if (havaStart && position == 0) {
            viewHolder.mTitleTime.setBackgroundColor(Color.parseColor("#ff737e"));
            viewHolder.mTitleTime.setTextColor(Color.parseColor("#ffffff"));
        } else {
            viewHolder.mTitleTime.setBackgroundColor(Color.parseColor("#ffffff"));
            viewHolder.mTitleTime.setTextColor(Color.parseColor("#262626"));
        }

        long offsetTimeStart = Long.parseLong(liveBean.getPlan_start_time()) - (System.currentTimeMillis() / 1000);
        long offsetTimeEnd = Long.parseLong(liveBean.getPlan_end_time()) - (System.currentTimeMillis() / 1000);
        if (offsetTimeStart > 24 * 60 * 60) {
            viewHolder.mTitleTime.setVisibility(View.VISIBLE);
            viewHolder.mTitleTime.setText(UIUtils.getString(R.string.start_time) + stampToDate(liveBean.getPlan_start_time()) + " - " + stampToDateEnd(liveBean.getPlan_end_time()));
        } else {
            if (offsetTimeStart < 0 && offsetTimeEnd > 0) {
                viewHolder.mTitleTime.setVisibility(View.VISIBLE);
                viewHolder.mTitleTime.setText(UIUtils.getString(R.string.start_time) + stampToDate(liveBean.getPlan_start_time()) + " - " + stampToDateEnd(liveBean.getPlan_end_time()));
            } else {
                if (Long.parseLong(liveBean.getPlan_start_time()) * 1000 < System.currentTimeMillis()) {
                    if (havaStart && position == 0) {
                        viewHolder.mTitleTime.setVisibility(View.VISIBLE);
                        viewHolder.mTitleTime.setText(UIUtils.getString(R.string.time_out) + "," + UIUtils.getString(R.string.please_start_live));
                    } else {
                        viewHolder.mTitleTime.setVisibility(View.GONE);
                    }
                } else {
                    viewHolder.mTitleTime.setVisibility(View.VISIBLE);
                    TimeCount clock;
                    if (reserveListActivity.timeCountList.get(position) == null) {
                        clock = new TimeCount(reserveListActivity, viewHolder.mTitleTime, Long.parseLong(liveBean.getPlan_start_time()) * 1000 - System.currentTimeMillis(), 1000l);
                        reserveListActivity.timeCountList.put(position, clock);
                    } else {
                        clock = reserveListActivity.timeCountList.get(position);
                    }
                    clock.setText(UIUtils.getString(R.string.start_time_in));
                    clock.start();
                }
            }
        }

        GlideManager.getInstance().loadImage(reserveListActivity, liveBean.getHr_cover(), viewHolder.mLiveImage);
        ViewGroup.LayoutParams layoutParams = viewHolder.mLiveImage.getLayoutParams();
        layoutParams.height = layoutParams.width / 4 * 3;
        viewHolder.mLiveImage.setLayoutParams(layoutParams);
        viewHolder.mLiveImageBg.setLayoutParams(layoutParams);

        viewHolder.mTitleName.setText(liveBean.getName());

        viewHolder.mLiveUserIconOne.setVisibility(View.GONE);
        viewHolder.mLiveUserIconTwo.setVisibility(View.GONE);
        viewHolder.mLiveUserIconThree.setVisibility(View.GONE);
        viewHolder.mLiveUserIconFour.setVisibility(View.GONE);

        if (liveBean.getUser() != null && liveBean.getUser().size() > 0) {
            if (liveBean.getUser().size() > 1) {
                viewHolder.mUserName.setVisibility(View.GONE);
                for (int i = 0; i < liveBean.getUser().size(); i++) {
                    switch (i) {
                        case 0:
                            viewHolder.mLiveUserIconOne.setVisibility(View.VISIBLE);
                            GlideManager.getInstance().loadCircleImg(reserveListActivity, liveBean.getUser().get(i).getUser_avatar(), viewHolder.mLiveUserIconOne);
                            break;
                        case 1:
                            viewHolder.mLiveUserIconTwo.setVisibility(View.VISIBLE);
                            GlideManager.getInstance().loadCircleImg(reserveListActivity, liveBean.getUser().get(i).getUser_avatar(), viewHolder.mLiveUserIconTwo);
                            break;
                        case 2:
                            viewHolder.mLiveUserIconThree.setVisibility(View.VISIBLE);
                            GlideManager.getInstance().loadCircleImg(reserveListActivity, liveBean.getUser().get(i).getUser_avatar(), viewHolder.mLiveUserIconThree);
                            break;
                        case 3:
                            viewHolder.mLiveUserIconFour.setVisibility(View.VISIBLE);
                            GlideManager.getInstance().loadCircleImg(reserveListActivity, liveBean.getUser().get(i).getUser_avatar(), viewHolder.mLiveUserIconFour);
                            break;
                    }
                }
            } else {
                viewHolder.mLiveUserIconOne.setVisibility(View.VISIBLE);
                viewHolder.mLiveUserIconTwo.setVisibility(View.GONE);
                viewHolder.mLiveUserIconThree.setVisibility(View.GONE);
                viewHolder.mLiveUserIconFour.setVisibility(View.GONE);
                viewHolder.mUserName.setVisibility(View.VISIBLE);
                viewHolder.mUserName.setText(liveBean.getUser().get(0).getUser_name());
                GlideManager.getInstance().loadCircleImg(reserveListActivity, liveBean.getUser().get(0).getUser_avatar(), viewHolder.mLiveUserIconOne);
            }
        }
    }

    @Override
    public int getItemCount() {
        return lives.size();
    }

    public static String stampToDate(String s) {
        String res;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy.MM.dd HH:mm");
        long lt = new Long(s);
        Date date = new Date(lt * 1000);
        res = simpleDateFormat.format(date);
        return res;
    }

    public static String stampToDateEnd(String s) {
        String res;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm");
        long lt = new Long(s);
        Date date = new Date(lt * 1000);
        res = simpleDateFormat.format(date);
        return res;
    }

    class Holder extends RecyclerView.ViewHolder {

        private TextView mTitleTime;
        private TextView mTitleName;
        private TextView mUserName;
        private TextView mLookVideo;
        private TextView mStartPush;
        private ImageView mLiveImage;
        private ImageView mLiveImageBg;
        private ImageView mLiveUserIconOne;
        private ImageView mLiveUserIconTwo;
        private ImageView mLiveUserIconThree;
        private ImageView mLiveUserIconFour;
        private RelativeLayout mPushLayout;

        public Holder(View itemView) {
            super(itemView);
            mTitleTime = (TextView) itemView.findViewById(R.id.start_live_time);
            mTitleName = (TextView) itemView.findViewById(R.id.live_name);
            mUserName = (TextView) itemView.findViewById(R.id.item_live_user_one_name_tv);
            mLookVideo = (TextView) itemView.findViewById(R.id.look_video);
            mStartPush = (TextView) itemView.findViewById(R.id.start_push);
            mLiveImage = (ImageView) itemView.findViewById(R.id.live_image);
            mLiveImageBg = (ImageView) itemView.findViewById(R.id.live_bg_image);
            mLiveUserIconOne = (ImageView) itemView.findViewById(R.id.item_live_user_one_avatar_civ);
            mLiveUserIconTwo = (ImageView) itemView.findViewById(R.id.item_live_user_two_avatar_civ);
            mLiveUserIconThree = (ImageView) itemView.findViewById(R.id.item_live_user_three_avatar_civ);
            mLiveUserIconFour = (ImageView) itemView.findViewById(R.id.item_live_user_four_avatar_civ);
            mPushLayout = (RelativeLayout) itemView.findViewById(R.id.start_push_ing);
        }
    }
}
