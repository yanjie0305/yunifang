package com.bwie.test.ynf_project.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bwie.test.ynf_project.R;
import com.bwie.test.ynf_project.activity.GoodsInfoActivity;
import com.bwie.test.ynf_project.activity.MainActivity;
import com.bwie.test.ynf_project.activity.PayMoneyActivity;
import com.bwie.test.ynf_project.base.BaseData;
import com.bwie.test.ynf_project.base.BaseFragment;
import com.bwie.test.ynf_project.bean.Car;
import com.bwie.test.ynf_project.dao.CarDao;
import com.bwie.test.ynf_project.utils.CommonUtils;
import com.bwie.test.ynf_project.utils.ImageLoaderUtils;
import com.bwie.test.ynf_project.utils.URLUtils;
import com.bwie.test.ynf_project.view.AddShopCarPopupWindow;
import com.bwie.test.ynf_project.view.OnMoneyChangeListener;
import com.bwie.test.ynf_project.view.ShowingPage;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.zhy.adapter.abslistview.CommonAdapter;
import com.zhy.adapter.abslistview.ViewHolder;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/11/28.
 */
public class CartFragment extends BaseFragment implements View.OnClickListener {
    private String data;
    private View view;
    private Button empty_btn;
    private ListView cart_lv;
    private Button cart_pay;
    private TextView cart_sum;
    private CheckBox cart_allcheck;
    private TextView cart_editor;
    private TextView cart_title;
    private RelativeLayout rela_cart;
    private RelativeLayout rela_empty;
    private ArrayList<Car> query;
    private CommonAdapter<Car> adapter;
    private CarDao dao;
    private TextView cart_text;
    private ImageView cart_add;
    private RelativeLayout lv_count;
    private ImageView cart_lv_item_img;
    private TextView cart_lv_item_title;
    private TextView cart_lv_item_count;
    int i = 1;
    private boolean flag = false;

    public OnMoneyChangeListener onMoneyChangeListener = new OnMoneyChangeListener() {
        @Override
        public void setChange() {
            setMoney();
        }
    };
    private TextView cart_lv_item_price;
    private ImageView cart_reduce;
    private TextView cart_count;
    private int currentNum;
    private int size;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onload() {
        CartFragment.this.showCurrentPage(ShowingPage.StateType.STATE_LOAD_SUCCESS);
    }

    @Override
    public View createSuccessView() {
        view = CommonUtils.inflate(R.layout.cart_page);
        initView();

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        dao = new CarDao(getActivity());
        query = dao.query();

        cart_allcheck.setOnClickListener(this);
        cart_editor.setOnClickListener(this);
        cart_pay.setOnClickListener(this);

        setAdapterData();

        Log.e("TAG", "%%%%%%%%%%%%%%" + query.size());
        Log.e("TAG", "*************" + query);
        showView();
    }

    private void showView() {
        cart_title.setText("购物车(" + query.size() + ")");
        if (query.size() > 0) {
            rela_cart.setVisibility(View.VISIBLE);
            rela_empty.setVisibility(View.GONE);


        } else {
            rela_cart.setVisibility(View.GONE);
            rela_empty.setVisibility(View.VISIBLE);
            empty_btn.setOnClickListener(this);
        }
    }

