package com.bhtc.huajuan.push.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * 直播bean
 */

public class LiveBean implements Parcelable {
    /*
     "id": "15",
        "name": "第一次添加",
        "hr_id": "199",
        "plan_start_time": "1495003320",
        "plan_end_time": "1495089720",
        "start_time": null,
        "end_time": "0",
        "hr_cover": "http://test.static.huajuanmall.com/subject/o_1bgaia9021mvv1cl0613iqn1pj17.png",
        "add_id": "95921809950353",
        "set_id": "0",
        "command": "",
        "add_time": "1495003381",
        "hongren_number": "1088",
        "hongren_uid": "97580662770614",
        "hongren_desc": "一直播美女主播",
        "hr_user_name": "Tina是个小妹妹——",
        "hr_user_avatar": "http://static.huajuanmall.com/user/qrcode/o_1bbo663abumd1fka1n9p1odk1ag7f.jpg",
        "watching_number": "125"
     */
    private String hr_user_name;
    private String watching_number;
    private String add_id;
    private String set_id;
    private String command;
    private String start_time;
    private String status;
    private String plan_end_time;
    private String hongren_desc;
    private String plan_start_time;
    private String hr_cover;
    private String id;
    private String hr_id;
    private String end_time;
    private String hongren_number;
    private String hongren_uid;
    private String name;
    private String hr_user_avatar;
    private String add_time;
    private String max_second;
    private String min_second;
    private String operator;
    private List<UserInfoBean> user;

    protected LiveBean(Parcel in) {
        hr_user_name = in.readString();
        watching_number = in.readString();
        add_id = in.readString();
        set_id = in.readString();
        command = in.readString();
        start_time = in.readString();
        status = in.readString();
        plan_end_time = in.readString();
        hongren_desc = in.readString();
        plan_start_time = in.readString();
        hr_cover = in.readString();
        id = in.readString();
        hr_id = in.readString();
        end_time = in.readString();
        hongren_number = in.readString();
        hongren_uid = in.readString();
        name = in.readString();
        hr_user_avatar = in.readString();
        add_time = in.readString();
        max_second = in.readString();
        min_second = in.readString();
        operator = in.readString();
        user = in.createTypedArrayList(UserInfoBean.CREATOR);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(hr_user_name);
        dest.writeString(watching_number);
        dest.writeString(add_id);
        dest.writeString(set_id);
        dest.writeString(command);
        dest.writeString(start_time);
        dest.writeString(status);
        dest.writeString(plan_end_time);
        dest.writeString(hongren_desc);
        dest.writeString(plan_start_time);
        dest.writeString(hr_cover);
        dest.writeString(id);
        dest.writeString(hr_id);
        dest.writeString(end_time);
        dest.writeString(hongren_number);
        dest.writeString(hongren_uid);
        dest.writeString(name);
        dest.writeString(hr_user_avatar);
        dest.writeString(add_time);
        dest.writeString(max_second);
        dest.writeString(min_second);
        dest.writeString(operator);
        dest.writeTypedList(user);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<LiveBean> CREATOR = new Creator<LiveBean>() {
        @Override
        public LiveBean createFromParcel(Parcel in) {
            return new LiveBean(in);
        }

        @Override
        public LiveBean[] newArray(int size) {
            return new LiveBean[size];
        }
    };

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public List<UserInfoBean> getUser() {
        return user;
    }

    public void setUser(List<UserInfoBean> user) {
        this.user = user;
    }

    public String getMax_second() {
        return max_second;
    }

    public void setMax_second(String max_second) {
        this.max_second = max_second;
    }

    public String getMin_second() {
        return min_second;
    }

    public void setMin_second(String min_second) {
        this.min_second = min_second;
    }

    public String getHr_user_name() {
        return hr_user_name;
    }

    public void setHr_user_name(String hr_user_name) {
        this.hr_user_name = hr_user_name;
    }

    public String getWatching_number() {
        return watching_number;
    }

    public void setWatching_number(String watching_number) {
        this.watching_number = watching_number;
    }

    public String getAdd_id() {
        return add_id;
    }

    public void setAdd_id(String add_id) {
        this.add_id = add_id;
    }

    public String getSet_id() {
        return set_id;
    }

    public void setSet_id(String set_id) {
        this.set_id = set_id;
    }

    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }

    public String getStart_time() {
        return start_time;
    }

    public void setStart_time(String start_time) {
        this.start_time = start_time;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPlan_end_time() {
        return plan_end_time;
    }

    public void setPlan_end_time(String plan_end_time) {
        this.plan_end_time = plan_end_time;
    }

    public String getHongren_desc() {
        return hongren_desc;
    }

    public void setHongren_desc(String hongren_desc) {
        this.hongren_desc = hongren_desc;
    }

    public String getPlan_start_time() {
        return plan_start_time;
    }

    public void setPlan_start_time(String plan_start_time) {
        this.plan_start_time = plan_start_time;
    }

    public String getHr_cover() {
        return hr_cover;
    }

    public void setHr_cover(String hr_cover) {
        this.hr_cover = hr_cover;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getHr_id() {
        return hr_id;
    }

    public void setHr_id(String hr_id) {
        this.hr_id = hr_id;
    }

    public String getEnd_time() {
        return end_time;
    }

    public void setEnd_time(String end_time) {
        this.end_time = end_time;
    }

    public String getHongren_number() {
        return hongren_number;
    }

    public void setHongren_number(String hongren_number) {
        this.hongren_number = hongren_number;
    }

    public String getHongren_uid() {
        return hongren_uid;
    }

    public void setHongren_uid(String hongren_uid) {
        this.hongren_uid = hongren_uid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHr_user_avatar() {
        return hr_user_avatar;
    }

    public void setHr_user_avatar(String hr_user_avatar) {
        this.hr_user_avatar = hr_user_avatar;
    }

    public String getAdd_time() {
        return add_time;
    }

    public void setAdd_time(String add_time) {
        this.add_time = add_time;
    }


}
