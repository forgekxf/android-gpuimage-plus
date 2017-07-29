package com.bhtc.huajuan.push.util;

import com.bhtc.huajuan.push.BuildConfig;

/**
 * Created by Administrator on 2016/3/7.
 */
public class ApiUrls {

    //VM 提取到ApiUrls
    public static String VM = "2017070301";
//    public static String BASE_HTTP = "https://app.huajuanmall.com/dm.json";
    //获取各个主域名
    public static String BASE_HTTP = BuildConfig.DEBUG ? "https://test.huajuanmall.com/dm.json" : "https://app.huajuanmall.com/dm.json";

    public static String BASE = BuildConfig.DEBUG ? "https://test.huajuanmall.com" : "https://app.huajuanmall.com";

    public static String STATIC_BASE = BuildConfig.DEBUG ? "https://teststatic.huajuanmall.com" : "https://static.huajuanmall.com";
    //统计主域名
    public static String ANALYTICS_BASE = BuildConfig.DEBUG ? "https://test.analytics.huajuanmall.com" : "https://analytics.huajuanmall.com";
    //推拉流域名
    public static String LIVESTREAMING_DM = BuildConfig.DEBUG ? "http://testlive.huajuanmall.com" : "http://live.huajuanmall.com";
    //ws域名
    public static String LIVESTREAMING_WS_DM = BuildConfig.DEBUG ? "ws://testlive.huajuanmall.com:8111" : "ws://live.huajuanmall.com:8111";
    //H5的主域名
    public static String HTML5_BASE = BuildConfig.DEBUG ? "http://test.m.huajuanmall.com/" : "https://m.huajuanmall.com";

    //七牛服务器
    public static final String IMAGE_TOKEN ="/qiniu/token";
    //弹幕
    public static final String DM_LOGIN_TOKEN ="/logintoken/";
    //推流
    public static final String PUBLISH_URL ="/rtmp/getPublishUrl";
    //推流初始化
    public static final String PUBLISH_INIT ="/live/detail";

    //红人端获取id
    public static final String PUBLISH_LIVEHR ="/livehr/entryLive";

    //开始推流
    public static final String START_LIVE ="/livehr/startLive";

    //结束推流
    public static final String EXIT_LIVE ="/livehr/exitLive";

    //获取直播关联商品列表
    public static final String GET_LIVE_GOODS ="/live/goods";

    //获取红人预约直播列表
    public static final String GET_LIVE_RESERVE ="/livehr/reserveList";

    //七牛视频TOKEN
    public static final String VIDEO_TOKEN ="/qiniu/videoToken";

    //app统计
    public static final String VIDEO_TJ = "/collect/app";

    //买家秀优惠价
    public static final String BUYER_DERATE ="/goods/getGoodsPayDerate";

    //应用程序初始化获取的一些值
    public static final String START ="/init/start";

    //我的 初始化，从全局接口切出来的数据
    public static final String MINE_START ="/init/getUserInit";

    //卷播个人页
    public static final String JUANBO_USER_LIST ="/buyerShow/getUserShowList";

    //红人广告
    public static final String HOT_ADVERT ="/ad/getHongrenDetailAd";

    //兑换优惠券
    public static final String CONVERT_COUPON ="/coupon/convertCoupon";

    //首页红人列表
    public static final String HOT_PEOPLE ="/rec/cls";
    //首页红人列表
    public static final String HOT_PEOPLE_SINGLE ="/rec/single";
    //今日头条
    public static final String DAILY_GOODS_SALE ="/rec/dailyGoodsSale";
    //红人页的广告
    public static final String GET_INDEX_BANNER_AD ="/ad/getIndexBannerAd";

    //顶部轮播广告
    public static final String TOP_ADVERT ="/subject/getCarousel";

    //顶部视频专题
    public static final String VIDEO_TOP ="/rec/videoList";

    //我的优惠券
    public static final String MY_COUPON ="/coupon/getAvailableCoupon";

    //优惠券分享
    public static final String COUPON_SHARE ="/coupon/getCoupontListShareInfo";

    //购物车第二步（填写订单）
    public static final String WRITE_ORDER ="/cart/gotoCartInfoStep2";

