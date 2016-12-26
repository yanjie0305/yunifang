package com.bwie.test.ynf_project.activity;

import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bwie.test.ynf_project.R;

public class MyOrderActivity extends AppCompatActivity {

    private ViewPager my_order_vp;
    private ImageView my_order_back;
    private TextView my_order_refund;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_order);
        my_order_vp = (ViewPager) findViewById(R.id.my_order_vp);
        my_order_back = (ImageView) findViewById(R.id.my_order_back);
        my_order_refund = (TextView) findViewById(R.id.my_order_refund);
        TextView tv_all = (TextView) findViewById(R.id.tv_all);
        TextView tv_pay = (TextView) findViewById(R.id.tv_pay);
        TextView tv_send = (TextView) findViewById(R.id.tv_send);
        TextView tv_receive = (TextView) findViewById(R.id.tv_receive);
        TextView tv_evaluate = (TextView) findViewById(R.id.tv_evaluate);

    }
}
