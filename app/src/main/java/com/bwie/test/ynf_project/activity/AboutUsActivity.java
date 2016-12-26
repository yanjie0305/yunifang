package com.bwie.test.ynf_project.activity;

import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.bwie.test.ynf_project.R;
import com.bwie.test.ynf_project.bean.Car;
import com.bwie.test.ynf_project.utils.ZXingUtils;
import com.bwie.test.ynf_project.view.AddShopCarPopupWindow;
import com.bwie.test.ynf_project.view.SelectSharePopupWindow;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;

public class AboutUsActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView aboutus_back;
    private ImageView aboutus_share;
    public ImageView aboutus_img;
    private SelectSharePopupWindow usMenuWindow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);
        aboutus_back = (ImageView) findViewById(R.id.aboutus_back);
        aboutus_share = (ImageView) findViewById(R.id.aboutus_share);
        aboutus_img = (ImageView) findViewById(R.id.aboutus_img);
        aboutus_back.setOnClickListener(this);
        aboutus_share.setOnClickListener(this);

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.aboutus_back:
                AboutUsActivity.this.finish();
                break;
            case R.id.aboutus_share:
                //实例化SelectPicPopupWindow
                usMenuWindow = new SelectSharePopupWindow(AboutUsActivity.this, itemsOnClick);
                //显示窗口
                usMenuWindow.showAtLocation(AboutUsActivity.this.findViewById(R.id.aboutus_main), Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
                break;

        }
    }
    private View.OnClickListener itemsOnClick = new View.OnClickListener() {
        public void onClick(View v) {
//            menuWindow.dismiss();
            switch (v.getId()) {
                case R.id.tv_qq:
                    new ShareAction(AboutUsActivity.this).setPlatform(SHARE_MEDIA.QQ)
                            .withText("hello")
                            .setCallback(umShareListener)
                            .share();
                    break;
                case R.id.tv_weixin:
                    break;
                case R.id.tv_sina:
                    break;

                default:
                    break;
            }
        }

    };
    private UMShareListener umShareListener = new UMShareListener() {
        @Override
        public void onResult(SHARE_MEDIA platform) {
            Log.d("plat", "platform" + platform);
            Toast.makeText(AboutUsActivity.this, platform + " 分享成功啦", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onError(SHARE_MEDIA platform, Throwable t) {
            Toast.makeText(AboutUsActivity.this, platform + " 分享失败啦", Toast.LENGTH_SHORT).show();
            if (t != null) {
                Log.d("throw", "throw:" + t.getMessage());
            }
        }

        @Override
        public void onCancel(SHARE_MEDIA platform) {
            Toast.makeText(AboutUsActivity.this, platform + " 分享取消了", Toast.LENGTH_SHORT).show();
        }
    };
}
