package com.bhtc.huajuan.push.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * WS 发送消息bean
 */

public class WSMessageBean extends BaseBean implements Parcelable{

    public static int CHANNEL_NOT_EXIST = 500005;  //直播频道不存在
    public static int WEBSOCKET_WORKERSTOP = 500008;
    public static int WEBSOCKET_SHUTDOWN = 500007;
    public static int WEBSOCKET_MANAGERSTOP = 500009;
    public static int PING_SUCCESS = 200;

    public static String CHANNEL_TESTING = "testlive3_20";   //测试
    public static final String STEP_IN_CHANNEL = "stepInChannel";   //进入直播间
    public static final String STEP_OUT_CHANNEL = "stepOutChannel";   //退出直播间
    public static final String STEP_OUT_HONGREN_MAIN = "stepOutHongrenMain";   //结束直播
    public static final String MSG = "msg";   //发送消息
    public static final String BUY = "buy";   //购买了什么 先调用购买接口成功后再发消息
    public static final String FOLLOW = "follow";   //关注消息 先调用关注接口成功后再发消息
    public static final String GRAB_GOODS_SUCCESS = "grab_goods_success";   //抢字消息,抢字按钮出现的时候，用户点击抢按钮成功后，调用此接口广播通知
    public static final String SHOW_GOODS = "show_goods";   //推送商品
    public static final String SHOW_GOODS_HONGREN = "show_goods_hongren";   //红人或场控推送商品
    public static final String HIDDEN_GOODS = "hidden_goods";   //撤回商品
    public static final String ADMIN_MSG = "admin_msg";   //场控消息
    public static final String ANNOUNCEMENT = "announcement";   //公告消息
    public static final String HIDDEN_ANNOUNCEMENT = "announcement_revert";   //撤销公告消息
    public static final String PING = "ping";   //心跳
    public static final String BLOCK = "block";   //用户被禁言
    public static final String HIDDEN_GRAB_BUTTON = "hidden_grab_button";   //隐藏抢字消息
    public static final String SHOW_GRAB_BUTTON = "show_grab_button";   //抢字消息

    private String id;
    private String uid;
    private String action_type;
    private String channel;
    private String logintoken;
    private String add_time;
    private MessageData action_data;

    public WSMessageBean(){}

    protected WSMessageBean(Parcel in) {
        id = in.readString();
        uid = in.readString();
        action_type = in.readString();
        channel = in.readString();
        logintoken = in.readString();
        add_time = in.readString();
        action_data = in.readParcelable(MessageData.class.getClassLoader());
    }

    public static final Creator<WSMessageBean> CREATOR = new Creator<WSMessageBean>() {
        @Override
        public WSMessageBean createFromParcel(Parcel in) {
            return new WSMessageBean(in);
        }

        @Override
        public WSMessageBean[] newArray(int size) {
            return new WSMessageBean[size];
        }
    };

    public boolean reStart(){
        return code == WEBSOCKET_WORKERSTOP || code == WEBSOCKET_SHUTDOWN || code == WEBSOCKET_MANAGERSTOP;
    }

    public boolean channelNotExist(){
        return code == CHANNEL_NOT_EXIST;
    }

    public String getAdd_time() {
        return add_time;
    }