    //设置适配器
    private void setAdapterData() {
        adapter = new CommonAdapter<Car>(getActivity(), R.layout.cart_lv_item, query) {
            @Override
            protected void convert(ViewHolder viewHolder, final Car item, final int position) {
                cart_lv_item_title = viewHolder.getView(R.id.cart_lv_item_title);
                cart_lv_item_price = viewHolder.getView(R.id.cart_lv_item_price);
                cart_lv_item_count = viewHolder.getView(R.id.cart_lv_item_count);
                CheckBox  cart_lv_item_cb = viewHolder.getView(R.id.cart_lv_item_cb);
                cart_lv_item_cb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if (isChecked) {
                            item.setCheck(true);
                        } else {
                            item.setCheck(false);
                        }
                        setMoney();
                    }

                });
                lv_count = viewHolder.getView(R.id.lv_count);
                cart_add = viewHolder.getView(R.id.cart_add);
                cart_reduce = viewHolder.getView(R.id.cart_reduce);
                cart_add.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        setShopCount(true, query.get(position).getCount(), position, cart_count, cart_add, cart_reduce);
                        ++i;
                        if (i > 1) {
                            cart_reduce.setImageResource(R.mipmap.reduce_able);
                        }
                        cart_count.setText(i + "");
                    }


                });
                cart_reduce.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        setShopCount(false, query.get(position).getCount(), position, cart_count, cart_add, cart_reduce);
                        --i;
                        if (i <= 1) {
                            cart_reduce.setImageResource(R.mipmap.reduce_unable);
                        } else {
                            cart_reduce.setImageResource(R.mipmap.reduce_able);
                        }
                    }
                });
                cart_count = viewHolder.getView(R.id.cart_count);
                cart_lv_item_img = viewHolder.getView(R.id.cart_lv_item_img);
                cart_lv_item_title.setText(item.getTitle());
                cart_lv_item_price.setText("￥" + item.getPrice());
                cart_lv_item_count.setText("数量: " + item.getCount());

                if (flag) {
                    lv_count.setVisibility(View.VISIBLE);
                    cart_lv_item_count.setVisibility(View.GONE);

                } else {
                    lv_count.setVisibility(View.GONE);
                    cart_lv_item_count.setVisibility(View.VISIBLE);
                }
                ImageLoader.getInstance().displayImage(item.getImg(), cart_lv_item_img, ImageLoaderUtils.initOptions());
                cart_lv_item_cb.setChecked(item.getCheck());
            }
        };
        cart_lv.setAdapter(adapter);
        cart_lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), GoodsInfoActivity.class);
                intent.putExtra("id",query.get(position).getId());
                startActivity(intent);
            }
        });

    }

    private void setShopCount(boolean add, String count, int position, TextView cart_count, ImageView cart_add, ImageView cart_reduce) {
        if (add) {
            currentNum = Integer.parseInt(count);
            if (currentNum == 5) {
                return;
            }
            currentNum++;
        } else {
            if (currentNum == 1) {
                return;
            }
            currentNum--;
        }
        if (currentNum > 1 && currentNum < 5) {
            cart_reduce.setImageResource(R.mipmap.reduce_able);
        } else if (currentNum >= 5) {
            cart_add.setImageResource(R.mipmap.add_unable);
        } else {
            cart_reduce.setImageResource(R.mipmap.reduce_unable);
            cart_add.setImageResource(R.mipmap.add_able);
        }
        query.get(position).setCount(currentNum + "");
        cart_count.setText("" + query.get(position).getCount());
        adapter.notifyDataSetChanged();
    }

    private void initView() {
        cart_lv = (ListView) view.findViewById(R.id.cart_lv);
        cart_pay = (Button) view.findViewById(R.id.cart_pay);
        cart_sum = (TextView) view.findViewById(R.id.cart_sum);
        cart_allcheck = (CheckBox) view.findViewById(R.id.cart_allcheck);
        cart_editor = (TextView) view.findViewById(R.id.cart_editor);
        cart_title = (TextView) view.findViewById(R.id.cart_title);
        rela_cart = (RelativeLayout) view.findViewById(R.id.rela_cart);
        rela_empty = (RelativeLayout) view.findViewById(R.id.rela_empty);
        empty_btn = (Button) view.findViewById(R.id.empty_btn);
        cart_text = (TextView) view.findViewById(R.id.cart_text);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.cart_allcheck:
                if (cart_allcheck.isChecked()) {
                    for (int i = 0; i < query.size(); i++) {
                        query.get(i).setCheck(true);
                    }
                    cart_text.setText("全不选");
                    adapter.notifyDataSetChanged();
                    setMoney();
                } else {
                    for (int i = 0; i < query.size(); i++) {
                        query.get(i).setCheck(false);
                    }
                    adapter.notifyDataSetChanged();
                    setMoney();
                    cart_text.setText("全选");
                }
                break;
            case R.id.cart_editor:
                if (cart_editor.getText().toString().equals("编辑")) {
                    flag = true;
                    cart_editor.setText("完成");
                    cart_sum.setVisibility(View.GONE);
                    cart_pay.setText("删除");

                } else {
                    flag = false;
                    cart_editor.setText("编辑");
                    cart_sum.setVisibility(View.VISIBLE);
                    cart_pay.setText("结算");
                }
                adapter.notifyDataSetChanged();
                // cart_title.setText("购物车(" + query.size() + ")");
                break;
            case R.id.cart_pay:
                if (cart_pay.getText().toString().equals("删除")) {
                    for (int i = query.size() - 1; i >= 0; i--) {
                        if (query.get(i).getCheck()) {
                            dao.delete(query.get(i).getTitle());
                            query.remove(i);
                            adapter.notifyDataSetChanged();
                        }
                    }
                    showView();
                } else {
                    jumpActivity();
                }
//                float sum = 0;
//                for (int i = 0; i < query.size(); i++) {
//                    if (query.get(i).getCheck()) {
//                        sum = sum + Float.parseFloat(query.get(i).getPrice());
//                    }
//                }
                setMoney();
                break;
            case R.id.empty_btn:
                ((MainActivity) getActivity()).vp.setCurrentItem(0);
                break;

        }
    }

    private void jumpActivity() {
        ArrayList<Car> list = new ArrayList<>();
        for (int i = 0; i < query.size(); i++) {
            if (query.get(i).getCheck()) {
                list.add(query.get(i));
            }
        }
        Intent intent = new Intent(getActivity(), PayMoneyActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("goods", list);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    private void setMoney() {
        float sum = 0;
        int a =0;
        for (int i = 0; i < query.size(); i++) {
            if (query.get(i).getCheck()) {
                sum = sum + Float.parseFloat(query.get(i).getPrice()) * Float.parseFloat(query.get(i).getCount());
           a=a+1;
            }
        }
        cart_allcheck.setChecked(a == query.size() ? true : false);
        float sumprice = (float) Math.round(sum * 100) / 100;
        cart_sum.setText("总价: ￥" + sumprice);

    }
}
