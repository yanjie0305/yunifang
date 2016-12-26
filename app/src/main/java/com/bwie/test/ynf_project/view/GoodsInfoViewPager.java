package com.bwie.test.ynf_project.view;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bwie.test.ynf_project.R;
import com.bwie.test.ynf_project.utils.CommonUtils;
import com.bwie.test.ynf_project.utils.ImageLoaderUtils;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/12/6.
 */
public class GoodsInfoViewPager extends ViewPager {

    private DisplayImageOptions imageOptions;
    public ArrayList<String> imageUrlList;
    public ArrayList<ImageView> dotList;
    private MyPageAdapter myPageAdapter;
    String url = "http://image.hmeili.com/yunifang/images/goods/temp/161205184533210330897151635.png";
    public GoodsInfoViewPager(Context context) {
        super(context);
        init();
    }

    private void init() {
        imageOptions = ImageLoaderUtils.initOptions();
    }

    public GoodsInfoViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }
    //初始化数据
    public void initData(final ArrayList<String> imageUrlList, final int[] dotsArray, final ArrayList<ImageView> dotList) {
        this.imageUrlList = imageUrlList;
        this.dotList = dotList;
        this.setOnPageChangeListener(new OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }
            @Override
            public void onPageSelected(int position) {
                for (int i = 0; i < dotList.size(); i++) {
                    if (position==i){
                        dotList.get(i).setImageResource(dotsArray[0]);
                    }else {
                        dotList.get(i).setImageResource(dotsArray[1]);
                    }
                }
            }
            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
    }
    public void startPager() {
        if (myPageAdapter == null) {
            myPageAdapter = new MyPageAdapter();
        }
        this.setAdapter(myPageAdapter);

    }
    class MyPageAdapter extends PagerAdapter {
        @Override
        public int getCount() {
            return imageUrlList.size();
        }
        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }
        @Override
        public Object instantiateItem(ViewGroup container, final int position) {
            View view = CommonUtils.inflate(R.layout.roolviewpager);
            ImageView iv_roolviewpager = (ImageView) view.findViewById(R.id.iv_roolviewpager);
            ImageView img_roolviewpager = (ImageView) view.findViewById(R.id.img_roolviewpager);
            iv_roolviewpager.setScaleType(ImageView.ScaleType.CENTER_CROP);
            ImageLoader.getInstance().displayImage(imageUrlList.get(position), iv_roolviewpager, imageOptions);
            ImageLoader.getInstance().displayImage(url, img_roolviewpager, imageOptions);

            container.addView(view);
            return view;
        }
        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    }
}