    //购物车第二步（设置订单优惠券）
    public static final String POST_SET_CART_INFO_STEP2COUPON ="/cart/setCartInfoStep2Coupon";

    //购物车第二步（清空订单优惠券）
    public static final String POST_CLEAR_CART_INFO_COUPON ="/cart/clearCartInfoCoupon";

    //购物车第二步（设置订单花红）
    public static final String POST_SET_CART_INFO_STEP2SCORE ="/cart/setCartInfoStep2Score";

    //热门搜索关键字
    public static final String SEARCH_HOTKW ="/search/hotKW";

    //搜索红人
    public static final String SEARCH_USER="/search/searchHongren";

    //搜索商品
    public static final String SEARCH_GOODS="/search/searchGoods";

    //推荐标签数据
    public static final String GOOD_TAG_DATA ="/goods/discovery";

    //品牌下商品
    public static final String GOOD_BRAND ="/goods/search";

    //新版红人列表
    public static final String NEW_HOT_PERSON ="/hongren/chennel";

    //品牌馆凑单商品列表
    public static final String GOOD_SUBJECT ="/goods/getGoodsInfoBySubjectId";

    //分类下商品
    public static final String CLASS_GOODS_TIME ="/goods/getGoodsByGcIdOnline";
    public static final String CLASS_GOODS_HOT ="/goods/getGoodsByGcIdSale";

    //支付模块 POST
    public static final String GET_CHARGE ="/pay/getcharge";

    //支付成功 POST
    public static final String PAY_SUCCESS ="/pay/paySuccessCallback";
    //0元订单支付成功 POST
    public static final String PAY_ZERO_STATE ="/order/changeZeroPayOrderState";

    //支付失败 POST
    public static final String PAY_ERROR ="/pay/payErrorCollect";

    //注册模块
    public static final String REG_STEP_1 ="/register/regStep1";
    //验证注册验证码
    public static final String VALIDATE_REG_CODE ="/register/validateRegCode";
    //进行注册
    public static final String REG_STEP_2 ="/register/regStep2";

    //登录模块
    public static final String DO_LOGIN ="/login/doLogin";
    //个人设置模块 退出登录
    public static final String LOGIN_OUT ="/login/loginOut";
    //GET  个人页FEED列表
    public static final String FEEDLIST ="/feed/feedList";
    //获取登录验证码
    public static final String GET_MOBILE_LOGIN_CODE ="/login/getMobileLoginCode";
    //用手机号直接登录
    public static final String DO_MOBILE_LOGIN ="/login/hongrenLogin";


    //用户相关接口
    //取消关注某人
    public static final String DEL_FOLLOW_USER ="/follow/delFollowUser";
    //关注某人
    public static final String FOLLOW_USER ="/follow/followUser";
    //添加收藏
    public static final String ADD_FAVORITE ="/user/addFavorite";
    //取消收藏
    public static final String DEL_FAVORITE ="/user/delFavorite";

    //    //分享视频
//    public static final String SHARE_VIDEO ="/share/video";
//
//    //分享商品
//    public static final String SHARE_GOODS ="/share/goods";
//    //分享优惠券
//    public static final String SHARE_COUPON ="/share/coupon";
//
//    //分享优惠券
//    public static final String SHARE_TAG ="/share/tag";
    //分享买家秀接口统计
    public static final String SHARE_BUYERSHOW ="/share/buyerShow";

    //分享
    public static final String SHARE ="/share";
    public static final String SHARE_BUY ="/share/buyerShow";

    //个人模块
    public static final String GET_MY_PAGE_DATA ="/user/getMyPageData";
    public static final String POST_UPDATE_USER ="/user/updateUser";
    //收藏的商品列表
    public static final String GOODS ="/fav/goods";
    //我的粉丝列表
    public static final String FANSLIST ="/user/getFansList";
    //POST  升级粉丝VIP
    public static final String FANSVIP ="/user/setFansVip";
    //收藏的视频列表
    public static final String VIDEO ="/fav/video";
    //获取用户关注的普通用户列表
    public static final String NORMAL_FOLLOW_LI ="/follow/normalFollowLi";
    //关注的红人列表
    public static final String FOLI ="/follow/foli";
    //未关注的红人列表
    public static final String UNFOLI ="/follow/unfoli";
    //关注标签
    public static final String FOLLOW_TAG ="/follow/followTag";
    //取消关注标签
    public static final String DEL_FOLLOW_TAG ="/follow/delFollowTag";
    //我的标签列表
    public static final String MY_TAGS ="/follow/myTags";

