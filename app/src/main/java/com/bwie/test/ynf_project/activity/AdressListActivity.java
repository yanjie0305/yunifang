package com.bwie.test.ynf_project.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.bwie.test.ynf_project.R;
import com.bwie.test.ynf_project.bean.AdressBean;
import com.bwie.test.ynf_project.dao.AdressDao;
import com.bwie.test.ynf_project.utils.CommonUtils;
import com.zhy.adapter.abslistview.CommonAdapter;
import com.zhy.adapter.abslistview.ViewHolder;

import java.util.ArrayList;

public class AdressListActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView iv_back;
    private TextView manager;
    private ListView lv_adress;
    private Button addAdress;
    public static final int SURE_RETYPR = 400;
    private CheckBox check_adress;
    private ArrayList<AdressBean> query;
    private AdressDao adressDao;
    private CommonAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adress_list);
        iv_back = (ImageView) findViewById(R.id.iv_back);
        manager = (TextView) findViewById(R.id.manager);
        lv_adress = (ListView) findViewById(R.id.lv_adress);
        addAdress = (Button) findViewById(R.id.addAdress);
        iv_back.setOnClickListener(this);
        manager.setOnClickListener(this);
        addAdress.setOnClickListener(this);
        adressDao = new AdressDao(this);
        query = adressDao.query();
        adapter = new CommonAdapter<AdressBean>(AdressListActivity.this, R.layout.adress_lv_item, query) {

            @Override
             protected void convert(ViewHolder viewHolder, AdressBean item, int position) {
                 TextView name =  viewHolder.getView(R.id.name);
                check_adress = viewHolder.getView(R.id.check_adress);
                 name.setText("收货人:"+item.getName()+"           "+item.getPhone()+"\r\n"+item.getProvince()+item.getAdress());

             }
         };
        lv_adress.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        lv_adress.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = getIntent();
                jumpActivity(intent,SURE_RETYPR, query.get(position));
                AdressListActivity.this.finish();
            }


        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        query = adressDao.query();
        adapter.notifyDataSetChanged();
    }

    private void jumpActivity(Intent intent, int resultCode, AdressBean adressBean) {
        Bundle bundle = new Bundle();
        bundle.putSerializable("adress",adressBean);
        Log.e("TAG","********************"+adressBean.getName());
        intent.putExtras(bundle);
        setResult(resultCode,intent);
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_back:
                AdressListActivity.this.finish();
                break;
            case R.id.manager:

                break;
            case R.id.addAdress:
                CommonUtils.jumpActivity(AdressListActivity.this,AdressActivity.class);
                break;

        }
    }
}
