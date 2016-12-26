package com.bwie.test.ynf_project.view;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
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
 * Created by Administrator on 2016/12/1.
 */
public class MyRoolViewPager extends ViewPager {

    private DisplayImageOptions imageOptions;

    private ArrayList<String> imageUrlList;
    private ArrayList<ImageView> dotList;
    private MyPageAdapter myPageAdapter;
   // private OnPageClickListener onPageClickListener;
    private static final int ROOL=0;
    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            int currentItem = MyRoolViewPager.this.getCurrentItem();
            currentItem++;
            MyRoolViewPager.this.setCurrentItem(currentItem);
            sendEmptyMessageDelayed(ROOL,2000);
        }
    };
    private OnPageClickListener onPageClickListener;

    public MyRoolViewPager(Context context) {
        super(context);
        init();
    }

    public MyRoolViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        imageOptions = ImageLoaderUtils.initOptions();
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
                    if (position%dotList.size()==i){
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

    //设置适配器开始轮播
    public void startViewPager() {
        if (myPageAdapter == null) {
            myPageAdapter = new MyPageAdapter();
        }
        this.setAdapter(myPageAdapter);
        handler.sendEmptyMessageDelayed(ROOL,2000);

    }
    public void startPager() {
        if (myPageAdapter == null) {
            myPageAdapter = new MyPageAdapter();
        }
        this.setAdapter(myPageAdapter);
        handler=null;

    }

    class MyPageAdapter extends PagerAdapter {
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
            View view = CommonUtils.inflate(R.layout.roolviewpager);
            ImageView iv_roolviewpager = (ImageView) view.findViewById(R.id.iv_roolviewpager);
            iv_roolviewpager.setScaleType(ImageView.ScaleType.CENTER_CROP);
            ImageLoader.getInstance().displayImage(imageUrlList.get(position%imageUrlList.size()), iv_roolviewpager, imageOptions);
            container.addView(view);
            view.setOnTouchListener(new OnTouchListener() {

                private long downTime;
                private float downY;
                private float downX;

                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    switch (event.getAction()){
                        case MotionEvent.ACTION_DOWN:
                            downX = event.getX();
                            downY = event.getY();
                            downTime = System.currentTimeMillis();
                            if (handler != null) {
                                handler.removeCallbacksAndMessages(null);
                            }

                            break;
                        case MotionEvent.ACTION_UP:
                            float upX = event.getX();
                            float upY = event.getY();
                            if (downX==upX&&downY==upY&&System.currentTimeMillis()-downTime<1000){
                            if (onPageClickListener!=null){
                                onPageClickListener.setOnPage(position);
                            }
                            }
                        case MotionEvent.ACTION_CANCEL:
                            if (handler!=null){
                                handler.sendEmptyMessageDelayed(ROOL,2000);
                            }

                            break;

                    }
                    return true;
                }
            });
            return view;
        }
        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        handler.removeCallbacksAndMessages(null);
    }
    public interface OnPageClickListener{
        public void setOnPage(int position);

    }
    public void setOnPageClickListener(OnPageClickListener onPageClickListener){
        this.onPageClickListener = onPageClickListener;
    }
}
