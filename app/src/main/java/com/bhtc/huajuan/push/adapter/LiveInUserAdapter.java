package com.bhtc.huajuan.push.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bhtc.huajuan.push.R;
import com.bhtc.huajuan.push.bean.UserInfoBean;
import com.bhtc.huajuan.push.util.GlideManager;
import com.bhtc.huajuan.push.util.UIUtils;

import java.util.List;

/**
 * 直播页的进入人头adapter
 * <P/>Created by Administrator on 2017/5/16.
 */
public class LiveInUserAdapter extends RecyclerView.Adapter {
    private Context mContext;
    private List<UserInfoBean> mList;
    private LayoutInflater mLayoutInflater;
    private String watchNumber;
    private int[] headers = {R.drawable.header_fen, R.drawable.header_huang, R.drawable.header_lv, R.drawable.header_zi};

    public LiveInUserAdapter(Context context, List<UserInfoBean> mList, String watchNumber) {
        this.mContext = context;
        this.mList = mList;
        this.watchNumber = watchNumber;
        mLayoutInflater = LayoutInflater.from(mContext);
    }

    public void setWatchNumberRemove(String watching_number, List<UserInfoBean> watch_user) {
        this.watchNumber = watching_number;
        if (watch_user != null) {
            mList = watch_user;
            notifyDataSetChanged();
        } else {
            notifyItemChanged(0);
        }
    }

    public void setWatchNumberAdd(String watching_number, List<UserInfoBean> watch_user) {
        this.watchNumber = watching_number;
        if (watch_user != null) {
            mList = watch_user;
            notifyDataSetChanged();
        } else {
            notifyItemChanged(0);
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return 1;
        }
        return 2;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder;
        if (viewType == 1) {
            viewHolder = new NumHolder(mLayoutInflater.inflate(R.layout.item_live_in_user_num, parent, false));
        } else {
            viewHolder = new HeaderHolder(mLayoutInflater.inflate(R.layout.item_live_in_user_header, parent, false));
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (position == 0) {
            NumHolder numHolder = (NumHolder) holder;
            String numString;
            long num = 0;
            try {
                num = Long.parseLong(watchNumber);
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (num < 4) {
                numString = "4";
            } else if (num > 9999) {
                long wan = num / 10000;
                long qian = (num % 10000) / 1000;
                numString = wan + "." + qian + "万";
            } else {
                numString = num + "";
            }
            numHolder.mUserNum.setText(UIUtils.getString(R.string.live_in_user_num, numString));
        } else {
            HeaderHolder headerHolder = (HeaderHolder) holder;
            UserInfoBean userInfoBean = null;
            int drowable = headers[position - 1];
            if (position == 1) {
                position = mList.size() - 1;
            } else if (position == 2) {
                position = mList.size() - 2;
            } else if (position == 3) {
                position = mList.size() - 3;
            } else {
                position = mList.size() - 4;
            }
            if (position >= 0) {
                userInfoBean = mList.get(position);
            }
            if (userInfoBean != null) {
                GlideManager.getInstance().loadLiveHeaderCircleImage(mContext, userInfoBean.getUser_avatar(), headerHolder.mUserHeader, drowable);
            } else {
                GlideManager.getInstance().loadLiveHeaderCircleImage(mContext, "", headerHolder.mUserHeader, drowable);
            }
        }
    }

    @Override
    public int getItemCount() {
        return 5;
    }

    class HeaderHolder extends RecyclerView.ViewHolder {
        private ImageView mUserHeader;

        public HeaderHolder(View itemView) {
            super(itemView);
            mUserHeader = (ImageView) itemView.findViewById(R.id.item_live_user_header);
        }
    }

    class NumHolder extends RecyclerView.ViewHolder {
        private TextView mUserNum;

        public NumHolder(View itemView) {
            super(itemView);
            mUserNum = (TextView) itemView.findViewById(R.id.item_live_user_num);
        }
    }

}
