package com.bwie.test.ynf_project.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.bwie.test.ynf_project.R;
import com.bwie.test.ynf_project.bean.HomePage;
import com.bwie.test.ynf_project.utils.ImageLoaderUtils;
import com.nostra13.universalimageloader.core.ImageLoader;

public class MoreActivity extends AppCompatActivity {

    private ImageView back;
    private TextView goods_detail;
    private GridView more_gv;
    private HomePage.DataBean.SubjectsBean subjects;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_more);
        back = (ImageView) findViewById(R.id.back);
        goods_detail = (TextView) findViewById(R.id.goods_detail);
        more_gv = (GridView) findViewById(R.id.more_gv);
        subjects = (HomePage.DataBean.SubjectsBean) getIntent().getSerializableExtra("subjects");
        goods_detail.setText(subjects.detail);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

       more_gv.setAdapter(new BaseAdapter() {
           @Override
           public int getCount() {
               return subjects.goodsList.size();
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
               View view = View.inflate(MoreActivity.this, R.layout.allgoods_gv_item, null);
               ImageView more_img = (ImageView) view.findViewById(R.id.allgoods_item_iv);
               TextView more_name = (TextView) view.findViewById(R.id.allgoods_gv_item_name);
               TextView allgoods_gv_item_eff = (TextView) view.findViewById(R.id.allgoods_gv_item_eff);
               TextView allgoods_shop_price = (TextView) view.findViewById(R.id.allgoods_shop_price);
               TextView allgoods_market_price = (TextView) view.findViewById(R.id.allgoods_market_price);

               ImageLoader.getInstance().displayImage(subjects.goodsList.get(position).goods_img,more_img, ImageLoaderUtils.initOptions());
               more_name.setText(subjects.goodsList.get(position).goods_name);
               allgoods_gv_item_eff.setText(subjects.goodsList.get(position).efficacy);
               allgoods_shop_price.setText("￥"+subjects.goodsList.get(position).shop_price);
               allgoods_market_price.setText("￥"+subjects.goodsList.get(position).market_price);
               return view;
           }
       });
    }
}
