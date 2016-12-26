package com.bwie.test.ynf_project.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bwie.test.ynf_project.activity.AllGoodsActivity;
import com.bwie.test.ynf_project.activity.GoodsInfoActivity;
import com.bwie.test.ynf_project.activity.InfoActivity;
import com.bwie.test.ynf_project.activity.MoreActivity;
import com.bwie.test.ynf_project.R;
import com.bwie.test.ynf_project.base.BaseData;
import com.bwie.test.ynf_project.base.BaseFragment;
import com.bwie.test.ynf_project.bean.HomePage;
import com.bwie.test.ynf_project.utils.CommonUtils;
import com.bwie.test.ynf_project.utils.ImageLoaderUtils;
import com.bwie.test.ynf_project.utils.URLUtils;
import com.bwie.test.ynf_project.view.MyGridView;
import com.bwie.test.ynf_project.view.MyListView;
import com.bwie.test.ynf_project.view.MyRoolViewPager;
import com.bwie.test.ynf_project.view.ShowingPage;
import com.google.gson.Gson;
import com.liaoinstan.springview.container.DefaultHeader;
import com.liaoinstan.springview.widget.SpringView;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.zhy.adapter.abslistview.CommonAdapter;
import com.zhy.adapter.abslistview.ViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/11/28.
 */
public class HomeFragment extends BaseFragment implements SpringView.OnFreshListener {

    private String data;
    private MyRoolViewPager home_vp;
    private LinearLayout ll_dots;
    public ArrayList<String> imgUrlList = new ArrayList<>();
    public ArrayList<ImageView> dotsList = new ArrayList<>();
    private HomePage homePage;
    int[] dotArray = new int[]{R.mipmap.page_indicator_focused, R.mipmap.page_indicator_unfocused};
    public static GridView gv_type;
    private TextView week_name;
    private TextView week_goods_name;
    private TextView week_shop_price;
    private LinearLayout lin_week;
    private ViewPager viewPager;
    private MyListView home_lv;
    private MyGridView goods_gv;
    private TextView checkall;
    private SpringView springView;
    private List<HomePage.DataBean.DefaultGoodsListBean> defaultGoodsList;
    private List<HomePage.DataBean.SubjectsBean> subjects;
    private ImageView imageView;
    private TextView week_market_price;
    private ImageView week_iv;
    private RelativeLayout week_more;


