package com.bwie.test.ynf_project.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.bwie.test.ynf_project.R;
import com.bwie.test.ynf_project.fragment.Fragment1;
import com.bwie.test.ynf_project.fragment.Fragment2;
import com.bwie.test.ynf_project.fragment.Fragment3;
import com.bwie.test.ynf_project.fragment.Fragment4;
import com.bwie.test.ynf_project.fragment.Fragment5;

public class LaunchActivity extends AppCompatActivity {

    private ViewPager launch_vp;
    private SharedPreferences sp;
    private boolean flag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launch);
        launch_vp = (ViewPager) findViewById(R.id.launch_vp);
        sp = getSharedPreferences("guo", MODE_PRIVATE);
        flag = sp.getBoolean("first", false);
        if (flag){
            Intent intent = new Intent(LaunchActivity.this,Launch2Activity.class);
            startActivity(intent);
        }else {
            SharedPreferences.Editor edit = sp.edit();
            edit.putBoolean("first",true);
            edit.commit();
            launch_vp.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
                Fragment fragment = null;
                @Override
                public Fragment getItem(int position) {
                    switch (position){
                        case 0:
                            fragment = new Fragment1();
                            break;
                        case 1:
                            fragment = new Fragment2();
                            break;
                        case 2:
                            fragment = new Fragment3();
                            break;
                        case 3:
                            fragment = new Fragment4();
                            break;
                        case 4:
                            fragment = new Fragment5();
                            break;

                    }
                    return fragment;
                }

                @Override
                public int getCount() {
                    return 5;
                }
            });

        }
        }

}
