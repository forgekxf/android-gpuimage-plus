package com.bhtc.huajuan.push.util;

/**
 * Created by qkk on 2016/4/19.
 * 接口 请求参数key，value， 返回状态码
 */
public class HttpConfig {


    /**
     * key 参数
     */
    public interface KeyParmas{
        String MOBILE = "user_mobile";
        String UID = "uid";
        String USER_NAME = "user_name";
        String USER_SEX = "user_sex";
        String USER_BIRTHDAY = "user_birthday";
        String USER_AVATAR = "user_avatar";
        String SKU_ID = "sku_id";
        String SKU_NUM = "sku_num";
        String TYPE = "type";
        String EXTRA_PARAMETER = "extra_parameter";
        String ORDERSN = "orderSn";
        String ADDRESSID = "address_id";
        String GOODS_ID = "goods_id";
        String SHARE_KEY = "share_key";
        String SHARE_TYPE = "share_type";
        String TAG_ID_SHARE = "tag_id";
        String F_ID = "fid";


        String OFFSET = "offset";
        String CODE = "user_passwd";
        String CHANNEL = "from_platform";
        String DEVICE = "device";
        //收藏的id
        String FAV_ID = "fav_id";
        String FAV_TYPE = "fav_type";
        String TAG_ID = "tag_id";
        String FUID = "fuid";
        String USER_MOBILE = "user_mobile";
        String USER_PASSWD = "user_passwd";
        String ID = "id";
        String CLIENT_ID = "client_id";

        String PLATFORM = "platform";
        String VIDEO_ID = "video_id";
        String ORDER_SN = "orderSn";
        String ORDER_STATE = "orderState";
        String EXTRA_BUNDLE = "extra_bundle";
        String COUPON_CODE = "coupon_code";
        String FU_ID = "fuid";
        String FILE_MD5 = "file";
        String FILE_MD5_VER = "ver";
        String FILE_MD5_VM = "vm";
        //
        String TO_UID = "to_uid";
        String MESSAGES = "messages";
        //订单优惠券
        String ORDER_PREVIEW_ID = "order_preview_id";
        //订单花红数量
        String SCORE = "score";

        String ACCESS_TOKEN = "access_token";
        String OPENID = "openid";
        String EXPIRES_TIME = "expires_time";
        String REFRESH_TOKEN = "refresh_token";

    }

    /**
     * value 参数
     */
    public interface ValueParms{
        String ALL = "all";

        //取消收藏的类型 goods,video
        String FAV_TYPE_GOODS = "goods";
        String FAV_TYPE_video = "video";
        String DEFAULT = "default";
        String GOODS_DEND = "goods_send";
        String PAY = "pay";
        String NO_USE_COUPON = "no_use_coupon";
        String FINISH = "finish";
        String CART = "cart";
        String SPECIAL = "special";
        String FULL_SCREEN = "full_screen";
        String GOODS_DETAIL = "goods_detail";
        String COUDAN = "coudan";
    }

    /**
     * 返回状态码
     */
    public interface ResultCode{
        int SUCCESS = 200;
        int OUT_LOGIN = 10001;
        //参数错误
        int PARAMS_ERROR_03 = 10003;
        //数据库错误，添加失败
        int DATABASE_ERROR_04 = 1004;
        //10分钟内最多获取3次验证码，请您稍后再来
        int CODE_TIME_OUT = 10041;
        //发送验证码失败
        int SEND_CODE_FAILURE_15 = 10015;
        //验证码错误
        int CODE_ERROR_16 = 10016;
        //人机验证
        int CODE_ERROR_10042 = 10042;
        //库存不足
        int CODE_ERROR_10500 = 10500;
        //订单预览中某些商品的购买数量达到上限
        int CODE_ERROR_10501 = 10501;
        //不支持送货地区
        int CODE_ERROR_13151 = 13151;
        //黑名单
        int CODE_ERROR_13501 = 13501;
        //优惠券不存在
        int CODE_ERROR_12002 = 12002;
        //已领取过优惠券
        int CODE_ERROR_10102 = 10102;
        //领取优惠券失败
        int CODE_ERROR_10004 = 10004;
        //领取优惠券 优惠券失效
        int CODE_ERROR_10103 = 10103;
        //不是VIP,不能购买VIP商品
        int CODE_ERROR_10040 = 10040;
        //情况订单优惠券失败
        int CODE_ERROR_10005 = 10005;
    }

    /**
     * 返回的数字类型数据的含义
     */
    public interface ResultDataType{
        //性别
        int WOMEN = 1;
        int MAN = 2;

        //已收藏
        int FAV = 1;
    }
    public interface HeaderKeyParams{
        String X_REQUESTED_WITH = "X-REQUESTED-WITH";
        String SERVICE = "SERVICE";
        String COOKIE = "cookie";
        String USER_AGENT = "user-agent";
        String VERSION_NAME = "ver";
        String VM = "VM";
    }

    public interface HeaderValueParams{
        String XML_HTTP_REQUEST = "XMLHttpRequest";
        String HJAPP_ANDROID = "HJAPP-android";
        String LOGIN_TOKEN = "logintoken=%s";
        //线下vm
        String DEBUG_VM = "0";
        String LS_DEBUG_VM = "2015041703";
        //线上VM
//        String VM = "2016121201";
//        String VM = "0";
    }

    public interface ExtraParms{
        String ANIMTION = "start_activity_animtion";
        String ANIMTION_OUT = "start_activity_animtion_out";
    }
}