    //POST  获取赠送花红手机验证码
    public static final String GIVE_SCORE_CODE ="/user/getGiveScoreCode";
    //POST  赠送花红
    public static final String GIVE_SCORE ="/user/giveScore";

    //用户收货地址
    //添加收货地址
    public static final String ADD_ADDRESS ="/user/addAddress";
    //删除收货地址
    public static final String DEL_ADDRESS ="/user/delAddress";
    //获取收货地址
    public static final String GET_USER_ADDRESS ="/user/getUserAddress";
    //更新收货地址
    public static final String UPDATE_ADDRESS ="/user/updateAddress";
    //设置默认收货地址
    public static final String SET_DEFAULT_ADDRESS ="/user/setDefaultAddress";

    //购物车第二步更改收货地址
    public static final String SET_ADDRESS_V2 ="/cart/setCartInfoStep2Address";

    //获取标签下的商品列表
    public static final String GET_TAG_GOODS_LIST ="/goods/getTagGoods";

    //提交订单
    public static final String SUBMIT_ORDER_INFO ="/order/submitV2";

    //单品推荐
    public static final String GET_TAG_GOODS_SINGLE ="/tag/getTagRecByTagId";

    //商品详情
    public static final String GET_GOODS_DETAIL ="/goods/detail";

    //POST  添加商品到货通知
    public static final String ADD_ARRIVAL_NOTIFY ="/goods/addArrivalNotify";
    //POST  设置某个商品属于哪个活动
    public static final String SET_GOOD_ACTIVITY ="/cart/setGoodsActivity";
    //POST  活动专题优惠券列表
    public static final String GET_PREFERENT_COUPON_LIST ="/coupon/getPreferentialCouponList";
    //POST   领取活动专题优惠券
    public static final String RECEIVE_PREFERENT_COUPON ="/coupon/receivePreferentialCoupon";
    //POST   获取卷播分享信息
    public static final String GET_SHOW_SHAREINFO ="/feed/getShowShareInfo";

    //GET  获取视频播放时随机出现的宝箱
    public static final String GET_TREASURE_BOX ="/video/getTreasureBox";
    //POST 领取宝箱
    public static final String OPEN_TREASURE_BOX ="/video/openTreasureBox";

    //POST 微博登录
    public static final String WEIBO_CALLBACK ="/weibo/weiboCallback";

    //订单
    //订单列表
    public static final String ORDER_LIST ="/order/li";
    public static final String ORDER_DETAILS ="/order/detail";
    public static final String DELETE_ORDER ="/order/del";
    //订单跟踪
    public static final String ORDER_SHIPPING_CODE ="/order/getShippingByCode";
    //POST  修改订单的发货时间
    public static final String ORDER_TAKE_TIME ="/order/changeOrderTakeDeliveryTime";

    //购物车
    //GET  购物车列表
    public static final String CART_LIST ="/cart";
    public static final String CART_LIST_V2 ="/cart/v2";


