package com.bwie.test.ynf_project.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.bwie.test.ynf_project.R;
import com.bwie.test.ynf_project.base.BaseData;
import com.bwie.test.ynf_project.bean.AllGoods;
import com.bwie.test.ynf_project.utils.ImageLoaderUtils;
import com.bwie.test.ynf_project.utils.URLUtils;
import com.bwie.test.ynf_project.view.ShowingPage;
import com.google.gson.Gson;
import com.liaoinstan.springview.container.DefaultFooter;
import com.liaoinstan.springview.container.DefaultHeader;
import com.liaoinstan.springview.widget.SpringView;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.zhy.adapter.abslistview.CommonAdapter;
import com.zhy.adapter.abslistview.ViewHolder;

import java.util.Collections;

public class AllGoodsActivity extends AppCompatActivity implements View.OnClickListener, SpringView.OnFreshListener {
    private SpringView allgoods_springView;
    private AllGoods allGoods;
    private BaseAdapter adapter;
    private GridView allgoods_gv;
    private ImageView allgoods_back;
    private RadioGroup allgoods_rg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_goods);
        initView();
        initData();
        initCheckChange();
    }

    private void initCheckChange() {
        allgoods_rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                for (int i = 0; i < allgoods_rg.getChildCount(); i++) {
                    RadioButton radioButton = (RadioButton) allgoods_rg.getChildAt(i);
                    if (allgoods_rg.getChildAt(i).getId()== checkedId) {
                        radioButton.setChecked(true);
                        radioButton.setTextColor(getResources().getColor(R.color.colorTextMain));
                        if (i == 0) {
//                            adapter = null;
                            initData();

                        } else if (i == 1) {
                            Collections.sort(allGoods.data,new AllGoods.DataBean());
                            adapter.notifyDataSetChanged();
                        }else {
                            Collections.reverse(allGoods.data);
                            adapter.notifyDataSetChanged();
                        }
                    }else {
                        radioButton.setChecked(false);
                        radioButton.setTextColor(getResources().getColor(R.color.colorTextBlack));
                    }
                }

            }
        });
    }

    private void initData() {
        new BaseData() {
            @Override
            protected void setResultData(String data) {
                Log.e("TAG", "*************" + data);
                Gson gson = new Gson();
                allGoods = gson.fromJson(data, AllGoods.class);
                Log.e("TAG", "&&&&&&&&&&&&&&&&&&&&&&&" + allGoods);
                setBaseAdapter();

            }

            @Override
            protected void setResultError(ShowingPage.StateType stateLoadError) {

            }
        }.getData(URLUtils.ALLGOODS, URLUtils.ALLGOODSARGS, 0, BaseData.NORMALTIME);
    }

    private void initView() {
        allgoods_gv = (GridView) findViewById(R.id.allgoods_gv);
        allgoods_rg = (RadioGroup) findViewById(R.id.allgoods_rg);
        allgoods_back = (ImageView) findViewById(R.id.allgoods_back);
        allgoods_back.setOnClickListener(this);

        allgoods_springView = (SpringView) findViewById(R.id.allgoods_springView);
        allgoods_springView.setHeader(new DefaultHeader(this));
        allgoods_springView.setFooter(new DefaultFooter(this));
        allgoods_springView.setListener(this);
        allgoods_springView.setType(SpringView.Type.FOLLOW);

    }

    private void setBaseAdapter() {

        adapter = new CommonAdapter<AllGoods.DataBean>(AllGoodsActivity.this, R.layout.allgoods_gv_item, allGoods.data) {
            @Override
            protected void convert(ViewHolder viewHolder, AllGoods.DataBean item, int position) {
                TextView allgoods_gv_item_eff = viewHolder.getView(R.id.allgoods_gv_item_eff);
                TextView allgoods_gv_item_name = viewHolder.getView(R.id.allgoods_gv_item_name);
                TextView allgoods_shop_price = viewHolder.getView(R.id.allgoods_shop_price);
                TextView allgoods_market_price = viewHolder.getView(R.id.allgoods_market_price);
                ImageView allgoods_item_iv = viewHolder.getView(R.id.allgoods_item_iv);

                ImageLoader.getInstance().displayImage(item.goods_img, allgoods_item_iv, ImageLoaderUtils.initOptions());
                allgoods_item_iv.setScaleType(ImageView.ScaleType.CENTER_CROP);
                allgoods_gv_item_eff.setText(item.efficacy);
                allgoods_gv_item_name.setText(item.goods_name);
                allgoods_shop_price.setText("￥" + item.shop_price);
                allgoods_market_price.setText("￥" + item.market_price);
            }
        };
        allgoods_gv.setAdapter(adapter);
        allgoods_gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(AllGoodsActivity.this, GoodsInfoActivity.class);
                intent.putExtra("id", allGoods.data.get(position).id);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.allgoods_back:
                finish();
                break;

        }
    }

    public void stopLoad() {
        allgoods_springView.scrollTo(0, 0);
    }

    @Override
    public void onRefresh() {

        initData();
        stopLoad();
    }

    @Override
    public void onLoadmore() {
        stopLoad();
    }
}
