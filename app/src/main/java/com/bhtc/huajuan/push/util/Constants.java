package com.bhtc.huajuan.push.util;

/**
 * Created by Administrator on 2016/3/9.
 */
public class Constants {
    public static final String COMMODITY_DETAIL_LOCAL = "file:///android_asset/productdetail/hj_product_page.html";

    //SP 是SharedPreference的意思
    public interface SpName {
        /**
         * 尖货分类数据
         */
        String GOODS_CLASS = "goods_class";
        /**
         * 搜索默认字段
         */
        String SEARCH_DEFAULT = "search_default";
        String SEARCH_DEFAULT_TEXT = "search_default_text";
        String SEARCH_DEFAULT_TEXT_PERSON = "search_default_text_person";
        /**
         * 尖货分类数据
         */
        String REDSKINS_CLASS = "redskins_class";
        /**
         * 版本数据
         */
        String VERSION_DATA = "version_data";
        /**
         * 用于做app常用配置的spName
         */
        String CONFIG = "config";
        /**
         * 用于做文案替换的spName
         */
        String TEXT = "text"; //用于做文案的替换
        /**
         * 用于存储用户的个人信息spName
         */
        String USER_INFO = "user_info";

        String CID_SESSION = "cid_session";

        String TASK_MAIN_ACT = "task_main_activity";

        String SHOW_INDEX_AD = "show_index_ad";

        String VIDEO_FULLSCREEN_ISFRIST = "video_fullscreen_isfrist";

        String DILOG_SESSION = "dilog_session";
        //收获地址的身份证是否显示
        String ID_NUMBER_SHOW = "id_number_show";
        //优惠券是否显示
        String NEED_SHOW_CONPON = "id_conpon";
        //优惠券是否显示
        String FRIST_PERSONAL_MONEY = "frist_personal_money";
        //用户消息
        String CHAT_MESSAGE = "chat_message";
        //推荐频道推荐请求时间
        String PERSONAL_TIME_NAME = "personal_time_name";

        //推荐频道分类接口是否只在推荐tab请求
        String LOAD_VIDEO_CLASS_INDEX = "load_video_class_index";
        //全局管理表名，以后全局管理型的存储最好都以此建表。
        String GLOBAL_MANAGEMENT = "global_management";
        //控制第一次显示的数据名
        String FIRST_ONE = "first_one";
    }

    //登录以后保存个人信息到本地 这些是 USER_INFO的SPkey
    public interface SpKeyUserInfo {
        String ADD_TIME = "add_time";
        String CLIENT_ID = "client_id";
        String IS_ALLOWTALK = "is_allowtalk";
        String IS_BUY = "is_buy";
        String IS_HONGREN = "is_hongren";
        String LAST_UPDATE_TIME = "last_update_time";
        String LOGIN_DEV = "login_dev";
        String LOGIN_PLATFORM = "login_platform";
        String UID = "uid";
        String USER_AREAID = "user_areaid";
        String USER_AVATAR = "user_avatar";
        String USER_BIRTHDAY = "user_birthday";
        String USER_CITYID = "user_cityid";
        String USER_LOGIN_IP = "user_login_ip";
        String USER_LOGIN_NUM = "user_login_num";
        String USER_LOGIN_TIME = "user_login_time";
        String USER_MOBILE = "user_mobile";
        String USER_NAME = "user_name";
        String USER_OLD_LOGIN_IP = "user_old_login_ip";
        String USER_OLD_LOGIN_TIME = "user_old_login_time";
        String USER_PROVINCEID = "user_provinceid";
        String USER_CONSTELLATION = "user_constellation";
        String USER_SEX = "user_sex";
        String USER_AGE = "user_age";
        String USER_STATE = "user_state";
        String USER_TRUENAME = "user_truename";
        String IS_REG_EASEMOB = "is_reg_easemob";
        String USER_TYPE = "user_type";

        //全局获取到的订单信息
        //已取消
        String ORDER_STATE_CANCELED = "order_state_canceled";
        //待付款
        String ORDER_STATE_PENDING_PAY = "order_state_pending_pay";
        //待发货
        String ORDER_STATE_PEDNING_SHIP = "order_state_pedning_ship";
        //已发货 就是待收获的数量
        String ORDER_STATE_SHIPED = "order_state_shiped";
        //已收货
        String ORDER_STATE_RECEIPTED = "order_state_receipted";
        //购物车总数
        String CART_COUNT = "cart_count";
        //全局接口参数：从我的个认页的第一条数据的add_time得到
        String LAST_FEED_TIME = "lastFeedTime";
        //我的卷播还有多少没录
        String NOT_RECOD_SHOW_COUNT = "notRecodShowCount";
        //未读的feed数量
        String NEW_FEED = "newFeed";
        //我的花红最近增长了多少
        String NOT_READ_SCORE_COUNT = "notReadScoreCount";


