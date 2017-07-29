package com.bhtc.huajuan.push.bean;

import android.os.Parcel;
import android.os.Parcelable;

public class UserInfoBean implements Parcelable {
    private String add_time;
    private String client_id;
    private String is_allowtalk;
    private String is_buy;
    private String is_hongren;//是否是红人，1是，0不是 ,
    private String last_update_time;
    private String login_dev;
    private String login_platform;
    private String uid;
    private String user_areaid;
    private String user_avatar;  //头像
    private String user_birthday;//用户生日 ,
    private String user_cityid;
    private String user_login_ip;
    private String user_login_num;
    private String user_login_time;
    private String user_mobile;
    private String user_name;
    private String user_old_login_ip;
    private String user_old_login_time;
    private String user_provinceid;
    private String user_sex;
    private String user_state;
    private String user_truename;
    private int is_reg_easemob;//是否注册环信，1注册，0未注册 ,
    private String hongren_desc;
    private int isFollow;
    private String user_type;//用户类型，normal为普通用户，vip为VIP用户 ,
    private String fansCount;//用户的粉丝数量 ,
    private String videoCount;//用户关联的视频数量 ,
    private String constellation;//用户星座  ,
    private String recommend_desc;//关注引导页面 用户描述信息  ,
    private String age;//用户年龄 ,
    private int score;//用户花红 ,
    private int isGetCoupon;//是否可以领取优惠券，1表示可以领取，0表示不能领取
    private String isNewGoods;    //是否为上新标签 1 ：有 0：无
    private String hongren_number;   //红人小铺编号
    private float make_money;   //赚了多少钱
    private int online_count;   //录制多少卷播

    public UserInfoBean() {
    }


