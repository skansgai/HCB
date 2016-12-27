package com.haochibao.utill.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/12/22.
 */
public class CommentModel {
    public String userName,userLevel,content,time,scan,portraitPath,imgPath;
    public double rating;
    public Boolean state=false;
    public List<Integer> imgIds = new ArrayList<Integer>();
}
