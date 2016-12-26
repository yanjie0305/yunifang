package com.bwie.test.ynf_project.base;

import android.text.TextUtils;
import android.util.Log;

import com.bwie.test.ynf_project.app.MyApplication;
import com.bwie.test.ynf_project.utils.CommonUtils;
import com.bwie.test.ynf_project.utils.MD5Encoder;
import com.bwie.test.ynf_project.utils.NetUtils;
import com.bwie.test.ynf_project.view.ShowingPage;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by Administrator on 2016/10/31.
 */
public abstract class BaseData {

    private final File fileDir;
    public static final int NOTIME = 0;
    public static final int NORMALTIME = 3 * 24 * 60 * 60 * 1000;

    //缓存数据存到哪里
    public BaseData() {
        //找到存储路径
        File cacheDir = CommonUtils.getContext().getCacheDir();
        fileDir = new File(cacheDir, "yunifang");
        if (!fileDir.exists()) {
            fileDir.mkdir();
        }
    }

    public void getData(String path, String args, int index, int validTime) {
        //先判断有效时间
        if (validTime == 0) {
            //直接请求网络，获取最新数据
            getDataFromNet(path, args, index, validTime);
        } else {
            //从本地获取
            String data = getDataFromLocal(path, index, validTime);
            if (TextUtils.isEmpty(data)) {
                //如果为空请求网络
                getDataFromNet(path, args, index, validTime);
            } else {
                //拿到了数据，返回数据
                setResultData(data);
            }
        }
    }

    public void postData(String path, HashMap<String, String> argsMap, int index, int validTime) {
        //先判断有效时间
        if (validTime == 0) {
            //直接请求网络，获取最新数据
            postDataFromNet(path, argsMap, index, validTime);
        } else {
            //从本地获取
            String data = getDataFromLocal(path, index, validTime);
            if (TextUtils.isEmpty(data)) {
                //如果为空请求网络
                postDataFromNet(path, argsMap, index, validTime);
            } else {
                //拿到了数据，返回数据
                setResultData(data);
            }
        }
    }

    public void postDataFromNet(String path, HashMap<String, String> argsMap, int index, int validTime) {
        RequestParams requestParams = new RequestParams(path);
        Set<Map.Entry<String, String>> entries = argsMap.entrySet();
        for (Map.Entry<String, String> entry : entries) {
            requestParams.addParameter(entry.getKey(), entry.getValue());
        }
        x.http().get(requestParams, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String s) {

            }

            @Override
            public void onError(Throwable throwable, boolean b) {

            }

            @Override
            public void onCancelled(CancelledException e) {

            }

            @Override
            public void onFinished() {

            }
        });
    }

    protected abstract void setResultData(String data);

    private String getDataFromLocal(String path, int index, int validTime) {
        try {
            File file = new File(fileDir, MD5Encoder.encode(path) + index);
            BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
            String s = bufferedReader.readLine();
            long time = Long.parseLong(s);
            if (System.currentTimeMillis()  <time ) {
                StringBuilder builder = new StringBuilder();
                String line = null;
                while ((line = bufferedReader.readLine()) != null) {
                    builder.append(line);
                }
                bufferedReader.close();
                return builder.toString();
            } else {
                return null;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private void getDataFromNet(final String path, final String args, final int index, final int validTime) {
        RequestParams requestParams = new RequestParams(path + "?" + args);
        x.http().get(requestParams, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(final String s) {
                setResultData(s);
                CommonUtils.executeRunnalbe(new Runnable() {
                    @Override
                    public void run() {
                        writeDataToLocal(path, args, index, validTime, s);
                    }
                });
            }


            @Override
            public void onError(Throwable throwable, boolean b) {
                Log.i("TAG", "`````````````````onError:");
                setResultError(ShowingPage.StateType.STATE_LOAD_ERROR);
            }

            @Override
            public void onCancelled(CancelledException e) {
                Log.i("TAG", "`````````````````onCancelled:");

            }

            @Override
            public void onFinished() {
                Log.i("TAG", "`````````````````onFinished:");

            }
        });

    }

    protected abstract void setResultError(ShowingPage.StateType stateLoadError);

    private void writeDataToLocal(String path, String args, int index, int validTime, String data) {
        try {
            File file = new File(fileDir, MD5Encoder.encode(path) + index);
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file));
            bufferedWriter.write(data);
            bufferedWriter.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
