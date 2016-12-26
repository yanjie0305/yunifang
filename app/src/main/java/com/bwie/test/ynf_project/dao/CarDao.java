package com.bwie.test.ynf_project.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.bwie.test.ynf_project.bean.Car;
import com.bwie.test.ynf_project.sqlite.MySQliteOpenHelper;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/12/9.
 */
public class CarDao {

    private final MySQliteOpenHelper sqlite;

    public CarDao(Context context) {
        sqlite = new MySQliteOpenHelper(context);
    }
    public void add(Car car){
        SQLiteDatabase db = sqlite.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("title",car.getTitle());
        values.put("price",car.getPrice());
        values.put("img",car.getImg());
        values.put("count",car.getCount());
        values.put("icon",car.getIcon());
        values.put("id",car.getId());
        db.insert("shopcar",null,values);
        db.close();
    }
    public void delete(String title){
        SQLiteDatabase db = sqlite.getWritableDatabase();
        db.delete("shopcar","title=?",new String[]{title});
        db.close();
    }

        ArrayList<Car> carList = new ArrayList<>();
    public ArrayList<Car> query(){
        SQLiteDatabase db = sqlite.getReadableDatabase();
        Cursor cursor = db.query("shopcar", null, null, null, null, null, null);
        carList.clear();
        while (cursor.moveToNext()){
            String title = cursor.getString(cursor.getColumnIndex("title"));
            String price = cursor.getString(cursor.getColumnIndex("price"));
            String count = cursor.getString(cursor.getColumnIndex("count"));
            String img = cursor.getString(cursor.getColumnIndex("img"));
            String icon = cursor.getString(cursor.getColumnIndex("icon"));
           String id = cursor.getString(cursor.getColumnIndex("id"));

            carList.add(new Car(title,price,img,count,icon,false,id));

        }
        return carList;
    }
}