    protected UserInfoBean(Parcel in) {
        add_time = in.readString();
        client_id = in.readString();
        is_allowtalk = in.readString();
        is_buy = in.readString();
        is_hongren = in.readString();
        last_update_time = in.readString();
        login_dev = in.readString();
        login_platform = in.readString();
        uid = in.readString();
        user_areaid = in.readString();
        user_avatar = in.readString();
        user_birthday = in.readString();
        user_cityid = in.readString();
        user_login_ip = in.readString();
        user_login_num = in.readString();
        user_login_time = in.readString();
        user_mobile = in.readString();
        user_name = in.readString();
        user_old_login_ip = in.readString();
        user_old_login_time = in.readString();
        user_provinceid = in.readString();
        user_sex = in.readString();
        user_state = in.readString();
        user_truename = in.readString();
        is_reg_easemob = in.readInt();
        hongren_desc = in.readString();
        isFollow = in.readInt();
        user_type = in.readString();
        fansCount = in.readString();
        videoCount = in.readString();
        constellation = in.readString();
        recommend_desc = in.readString();
        age = in.readString();
        score = in.readInt();
        isGetCoupon = in.readInt();
        isNewGoods = in.readString();
        hongren_number = in.readString();
        make_money = in.readFloat();
        online_count = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(add_time);
        dest.writeString(client_id);
        dest.writeString(is_allowtalk);
        dest.writeString(is_buy);
        dest.writeString(is_hongren);
        dest.writeString(last_update_time);
        dest.writeString(login_dev);
        dest.writeString(login_platform);
        dest.writeString(uid);
        dest.writeString(user_areaid);
        dest.writeString(user_avatar);
        dest.writeString(user_birthday);
        dest.writeString(user_cityid);
        dest.writeString(user_login_ip);
        dest.writeString(user_login_num);
        dest.writeString(user_login_time);
        dest.writeString(user_mobile);
        dest.writeString(user_name);
        dest.writeString(user_old_login_ip);
        dest.writeString(user_old_login_time);
        dest.writeString(user_provinceid);
        dest.writeString(user_sex);
        dest.writeString(user_state);
        dest.writeString(user_truename);
        dest.writeInt(is_reg_easemob);
        dest.writeString(hongren_desc);
        dest.writeInt(isFollow);
        dest.writeString(user_type);
        dest.writeString(fansCount);
        dest.writeString(videoCount);
        dest.writeString(constellation);
        dest.writeString(recommend_desc);
        dest.writeString(age);
        dest.writeInt(score);
        dest.writeInt(isGetCoupon);
        dest.writeString(isNewGoods);
        dest.writeString(hongren_number);
        dest.writeFloat(make_money);
        dest.writeInt(online_count);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<UserInfoBean> CREATOR = new Creator<UserInfoBean>() {
        @Override
        public UserInfoBean createFromParcel(Parcel in) {
            return new UserInfoBean(in);
        }

        @Override
        public UserInfoBean[] newArray(int size) {
            return new UserInfoBean[size];
        }
    };

    public String getHongren_number() {
        return hongren_number;
    }

    public void setHongren_number(String hongren_number) {
        this.hongren_number = hongren_number;
    }

    public String getIsNewGoods() {
        return isNewGoods;
    }

    public void setIsNewGoods(String isNewGoods) {
        this.isNewGoods = isNewGoods;
    }

    public String getRecommend_desc() {
        return recommend_desc;
    }

    public void setRecommend_desc(String recommend_desc) {
        this.recommend_desc = recommend_desc;
    }

    public int getIsGetCoupon() {
        return isGetCoupon;
    }

    public void setIsGetCoupon(int isGetCoupon) {
        this.isGetCoupon = isGetCoupon;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getFansCount() {
        return fansCount;
    }

    public void setFansCount(String fansCount) {
        this.fansCount = fansCount;
    }

    public String getVideoCount() {
        return videoCount;
    }

    public void setVideoCount(String videoCount) {
        this.videoCount = videoCount;
    }

    public String getConstellation() {
        return constellation;
    }

    public void setConstellation(String constellation) {
        this.constellation = constellation;
    }

    public String getUser_type() {
        return user_type;
    }

    public void setUser_type(String user_type) {
        this.user_type = user_type;
    }

    public int getIsFollow() {
        return isFollow;
    }

    public void setIsFollow(int isFollow) {
        this.isFollow = isFollow;
    }

    public String getAdd_time() {
        return add_time;
    }

    public UserInfoBean setAdd_time(String add_time) {
        this.add_time = add_time;
        return this;
    }

    public String getClient_id() {
        return client_id;
    }

    public UserInfoBean setClient_id(String client_id) {
        this.client_id = client_id;
        return this;
    }

    public String getIs_allowtalk() {
        return is_allowtalk;
    }

    public UserInfoBean setIs_allowtalk(String is_allowtalk) {
        this.is_allowtalk = is_allowtalk;
        return this;
    }

    public String getIs_buy() {
        return is_buy;
    }

    public UserInfoBean setIs_buy(String is_buy) {
        this.is_buy = is_buy;
        return this;
    }

    public String getIs_hongren() {
        return is_hongren;
    }

    public UserInfoBean setIs_hongren(String is_hongren) {
        this.is_hongren = is_hongren;
        return this;
    }

    public String getLast_update_time() {
        return last_update_time;
    }

    public UserInfoBean setLast_update_time(String last_update_time) {
        this.last_update_time = last_update_time;
        return this;
    }

    public String getLogin_dev() {
        return login_dev;
    }

    public UserInfoBean setLogin_dev(String login_dev) {
        this.login_dev = login_dev;
        return this;
    }

    public String getLogin_platform() {
        return login_platform;
    }

    public UserInfoBean setLogin_platform(String login_platform) {
        this.login_platform = login_platform;
        return this;
    }

    public String getUid() {
        return uid;
    }

    public UserInfoBean setUid(String uid) {
        this.uid = uid;
        return this;
    }

    public String getUser_areaid() {
        return user_areaid;
    }

    public UserInfoBean setUser_areaid(String user_areaid) {
        this.user_areaid = user_areaid;
        return this;
    }

    public String getUser_avatar() {
        return user_avatar;
    }

    public UserInfoBean setUser_avatar(String user_avatar) {
        this.user_avatar = user_avatar;
        return this;
    }

    public String getUser_birthday() {
        return user_birthday;
    }

    public UserInfoBean setUser_birthday(String user_birthday) {
        this.user_birthday = user_birthday;
        return this;
    }

    public String getUser_cityid() {
        return user_cityid;
    }

    public UserInfoBean setUser_cityid(String user_cityid) {
        this.user_cityid = user_cityid;
        return this;
    }

    public String getUser_login_ip() {
        return user_login_ip;
    }

    public UserInfoBean setUser_login_ip(String user_login_ip) {
        this.user_login_ip = user_login_ip;
        return this;
    }

    public String getUser_login_num() {
        return user_login_num;
    }

    public UserInfoBean setUser_login_num(String user_login_num) {
        this.user_login_num = user_login_num;
        return this;
    }

    public String getUser_login_time() {
        return user_login_time;
    }

    public UserInfoBean setUser_login_time(String user_login_time) {
        this.user_login_time = user_login_time;
        return this;
    }

    public String getUser_mobile() {
        return user_mobile;
    }

    public UserInfoBean setUser_mobile(String user_mobile) {
        this.user_mobile = user_mobile;
        return this;
    }

    public String getUser_name() {
        return user_name;
    }

    public UserInfoBean setUser_name(String user_name) {
        this.user_name = user_name;
        return this;
    }

    public String getUser_old_login_ip() {
        return user_old_login_ip;
    }

    public UserInfoBean setUser_old_login_ip(String user_old_login_ip) {
        this.user_old_login_ip = user_old_login_ip;
        return this;
    }

    public String getUser_old_login_time() {
        return user_old_login_time;
    }

    public UserInfoBean setUser_old_login_time(String user_old_login_time) {
        this.user_old_login_time = user_old_login_time;
        return this;
    }

    public String getUser_provinceid() {
        return user_provinceid;
    }

    public UserInfoBean setUser_provinceid(String user_provinceid) {
        this.user_provinceid = user_provinceid;
        return this;
    }

    public String getUser_sex() {
        return user_sex;
    }

    public UserInfoBean setUser_sex(String user_sex) {
        this.user_sex = user_sex;
        return this;
    }

    public String getUser_state() {
        return user_state;
    }

    public UserInfoBean setUser_state(String user_state) {
        this.user_state = user_state;
        return this;
    }

    public String getUser_truename() {
        return user_truename;
    }

    public UserInfoBean setUser_truename(String user_truename) {
        this.user_truename = user_truename;
        return this;
    }

    public int getIs_reg_easemob() {
        return is_reg_easemob;
    }

    public void setIs_reg_easemob(int is_reg_easemob) {
        this.is_reg_easemob = is_reg_easemob;
    }

    public String getHongren_desc() {
        return hongren_desc;
    }

    public void setHongren_desc(String hongren_desc) {
        this.hongren_desc = hongren_desc;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public void setMake_money(float make_money) {
        this.make_money = make_money;
    }

    public int getOnline_count() {
        return online_count;
    }

    public void setOnline_count(int online_count) {
        this.online_count = online_count;
    }

}
