package com.bwie.test.ynf_project.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.bwie.test.ynf_project.R;
import com.bwie.test.ynf_project.bean.GoodsDesc;
import com.bwie.test.ynf_project.utils.ImageLoaderUtils;
import com.nostra13.universalimageloader.core.ImageLoader;

/**
 * Created by Administrator on 2016/12/9.
 */
public class MyAdapter extends BaseAdapter {
    Context context ;
    GoodsDesc[] goodsDescs;

    public MyAdapter(Context context, GoodsDesc[] goodsDescs) {
        this.context = context;
        this.goodsDescs = goodsDescs;
    }

    @Override
    public int getCount() {
        return goodsDescs.length;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = View.inflate(context, R.layout.goodsdesc_gv_item, null);
       ImageView goodsdesc_gv_item_img = (ImageView) view.findViewById(R.id.goodsdesc_gv_item_img);
        ImageLoader.getInstance().displayImage(goodsDescs[position].url,goodsdesc_gv_item_img, ImageLoaderUtils.initOptions());
        return view;
    }
}
