package com.haochibao.utill.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.haochibao.R;

import java.util.HashMap;
import java.util.List;

/**
 * Created by Administrator on 2016/12/10.
 */

public class MineCollectionAdapter extends BaseAdapter {

    Context context;
    LayoutInflater inflater;
    List<HashMap<String,Object>> list;
    public MineCollectionAdapter(Context context,List<HashMap<String,Object>> list){
        this.context = context;
        this.list=list;
        inflater = LayoutInflater.from(context);
    }
    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHodler viewHodler;
        if (convertView == null){
            viewHodler=new ViewHodler();
            convertView = inflater.inflate(R.layout.activity_mine_collection_lv_item,null);
            viewHodler.imageView= (ImageView) convertView.findViewById(R.id.image_view);
            viewHodler.name= (TextView) convertView.findViewById(R.id.store_name);
            viewHodler.grade= (RatingBar) convertView.findViewById(R.id.grade);
            viewHodler.price= (TextView) convertView.findViewById(R.id.people_price);
            viewHodler.GoTo= (TextView) convertView.findViewById(R.id.go_to);
            convertView.setTag(viewHodler);
        }
        viewHodler= (ViewHodler) convertView.getTag();
        HashMap<String,Object> map=list.get(position);
        viewHodler.imageView.setImageBitmap((Bitmap) map.get("img"));
        viewHodler.name.setText((String)map.get("name")+"("+map.get("location")+"店)");
        viewHodler.price.setText(map.get("price")+"元/人");
        viewHodler.grade.setRating(Float.valueOf((String)map.get("grade")));
        viewHodler.GoTo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("GoTo","我要去");
            }
        });
        return convertView;
    }
    class ViewHodler{
        ImageView imageView;
        TextView name;
        RatingBar grade;
        TextView price;
        TextView GoTo;
    }
}