    //POST  购物车里改SKU的数量
    public static final String SHOPPING_CHANGE_NUM ="/cart/changeCart";
    //POST  购物车里改SKU的数量
    public static final String SHOPPING_CHANGE_NUM_V2 ="/cart/changeCartV2";
    //POST  删除购物车某商品
    public static final String SHOPPING_DELETE ="/cart/del";
    //POST  删除购物车某商品
    public static final String SHOPPING_DELETE_V2 ="/cart/delV2";
    //POST   选中某个商品
    public static final String SELECT_CART_GOODS ="/cart/selectedCartGoods";
    //POST   设置为未选中状态商品
    public static final String UN_SELECT_CART_GOODS ="/cart/unselectedCartGoods";
    //POST  添加商品到货提醒
    public static final String ADD_GOODS_REMIND ="/goods/addArrivalNotify";
    //POST  填写订单修改配送方式
    public static final String SET_CART_FREIGHT ="/cart/setCartInfoStep2Freight";
    //POST  提交订单
    public static final String SUBMIT_ORDER ="/order/submit";
    //GET  获取商品分类列表
    public static final String GET_GOODS_BYID ="/goods/getGoodsClassById";
    //GET  搜索商品
    public static final String GET_SEARCH_GOODS ="/goods/search";
    //POST  添加商品到购物车
    public static final String ADD_TO_CART_V2 ="/cart/addToCartV2";
    //POST  添加秒杀商品到购物车
    public static final String ADD_FLASH_TO_CART ="/cart/addFlashToCart";
    //POST  添加套装商品到购物车
    public static final String ADD_SUIT_TO_CART ="/cart/addSuitToCart";
    //POST  修改订单的发货地址
    public static final String CHANGE_ORDER_ADDRESS ="/order/changeOrderAddress";
    //POST  取消提醒某场次的商品
    public static final String DEL_FOLLOW_FLASH_SALE_GOODS ="/follow/delFollowFlashSaleGoods";
    //POST  关注某场活动的商品，获得提醒
    public static final String FOLLOW_FLASH_SALE_GOODS ="/follow/followFlashSaleGoods";

    //POST  秒杀列表
    public static final String GET_FLASH_SALE_LIST ="/goods/getFlashSaleList";

    //GET  获取商品基本信息
    public static final String GET_GOODS_DATA ="/goods/getGoodsBaseDataByGoodsId";

    //GET  视频详情页
    public static final String GET_VIDEO_DETAIL ="/video/detail";
    //GET  视频全屏商品弹层
    public static final String GET_GOODS_BASE_DETAIL ="/goods/goodsBaseDetail";


    //GET  红人详情页
    public static final String HOT_DETAIL ="/hongren/detail";
    // 红人的小铺的商品接口
    public static final String HOT_STORE_LIST ="/hongren/getHongrenGoodsList";
    // 红人视频列表接口
    public static final String HOT_VIDEO_LIST ="/hongren/getHongrenVideoList";
    //GET  获取用户消息
    public static final String GET_USER_NOTIFY ="/notify/userNotify";
    //GET  获取系统消息
    public static final String GET_SYS_NOTIFY ="/notify/sysNotify";
    //POST  删除消息
    public static final String POST_DEL_NOTIFY ="/notify/delNotify";
    //POST  设置消息已读
    public static final String POST_SET_NOTIFY_READ ="/notify/setNotifyRead";
    //POST  更新个推cid
    public static final String POST_UPDATE_CLIENT_ID ="/login/updateClientId";
    //GET  人机验证
    public static final String LOGIN_VALIDATE_CODE ="/login/getValidateCode";

    //GET  安卓下载最新版本
    public static final String GET_VERSION_UPGRADE ="/download/latest";