        //登录后获取的logintoken
        String LOGIN_TOKEN = "login_token";
        //环信登录session
        String CHAT_LOGIN_SESSION = "chat_login_session";
        //message红点
        String MESSAGE_POINT = "message_point";
        //使用的优惠券id
        String COUPON_ID = "couponId";
        //使用的花红数值
        String HUAHONG_NUM = "huahongNum";
    }

    public interface SpGoodsClass {
        //商品页面尖货分类数据
        String GC_ID = "gc_id";
        String GC_SORT = "gc_sort";
        String GC_IMG = "gc_img";
        String GC_NAME = "gc_name";
        String SIZE = "size";
    }

    public interface SpKeyRedskinsClass {
        //红人列表分类数据
        String VISIBLE_COUNT = "visible_count";
        String SIZE = "size";
        String VC_NAME = "vc_name";
        String VC_ID = "vc_id";
        String VC_PADDING = "vc_padding";
        String IS_NEW = "is_new";
    }

    public interface SpKeyVersionData {
        //版本号数据
        String IS_FORCE_UPGRADE = "is_force_upgrade";
        String INSIDE_VER = "inside_ver_str";//inside_ver
        String MATCHING_VER = "matching_ver";
        String VER = "ver";
        String VER_DESC = "vm_desc";
    }

    public interface SpKeySessionClass {
        String CLIENT_ID = "client_id";
        String NOTIFY_TIME = "notify_time";
        String MESSAGE_COUNT = "message_count";
    }
    public interface SpkeyMainExitClass{
        String HAVE_MAIN_TASK = "";
    }
    public interface SpkeyShowIndexAdClass{
        String SHOW_INDEX_AD_RADIO = "show_index_ad_radio";
        String SHOW_INDEX_AD_IMG = "show_index_ad_img";
    }
    public interface SpkeyDialogSessionClass{
        String DIALOG_SESSION_FIRST = "dialog_session_first";
        String DIALOG_SESSION_COUNT = "dialog_session_count";
        String DIALOG_SESSION_HAD_LOGIN = "dialog_session_had_login";
        String DIALOG_NOTIFY_OFF_COUNT = "dialog_notify_off_count";
    }

    /**
     * 储存全局管理的一些字段
     * 如：是否显示国旗，是否显示身份证号。。。。
     */
    public interface SpkeyGMClass {
        String ID_NUMBER_NEED_SHOW = "id_number_need_show";
//        String IS_SHOW_DELIVERY_TIME = "is_show_delivery_time";
        String BUY_SHOW_REWARD = "buyer_show_reward";
        String COUNTRY_IS_SHOW = "country_is_show";//除商品详情页外所有的国旗是否显示。1=显示，0=不显示。
        String BUYER_SHOW_POLLING = "buyer_show_polling_time";//控制卷播广场轮询时间
    }
    public interface SpKeyFirstOneClass {
        String JB_SQUARE_ACTIVITY = "jb_square_activity";
    }

    public interface SpkeyConponClass{
        String NO_PAY_COUPON = "nopaycoupon";
        String WELCOME_COUPON = "welcomecoupon";
        String INIT = "init";
        String NEED_SHOW_DIALOG_ONE = "need_show_dialog_one";
    }
    public interface SpkeyChatMessageClass{
        String USER_CHAT = "user_chat";
        String CUSTOMER_CHAT = "customer_chat";
    }

    public interface IsCache {
        String HOT_CONCERN_IS_CACHE = "hot_concern_is_cache";
        String SHOP_CART_CACHE_TIME = "shop_cart_cache_time";
    }
    public interface SpkeyPersonalTimeClass{
        //推荐频道推荐请求时间
        String PERSONAL_TIME = "personal_time";
    }
    public interface SpkeyLoadClassIndexClass{
        //推荐频道推荐请求时间
        String LOAD_CLASS_INDEX = "load_class_index";
    }
}
