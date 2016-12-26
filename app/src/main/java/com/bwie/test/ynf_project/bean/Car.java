package com.bwie.test.ynf_project.bean;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/12/9.
 */
public class Car implements Serializable{
    public String id;
    public  String title;
    public  String price;
    public  String img;
    public  String count;
    public  String icon;
    public Boolean check;

    public Boolean getCheck() {
        return check;
    }

    public void setCheck(Boolean check) {
        this.check = check;
    }

    public Car(String title, String price, String img, String count, String icon, Boolean check,String id) {
        this.title = title;
        this.price = price;
        this.img = img;
        this.count = count;
        this.icon = icon;
        this.check = check;
        this.id = id;
    }

    public Car() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }
}
