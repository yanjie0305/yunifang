package com.bwie.test.ynf_project.activity;

import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.bwie.test.ynf_project.R;
import com.bwie.test.ynf_project.base.BaseData;
import com.bwie.test.ynf_project.bean.HomePage;
import com.bwie.test.ynf_project.utils.CommonUtils;
import com.bwie.test.ynf_project.view.ShowingPage;
import com.google.gson.Gson;
import com.nostra13.universalimageloader.core.ImageLoader;

public class Launch3Activity extends AppCompatActivity {
    private HomePage homePage;
    private ImageView launch3_iv;
    private Button btn_jump;
    private boolean boo = true;
    String path = "http://image.hmeili.com/yunifang/images/goods/ad0/16101517113385857065462262.jpg";
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            int a = msg.arg1;
            btn_jump.setText("跳过" + a + "秒");
            if (a == 0) {
                CommonUtils.jumpActivity(Launch3Activity.this, MainActivity.class);
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launch3);

        launch3_iv = (ImageView) findViewById(R.id.launch3_iv);
        ImageLoader.getInstance().displayImage(path, launch3_iv);
        btn_jump = (Button) findViewById(R.id.btn_jump);
        new Thread() {
            @Override
            public void run() {
                for (int i = 4; i > -1; i--) {
                    if (boo) {
                        SystemClock.sleep(1000);
                        Message msg = Message.obtain();
                        msg.arg1 = i;
                        handler.sendMessage(msg);
                    }
                }

            }
        }.start();

        btn_jump.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boo = false;
                CommonUtils.jumpActivity(Launch3Activity.this, MainActivity.class);
            }
        });
        BaseData baseData = new BaseData() {
            @Override
            protected void setResultData(String data) {
                Gson gson = new Gson();
                homePage = gson.fromJson(data, HomePage.class);

            }

            @Override
            protected void setResultError(ShowingPage.StateType stateLoadError) {

            }
        };
    }
}
