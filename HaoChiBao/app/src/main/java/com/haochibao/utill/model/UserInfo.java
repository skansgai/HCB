package com.haochibao.utill.model;

import android.graphics.Bitmap;

/**
 * Created by Administrator on 2016/12/26.
 */

public class UserInfo {
    private  String name;
    private  Bitmap bitmap;
    private  String sex;
    private  String location;
    private  String signature;
    private  String birthday;

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public  Bitmap getBitmap() {
        return bitmap;
    }

    public  void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    public  String getName() {
        return name;
    }

    public  void setName(String name) {
        this.name = name;
    }
}
