package com.bwie.test.ynf_project.view;

import android.support.v4.view.ViewPager;
import android.view.View;

/**
 * Created by Administrator on 2016/12/2.
 */
public class AlphaPageTransformer implements ViewPager.PageTransformer {
    private static final float DEFAULT_MIN_ALPHA = 0.5f;
    private float mMinAlpha = DEFAULT_MIN_ALPHA;

    public AlphaPageTransformer() {

    }

    @Override
    public void transformPage(View view, float position) {
        if (position < -1) {
            view.setAlpha(mMinAlpha);
        } else if (position <= 1) { // [-1,1]

            if (position < 0) //[0，-1]
            {
                float factor = mMinAlpha + (1 - mMinAlpha) * (1 + position);
                view.setAlpha(factor);
            } else//[1，0]
            {
                float factor = mMinAlpha + (1 - mMinAlpha) * (1 - position);
                view.setAlpha(factor);
            }
        } else { // (1,+Infinity]
            view.setAlpha(mMinAlpha);
        }
    }
}
