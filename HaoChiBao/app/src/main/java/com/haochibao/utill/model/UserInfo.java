package com.haochibao.utill.model;

import android.graphics.Bitmap;

/**
 * Created by Administrator on 2016/12/26.
 */

public class UserInfo {
    private  String name;
    private  Bitmap bitmap;

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
