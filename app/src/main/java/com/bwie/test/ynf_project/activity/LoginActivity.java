package com.bwie.test.ynf_project.activity;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bwie.test.ynf_project.R;
import com.bwie.test.ynf_project.app.MyApplication;
import com.bwie.test.ynf_project.utils.CommonUtils;
import com.bwie.test.ynf_project.utils.ImageLoaderUtils;
import com.bwie.test.ynf_project.view.LoginPop;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.bean.SHARE_MEDIA;

import org.xutils.x;

import java.util.Map;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btn_login;
    private TextView forget_pwd;
    private TextView third_login;
    private TextView btn_account;
    private TextView btn_phone;
    private EditText login_pwd;
    private LinearLayout ll_pwd;
    private LoginPop menuWindow;
    private ImageView mine_icon;
    private TextView user_name;
    private ImageView back_login;
    private TextView register;
    private EditText phoneNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();
    }



    private void initView() {
        btn_account = (TextView) findViewById(R.id.btn_account);
        btn_phone = (TextView) findViewById(R.id.btn_phone);
        btn_login = (Button) findViewById(R.id.btn_login);
        forget_pwd = (TextView) findViewById(R.id.forget_pwd);
        third_login = (TextView) findViewById(R.id.third_login);
        login_pwd = (EditText) findViewById(R.id.login_pwd);
        phoneNumber = (EditText) findViewById(R.id.phoneNumber);
        ll_pwd = (LinearLayout) findViewById(R.id.ll_pwd);
       // mine_icon = (ImageView) findViewById(R.id.mine_icon);
      // user_name = (TextView) findViewById(R.id.user_name);
        back_login = (ImageView) findViewById(R.id.back_login);
        register = (TextView) findViewById(R.id.register);
        btn_account.setOnClickListener(this);
        btn_phone.setOnClickListener(this);
        btn_login.setOnClickListener(this);
        forget_pwd.setOnClickListener(this);
        third_login.setOnClickListener(this);
        back_login.setOnClickListener(this);
        register.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back_login:
                finish();
                break;
            case R.id.register:

                break;
            case R.id.btn_account:
                btn_account.setTextColor(Color.RED);
                btn_phone.setTextColor(Color.GRAY);
                login_pwd.setVisibility(View.VISIBLE);
                ll_pwd.setVisibility(View.GONE);
                forget_pwd.setVisibility(View.VISIBLE);
                break;
            case R.id.btn_phone:
                btn_phone.setTextColor(Color.RED);
                btn_account.setTextColor(Color.GRAY);
                login_pwd.setVisibility(View.GONE);
                ll_pwd.setVisibility(View.VISIBLE);
                forget_pwd.setVisibility(View.GONE);
                break;
            case R.id.forget_pwd:
                break;
            case R.id.btn_login:
                break;
            case R.id.third_login:
                //实例化SelectPicPopupWindow
                menuWindow = new LoginPop(LoginActivity.this, itemsOnClick);
                //显示窗口
                menuWindow.showAtLocation(LoginActivity.this.findViewById(R.id.login_main), Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
                break;
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);

    }
    //为弹出窗口实现监听类
    private View.OnClickListener itemsOnClick = new View.OnClickListener() {

        public void onClick(View v) {
            menuWindow.dismiss();
            switch (v.getId()) {
                case R.id.login_qq:
                    UMShareAPI  mShareAPI = UMShareAPI.get( LoginActivity.this );
                    //mShareAPI.doOauthVerify(LoginActivity.this, SHARE_MEDIA.QQ, umAuthListener);
                    mShareAPI.getPlatformInfo(LoginActivity.this, SHARE_MEDIA.QQ, umAuthListener);
                    break;
                case R.id.login_sina:
                    break;
                case R.id.login_weixin:
                    break;
                default:
                    break;
            }
        }
    };
    private UMAuthListener umAuthListener = new UMAuthListener() {
        @Override
        public void onComplete(SHARE_MEDIA platform, int action, Map<String, String> data) {
            CommonUtils.saveSp("user_icon",data.get("profile_image_url"));
            CommonUtils.saveSp("user_name",data.get("screen_name"));
            finish();
            Toast.makeText(getApplicationContext(), "登录成功", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onError(SHARE_MEDIA platform, int action, Throwable t) {
            Toast.makeText( getApplicationContext(), "Authorize fail", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onCancel(SHARE_MEDIA platform, int action) {
            Toast.makeText( getApplicationContext(), "Authorize cancel", Toast.LENGTH_SHORT).show();
        }
    };

}
