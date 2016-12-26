package com.bwie.test.ynf_project.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.bwie.test.ynf_project.R;
import com.bwie.test.ynf_project.activity.GoodsInfoActivity;
import com.bwie.test.ynf_project.base.BaseData;
import com.bwie.test.ynf_project.base.BaseFragment;
import com.bwie.test.ynf_project.bean.GoodsCategory;
import com.bwie.test.ynf_project.utils.CommonUtils;
import com.bwie.test.ynf_project.utils.URLUtils;
import com.bwie.test.ynf_project.view.ShowingPage;
import com.google.gson.Gson;
import com.liaoinstan.springview.container.DefaultFooter;
import com.liaoinstan.springview.container.DefaultHeader;
import com.liaoinstan.springview.widget.SpringView;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.zhy.adapter.abslistview.CommonAdapter;
import com.zhy.adapter.abslistview.ViewHolder;

/**
 * Created by Administrator on 2016/12/8.
 */
public class MaskFragment extends BaseFragment implements SpringView.OnFreshListener {
    public static final int SUCCESS = 0;
    private GridView frag_mask_gv;
    private GoodsCategory.DataBean dataBean;
    private SpringView mask_frag_sv;
    private String id;
    private GoodsCategory goodsCategory;
    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
           GoodsCategory goodsCategory = (GoodsCategory) msg.obj;
            initAdapter(goodsCategory);
        }
    };
    @Override
    public void onload() {
        MaskFragment.this.showCurrentPage(ShowingPage.StateType.STATE_LOAD_SUCCESS);
    }

    @Override
    public View createSuccessView() {
        View view = CommonUtils.inflate(R.layout.fragment);
        frag_mask_gv = (GridView) view.findViewById(R.id.frag_mask_gv);
        mask_frag_sv = (SpringView) view.findViewById(R.id.mask_frag_sv);
        mask_frag_sv.setHeader(new DefaultHeader(getActivity()));
        mask_frag_sv.setFooter(new DefaultFooter(getActivity()));
        mask_frag_sv.setListener(this);
        mask_frag_sv.setType(SpringView.Type.FOLLOW);
        id = getArguments().getString("id");
        getData(id, BaseData.NORMALTIME);
        return view;
    }

    private void getData(String id, int time) {
        new BaseData() {
            @Override
            protected void setResultData(String data) {
                Gson gson = new Gson();
                goodsCategory = gson.fromJson(data, GoodsCategory.class);
               handler.obtainMessage(SUCCESS,goodsCategory).sendToTarget();
            }

            @Override
            protected void setResultError(ShowingPage.StateType stateLoadError) {

            }
        }.getData(URLUtils.GOODSCATEGORY,URLUtils.GOODSCATEGORYARGS+id,0,time);
    }


    public static Fragment getInstance(String urlId) {
        MaskFragment maskFragment = new MaskFragment();
        Bundle args = new Bundle();
        args.putSerializable("id", urlId);
        maskFragment.setArguments(args);
        return maskFragment;
    }
    private void initAdapter(final GoodsCategory goodsCategory) {
        frag_mask_gv.setAdapter(new CommonAdapter<GoodsCategory.DataBean>(getActivity(),R.layout.goods_gv_item,goodsCategory.data) {
            @Override
            protected void convert(ViewHolder viewHolder, GoodsCategory.DataBean item, int position) {
                ImageView goods_item_iv = viewHolder.getView(R.id.goods_item_iv);
                ImageView goods_url = viewHolder.getView(R.id.goods_url);
                TextView goods_gv_item_name = viewHolder.getView(R.id.goods_gv_item_name);
                TextView goods_gv_item_eff = viewHolder.getView(R.id.goods_gv_item_eff);
                TextView goods_shop_price = viewHolder.getView(R.id.goods_shop_price);
                TextView goods_market_price = viewHolder.getView(R.id.goods_market_price);
                ImageLoader.getInstance().displayImage(item.goods_img, goods_item_iv);
                ImageLoader.getInstance().displayImage(item.watermarkUrl, goods_url);
                goods_gv_item_name.setText(item.goods_name);
                goods_gv_item_eff.setText(item.efficacy);
                goods_shop_price.setText("￥" + item.shop_price);
                goods_market_price.setText("￥" + item.market_price);
            }
        });
        frag_mask_gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), GoodsInfoActivity.class);
                intent.putExtra("id",goodsCategory.data.get(position).id);
                startActivity(intent);
            }
        });
    }
    public void stopLoad() {
       mask_frag_sv.onFinishFreshAndLoad();
    }
    @Override
    public void onRefresh() {
        getData(id,BaseData.NORMALTIME);
        stopLoad();
    }

    @Override
    public void onLoadmore() {
    stopLoad();
    }
}
