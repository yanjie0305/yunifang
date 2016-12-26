package com.bwie.test.ynf_project.view;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.bwie.test.ynf_project.R;
import com.bwie.test.ynf_project.activity.GoodsInfoActivity;
import com.bwie.test.ynf_project.bean.GoodsInfo;
import com.nostra13.universalimageloader.core.ImageLoader;

/**
 * Created by Administrator on 2016/12/5.
 */
public class AddShopCarPopupWindow extends PopupWindow {

    private final View mMenuView;
    private final TextView addcar_price;
    private final TextView addcar_kucun;
    private final TextView addcar_xg;
    private final Button ok;
    private final ImageView addcar_img;
    public static ImageView reduce;
   public static ImageView add;
   public static TextView pop_text;
    private final ImageView addcart_cancel;

    public AddShopCarPopupWindow(GoodsInfoActivity context, View.OnClickListener itemsOnClick,GoodsInfo goodsInfo) {
        super(context);
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mMenuView = inflater.inflate(R.layout.addcarpop, null);
        addcar_img = (ImageView) mMenuView.findViewById(R.id.addcar_img);
        addcar_price = (TextView) mMenuView.findViewById(R.id.addcar_price);
        addcar_kucun = (TextView) mMenuView.findViewById(R.id.addcar_kucun);
        addcar_xg = (TextView) mMenuView.findViewById(R.id.addcar_xg);
        pop_text = (TextView) mMenuView.findViewById(R.id.pop_text);
        addcart_cancel = (ImageView) mMenuView.findViewById(R.id.addcart_cancel);
        ok = (Button) mMenuView.findViewById(R.id.ok);
        reduce = (ImageView) mMenuView.findViewById(R.id.reduce);
        add = (ImageView) mMenuView.findViewById(R.id.add);

        ImageLoader.getInstance().displayImage(goodsInfo.data.goods.goods_img,addcar_img);
        addcar_price.setText("￥"+goodsInfo.data.goods.shop_price);
        addcar_kucun.setText("库存"+goodsInfo.data.goods.stock_number+"件");
        addcar_xg.setText("限购"+goodsInfo.data.goods.restrict_purchase_num+"件");
        //设置按钮监听
        ok.setOnClickListener(itemsOnClick);
        reduce.setOnClickListener(itemsOnClick);
        add.setOnClickListener(itemsOnClick);
        addcart_cancel.setOnClickListener(itemsOnClick);

        //设置SelectPicPopupWindow的View
        this.setContentView(mMenuView);
        //设置SelectPicPopupWindow弹出窗体的宽
        this.setWidth(LinearLayout.LayoutParams.FILL_PARENT);
        //设置SelectPicPopupWindow弹出窗体的高
        this.setHeight(LinearLayout.LayoutParams.WRAP_CONTENT);
        //设置SelectPicPopupWindow弹出窗体可点击
        this.setFocusable(true);
        //设置SelectPicPopupWindow弹出窗体动画效果
        this.setAnimationStyle(R.style.take_photo_anim);


        //实例化一个ColorDrawable颜色为半透明
        ColorDrawable dw = new ColorDrawable(Color.WHITE);
        //设置SelectPicPopupWindow弹出窗体的背景
        this.setBackgroundDrawable(dw);
        //mMenuView添加OnTouchListener监听判断获取触屏位置如果在选择框外面则销毁弹出框
//        mMenuView.setOnTouchListener(new View.OnTouchListener() {
//
//            public boolean onTouch(View v, MotionEvent event) {
//
//                int height = mMenuView.findViewById(R.id.pop_layout).getTop();
//                int y=(int) event.getY();
//                if(event.getAction()== MotionEvent.ACTION_UP){
//                    if(y<height){
//                        dismiss();
//                    }
//                }
//                return true;
//            }
//        });

    }
}
