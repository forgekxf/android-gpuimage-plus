package com.bhtc.huajuan.push.bean;

/**
 * Created by kouxiongfei on 2017/5/19.
 */

public class LiveItemBean {
//    private GoodsParentBean goods;
    private String id;
    private String alias_image;
    private String add_time;
    private String alias_name;
    private String live_id;

    private String goods_id;                //商品id
    private String goods_name;              //商品名字
    private String goods_desc;              //商品描述
    private String goods_marketprice;        //商品市场价
    private String goods_hj_price;        //商品市场价
    private String goods_price;              //商品价格
    private String goods_num;                  //商品数量
    private String goods_discount;          //商品折扣
    private String goods_spec;              //商品规格
    private String goods_attr;              //商品属性
    private String goods_state;             //商品状态：0是下架，1正常 ,
    private String goods_type;              //6,7为中奖 ,
    private String goods_stock;                //商品库存数量
    private String goods_image;
    private String remark;                  //此商品不参加“双11全场满减”活动"
    private String goods_freight;            //商品运费
    private String gc_id;                      //类别ID
    private String origin_id;               //商品产地id
    private String origin_desc;             //商品产地描述
    private String origin_image;            //商品产地图片url
    private String brand_id;                   //品牌ID
    private String is_arrival;              //是否已经添加了到货提醒，1表示已经添加，0表示没有添加 ,
    private String common_id;                 //在加入购物车卡片上用
    private String spec_images;             //商品的营销图片
    private int is_show;             //弹出按钮 0
    private int pop_show;
    private String limit;
    private String spec_price_id;
    private String discount;
    private boolean isQiang;

    public boolean isQiang() {
        return isQiang;
    }

    public void setQiang(boolean qiang) {
        isQiang = qiang;
    }

    public int getIs_show() {
        return is_show;
    }

    public void setIs_show(int is_show) {
        this.is_show = is_show;
    }

    public String getSpec_price_id() {
        return spec_price_id;
    }

    public void setSpec_price_id(String spec_price_id) {
        this.spec_price_id = spec_price_id;
    }

    public String getLimit() {
        return limit;
    }

    public void setLimit(String limit) {
        this.limit = limit;
    }

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }
//    public GoodsParentBean getGoods() {
//        return goods;
//    }
//
//    public void setGoods(GoodsParentBean goods) {
//        this.goods = goods;
//    }

    public int getPop_show() {
        return pop_show;
    }

    public void setPop_show(int pop_show) {
        this.pop_show = pop_show;
    }

    public int getIs_withdraw() {
        return is_show;
    }

    public void setIs_withdraw(int is_show) {
        this.is_show = is_show;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAlias_image() {
        return alias_image;
    }

    public void setAlias_image(String alias_image) {
        this.alias_image = alias_image;
    }

    public String getGoods_id() {
        return goods_id;
    }

    public void setGoods_id(String goods_id) {
        this.goods_id = goods_id;
    }

    public String getAdd_time() {
        return add_time;
    }

    public void setAdd_time(String add_time) {
        this.add_time = add_time;
    }

    public String getAlias_name() {
        return alias_name;
    }

    public void setAlias_name(String alias_name) {
        this.alias_name = alias_name;
    }

    public String getLive_id() {
        return live_id;
    }

    public void setLive_id(String live_id) {
        this.live_id = live_id;
    }

    public String getGoods_name() {
        return goods_name;
    }

    public void setGoods_name(String goods_name) {
        this.goods_name = goods_name;
    }

    public String getGoods_desc() {
        return goods_desc;
    }

    public void setGoods_desc(String goods_desc) {
        this.goods_desc = goods_desc;
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

    public String getGoods_price() {
        return goods_price;
    }

    public void setGoods_price(String goods_price) {
        this.goods_price = goods_price;
    }

    public String getGoods_num() {
        return goods_num;
    }

    public void setGoods_num(String goods_num) {
        this.goods_num = goods_num;
    }

    public String getGoods_discount() {
        return goods_discount;
    }

    public void setGoods_discount(String goods_discount) {
        this.goods_discount = goods_discount;
    }

    public String getGoods_spec() {
        return goods_spec;
    }

    public void setGoods_spec(String goods_spec) {
        this.goods_spec = goods_spec;
    }

    public String getGoods_attr() {
        return goods_attr;
    }

    public void setGoods_attr(String goods_attr) {
        this.goods_attr = goods_attr;
    }

    public String getGoods_state() {
        return goods_state;
    }

    public void setGoods_state(String goods_state) {
        this.goods_state = goods_state;
    }

    public String getGoods_type() {
        return goods_type;
    }

    public void setGoods_type(String goods_type) {
        this.goods_type = goods_type;
    }

    public String getGoods_stock() {
        return goods_stock;
    }

    public void setGoods_stock(String goods_stock) {
        this.goods_stock = goods_stock;
    }

    public String getGoods_image() {
        return goods_image;
    }

    public void setGoods_image(String goods_image) {
        this.goods_image = goods_image;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getGoods_freight() {
        return goods_freight;
    }

    public void setGoods_freight(String goods_freight) {
        this.goods_freight = goods_freight;
    }

    public String getGc_id() {
        return gc_id;
    }

    public void setGc_id(String gc_id) {
        this.gc_id = gc_id;
    }

    public String getOrigin_id() {
        return origin_id;
    }

    public void setOrigin_id(String origin_id) {
        this.origin_id = origin_id;
    }

    public String getOrigin_desc() {
        return origin_desc;
    }

    public void setOrigin_desc(String origin_desc) {
        this.origin_desc = origin_desc;
    }

    public String getOrigin_image() {
        return origin_image;
    }

    public void setOrigin_image(String origin_image) {
        this.origin_image = origin_image;
    }

    public String getBrand_id() {
        return brand_id;
    }

    public void setBrand_id(String brand_id) {
        this.brand_id = brand_id;
    }

    public String getIs_arrival() {
        return is_arrival;
    }

    public void setIs_arrival(String is_arrival) {
        this.is_arrival = is_arrival;
    }

    public String getCommon_id() {
        return common_id;
    }

    public void setCommon_id(String common_id) {
        this.common_id = common_id;
    }

    public String getSpec_images() {
        return spec_images;
    }

    public void setSpec_images(String spec_images) {
        this.spec_images = spec_images;
    }
}
