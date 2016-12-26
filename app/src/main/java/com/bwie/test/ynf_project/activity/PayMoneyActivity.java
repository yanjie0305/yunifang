package com.bwie.test.ynf_project.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bwie.test.ynf_project.R;
import com.bwie.test.ynf_project.bean.AdressBean;
import com.bwie.test.ynf_project.bean.Car;
import com.bwie.test.ynf_project.dao.AdressDao;
import com.bwie.test.ynf_project.pay.PayActivity;
import com.bwie.test.ynf_project.utils.CommonUtils;
import com.bwie.test.ynf_project.view.MyListView;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.zhy.adapter.abslistview.CommonAdapter;
import com.zhy.adapter.abslistview.ViewHolder;

import java.util.ArrayList;

public class PayMoneyActivity extends AppCompatActivity implements View.OnClickListener {

    private ArrayList<Car> list;
    private TextView title_text;
    private ImageView title_back;
    private TextView pay_sum;
    private TextView shipay;
    private Button btn_pay;
    private float sum;
    private RelativeLayout rela_address;
    private AdressDao adressDao;
    private ArrayList<AdressBean> adressList;
    private AdressBean adressBean;
    private TextView adress_shou;
    private ImageView adress_next;
    public static final int NEW_RQTYPE = 200;
    public static final int CHOOSE_RQTYPE = 300;
    private MyListView lv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay_money);
        initView();

        rela_address.setOnClickListener(this);
        title_back.setOnClickListener(this);
        btn_pay.setOnClickListener(this);
        getIntentData();
        adressDao = new AdressDao(this);
        adressList = adressDao.query();
        if (adressList.size()>0){
            setAdress(adressList.get(0));
        }
        Log.e("TAG","%%%%%%%%%%%%%%%%%"+list);
        sumCount();
        lv.setAdapter(new CommonAdapter<Car>(PayMoneyActivity.this,R.layout.cart_lv_item,list) {
            @Override
            protected void convert(ViewHolder viewHolder, Car item, int position) {
                ImageView cart_lv_item_img =viewHolder.getView(R.id.cart_lv_item_img);
               TextView cart_lv_item_title = viewHolder.getView(R.id.cart_lv_item_title);
               TextView cart_count = viewHolder.getView(R.id.cart_count);
                TextView cart_lv_item_price =viewHolder.getView(R.id.cart_lv_item_price);
               CheckBox cart_lv_item_cb = viewHolder.getView(R.id.cart_lv_item_cb);
               RelativeLayout lv_count = viewHolder.getView(R.id.lv_count);
                cart_lv_item_cb.setVisibility(View.GONE);
                ImageLoader.getInstance().displayImage(item.getImg(),cart_lv_item_img);
                cart_lv_item_title.setText(item.getTitle());
                cart_count.setText(item.getCount());
                cart_lv_item_price.setText(item.getPrice());
                lv_count.setVisibility(View.VISIBLE);

            }
        });
    }

    private void initView() {
        title_text = (TextView) findViewById(R.id.title_text);
        title_back = (ImageView) findViewById(R.id.title_back);
        pay_sum = (TextView) findViewById(R.id.pay_sum);
        shipay = (TextView) findViewById(R.id.shipay);
        btn_pay = (Button) findViewById(R.id.btn_pay);
        rela_address = (RelativeLayout) findViewById(R.id.rela_address);
        adress_shou = (TextView) findViewById(R.id.adress_shou);
        adress_next = (ImageView) findViewById(R.id.adress_next);
        title_text.setText("确认订单");

        lv = (MyListView) findViewById(R.id.pay_lv);
    }

    private void getIntentData() {
        list = (ArrayList<Car>) getIntent().getSerializableExtra("goods");
    }

    private void setAdress(AdressBean adressBean) {
       adress_shou.setText("收货人:"+adressBean.getName()+"                "+adressBean.getPhone()+"\r\n"+adressBean.getProvince()+adressBean.getAdress());
        adress_next.setVisibility(View.GONE);
    }

    private void sumCount() {
        int count_sum = 0 ;
        float sumPrice = 0;
        for (int i = 0; i < list.size(); i++) {
            count_sum = count_sum+Integer.parseInt(list.get(i).getCount());
            sumPrice = sumPrice + Float.parseFloat(list.get(i).getPrice())*Float.parseFloat(list.get(i).getCount());

        }
        sum = (float)Math.round(sumPrice * 100) / 100;
        pay_sum.setText("共计"+count_sum+"件商品 总计: ￥"+ sum);
        shipay.setText("实付: ￥"+ sum);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.rela_address:
                if (adressList.size()>0){
                    jumpAdress(AdressListActivity.class,CHOOSE_RQTYPE);
                }else {
                    jumpAdress(AdressActivity.class,NEW_RQTYPE);
                }

                break;
            case R.id.title_back:
                PayMoneyActivity.this.finish();
                break;
            case R.id.btn_pay:
                sumCount();
                //跳转界面，进行支付-----生成订单信息--OrderInfo  商品信息，价格，描述
                Intent intent =new Intent(PayMoneyActivity.this, PayActivity.class);
                intent.putExtra("name","航空母舰");
                intent.putExtra("des","这是宇宙上最厉害的商品");
                intent.putExtra("price",sum);
                startActivity(intent);
                break;
        }
    }
    //跳转到填写地址的界面
    public void jumpAdress(Class cla, int requstCode) {
        Intent intent = new Intent(this, cla);
        startActivityForResult(intent, requstCode);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case NEW_RQTYPE:
                switch (resultCode){
                    case AdressActivity.SURPAY_RETYPE:
                       adressBean = (AdressBean) data.getSerializableExtra("adress");
                        setAdress(adressBean);
                        break;
                }
                break;
            case CHOOSE_RQTYPE:
                switch (resultCode){
                    case AdressListActivity.SURE_RETYPR:
                        adressBean = (AdressBean) data.getSerializableExtra("adress");
                        Log.e("TAG","$$$$$$$$$$$$$"+adressBean.getName());
                        setAdress(adressBean);
                        break;
                }
                break;
        }
    }
}
