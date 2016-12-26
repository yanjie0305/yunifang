package com.bwie.test.ynf_project.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bwie.test.ynf_project.R;
import com.bwie.test.ynf_project.bean.AdressBean;
import com.bwie.test.ynf_project.dao.AdressDao;
import com.bwie.test.ynf_project.utils.CommonUtils;

import java.io.Serializable;
import java.util.ArrayList;

public class AdressActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView save;
    private EditText et_name;
    private EditText et_phone;
    private EditText et_province;
    private EditText et_adress;
    private String name;
    private String phone;
    private String province;
    private String adress;
    private AdressDao adressDao;
    private ImageView adress_back;
    private AdressBean adressBean;
    public static   final  int SURPAY_RETYPE = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adress);
        initView();
        adressDao = new AdressDao(this);


    }

    private void getEdData() {
        name = et_name.getText().toString();
        phone = et_phone.getText().toString();
        province = et_province.getText().toString();
        adress = et_adress.getText().toString();
        if (TextUtils.isEmpty(name)){
            Toast.makeText(AdressActivity.this, "姓名不能为空", Toast.LENGTH_SHORT).show();
            return;
        }else if (TextUtils.isEmpty(phone)){
            Toast.makeText(AdressActivity.this, "手机号不能为空", Toast.LENGTH_SHORT).show();
            return;
        }else if (TextUtils.isEmpty(province)){
            Toast.makeText(AdressActivity.this, "省份不能为空", Toast.LENGTH_SHORT).show();
            return;
        }else if (TextUtils.isEmpty(adress)){
            Toast.makeText(AdressActivity.this, "详细地址不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
        adressBean = new AdressBean(name, phone, province, adress, true);
        adressDao.add(adressBean);
    }

    private void initView() {
        et_name = (EditText) findViewById(R.id.et_name);
        et_phone = (EditText) findViewById(R.id.et_phone);
        et_province = (EditText) findViewById(R.id.et_province);
        et_adress = (EditText) findViewById(R.id.et_adress);
        save = (TextView) findViewById(R.id.save);
        adress_back = (ImageView) findViewById(R.id.adress_back);
        save.setOnClickListener(this);
        adress_back.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.save:
                getEdData();
                if (adressBean==null){
                    return;
                }
                Intent intent = new Intent();
                if (adressDao.query().size()==1){
                    jumpActivity(intent,SURPAY_RETYPE);
                }else {
                    jumpActivity(intent,200);
                }
                AdressActivity.this.finish();
                break;
            case R.id.adress_back:
                AdressActivity.this.finish();
                break;
        }
    }

    private void jumpActivity(Intent intent, int resultCode) {

        Bundle bundle = new Bundle();
        bundle.putSerializable("adress", adressBean);
        intent.putExtras(bundle);
        setResult(resultCode,intent);
    }
}
