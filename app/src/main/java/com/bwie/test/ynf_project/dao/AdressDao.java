package com.bwie.test.ynf_project.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.bwie.test.ynf_project.bean.AdressBean;
import com.bwie.test.ynf_project.sqlite.MySQliteOpenHelper;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/12/24.
 */
public class AdressDao {

    private final MySQliteOpenHelper sq;

    public AdressDao(Context context) {
        sq = new MySQliteOpenHelper(context);
    }
    public void add(AdressBean adressBean){
        SQLiteDatabase db = sq.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("name",adressBean.getName());
        values.put("phone",adressBean.getPhone());
        values.put("province",adressBean.getProvince());
        values.put("adress",adressBean.getAdress());
        db.insert("adresss",null,values);
        db.close();
    }
    ArrayList<AdressBean> adressList = new ArrayList<>();
    public ArrayList<AdressBean> query(){
        SQLiteDatabase db = sq.getReadableDatabase();
        adressList.clear();
        Cursor cursor = db.query("adresss", null, null, null, null, null, null);
        while (cursor.moveToNext()){
            String name = cursor.getString(cursor.getColumnIndex("name"));
            String phone = cursor.getString(cursor.getColumnIndex("phone"));
            String province = cursor.getString(cursor.getColumnIndex("province"));
            String adress = cursor.getString(cursor.getColumnIndex("adress"));
            adressList.add(new AdressBean(name,phone,province,adress,true));
        }
        return adressList;
    }

}
