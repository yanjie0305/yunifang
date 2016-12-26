package com.bwie.test.ynf_project.bean;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/12/24.
 */
public class AdressBean implements Serializable{
    public String name;
    public String phone;
    public String province;
    public String adress;
    public boolean check;

    public AdressBean() {
    }

    public AdressBean(String name, String phone, String province, String adress, boolean check) {
        this.name = name;
        this.phone = phone;
        this.province = province;
        this.adress = adress;
        this.check = check;
    }

    public boolean isCheck() {
        return check;
    }

    public void setCheck(boolean check) {
        this.check = check;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getAdress() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }
}
