package com.haochibao.utill.model;

import android.graphics.Bitmap;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/12/22.
 */
public class CommentModel {
    public String userName,userLevel,content,time,scan;
    public double rating;
    public Boolean state=false;
    public Bitmap portrait;
    public List<Bitmap> bitmaps = new ArrayList<Bitmap>();
}
