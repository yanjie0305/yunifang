package com.bwie.test.ynf_project.activity;

import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.bwie.test.ynf_project.R;
import com.bwie.test.ynf_project.base.BaseData;
import com.bwie.test.ynf_project.bean.Category;
import com.bwie.test.ynf_project.bean.GoodsCategory;
import com.bwie.test.ynf_project.fragment.MaskFragment;
import com.bwie.test.ynf_project.utils.CommonUtils;
import com.bwie.test.ynf_project.utils.URLUtils;
import com.bwie.test.ynf_project.view.ShowingPage;
import com.google.gson.Gson;
import com.zhy.autolayout.AutoLayoutActivity;

import java.io.Serializable;

public class FacialMaskActivity extends AutoLayoutActivity implements View.OnClickListener {

    private ImageView title_back;
    private TextView title_text;
    private RadioGroup mask_rg;
    private ViewPager mask_vp;
    private int id;
    private GoodsCategory goodsCategory;
    private HorizontalScrollView hs;
    private Category.DataBean.CategoryBean maskData;
    private Category.DataBean.CategoryBean.ChildrenBean child;
    private LinearLayout mask_lin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_facial_mask);
        getSupportActionBar().hide();
        initView();
    }

    //初始化控件
    private void initView() {
        title_back = (ImageView) findViewById(R.id.title_back);
        title_text = (TextView) findViewById(R.id.title_text);
        mask_rg = (RadioGroup) findViewById(R.id.mask_rg);
        mask_vp = (ViewPager) findViewById(R.id.mask_vp);
         hs = (HorizontalScrollView) findViewById(R.id.hs);
        id = getIntent().getIntExtra("id", -1);
        maskData = (Category.DataBean.CategoryBean) getIntent().getSerializableExtra("mask");
        child = (Category.DataBean.CategoryBean.ChildrenBean) getIntent().getSerializableExtra("child");
        title_text.setText("面膜");
        title_back.setOnClickListener(this);
        //按类型划分，显示不同的界面
        if (maskData.cat_name.equals("按功效")) {
            virtueMask();
        } else if (maskData.cat_name.equals("按属性")) {
            natureMask();
        } else if (maskData.cat_name.equals("按肤质")) {
            skinMask();
        }
    }

    //按肤质
    private void skinMask() {
        hs.setVisibility(View.VISIBLE);
        mask_rg.setVisibility(View.GONE);
        title_text.setText(maskData.cat_name);
        RadioGroup hotiradioGroup = new RadioGroup(this);
        hotiradioGroup.setOrientation(RadioGroup.HORIZONTAL);
        for (int i = 0; i < maskData.children.size(); i++) {
            RadioButton radioButton = initRadioButton(0, i);
            RadioGroup.LayoutParams params = new RadioGroup.LayoutParams(200, RadioGroup.LayoutParams.MATCH_PARENT, 1.0f);
            hotiradioGroup.addView(radioButton, params);
            initData(hotiradioGroup, maskData.children.size(), 0);
            mask_vp.setCurrentItem(id);
        }
        hs.addView(hotiradioGroup, new RadioGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
    }

    //按属性
    private void natureMask() {
        hs.setVisibility(View.GONE);
        if (id == 0) {
            title_text.setText("面膜");
            mask_rg.setVisibility(View.VISIBLE);
            for (int i = 6; i < maskData.children.size(); i++) {
                RadioButton radioButton = initRadioButton(6, i);
                RadioGroup.LayoutParams params = new RadioGroup.LayoutParams(0, RadioGroup.LayoutParams.MATCH_PARENT, 1.0f);
                mask_rg.addView(radioButton, params);
            }
            initData(mask_rg, 3, -1);
        } else {
            title_text.setText(child.cat_name);
            mask_rg.setVisibility(View.GONE);
            initData(mask_rg, 1, Integer.parseInt(child.id));
        }
    }

    private void initData(final RadioGroup mask_rg, final int size, final int typeId) {
        mask_rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                for (int i = 0; i < group.getChildCount(); i++) {
                    if (group.getChildAt(i).getId() == checkedId) {
                        mask_vp.setCurrentItem(i);
                    }
                }
            }
        });
        mask_vp.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                if (typeId == 0) {
                    return MaskFragment.getInstance(maskData.children.get(position).id + "");
                } else if (typeId == -1) {
                    return MaskFragment.getInstance(maskData.children.get(position + 6).id + "");
                } else {
                    return MaskFragment.getInstance(typeId + "");
                }

            }

            @Override
            public int getCount() {
                return size;
            }
        });
        mask_vp.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                for (int i = 0; i < mask_rg.getChildCount(); i++) {
                    RadioButton radioButton = (RadioButton) mask_rg.getChildAt(i);
                    if (i == position) {
                        radioButton.setChecked(true);
                        radioButton.setTextColor(getResources().getColor(R.color.colorTextMain));
                    } else {
                        radioButton.setChecked(false);
                        radioButton.setTextColor(getResources().getColor(R.color.colorTextBlack));
                    }
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });

    }

    private RadioButton initRadioButton(int selectId, int i) {
        RadioButton radioButton = (RadioButton) CommonUtils.inflate(R.layout.radiobutton_layout);
        radioButton.setText(maskData.children.get(i).cat_name);
        if (i == selectId) {
            radioButton.setChecked(true);
            radioButton.setTextColor(getResources().getColor(R.color.colorTextMain));
        } else {
            radioButton.setChecked(false);
            radioButton.setTextColor(getResources().getColor(R.color.colorTextBlack));
        }
        return radioButton;
    }

    //按功效
    private void virtueMask() {
        hs.setVisibility(View.GONE);
        mask_rg.setVisibility(View.VISIBLE);
        title_text.setText(maskData.cat_name);
        for (int i = 0; i < maskData.children.size(); i++) {
            RadioButton radioButton = initRadioButton(0, i);
            RadioGroup.LayoutParams params = new RadioGroup.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT, 1.0f);
            mask_rg.addView(radioButton, params);
        }
        initData(mask_rg, maskData.children.size(), 0);
        mask_vp.setCurrentItem(id);

    }

    @Override
    public void onClick(View v) {
        FacialMaskActivity.this.finish();
    }
}
