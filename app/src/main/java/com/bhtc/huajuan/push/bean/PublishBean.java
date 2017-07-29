package com.bhtc.huajuan.push.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by forge on 2017/1/23.
 */
public class PublishBean  implements Parcelable {
    private String rtmp_publish_url;
    private String rtmp_play_url;
    private String stream_key;

    protected PublishBean(Parcel in) {
        rtmp_publish_url = in.readString();
        rtmp_play_url = in.readString();
        stream_key = in.readString();
    }

    public static final Creator<PublishBean> CREATOR = new Creator<PublishBean>() {
        @Override
        public PublishBean createFromParcel(Parcel in) {
            return new PublishBean(in);
        }

        @Override
        public PublishBean[] newArray(int size) {
            return new PublishBean[size];
        }
    };

    public String getRtmp_play_url() {
        return rtmp_play_url;
    }

    public void setRtmp_play_url(String rtmp_play_url) {
        this.rtmp_play_url = rtmp_play_url;
    }

    public String getRtmp_publish_url() {
        return rtmp_publish_url;
    }

    public void setRtmp_publish_url(String rtmp_publish_url) {
        this.rtmp_publish_url = rtmp_publish_url;
    }

    public String getStream_key() {
        return stream_key;
    }

    public void setStream_key(String stream_key) {
        this.stream_key = stream_key;
    }

    @Override
    public String toString() {
        return "PublishBean{" +
                "rtmp_publish_url='" + rtmp_publish_url + '\'' +
                ", rtmp_play_url='" + rtmp_play_url + '\'' +
                ", stream_key='" + stream_key + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(rtmp_publish_url);
        dest.writeString(rtmp_play_url);
        dest.writeString(stream_key);
    }
}
