package com.bwie.test.ynf_project.activity;

import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.bwie.test.ynf_project.R;
import com.bwie.test.ynf_project.fragment.FragmentFactory;
import com.zhy.autolayout.AutoLayoutActivity;

public class MainActivity extends AutoLayoutActivity {

    public static ViewPager vp;
    private RadioGroup rg_main;
    private RadioButton rb_home;
    private RadioButton rb_category;
    private RadioButton rb_cart;
    private RadioButton rb_mine;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        vp = (ViewPager) findViewById(R.id.vp);
        rg_main = (RadioGroup) findViewById(R.id.rg_main);
        rb_home = (RadioButton) findViewById(R.id.rb_home);
        rb_category = (RadioButton) findViewById(R.id.rb_category);
        rb_cart = (RadioButton) findViewById(R.id.rb_cart);
        rb_mine = (RadioButton) findViewById(R.id.rb_mine);
        vp.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return FragmentFactory.getFragent(position);
            }

            @Override
            public int getCount() {
                return 4;
            }
        });
        rg_main.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                for (int i = 0; i <rg_main.getChildCount() ; i++) {
                   RadioButton rb = (RadioButton) rg_main.getChildAt(i);
                    if (rg_main.getChildAt(i).getId()==checkedId){
                        vp.setCurrentItem(i);
                        rb.setTextColor(Color.RED);
                    }else {
                        rb.setTextColor(Color.GRAY);
                    }
                }
            }
        });
        vp.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                for (int i = 0; i <rg_main.getChildCount() ; i++) {
                    RadioButton rb = (RadioButton) rg_main.getChildAt(i);
                    if (i==position){
                        rb.setChecked(true);
                        rb.setTextColor(Color.RED);
                    }else {
                        rb.setChecked(false);
                        rb.setTextColor(Color.GRAY);
                    }
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }
}
