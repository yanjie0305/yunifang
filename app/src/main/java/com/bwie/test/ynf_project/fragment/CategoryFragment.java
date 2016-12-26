package com.bwie.test.ynf_project.fragment;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bwie.test.ynf_project.R;
import com.bwie.test.ynf_project.activity.AllGoodsActivity;
import com.bwie.test.ynf_project.activity.GoodsInfoActivity;
import com.bwie.test.ynf_project.activity.FacialMaskActivity;
import com.bwie.test.ynf_project.base.BaseData;
import com.bwie.test.ynf_project.base.BaseFragment;
import com.bwie.test.ynf_project.bean.Category;
import com.bwie.test.ynf_project.utils.CommonUtils;
import com.bwie.test.ynf_project.utils.URLUtils;
import com.bwie.test.ynf_project.view.MyGridView;
import com.bwie.test.ynf_project.view.ShowingPage;
import com.google.gson.Gson;
import com.liaoinstan.springview.container.DefaultFooter;
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
public class CategoryFragment extends BaseFragment implements View.OnClickListener, SpringView.OnFreshListener {
    private String data;
    private View view;
    private Category category;
    private ImageView category_facial_mask;
    private ImageView category_emollient_water;
    private ImageView category_body_lotion;
    private ImageView category_facial_cleanser;
    private ImageView category_other;
    private ImageView category_kit;
    private MyGridView gongxiao_gv;
    private GridView fuzhi_gv;
    private ArrayList<Integer> pic;
    private ArrayList<Category.DataBean.CategoryBean.ChildrenBean> children;

    private MyGridView category_star_gv;
    private LinearLayout ll_gongxiao;
    private ImageView classify_hydrating;
    private ImageView classify_soothing;
    private ImageView classify_control_oil;
    private ImageView classify_whitening;
    private ImageView classify_firming;
    private TextView queryAll;
    private SpringView category_sv;

    @Override
    public void onload() {
        getData(BaseData.NORMALTIME);
    }

    @Override
    public View createSuccessView() {

        view = CommonUtils.inflate(R.layout.category_page);

        initView(view);

        return view;
    }

    private void getData(int time) {
        BaseData baseData = new BaseData() {
            @Override
            protected void setResultData(String data) {
                Log.e("TAG", "setResultData: **********" + data);
                CategoryFragment.this.data = data;
                CategoryFragment.this.showCurrentPage(ShowingPage.StateType.STATE_LOAD_SUCCESS);
                initData(data);
            }

            @Override
            protected void setResultError(ShowingPage.StateType stateLoadError) {

            }
        };
        baseData.getData(URLUtils.CATEGORY, URLUtils.CATEGORYARGS, 0, time);
    }

    private void initView(View view) {
        view.findViewById(R.id.category_facial_cleanser);
        category_facial_mask = (ImageView) view.findViewById(R.id.category_facial_mask);
        category_emollient_water = (ImageView) view.findViewById(R.id.category_emollient_water);
        category_body_lotion = (ImageView) view.findViewById(R.id.category_body_lotion);
        category_facial_cleanser = (ImageView) view.findViewById(R.id.category_facial_cleanser);
        category_other = (ImageView) view.findViewById(R.id.category_other);
        category_kit = (ImageView) view.findViewById(R.id.category_kit);
        classify_hydrating = (ImageView) view.findViewById(R.id.classify_hydrating);
        classify_soothing = (ImageView) view.findViewById(R.id.classify_soothing);
        classify_control_oil = (ImageView) view.findViewById(R.id.classify_control_oil);
        classify_whitening = (ImageView) view.findViewById(R.id.classify_whitening);
        classify_firming = (ImageView) view.findViewById(R.id.classify_firming);
        fuzhi_gv = (GridView) view.findViewById(R.id.fuzhi_gv);
        category_star_gv = (MyGridView) view.findViewById(R.id.category_star_gv);
        ll_gongxiao = (LinearLayout) view.findViewById(R.id.ll_gongxiao);
        queryAll = (TextView) view.findViewById(R.id.queryAll);
        category_sv = (SpringView) view.findViewById(R.id.category_sv);
        category_sv.setHeader(new DefaultHeader(getActivity()));
        category_sv.setFooter(new DefaultFooter(getActivity()));
        category_sv.setListener(this);
        category_sv.setType(SpringView.Type.FOLLOW);
        category_facial_mask.setOnClickListener(this);
        category_emollient_water.setOnClickListener(this);
        category_body_lotion.setOnClickListener(this);
        category_facial_cleanser.setOnClickListener(this);
        category_other.setOnClickListener(this);
        category_kit.setOnClickListener(this);
        classify_hydrating.setOnClickListener(this);
        classify_soothing.setOnClickListener(this);
        classify_control_oil.setOnClickListener(this);
        classify_whitening.setOnClickListener(this);
        classify_firming.setOnClickListener(this);
        queryAll.setOnClickListener(this);
    }

    public void stopLoad() {
        category_sv.onFinishFreshAndLoad();
    }

