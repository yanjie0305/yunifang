package com.bwie.test.ynf_project.activity;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.bwie.test.ynf_project.R;

public class Launch2Activity extends AppCompatActivity {

    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Intent intent = new Intent(Launch2Activity.this,Launch3Activity.class);
            startActivity(intent);
            finish();
            handler.removeCallbacksAndMessages(null);
    }
};
@Override
protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launch2);

        handler.sendEmptyMessageDelayed(0,3000);


    }

}
