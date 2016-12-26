package com.bwie.test.ynf_project.app;

import android.app.Application;
import android.content.Context;
import android.os.Handler;
import android.os.Process;
import android.text.TextUtils;

import com.bwie.test.ynf_project.utils.CommonUtils;
import com.bwie.test.ynf_project.utils.ImageLoaderUtils;
import com.umeng.socialize.PlatformConfig;
import com.umeng.socialize.UMShareAPI;

import org.xutils.x;

import java.util.concurrent.ExecutorService;

/**
 * Created by Administrator on 2016/11/28.
 */
public class MyApplication extends Application {

    private static Context context;
    private static Handler handler;
    private static int mainThreadId;
   private static ExecutorService executorService;
    public static boolean isLogin = false;
    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
        handler = new Handler();
        mainThreadId = Process.myTid();
        x.Ext.init(this);
        x.Ext.setDebug(true);
        ImageLoaderUtils.initConfiguration(this);
        PlatformConfig.setQQZone("100424468", "c7394704798a158208a74ab60104f0ba");
        UMShareAPI.get(this);
        String user_name = CommonUtils.getSp("user_name");
        if (!TextUtils.isEmpty(user_name)){
            MyApplication.isLogin =true;
        }
    }
    public static int getMainThreadId(){
        return  mainThreadId;
    }
    public static Handler getHandler(){
        return handler;
    }
    public static Context getContext(){
        return context;
    }
    public static Thread getMainThread(){
        return Thread.currentThread();
    }
    public static ExecutorService getThreadPool(){

        return executorService;
    }

}