    private void initData(String data) {
        Gson gson = new Gson();
        category = gson.fromJson(data, Category.class);
        Log.e("TAG", "initData: **********" + category);
        //按功效点击事件


        //按肤质
        children = (ArrayList<Category.DataBean.CategoryBean.ChildrenBean>) category.data.category.get(2).children;
        Log.e("TAG", "children: **********" + children.size());
        final int[] color = {getResources().getColor(R.color.background1), getResources().getColor(R.color.background2), getResources().getColor(R.color.background3), getResources().getColor(R.color.background4), getResources().getColor(R.color.background5), getResources().getColor(R.color.background6)};
        fuzhi_gv.setAdapter(new CommonAdapter<Category.DataBean.CategoryBean.ChildrenBean>(getActivity(), R.layout.fuzhi_gv_item, children) {
            @Override
            protected void convert(ViewHolder viewHolder, Category.DataBean.CategoryBean.ChildrenBean item, int position) {
                TextView gv_tv = viewHolder.getView(R.id.gv_tv);
                Log.e("TAG", "cat_name: **********" + item.cat_name);
                gv_tv.setText("#" + item.cat_name + "#");
                gv_tv.setTextColor(Color.WHITE);
                gv_tv.setBackgroundColor(color[position]);
            }
        });
        fuzhi_gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                enterActivity(category.data.category.get(2),null,position);
            }
        });
        //明星产品
        final List<Category.DataBean.GoodsBriefBean> goodsBrief = category.data.goodsBrief;
        category_star_gv.setAdapter(new CommonAdapter<Category.DataBean.GoodsBriefBean>(getActivity(), R.layout.goods_gv_item, goodsBrief) {
            @Override
            protected void convert(ViewHolder viewHolder, Category.DataBean.GoodsBriefBean item, int position) {
                ImageView goods_item_iv = viewHolder.getView(R.id.goods_item_iv);
                ImageView goods_url = viewHolder.getView(R.id.goods_url);
                TextView goods_gv_item_name = viewHolder.getView(R.id.goods_gv_item_name);
                TextView goods_gv_item_eff = viewHolder.getView(R.id.goods_gv_item_eff);
                TextView goods_shop_price = viewHolder.getView(R.id.goods_shop_price);
                TextView goods_market_price = viewHolder.getView(R.id.goods_market_price);
                goods_market_price.getPaint().setFlags(Paint. STRIKE_THRU_TEXT_FLAG);
                ImageLoader.getInstance().displayImage(item.goods_img, goods_item_iv);
                ImageLoader.getInstance().displayImage(item.watermarkUrl, goods_url);
                goods_gv_item_name.setText(item.goods_name);
                goods_gv_item_eff.setText(item.efficacy);
                goods_shop_price.setText("￥" + item.shop_price);
                goods_market_price.setText("￥" + item.market_price);
            }
        });
        category_star_gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), GoodsInfoActivity.class);
                intent.putExtra("id", goodsBrief.get(position).id);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.category_facial_mask:
              enterActivity(category.data.category.get(1),category.data.category.get(1).children.get(0),0);

                break;
            case R.id.category_emollient_water:
               enterActivity(category.data.category.get(1),category.data.category.get(1).children.get(1),39);
                break;
            case R.id.category_body_lotion:
                enterActivity(category.data.category.get(1),category.data.category.get(1).children.get(2),40);

                break;
            case R.id.category_facial_cleanser:
                enterActivity(category.data.category.get(1),category.data.category.get(1).children.get(3),24);

                break;
            case R.id.category_other:
                enterActivity(category.data.category.get(1),category.data.category.get(1).children.get(4),35);

                break;
            case R.id.category_kit:
                enterActivity(category.data.category.get(1),category.data.category.get(1).children.get(5),33);

                break;
            case R.id.classify_hydrating:
                enterActivity(category.data.category.get(0),null,0);
                break;
            case R.id.classify_soothing:
                enterActivity(category.data.category.get(0),null,1);
                break;
            case R.id.classify_control_oil:
                enterActivity(category.data.category.get(0),null,2);
                break;
            case R.id.classify_whitening:
                enterActivity(category.data.category.get(0),null,3);
                break;
            case R.id.classify_firming:
                enterActivity(category.data.category.get(0),null,4);
                break;
            case R.id.queryAll:
               Intent intent = new Intent(getActivity(),AllGoodsActivity.class);
                startActivity(intent);
                break;
        }

    }

    public void enterActivity(Category.DataBean.CategoryBean bean,Category.DataBean.CategoryBean.ChildrenBean child,int id) {
        Intent intent = new Intent(getActivity(),FacialMaskActivity.class );
        Bundle bundle = new Bundle();
        bundle.putSerializable("mask",bean);
        bundle.putSerializable("child",child);
        intent.putExtra("id",id);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    @Override
    public void onRefresh() {
        getData(BaseData.NORMALTIME);
        stopLoad();
    }

    @Override
    public void onLoadmore() {
        stopLoad();
    }
}
