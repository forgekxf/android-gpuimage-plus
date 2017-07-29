package com.bhtc.huajuan.push.bean;

import java.util.List;

/**
 * Created by kouxiongfei on 2017/5/19.
 */

public class LiveGoodsBean extends BaseBean {

    private List<LiveItemBean> goods_info;
    private LiveItemBean grab_info;

    public LiveItemBean getGrab_info() {
        return grab_info;
    }

    public void setGrab_info(LiveItemBean grab_info) {
        this.grab_info = grab_info;
    }

    public List<LiveItemBean> getGoods_info() {
        return goods_info;
    }

    public void setGoods_info(List<LiveItemBean> goods_info) {
        this.goods_info = goods_info;
    }
}
