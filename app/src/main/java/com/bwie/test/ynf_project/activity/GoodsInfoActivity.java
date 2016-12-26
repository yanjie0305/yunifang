package com.bwie.test.ynf_project.activity;

import android.content.Intent;
import android.graphics.Color;
import android.support.v4.view.PagerAdapter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.bwie.test.ynf_project.R;
import com.bwie.test.ynf_project.adapter.MyAdapter;
import com.bwie.test.ynf_project.base.BaseData;
import com.bwie.test.ynf_project.bean.Car;
import com.bwie.test.ynf_project.bean.GoodsDesc;
import com.bwie.test.ynf_project.bean.GoodsInfo;
import com.bwie.test.ynf_project.dao.CarDao;
import com.bwie.test.ynf_project.fragment.CartFragment;
import com.bwie.test.ynf_project.utils.CommonUtils;
import com.bwie.test.ynf_project.utils.URLUtils;
import com.bwie.test.ynf_project.view.AddShopCarPopupWindow;
import com.bwie.test.ynf_project.view.GoodsInfoViewPager;
import com.bwie.test.ynf_project.view.MyGridView;
import com.bwie.test.ynf_project.view.SelectSharePopupWindow;
import com.bwie.test.ynf_project.view.ShowingPage;
import com.google.gson.Gson;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.zhy.adapter.abslistview.CommonAdapter;
import com.zhy.adapter.abslistview.ViewHolder;

import java.util.ArrayList;
import java.util.List;

