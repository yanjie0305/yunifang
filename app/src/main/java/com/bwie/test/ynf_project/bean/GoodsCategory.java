package com.bwie.test.ynf_project.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2016/12/8.
 */
public class GoodsCategory {

    public int code;
    public String msg;
    public List<DataBean> data;
    public static class DataBean implements Serializable{
        public String id;
        public String goods_name;
        public String shop_price;
        public String market_price;
        public boolean is_coupon_allowed;
        public boolean is_allow_credit;
        public String goods_img;
        public String sales_volume;
        public String efficacy;
        public String watermarkUrl;
        public int sort;
    }
}
