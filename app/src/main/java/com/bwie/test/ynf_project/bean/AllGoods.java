package com.bwie.test.ynf_project.bean;

import java.util.Comparator;
import java.util.List;

/**
 * Created by Administrator on 2016/12/2.
 */
public  class AllGoods {

    public int code;
    public String msg;
    public List<DataBean> data;

    public static class DataBean implements Comparator<DataBean> {
        public String efficacy;
        public String goods_img;
        public String goods_name;
        public String id;
        public boolean is_allow_credit;
        public boolean is_coupon_allowed;
        public double market_price;
        public int sales_volume;
        public double shop_price;
        public int sort;

        @Override
        public int compare(DataBean lhs, DataBean rhs) {
            if (lhs.shop_price > rhs.shop_price) {
                return (int) (lhs.shop_price - rhs.shop_price);
            }
            if (lhs.shop_price < rhs.shop_price) {
                return (int) (lhs.shop_price - rhs.shop_price);
            }
            return 0;
        }
    }
}