public class GoodsInfoActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView goodsinfo_back;
    private ImageView goodsinfo_shopping;
    private ImageView goodsinfo_share;
    private LinearLayout content_service;
    private TextView tv_shopcar;
    private TextView tv_soonbuy;
    private SelectSharePopupWindow menuWindow;
    private String id;
    private GoodsInfo goodsInfo;
    private TextView goodsinfo_title;
    private TextView goodsinfo_shop_price;
    private TextView goodsinfo_market_price;
    private LinearLayout goodsinfo_ll_dots;
    private List<GoodsInfo.DataBean.GoodsBean.GalleryBean> gallery;
    ArrayList<String> imageUrlList = new ArrayList<>();
    ArrayList<ImageView> dotsList = new ArrayList<>();
    int[] dotArray = new int[]{R.drawable.dot_focuse, R.drawable.dot_normal};
    private GoodsInfoViewPager goodsinfo_vp;
    private ImageView goodsinfo_coupons;
    private MyGridView goodsinfo_gv;
    private TextView tv_sales;
    private TextView tv_collect;
    private TextView goods;
    private TextView tv_attributes;
    private TextView commentNumber;
    private MyGridView goodsdesc_gv;
    private GoodsDesc[] goodsDesc;
    private AddShopCarPopupWindow menuWindows;
    private CheckBox rb_collect;
    private int i = 1;
    private CarDao dao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goods_info);
        id = getIntent().getStringExtra("id");
        initView();
        initData();
    }

    //初始化viewpager
    private void initViewPager() {
        gallery = goodsInfo.data.goods.gallery;
        for (int i = 0; i < gallery.size(); i++) {
            imageUrlList.add(gallery.get(i).normal_url);
        }
        initDots(gallery);
        goodsinfo_vp.initData(imageUrlList, dotArray, dotsList);
        goodsinfo_vp.startPager();
    }

    //设置小点
    private void initDots(List<GoodsInfo.DataBean.GoodsBean.GalleryBean> gallery) {
        dotsList.clear();
        goodsinfo_ll_dots.removeAllViews();
        for (int i = 0; i < gallery.size(); i++) {
            ImageView imageView = new ImageView(GoodsInfoActivity.this);
            if (i == 0) {
                imageView.setImageResource(dotArray[0]);
            } else {
                imageView.setImageResource(dotArray[1]);
            }
            dotsList.add(imageView);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            params.setMargins(5, 0, 5, 0);
            goodsinfo_ll_dots.addView(imageView, params);
        }
    }

    //获取数据
    public void initData() {
        new BaseData() {
            @Override
            protected void setResultData(String data) {
                Log.i("TAG", "setResultData: **************" + data);
                Gson gson = new Gson();
                goodsInfo = gson.fromJson(data, GoodsInfo.class);
                Log.i("TAG", "setResultData: **************" + goodsInfo);
                //初始化viewpager轮播图
                initViewPager();
                goodsinfo_title.setText(goodsInfo.data.goods.goods_name);
                goodsinfo_shop_price.setText("￥" + goodsInfo.data.goods.shop_price);
                goodsinfo_market_price.setText("￥" + goodsInfo.data.goods.market_price);
                goodsinfo_gv.setAdapter(new CommonAdapter<GoodsInfo.DataBean.ActivityBean>(GoodsInfoActivity.this, R.layout.goodsinfo_gv, goodsInfo.data.activity) {
                    @Override
                    protected void convert(ViewHolder viewHolder, GoodsInfo.DataBean.ActivityBean item, int position) {
                        TextView gv_title = viewHolder.getView(R.id.gv_title);
                        gv_title.setText(item.title);
                    }
                });
                goodsinfo_gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Intent intent = new Intent(GoodsInfoActivity.this, InfoActivity.class);
                        intent.putExtra("webUrl", goodsInfo.data.activity.get(position).description);
                        startActivity(intent);
                    }
                });
                tv_sales.setText(goodsInfo.data.goods.sales_volume);
                tv_collect.setText(goodsInfo.data.goods.collect_count);
                commentNumber.setText("评论（" + goodsInfo.data.commentNumber + "）");
                //产品详情
                String goods_desc = goodsInfo.data.goods.goods_desc;
                Gson gson1 = new Gson();
                goodsDesc = gson1.fromJson(goods_desc, GoodsDesc[].class);
                goodsdesc_gv.setAdapter(new MyAdapter(GoodsInfoActivity.this, goodsDesc));
            }

            @Override
            protected void setResultError(ShowingPage.StateType stateLoadError) {
            }
        }.getData(URLUtils.GOODSINFO, URLUtils.GOODSINFOARGS + id, 0, BaseData.NORMALTIME);

    }

    //找控件
    private void initView() {
        dao = new CarDao(GoodsInfoActivity.this);
        goodsinfo_back = (ImageView) findViewById(R.id.goodsinfo_back);
        goodsinfo_shopping = (ImageView) findViewById(R.id.goodsinfo_shopping);
        goodsinfo_share = (ImageView) findViewById(R.id.goodsinfo_share);
        content_service = (LinearLayout) findViewById(R.id.content_service);
        tv_shopcar = (TextView) findViewById(R.id.tv_shopcar);
        tv_soonbuy = (TextView) findViewById(R.id.tv_soonbuy);
        goodsinfo_vp = (GoodsInfoViewPager) findViewById(R.id.goodsinfo_vp);
        goodsinfo_ll_dots = (LinearLayout) findViewById(R.id.goodsinfo_ll_dots);
        goodsinfo_title = (TextView) findViewById(R.id.goodsinfo_title);
        goodsinfo_shop_price = (TextView) findViewById(R.id.goodsinfo_shop_price);
        goodsinfo_market_price = (TextView) findViewById(R.id.goodsinfo_market_price);
        goodsinfo_coupons = (ImageView) findViewById(R.id.goodsinfo_coupons);
        goodsinfo_gv = (MyGridView) findViewById(R.id.goodsinfo_gv);
        tv_sales = (TextView) findViewById(R.id.tv_sales);
        tv_collect = (TextView) findViewById(R.id.tv_collect);
        rb_collect = (CheckBox) findViewById(R.id.rb_collect);
        goods = (TextView) findViewById(R.id.goods);
        tv_attributes = (TextView) findViewById(R.id.attributes);
        commentNumber = (TextView) findViewById(R.id.commentNumber);
        goodsdesc_gv = (MyGridView) findViewById(R.id.goodsdesc_gv);
        goodsinfo_shopping.setOnClickListener(this);
        goodsinfo_back.setOnClickListener(this);
        goodsinfo_share.setOnClickListener(this);
        content_service.setOnClickListener(this);
        tv_shopcar.setOnClickListener(this);
        tv_soonbuy.setOnClickListener(this);
        goods.setOnClickListener(this);
        tv_attributes.setOnClickListener(this);
        commentNumber.setOnClickListener(this);
        rb_collect.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.goodsinfo_shopping:
//                Intent show=new Intent(GoodsInfoActivity.this,CartFragment.class);
//                show.putExtra("grxx",1);
//                startActivity(show);
//                finish();
                break;
            case R.id.goodsinfo_back:
                GoodsInfoActivity.this.finish();
                break;
            case R.id.goodsinfo_share:
                //实例化SelectPicPopupWindow
                menuWindow = new SelectSharePopupWindow(GoodsInfoActivity.this, itemsOnClick);
                //显示窗口
                menuWindow.showAtLocation(GoodsInfoActivity.this.findViewById(R.id.main), Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
                break;
            case R.id.content_service:
                break;
            case R.id.tv_shopcar:
                //实例化SelectPicPopupWindow
                menuWindows = new AddShopCarPopupWindow(GoodsInfoActivity.this, itemsOnClick, goodsInfo);
                //显示窗口
                menuWindows.showAtLocation(GoodsInfoActivity.this.findViewById(R.id.main), Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
                break;
            case R.id.tv_soonbuy:
                //实例化SelectPicPopupWindow
                menuWindows = new AddShopCarPopupWindow(GoodsInfoActivity.this, itemsOnClick, goodsInfo);
                //显示窗口
                menuWindows.showAtLocation(GoodsInfoActivity.this.findViewById(R.id.main), Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
                break;
            case R.id.rb_collect:
                rb_collect.setTextColor(Color.RED);
                break;
            case R.id.goods:
                //产品详情
                String goods_desc = goodsInfo.data.goods.goods_desc;
                Gson gson1 = new Gson();
                goodsDesc = gson1.fromJson(goods_desc, GoodsDesc[].class);
                goodsdesc_gv.setAdapter(new MyAdapter(GoodsInfoActivity.this, goodsDesc));
                goods.setTextColor(Color.RED);
                tv_attributes.setTextColor(Color.GRAY);
                commentNumber.setTextColor(Color.GRAY);
                break;
            case R.id.attributes:
                //产品参数
                List<GoodsInfo.DataBean.GoodsBean.AttributesBean> attributes = goodsInfo.data.goods.attributes;
                goodsdesc_gv.setAdapter(new CommonAdapter<GoodsInfo.DataBean.GoodsBean.AttributesBean>(GoodsInfoActivity.this, R.layout.attributes_gv_item, attributes) {
                    @Override
                    protected void convert(ViewHolder viewHolder, GoodsInfo.DataBean.GoodsBean.AttributesBean item, int position) {
                        TextView attr_name = viewHolder.getView(R.id.attr_name);
                        TextView attr_value = viewHolder.getView(R.id.attr_value);
                        attr_name.setText(item.attr_name);
                        attr_value.setText(item.attr_value);
                    }
                });
                goods.setTextColor(Color.GRAY);
                tv_attributes.setTextColor(Color.RED);
                commentNumber.setTextColor(Color.GRAY);
                break;
            case R.id.commentNumber:
                //评论
                List<GoodsInfo.DataBean.CommentsBean> comments = goodsInfo.data.comments;
                goodsdesc_gv.setAdapter(new CommonAdapter<GoodsInfo.DataBean.CommentsBean>(GoodsInfoActivity.this, R.layout.comments_gv_item, comments) {
                    @Override
                    protected void convert(ViewHolder viewHolder, GoodsInfo.DataBean.CommentsBean item, int position) {
                        ImageView comments_gv_item_img = viewHolder.getView(R.id.comments_gv_item_img);
                        TextView comments_gv_item_name = viewHolder.getView(R.id.comments_gv_item_name);
                        TextView comments_gv_item_content = viewHolder.getView(R.id.comments_gv_item_content);
                        TextView comments_gv_item_createtime = viewHolder.getView(R.id.comments_gv_item_createtime);
                        ImageLoader.getInstance().displayImage(item.user.icon, comments_gv_item_img);
                        comments_gv_item_name.setText(item.user.nick_name);
                        comments_gv_item_content.setText(item.content);
                        comments_gv_item_createtime.setText(item.createtime);
                    }
                });
                goods.setTextColor(Color.GRAY);
                tv_attributes.setTextColor(Color.GRAY);
                commentNumber.setTextColor(Color.RED);
                break;
        }
    }

    //为弹出窗口实现监听类
    private View.OnClickListener itemsOnClick = new View.OnClickListener() {
        public void onClick(View v) {
//            menuWindow.dismiss();
            switch (v.getId()) {
                case R.id.tv_qq:
                    new ShareAction(GoodsInfoActivity.this).setPlatform(SHARE_MEDIA.QQ)
                            .withText("hello")
                            .setCallback(umShareListener)
                            .share();
                    break;
                case R.id.tv_weixin:
                    break;
                case R.id.tv_sina:
                    break;
                case R.id.ok:

                    dao.add(new Car(goodsInfo.data.goods.goods_name, goodsInfo.data.goods.shop_price, goodsInfo.data.goods.goods_img,AddShopCarPopupWindow.pop_text.getText().toString(), null, false,goodsInfo.data.goods.id));
                    Log.e("TAG","&&&&&&&&&&&&&&&&&"+goodsInfo.data.goods.goods_name);
                    Toast.makeText(GoodsInfoActivity.this,"已加入购物车",Toast.LENGTH_SHORT).show();
                    menuWindows.dismiss();
                    break;
                case R.id.reduce:
                    --i;
                    if (i<=1){
                        i=1;
                        AddShopCarPopupWindow.reduce.setImageResource(R.mipmap.reduce_unable);
                    }else {
                        AddShopCarPopupWindow.reduce.setImageResource(R.mipmap.reduce_able);
                        AddShopCarPopupWindow.add.setImageResource(R.mipmap.add_able);
                    }
                    AddShopCarPopupWindow.pop_text.setText(i+"");
                    break;
                case R.id.add:
                    ++i;
                    if (i>=goodsInfo.data.goods.restrict_purchase_num){
                       i=goodsInfo.data.goods.restrict_purchase_num;
                        AddShopCarPopupWindow.add.setImageResource(R.mipmap.add_unable);

                    }else {
                        AddShopCarPopupWindow.add.setImageResource(R.mipmap.add_able);
                        AddShopCarPopupWindow.reduce.setImageResource(R.mipmap.reduce_able);
                    }
                    AddShopCarPopupWindow.pop_text.setText(i+"");
                    break;
                case R.id.addcart_cancel:
                    menuWindows.dismiss();
                    break;
                default:
                    break;
            }
        }

    };
    private UMShareListener umShareListener = new UMShareListener() {
        @Override
        public void onResult(SHARE_MEDIA platform) {
            Log.d("plat", "platform" + platform);
            Toast.makeText(GoodsInfoActivity.this, platform + " 分享成功啦", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onError(SHARE_MEDIA platform, Throwable t) {
            Toast.makeText(GoodsInfoActivity.this, platform + " 分享失败啦", Toast.LENGTH_SHORT).show();
            if (t != null) {
                Log.d("throw", "throw:" + t.getMessage());
            }
        }

        @Override
        public void onCancel(SHARE_MEDIA platform) {
            Toast.makeText(GoodsInfoActivity.this, platform + " 分享取消了", Toast.LENGTH_SHORT).show();
        }
    };
}
