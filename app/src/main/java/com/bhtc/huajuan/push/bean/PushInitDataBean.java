package com.bhtc.huajuan.push.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by kouxiongfei on 2017/5/17.
 */

public class PushInitDataBean extends BaseBean implements Parcelable{
    private LiveBean live;
    private PublishBean livestreaming;
    private List<UserInfoBean> watchingUser;
    private List<UserInfoBean> hongren;

    protected PushInitDataBean(Parcel in) {
        live = in.readParcelable(LiveBean.class.getClassLoader());
        livestreaming = in.readParcelable(PublishBean.class.getClassLoader());
        watchingUser = in.createTypedArrayList(UserInfoBean.CREATOR);
        hongren = in.createTypedArrayList(UserInfoBean.CREATOR);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(live, flags);
        dest.writeParcelable(livestreaming, flags);
        dest.writeTypedList(watchingUser);
        dest.writeTypedList(hongren);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<PushInitDataBean> CREATOR = new Creator<PushInitDataBean>() {
        @Override
        public PushInitDataBean createFromParcel(Parcel in) {
            return new PushInitDataBean(in);
        }

        @Override
        public PushInitDataBean[] newArray(int size) {
            return new PushInitDataBean[size];
        }
    };

    public List<UserInfoBean> getHongren() {
        return hongren;
    }

    public void setHongren(List<UserInfoBean> hongren) {
        this.hongren = hongren;
    }

    public List<UserInfoBean> getWatchingUser() {
        return watchingUser;
    }

    public void setWatchingUser(List<UserInfoBean> watchingUser) {
        this.watchingUser = watchingUser;
    }

    public LiveBean getLive() {
        return live;
    }

    public void setLive(LiveBean live) {
        this.live = live;
    }

    public PublishBean getLivestreaming() {
        return livestreaming;
    }

    public void setLivestreaming(PublishBean livestreaming) {
        this.livestreaming = livestreaming;
    }
}