    @Override
    public void onload() {
        new BaseData() {
            @Override
            protected void setResultData(String data) {
                if (TextUtils.isEmpty(data)) {
                    HomeFragment.this.showCurrentPage(ShowingPage.StateType.STATE_LOAD_EMPTY);
                } else {
                    HomeFragment.this.data = data;
                    HomeFragment.this.showCurrentPage(ShowingPage.StateType.STATE_LOAD_SUCCESS);

                }

                //初始化数据
                initData(HomeFragment.this.data);

                Log.i("TAG", ": initViewPager----------------" + data);

                //初始化viewpager轮播图
                initViewPager();
                //给导航栏设置适配器
                initTypeAdapter();
                //设置本周热销数据
                initweek();
                //优惠活动
                initSpecialOffer();
                //热销专题
                initHot();

                initGvData();

                checkall.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getActivity(), AllGoodsActivity.class);
                        startActivity(intent);
                    }
                });

            }

            @Override
            protected void setResultError(ShowingPage.StateType stateLoadError) {

            }
        }.getData(URLUtils.homeUrl, URLUtils.homeArgs, 0, BaseData.NORMALTIME);
    }

    @Override
    public View createSuccessView() {
        View view = CommonUtils.inflate(R.layout.home_page);
        initView(view);
        return view;
    }

    private void initGvData() {
        defaultGoodsList = homePage.data.defaultGoodsList;
        goods_gv.setAdapter(new CommonAdapter<HomePage.DataBean.DefaultGoodsListBean>(getActivity(), R.layout.goods_gv_item, defaultGoodsList) {
            @Override
            protected void convert(ViewHolder viewHolder, HomePage.DataBean.DefaultGoodsListBean item, int position) {
                ImageView goods_item_iv = viewHolder.getView(R.id.goods_item_iv);
                TextView goods_gv_item_eff = viewHolder.getView(R.id.goods_gv_item_eff);
                TextView goods_gv_item_name = viewHolder.getView(R.id.goods_gv_item_name);
                TextView goods_shop_price = viewHolder.getView(R.id.goods_shop_price);
                TextView goods_market_price = viewHolder.getView(R.id.goods_market_price);
                ImageLoader.getInstance().displayImage(item.goods_img, goods_item_iv);
                goods_item_iv.setScaleType(ImageView.ScaleType.CENTER_CROP);
                goods_gv_item_eff.setText(item.efficacy);
                goods_gv_item_name.setText(item.goods_name);
                goods_shop_price.setText("￥" + item.shop_price);
                goods_market_price.setText("￥" + item.market_price);
            }
        });
        goods_gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(),GoodsInfoActivity.class);
                intent.putExtra("id",defaultGoodsList.get(position).id);
                startActivity(intent);
            }
        });
    }
    //给导航栏设置适配器

    private void initTypeAdapter() {
        final List<HomePage.DataBean.Ad5Bean> ad5 = homePage.data.ad5;
        gv_type.setAdapter(new CommonAdapter<HomePage.DataBean.Ad5Bean>(getActivity(), R.layout.gv_type_item, ad5) {
            @Override
            protected void convert(ViewHolder viewHolder, HomePage.DataBean.Ad5Bean item, int position) {
                ImageView gv_type_item_iv = viewHolder.getView(R.id.gv_type_item_iv);
                ImageLoader.getInstance().displayImage(item.image, gv_type_item_iv, ImageLoaderUtils.initOptions());
                TextView gv_type_item_title = viewHolder.getView(R.id.gv_type_item_title);
                gv_type_item_title.setText(item.title);
            }
        });
        gv_type.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), InfoActivity.class);
                intent.putExtra("webUrl", ad5.get(position).ad_type_dynamic_data);
                startActivity(intent);
            }
        });
    }


    //热销
    private void initHot() {
        subjects = homePage.data.subjects;
        home_lv.setAdapter(new CommonAdapter<HomePage.DataBean.SubjectsBean>(getActivity(), R.layout.home_lv_item, subjects) {
            @Override
            protected void convert(ViewHolder viewHolder, final HomePage.DataBean.SubjectsBean item, int position) {
                final ImageView hot_iv = viewHolder.getView(R.id.hot_iv);
                ImageLoader.getInstance().displayImage(item.image, hot_iv, ImageLoaderUtils.initOptions());
                hot_iv.setScaleType(ImageView.ScaleType.CENTER_CROP);
                hot_iv.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getActivity(), MoreActivity.class);
                        intent.putExtra("subjects",item);
                        startActivity(intent);
                    }
                });
                final LinearLayout lin_hot = viewHolder.getView(R.id.lin_hot);
                RelativeLayout more = viewHolder.getView(R.id.more);
                more.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                    Intent intent = new Intent(getActivity(), MoreActivity.class);
                        intent.putExtra("subjects",item);
                        startActivity(intent);
                    }
                });
                final List<HomePage.DataBean.SubjectsBean.GoodsListBean> goodsList1 = homePage.data.subjects.get(position).goodsList;
                lin_hot.removeAllViews();
                for (int i = 0; i < 6; i++) {
                    View view2 = CommonUtils.inflate(R.layout.line_hot_item);
                    ImageView hot_item_iv = (ImageView) view2.findViewById(R.id.hot_item_iv);
                    TextView hot_goods_name = (TextView) view2.findViewById(R.id.hot_goods_name);
                    TextView hot_shop_price = (TextView) view2.findViewById(R.id.hot_shop_price);
                    TextView hot_market_price = (TextView) view2.findViewById(R.id.hot_market_price);
                    ImageLoader.getInstance().displayImage(goodsList1.get(i).goods_img, hot_item_iv);
                    hot_goods_name.setText(goodsList1.get(i).goods_name);
                    hot_shop_price.setText("￥" + goodsList1.get(i).shop_price);
                    hot_market_price.setText("￥" + goodsList1.get(i).market_price);
                    lin_hot.addView(view2);

                    lin_hot.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            for (int i = 0; i < lin_hot.getChildCount(); i++) {
                                Intent intent = new Intent(getActivity(),GoodsInfoActivity.class);
                                intent.putExtra("id",goodsList1.get(i).id);
                                startActivity(intent);
                            }
                        }
                    });
                }
            }
        });
    }

    //优惠活动
    private void initSpecialOffer() {
        final List<HomePage.DataBean.ActivityInfoBean.ActivityInfoListBean> activityInfoList = homePage.data.activityInfo.activityInfoList;
        //设置Page间间距
        viewPager.setPageMargin(20);
        //设置缓存的页面数量
        viewPager.setOffscreenPageLimit(3);

        viewPager.setAdapter(new PagerAdapter() {
            @Override
            public int getCount() {
                return Integer.MAX_VALUE;
            }

            @Override
            public boolean isViewFromObject(View view, Object object) {
                return view == object;
            }

            @Override
            public Object instantiateItem(ViewGroup container, final int position) {
                imageView = new ImageView(getActivity());
                ImageLoader.getInstance().displayImage(activityInfoList.get(position % activityInfoList.size()).activityImg, imageView, ImageLoaderUtils.initOptions());
               imageView.setOnClickListener(new View.OnClickListener() {
                   @Override
                   public void onClick(View v) {
                       Intent intent = new Intent(getActivity(),InfoActivity.class);
                       intent.putExtra("wenUrl",activityInfoList.get(position%activityInfoList.size()).activityData);
                       startActivity(intent);
                   }
               });
                container.addView(imageView);

                return imageView;
            }

            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {
                container.removeView((View) object);
            }
        });
        viewPager.setCurrentItem(activityInfoList.size() * 10000);

    }
    //本周热销
            private void initweek() {
                week_name.setText(homePage.data.bestSellers.get(0).name);
                final List<HomePage.DataBean.BestSellersBean.GoodsListBean> goodsList = homePage.data.bestSellers.get(0).goodsList;
                for ( int i = 0; i < 6; i++) {
                    View view1 = CommonUtils.inflate(R.layout.line_week_item);
                    week_iv = (ImageView) view1.findViewById(R.id.week_iv);
                    week_goods_name = (TextView) view1.findViewById(R.id.week_goods_name);
                    week_shop_price = (TextView) view1.findViewById(R.id.week_shop_price);
                    week_market_price = (TextView) view1.findViewById(R.id.week_market_price);
                    ImageLoader.getInstance().displayImage(goodsList.get(i).goods_img, week_iv, ImageLoaderUtils.initOptions());
                    week_goods_name.setText(goodsList.get(i).goods_name);
                    week_shop_price.setText("￥" + goodsList.get(i).shop_price);
                    week_market_price.setText("￥" + goodsList.get(i).market_price);
                    lin_week.addView(view1);
                   week_more.setOnClickListener(new View.OnClickListener() {
                       @Override
                       public void onClick(View v) {
                           Intent intent = new Intent(getActivity(), MoreActivity.class);
                           Bundle bundle = new Bundle();
                           bundle.putSerializable("subjects", homePage.data.bestSellers.get(0));
                           intent.putExtras(bundle);
                           startActivity(intent);
                       }
                   });
                    view1.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            for (int i = 0; i < lin_week.getChildCount(); i++) {
                                if (lin_week.getChildAt(i)==v){
                                    Intent intent = new Intent(getActivity(),GoodsInfoActivity.class);
                                    intent.putExtra("id",goodsList.get(i).id);
                                    startActivity(intent);
                                }
                            }

                        }
                    });

                }

            }


    //初始化viewpager轮播图
    private void initViewPager() {
        Log.i("TAG", "homePage: ------------------" + homePage);
        final List<HomePage.DataBean.Ad1Bean> ad1 = homePage.data.ad1;
        for (int i = 0; i < ad1.size(); i++) {
            imgUrlList.add(ad1.get(i).image);
        }
        initDots(ad1);
        home_vp.initData(imgUrlList, dotArray, dotsList);
        home_vp.startViewPager();
        home_vp.setOnPageClickListener(new MyRoolViewPager.OnPageClickListener() {
            @Override
            public void setOnPage(int position) {
                Intent intent = new Intent(getContext(), InfoActivity.class);
                intent.putExtra("webUrl", ad1.get(position % imgUrlList.size()).ad_type_dynamic_data);
                startActivity(intent);

            }
        });
    }


    //找控件
    private void initView(View view) {
        home_vp = (MyRoolViewPager) view.findViewById(R.id.home_vp);
        ll_dots = (LinearLayout) view.findViewById(R.id.ll_dots);
        gv_type = (GridView) view.findViewById(R.id.gv_type);
        week_name = (TextView) view.findViewById(R.id.week_name);
        lin_week = (LinearLayout) view.findViewById(R.id.lin_week);
        viewPager = (ViewPager) view.findViewById(R.id.id_viewpager);
        home_lv = (MyListView) view.findViewById(R.id.home_lv);
        goods_gv = (MyGridView) view.findViewById(R.id.goods_gv);
        checkall = (TextView) view.findViewById(R.id.checkall);
        springView = (SpringView) view.findViewById(R.id.springView);
        springView.setHeader(new DefaultHeader(getActivity()));
        springView.setListener(this);
        springView.setType(SpringView.Type.FOLLOW);
        week_more = (RelativeLayout) view.findViewById(R.id.week_more);
    }

    //获取数据
    private void initData(String data) {
        Log.i("TAG", "initData: ----------------" + data);
        Gson gson = new Gson();
        homePage = gson.fromJson(data, HomePage.class);
        Log.i("TAG", "homePage: ----------------" + homePage);
    }

    //设置小点
    private void initDots(List<HomePage.DataBean.Ad1Bean> ad1) {
        dotsList.clear();
        ll_dots.removeAllViews();
        for (int i = 0; i < ad1.size(); i++) {
            ImageView imageView = new ImageView(getActivity());
            if (i == 0) {
                imageView.setImageResource(dotArray[0]);
            } else {
                imageView.setImageResource(dotArray[1]);
            }
            dotsList.add(imageView);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            params.setMargins(5, 0, 5, 0);
            ll_dots.addView(imageView, params);
        }
    }

    @Override
    public void onRefresh() {

        springView.scrollTo(0, 0);

    }

    @Override
    public void onLoadmore() {

    }
}
