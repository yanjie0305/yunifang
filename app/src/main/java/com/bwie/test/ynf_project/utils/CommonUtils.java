package com.bwie.test.ynf_project.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.view.View;

import com.bwie.test.ynf_project.R;
import com.bwie.test.ynf_project.app.MyApplication;


public class CommonUtils {
    private static final String TAG = "TOPNEWS";
    public static SharedPreferences sharedPreferences;
    public static Context getContext() {
        return MyApplication.getContext();
    }

    public static Handler getHandler() {
        return MyApplication.getHandler();
    }

    public static Thread getMainThread() {
        return MyApplication.getMainThread();
    }

    public static int getMainThreadId() {
        return MyApplication.getMainThreadId();
    }

    // 获取资源文件夹操作
    public static Resources getResources() {
        return getContext().getResources();
    }

    // string文件中的字符串
    public static String getString(int stringId) {
        return getResources().getString(stringId);
    }

    // 返回drawable操作
    public static Drawable getDrawable(int drawableId) {
        return getResources().getDrawable(drawableId);
    }

    // string-array
    public static String[] getStringArray(int arrayId) {
        return getResources().getStringArray(arrayId);
    }

    // 颜色选择通过资源文件获取

    // dip--->px, 1dp = 2px,定义一个控件的宽高 layoutParams(w,h)
    public static int dip2px(int dip) {
        // 获取dp和px的转换关系的变量
        float density = getResources().getDisplayMetrics().density;
        return (int) (dip * density + 0.5);
    }

    // px---->dp
    public static int px2dip(int px) {
        float density = getResources().getDisplayMetrics().density;
        return (int) (px / density + 0.5);
    }

    // 判断当前线程是否在主线程中
    public static boolean isRunInMainThread() {
        return getMainThreadId() == android.os.Process.myTid();
    }

    // 区分是否在 主线程中,做UI处理
    public static void runInMainThread(Runnable runnable) {
        if (isRunInMainThread()) {
            // 如果当前任务就在主线程中,直接运行
            runnable.run();
        } else {
            // 如果当任务在子线程中,则将其传递到主线程中去执行
            getHandler().post(runnable);
        }
    }

    // 根据id获取颜色选择器xml
    public static ColorStateList getColorStateList(int mTabTextColorResId) {
        return getResources().getColorStateList(mTabTextColorResId);
    }

    public static View inflate(int layoutId) {
        View view = View.inflate(MyApplication.getContext(), layoutId, null);
        return view;
    }

    // 将dimens中的dp转换成像素值
    public static int getDimens(int dimensId) {
        return getResources().getDimensionPixelSize(dimensId);
    }

    public static void postDelayed(Runnable runnable, int delayed) {
        // 延时做任务方法
        getHandler().postDelayed(runnable, delayed);
    }

    // 移除指定任务的操作
    public static void removeCallBack(Runnable runnable) {
        getHandler().removeCallbacks(runnable);
    }

    public static void executeRunnalbe(Runnable runnable) {
        MyApplication.getThreadPool().execute(runnable);
    }

    //跳转的方法
    public static void jumpActivity(Activity activity, Class goTo) {
        Intent intent = new Intent(activity, goTo);
        activity.startActivity(intent);
        activity.overridePendingTransition(R.animator.xin_right, R.animator.xout_left);
    }

    //返回
    public static void finishActivity(Activity activity) {
        activity.finish();
        activity.overridePendingTransition(R.animator.xin_left, R.animator.xout_right);
    }
    public static void saveSp(String flag, String str) {
        if (sharedPreferences == null) {
            sharedPreferences = MyApplication.getContext().getSharedPreferences(TAG, MyApplication.getContext().MODE_PRIVATE);
        }
        SharedPreferences.Editor edit = sharedPreferences.edit();
        edit.putString(flag, str);
        edit.commit();
    }

    public static String getSp(String flag) {

        if (sharedPreferences == null) {
            sharedPreferences = MyApplication.getContext().getSharedPreferences(TAG, MyApplication.getContext().MODE_PRIVATE);
        }
        return sharedPreferences.getString(flag, "");
    }
}
