package com.bwie.test.ynf_project.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bwie.test.ynf_project.activity.LoginActivity;
import com.bwie.test.ynf_project.R;
import com.bwie.test.ynf_project.activity.SettingActivity;
import com.bwie.test.ynf_project.app.MyApplication;
import com.bwie.test.ynf_project.utils.CommonUtils;
import com.bwie.test.ynf_project.utils.ImageLoaderUtils;
import com.bwie.test.ynf_project.view.MyListView;
import com.nostra13.universalimageloader.core.ImageLoader;

/**
 * Created by Administrator on 2016/11/28.
 */
public class MineFragment extends Fragment implements View.OnClickListener {

    private View view;
    private LinearLayout lin_my_collection;
    private LinearLayout lin_my_coupon;
    private LinearLayout lin_my_lottery;
    private LinearLayout lin_my_order;
    private LinearLayout lin_personal_center_contact_service;
    private RadioButton rb_wait_evaluate;
    private RadioButton rb_wait_pay;
    private RadioButton rb_wait_receive_good;
    private RadioButton rb_wait_refund;
    private RadioButton rb_wait_send_good;
    private RelativeLayout relat;
    private RelativeLayout relat_login;
    private ImageView settings;
    private TextView mine_user_name;
    private TextView login;
    String[] text = {"我的订单", "邀请有礼", "刷脸测尺寸", "我的现金券", "我的抽奖单", "我收藏的商品", "联系客服"};
    int[] icon = {R.mipmap.my_order_icon, R.mipmap.my_invite_gift_icon, R.mipmap.my_face_test_icon, R.mipmap.my_coupon_icon, R.mipmap.my_lottery_icon, R.mipmap.my_collection_icon, R.mipmap.personal_center_contact_service_icon,};
    private MyListView mine_lv;
    private ImageView no_mine_icon;
    private ImageView is_mine_icon;
    private RelativeLayout relat_center;
    private Button login_btn;
    private ImageView img_v0;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.mine_page, null);
        initView();
        initListView();
        return view;
    }

    private void initListView() {
        mine_lv.setAdapter(new BaseAdapter() {
            @Override
            public int getCount() {
                return text.length;
            }

            @Override
            public Object getItem(int position) {
                return null;
            }

            @Override
            public long getItemId(int position) {
                return 0;
            }

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {

                View view = View.inflate(getActivity(), R.layout.mine_lv_item, null);
                ImageView mine_lv_img = (ImageView) view.findViewById(R.id.mine_lv_img);
                TextView mine_lv_text = (TextView) view.findViewById(R.id.mine_lv_text);
                mine_lv_img.setImageResource(icon[position]);
                mine_lv_text.setText(text[position]);
                return view;
            }


        });
    }

    @Override
    public void onResume() {
        super.onResume();
        initUserInfo();
    }

    private void initUserInfo() {

            String user_icon = CommonUtils.getSp("user_icon");
            String user_name = CommonUtils.getSp("user_name");
            if (!TextUtils.isEmpty("user_name") && !TextUtils.isEmpty("user_icon")) {
                is_mine_icon.setVisibility(View.VISIBLE);
                mine_user_name.setVisibility(View.VISIBLE);
                no_mine_icon.setVisibility(View.GONE);
                img_v0.setVisibility(View.VISIBLE);
                mine_user_name.setText(user_name);
                ImageLoader.getInstance().displayImage(user_icon, is_mine_icon, ImageLoaderUtils.initOptions());
                login_btn.setText("会员中心");

            }else {

                is_mine_icon.setVisibility(View.GONE);
                mine_user_name.setVisibility(View.GONE);
                no_mine_icon.setVisibility(View.VISIBLE);
                login_btn.setText("登录");
            }

    }

    private void initView() {
        rb_wait_evaluate = (RadioButton) view.findViewById(R.id.rb_wait_evaluate);
        rb_wait_pay = (RadioButton) view.findViewById(R.id.rb_wait_pay);
        rb_wait_receive_good = (RadioButton) view.findViewById(R.id.rb_wait_receive_good);
        rb_wait_refund = (RadioButton) view.findViewById(R.id.rb_wait_refund);
        rb_wait_send_good = (RadioButton) view.findViewById(R.id.rb_wait_send_good);
        relat = (RelativeLayout) view.findViewById(R.id.relat);
        mine_lv = (MyListView) view.findViewById(R.id.mine_lv);
        login_btn = (Button) view.findViewById(R.id.login_btn);
        settings = (ImageView) view.findViewById(R.id.settings);
        mine_user_name = (TextView) view.findViewById(R.id.user_name);
        no_mine_icon = (ImageView) view.findViewById(R.id.no_mine_icon);
        is_mine_icon = (ImageView) view.findViewById(R.id.is_mine_icon);
        img_v0 = (ImageView) view.findViewById(R.id.img_v0);
        no_mine_icon.setOnClickListener(this);
        is_mine_icon.setOnClickListener(this);
        login_btn.setOnClickListener(this);
        settings.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.no_mine_icon:
                CommonUtils.jumpActivity(getActivity(), LoginActivity.class);
                break;
            case R.id.is_mine_icon:
                Toast.makeText(getActivity(), "已登录", Toast.LENGTH_SHORT).show();
                break;
            case R.id.login_btn:

                CommonUtils.jumpActivity(getActivity(), LoginActivity.class);


                break;
            case R.id.rb_wait_pay:
                break;
            case R.id.rb_wait_send_good:
                break;
            case R.id.rb_wait_receive_good:
                break;
            case R.id.rb_wait_evaluate:
                break;
            case R.id.rb_wait_refund:
                break;
            case R.id.settings:
                Intent intent = new Intent(getActivity(), SettingActivity.class);
                startActivity(intent);
                break;
        }

    }
}
