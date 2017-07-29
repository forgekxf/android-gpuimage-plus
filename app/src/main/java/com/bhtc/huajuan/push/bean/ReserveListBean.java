package com.bhtc.huajuan.push.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by kouxiongfei on 2017/6/5.
 */
public class ReserveListBean extends BaseBean implements Parcelable {
    private List<LiveBean> lives;
    private LiveBean cur_live;

    protected ReserveListBean(Parcel in) {
        lives = in.createTypedArrayList(LiveBean.CREATOR);
        cur_live = in.readParcelable(LiveBean.class.getClassLoader());
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(lives);
        dest.writeParcelable(cur_live, flags);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<ReserveListBean> CREATOR = new Creator<ReserveListBean>() {
        @Override
        public ReserveListBean createFromParcel(Parcel in) {
            return new ReserveListBean(in);
        }

        @Override
        public ReserveListBean[] newArray(int size) {
            return new ReserveListBean[size];
        }
    };

    public LiveBean getCur_live() {
        return cur_live;
    }

    public void setCur_live(LiveBean cur_live) {
        this.cur_live = cur_live;
    }

    public List<LiveBean> getLives() {
        return lives;
    }

    public void setLives(List<LiveBean> lives) {
        this.lives = lives;
    }
}
