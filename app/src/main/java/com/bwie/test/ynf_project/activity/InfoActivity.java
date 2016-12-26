package com.bwie.test.ynf_project.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import com.bwie.test.ynf_project.R;
import com.bwie.test.ynf_project.fragment.HomeFragment;
import com.bwie.test.ynf_project.utils.CommonUtils;

public class InfoActivity extends AppCompatActivity {

    private WebView web;
    private ImageView info_back;
    private TextView info_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);
        web = (WebView) findViewById(R.id.web);
        info_back = (ImageView) findViewById(R.id.info_back);
        info_name = (TextView) findViewById(R.id.info_name);
        String webUrl = getIntent().getStringExtra("webUrl");
        web.loadUrl(webUrl);
        info_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              finish();
            }
        });
    }
}