    public void setAdd_time(String add_time) {
        this.add_time = add_time;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getAction_type() {
        return action_type;
    }

    public void setAction_type(String action_type) {
        this.action_type = action_type;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public String getLogintoken() {
        return logintoken;
    }

    public void setLogintoken(String logintoken) {
        this.logintoken = logintoken;
    }

    public MessageData getAction_data() {
        return action_data;
    }

    public void setAction_data(MessageData action_data) {
        this.action_data = action_data;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(uid);
        dest.writeString(action_type);
        dest.writeString(channel);
        dest.writeString(logintoken);
        dest.writeString(add_time);
        dest.writeParcelable(action_data, flags);
    }

    public static class MessageData implements Parcelable{
        private String msg;
        private String goods_id;
        private String goods_name;
        private String goods_image;
        private String goods_price;
        private String goods_marketprice;
        private String goods_hj_price;
        private String goods_stock;
        private String goods_state;
        private String cart_id;
        private String fuid;
        private String funame;
        private String user_name;
        private String uid;
        private String user_avatar;
        private String channel_count;
        private String timestamp;
        private String watching_number;
        private String parent_id;
        private String to_user_name;
        private String to_uid;
        private String limit;
        private String block_uid;
        private String block_user_name;
        private String expires;
        private String is_show;
        private String client;
        private int hongren_look;
        private String spec_price_id;
        private String discount;
        private String goods_desc;
        private String spec_images;
        private String cur_user_num;
        private List<UserInfoBean> watchingUser;

        public MessageData(){

        }

        protected MessageData(Parcel in) {
            msg = in.readString();
            goods_id = in.readString();
            goods_name = in.readString();
            goods_image = in.readString();
            goods_price = in.readString();
            goods_marketprice = in.readString();
            goods_hj_price = in.readString();
            goods_stock = in.readString();
            goods_state = in.readString();
            cart_id = in.readString();
            fuid = in.readString();
            funame = in.readString();
            user_name = in.readString();
            uid = in.readString();
            user_avatar = in.readString();
            channel_count = in.readString();
            timestamp = in.readString();
            watching_number = in.readString();
            parent_id = in.readString();
            to_user_name = in.readString();
            to_uid = in.readString();
            limit = in.readString();
            block_uid = in.readString();
            block_user_name = in.readString();
            expires = in.readString();
            is_show = in.readString();
            client = in.readString();
            hongren_look = in.readInt();
            spec_price_id = in.readString();
            discount = in.readString();
            goods_desc = in.readString();
            spec_images = in.readString();
            cur_user_num = in.readString();
            watchingUser = in.createTypedArrayList(UserInfoBean.CREATOR);
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(msg);
            dest.writeString(goods_id);
            dest.writeString(goods_name);
            dest.writeString(goods_image);
            dest.writeString(goods_price);
            dest.writeString(goods_marketprice);
            dest.writeString(goods_hj_price);
            dest.writeString(goods_stock);
            dest.writeString(goods_state);
            dest.writeString(cart_id);
            dest.writeString(fuid);
            dest.writeString(funame);
            dest.writeString(user_name);
            dest.writeString(uid);
            dest.writeString(user_avatar);
            dest.writeString(channel_count);
            dest.writeString(timestamp);
            dest.writeString(watching_number);
            dest.writeString(parent_id);
            dest.writeString(to_user_name);
            dest.writeString(to_uid);
            dest.writeString(limit);
            dest.writeString(block_uid);
            dest.writeString(block_user_name);
            dest.writeString(expires);
            dest.writeString(is_show);
            dest.writeString(client);
            dest.writeInt(hongren_look);
            dest.writeString(spec_price_id);
            dest.writeString(discount);
            dest.writeString(goods_desc);
            dest.writeString(spec_images);
            dest.writeString(cur_user_num);
            dest.writeTypedList(watchingUser);
        }

        @Override
        public int describeContents() {
            return 0;
        }

        public static final Creator<MessageData> CREATOR = new Creator<MessageData>() {
            @Override
            public MessageData createFromParcel(Parcel in) {
                return new MessageData(in);
            }

            @Override
            public MessageData[] newArray(int size) {
                return new MessageData[size];
            }
        };

        public String getCur_user_num() {
            return cur_user_num;
        }

        public void setCur_user_num(String cur_user_num) {
            this.cur_user_num = cur_user_num;
        }

        public String getSpec_images() {
            return spec_images;
        }

        public void setSpec_images(String spec_images) {
            this.spec_images = spec_images;
        }

        public String getGoods_desc() {
            return goods_desc;
        }

        public void setGoods_desc(String goods_desc) {
            this.goods_desc = goods_desc;
        }

        public String getSpec_price_id() {
            return spec_price_id;
        }

        public void setSpec_price_id(String spec_price_id) {
            this.spec_price_id = spec_price_id;
        }

        public String getDiscount() {
            return discount;
        }

        public void setDiscount(String discount) {
            this.discount = discount;
        }

        public String getClient() {
            return client;
        }

        public void setClient(String client) {
            this.client = client;
        }

        public int getHongren_look() {
            return hongren_look;
        }

        public void setHongren_look(int hongren_look) {
            this.hongren_look = hongren_look;
        }

        public String getGoods_state() {
            return goods_state;
        }

        public void setGoods_state(String goods_state) {
            this.goods_state = goods_state;
        }

        public String getIs_show() {
            return is_show;
        }

        public void setIs_show(String is_show) {
            this.is_show = is_show;
        }

        public String getBlock_uid() {
            return block_uid;
        }

        public void setBlock_uid(String block_uid) {
            this.block_uid = block_uid;
        }

        public String getBlock_user_name() {
            return block_user_name;
        }

        public void setBlock_user_name(String block_user_name) {
            this.block_user_name = block_user_name;
        }

        public String getExpires() {
            return expires;
        }

        public void setExpires(String expires) {
            this.expires = expires;
        }

        public String getLimit() {
            return limit;
        }

        public void setLimit(String limit) {
            this.limit = limit;
        }

        public String getTo_user_name() {
            return to_user_name;
        }

        public void setTo_user_name(String to_user_name) {
            this.to_user_name = to_user_name;
        }

        public String getTo_uid() {
            return to_uid;
        }

        public void setTo_uid(String to_uid) {
            this.to_uid = to_uid;
        }

        public List<UserInfoBean> getWatchingUser() {
            return watchingUser;
        }

        public void setWatchingUser(List<UserInfoBean> watchingUser) {
            this.watchingUser = watchingUser;
        }

        public String getMsg() {
            return msg;
        }

        public void setMsg(String msg) {
            this.msg = msg;
        }

        public String getGoods_id() {
            return goods_id;
        }

        public void setGoods_id(String goods_id) {
            this.goods_id = goods_id;
        }

        public String getGoods_name() {
            return goods_name;
        }

        public void setGoods_name(String goods_name) {
            this.goods_name = goods_name;
        }

        public String getGoods_image() {
            return goods_image;
        }

        public void setGoods_image(String goods_image) {
            this.goods_image = goods_image;
        }

        public String getGoods_price() {
            return goods_price;
        }

        public void setGoods_price(String goods_price) {
            this.goods_price = goods_price;
        }

        public String getGoods_marketprice() {
            return goods_marketprice;
        }

        public void setGoods_marketprice(String goods_marketprice) {
            this.goods_marketprice = goods_marketprice;
        }

        public String getGoods_hj_price() {
            return goods_hj_price;
        }

        public void setGoods_hj_price(String goods_hj_price) {
            this.goods_hj_price = goods_hj_price;
        }

        public String getGoods_stock() {
            return goods_stock;
        }

        public void setGoods_stock(String goods_stock) {
            this.goods_stock = goods_stock;
        }

        public String getCart_id() {
            return cart_id;
        }

        public void setCart_id(String cart_id) {
            this.cart_id = cart_id;
        }

        public String getFuid() {
            return fuid;
        }

        public void setFuid(String fuid) {
            this.fuid = fuid;
        }

        public String getFuname() {
            return funame;
        }

        public void setFuname(String funame) {
            this.funame = funame;
        }

        public String getUser_name() {
            return user_name;
        }

        public void setUser_name(String user_name) {
            this.user_name = user_name;
        }

        public String getUid() {
            return uid;
        }

        public void setUid(String uid) {
            this.uid = uid;
        }

        public String getUser_avatar() {
            return user_avatar;
        }

        public void setUser_avatar(String user_avatar) {
            this.user_avatar = user_avatar;
        }

        public String getChannel_count() {
            return channel_count;
        }

        public void setChannel_count(String channel_count) {
            this.channel_count = channel_count;
        }

        public String getTimestamp() {
            return timestamp;
        }

        public void setTimestamp(String timestamp) {
            this.timestamp = timestamp;
        }

        public String getWatching_number() {
            return watching_number;
        }

        public void setWatching_number(String watching_number) {
            this.watching_number = watching_number;
        }

        public String getParent_id() {
            return parent_id;
        }

        public void setParent_id(String parent_id) {
            this.parent_id = parent_id;
        }
    }
}
