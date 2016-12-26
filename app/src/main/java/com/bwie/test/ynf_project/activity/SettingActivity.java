package com.bwie.test.ynf_project.activity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bwie.test.ynf_project.R;
import com.bwie.test.ynf_project.app.MyApplication;
import com.bwie.test.ynf_project.bean.VersionInfo;
import com.bwie.test.ynf_project.utils.CommonUtils;
import com.bwie.test.ynf_project.utils.ZXingUtils;
import com.google.gson.Gson;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.File;

public class SettingActivity extends AppCompatActivity implements View.OnClickListener {

    private RelativeLayout shoppingNational;
    private RelativeLayout feedback;
    private RelativeLayout clear;
    private RelativeLayout aboutUs;
    private RelativeLayout phone;
    private RelativeLayout check_update;
    private Button drop_out;
    private TextView tv_clear;
    private File cacheDir;
    private TextView tv_update;
    private Boolean isLatest = false;
    private VersionInfo versionInfo;
    private TextView tv_number;
    private ImageView setting_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        initView();
        //清除缓存
        ClearCache();
        //检索版本
        updateVersion();
        String user_name = CommonUtils.getSp("user_name");
       if (user_name.isEmpty()){
           drop_out.setVisibility(View.GONE);
       }else {
           drop_out.setVisibility(View.VISIBLE);
       }
    }

    private void updateVersion() {
        //进行网络请求，获取版本信息
        RequestParams requestParams = new RequestParams("http://169.254.27.141:8080/versioninfo.txt");
        x.http().get(requestParams, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String s) {
                Gson gson = new Gson();
                versionInfo = gson.fromJson(s, VersionInfo.class);
                String versionName = versionInfo.getVersionName();
                //版本一致
                if (getVersionName().equals(versionName)) {
                    tv_update.setText("您当前是最新版本");
                    isLatest = true;
                } else {
                    tv_update.setText("最新版本为" + versionName);
                    isLatest = false;
                }

            }

            @Override
            public void onError(Throwable throwable, boolean b) {
                Toast.makeText(SettingActivity.this, "网络连接失败", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(CancelledException e) {
            }

            @Override
            public void onFinished() {

            }
        });


    }

    /**
     * 获取版本名称
     *
     * @return
     */
    public String getVersionName() {
        PackageManager packageManager = getPackageManager();
        //包名
        try {
            PackageInfo packageInfo = packageManager.getPackageInfo(getPackageName(), 0);
            String versionName = packageInfo.versionName;
            return versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    private void ClearCache() {
        //缓存路径
        cacheDir = getCacheDir();
        //获取当前缓存大小
        Long caCheSize = getCacheSize(cacheDir);
        tv_clear.setText("已缓存" + caCheSize + "M");

    }

    private Long getCacheSize(File dir) {
        long size = 0;
        File[] files = dir.listFiles();
        for (int i = 0; i < files.length; i++) {
            if (files[i].isDirectory()) {
                size = size + getCacheSize(files[i]);
            } else {
                size = size + files[i].length();
            }
        }

        return size;
    }

    private void initView() {
        tv_clear = (TextView) findViewById(R.id.tv_clear);
        shoppingNational = (RelativeLayout) findViewById(R.id.shoppingNational);
        feedback = (RelativeLayout) findViewById(R.id.feedback);
        clear = (RelativeLayout) findViewById(R.id.clear);
        aboutUs = (RelativeLayout) findViewById(R.id.aboutUs);
        phone = (RelativeLayout) findViewById(R.id.phone);
        check_update = (RelativeLayout) findViewById(R.id.check_update);
        drop_out = (Button) findViewById(R.id.drop_out);
        tv_update = (TextView) findViewById(R.id.tv_update);
        tv_number = (TextView) findViewById(R.id.tv_number);
        setting_back = (ImageView) findViewById(R.id.setting_back);
        shoppingNational.setOnClickListener(this);
        feedback.setOnClickListener(this);
        clear.setOnClickListener(this);
        aboutUs.setOnClickListener(this);
        phone.setOnClickListener(this);
        check_update.setOnClickListener(this);
        drop_out.setOnClickListener(this);
        setting_back.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.setting_back:
                SettingActivity.this.finish();
                break;
            case R.id.shoppingNational:
                break;
            case R.id.feedback:
                break;
            case R.id.clear:
                deleteCaChe(cacheDir);
                Long cacheSize = getCacheSize(cacheDir);
                tv_clear.setText("已缓存" + cacheSize + "M");

                break;
            case R.id.aboutUs:
                Intent intent = new Intent(SettingActivity.this, AboutUsActivity.class);
                startActivity(intent);

                break;
            case R.id.phone:
                String number = tv_number.getText().toString().trim();
                Intent intent1 = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + number));
                intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent1);
                break;
            case R.id.check_update:
                if (!isLatest) {
                    String downloadUrl = versionInfo.getDownloadUrl();
                    RequestParams requestParams = new RequestParams(downloadUrl);
                    final ProgressDialog progressDialog = new ProgressDialog(this);
                    progressDialog.show();
                    x.http().get(requestParams, new Callback.CommonCallback<File>() {
                        @Override
                        public void onSuccess(File file) {
                            //下载成功
                            Toast.makeText(SettingActivity.this, "下载成功", Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();
                            //安装apk界面打开
                            Intent intent = new Intent();
                            intent.setAction("android.intent.action.VIEW");
                            intent.addCategory("android.intent.category.DEFAULT");
                            Log.i("AAAA", "-----" + file.getAbsolutePath());
                            intent.setDataAndType(
                                    Uri.fromFile(file),
                                    "application/vnd.android.package-archive");
                            startActivity(intent);
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

                        //正在下载
                        public void onLoading(long total, long current, boolean b) {
                            progressDialog.setMax((int) total);
                            progressDialog.setProgress((int) current);
                        }
                    });

                }
                break;
            case R.id.drop_out:
                final AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("确定退出登录吗?");
                builder.setPositiveButton("退出",new AlertDialog.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        MyApplication.isLogin = false;
                        CommonUtils.saveSp("user_name", null);
                        CommonUtils.saveSp("user_icon", null);
                       SettingActivity.this.finish();
                    }
                });
                builder.setNegativeButton("取消",new AlertDialog.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
               builder.show();

                break;
        }
    }

    private void deleteCaChe(File dir) {
        File[] files = dir.listFiles();
        for (int i = 0; i < files.length; i++) {
            if (files[i].isFile()) {
                files[i].delete();
            } else {
                if (files[i].listFiles().length == 0) {
                    files[i].delete();
                } else {
                    deleteCaChe(files[i]);
                }
            }
            files[i].delete();
        }
    }
}
