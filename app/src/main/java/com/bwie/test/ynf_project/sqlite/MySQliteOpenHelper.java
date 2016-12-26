package com.bwie.test.ynf_project.sqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Administrator on 2016/12/9.
 */
public class MySQliteOpenHelper  extends SQLiteOpenHelper{
    public MySQliteOpenHelper(Context context) {
        super(context,"bwei",null,1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table shopcar(_id integer primary key autoincrement,title varchar(50)" +
                ",price varchar(20),count varchar(20),img varchar(100),icon varchar(50),id varchar(20),state varchar(20))");
        db.execSQL("create table adresss(_id integer primary key autoincrement,name varchar(50)" +
                ",phone varchar(20),province varchar(20),adress varchar(100))");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