    //GET  客服电话，及订单路径
    public static final String GET_SERVICE_START ="/service/start";
    //GET  创建环信用户
    public static final String GET_CREATE_USER ="/easemob/createUser";
    //GET  购物车推荐商品
    public static final String GET_SUGGEST_CART ="/suggest/cart";
    //GET  上传补丁文件md5
    public static final String SUBMIT_FILE_MD5 ="/bugfix/android/checkHotUpdateScript";
    //POST  修改订单确认收货
    public static final String POST_CHANGE_ORDER_STATE ="/order/changeOrderState";
    //GET 获取订单状态
    public static final String POST_GET_ORDER_STATE_BY_ORDERSN ="/order/getOrderStateByOrderSn";
    //POST 领取未注册优惠券
    public static final String POST_RECEIVE_NEW_USER_COUPON ="/coupon/receiveNewUserCoupon";
    //POST 领取未支付用户优惠券
    public static final String POST_FOLLOW_COUPON ="/follow/followUserAndGetCoupon";
    //POST 领取未支付用户优惠券
    public static final String POST_RECEIVE_NO_PAY_COUPON ="/coupon/receiveNoPayCoupon";
    //GET 获取红人列表
    public static final String GET_DAKA_LIST ="/hongren/getDakaList";
    //GET 关注引导红人列表
    public static final String GET_FOLLOW_GUIDE ="/hongren/getFollowGuide";
    //GET 获取红人vip商品列表列表
    public static final String GET_VIP_GOODS_LIST ="/goods/getVipGoodsList";
    //GET 获取个人中心广告列表
    public static final String GET_USER_CENTER_AD ="/ad/getUserCenterAd";
    //POST 上传买家秀
    public static final String ADD_USER_SHOW ="/user/addBuyerShow";
    //POST 上传私信信息
    public static final String POST_SEND_SIXIN ="/easemob/sendSixin";
    //GET 获取私信列表
    public static final String GET_MSG_NOTIFY ="/notify/msgNotify";
    //GET 获取花红记录列表
    public static final String GET_SCORE_LOG ="/user/scoreLog";
    //GET 设置所有消息已读
    public static final String GET_SET_ALL_NOTIFY_READ ="/notify/setAllNotifyRead";
    //GET 红人首页
    public static final String GET_HOT ="/rec/hot";
    //GET 红人首页频道栏目数据
    public static final String GET_MIDDLE ="/rec/middle";
    //GET 红人首页秒杀单独接口
    public static final String GET_FALSHSALWE ="/rec/getFalshSale";
    //GET 红人首页秒杀更多花卷视频
    public static final String GET_TOPICVIDEOLIST ="/video/getTopicVideoList";
    //GET 红人首页推荐列表
    public static final String GET_PERSONAL ="/rec/personal";



    //GET 卷播广场：顶部广告和数据接口
    public static final String GET_SPECIALR_REWARD ="/buyerShow/getSpecialReward";
    //GET 卷播广场最新列表
    public static final String GET_NEWEST_LIST ="/buyerShow/getNewestList";
    //GET 卷播广场最热列表
    public static final String GET_HOTTEST_LIST ="/buyerShow/getHottestList";
    //GET 卷播广场关注的人卷播列表
    public static final String GET_FOLLOW_LIST ="/buyerShow/getFollowList";
    //GET 用过商品id去查看用户是否买过商品是否修改过头像
    public static final String CHECK_USER_BUYER_SHOW ="/buyerShow/checkUserBuyershow";
    //GET 根据videoId获取卷播商品
    public static final String GET_GOODS_BY_VIDEO_ID ="/buyerShow/getGoodsByVideoId";
    //GET 更多卷播列表
    public static final String GET_MORE_SHOW_LIST ="/buyerShow/getMoreShowList";
    //POST 卷播广场列表红点
    public static final String GET_RED_DOT ="/buyerShow/getRedDot";
    //GET 抢沙发列表
    public static final String GET_GRAB_SOFA ="/buyerShow/getGrabSofa";



    //GET 消息中心接口
    public static final String GET_NEW_NOTIFY_BY_TYPE ="/notify/getNewNotifyByType";
    //GET 新版本各分类未读消息数量
    public static final String GET_ALL_TYPE_NOTIFY_COUNT ="/notify/getAllTypeNotifyCount";
    //GET 获取推荐频道导航分类
    public static final String GET_HONGREN_INDEX_CLASS ="/videoClass/getHongrenIndexClass";
    //GET 获取榜单标签分类
    public static final String GET_RANK_TAGS ="/market/getRankTags";
    //GET 获取榜单分类列表数据
    public static final String GET_RANK_BY_ID ="/market/getRankById";

    public static String getBaseUrl(String url){
        return BASE + url;
    }
    public static String getAnalyticsBaseurl(String url){
        return ANALYTICS_BASE + url;
    }
    public static String getHtml5BaseUrl(String url){
        return HTML5_BASE + url;
    }
    public static String getStaticBaseUrl(String url){
        return STATIC_BASE + url;
    }
    public static String getLivestreaming_dm(String url){
        return LIVESTREAMING_DM + url;
    }

    public static String getLivestreaming_ws_dm(String url){
        return LIVESTREAMING_WS_DM + url;
    }
}
