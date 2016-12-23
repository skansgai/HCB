package com.haochibao.utill.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.haochibao.R;
import com.haochibao.utill.model.EntertainmentModel;
import com.haochibao.utill.view_holder.SortViewHolder;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

/**
 * Created by Administrator on 2016/12/12.
 */
public class EntertainmentAdapter extends BaseAdapter {
    SortViewHolder viewHolder;
    Context context;
    LayoutInflater inflater;
    List<EntertainmentModel> list;
    public EntertainmentAdapter(Context context,List<EntertainmentModel> list){
        this.context = context;
        this.list = list;
        inflater = LayoutInflater.from(context);
        viewHolder=new SortViewHolder();
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
        if (convertView == null){
            convertView = inflater.inflate(R.layout.entertainment_list_item,null);
            viewHolder.setItemImg((ImageView) convertView.findViewById(R.id.item_img));
            viewHolder.setName((TextView) convertView.findViewById(R.id.name));
            viewHolder.setType((TextView) convertView.findViewById(R.id.type));
            viewHolder.setLocation((TextView) convertView.findViewById(R.id.location));
            viewHolder.setLook((TextView) convertView.findViewById(R.id.have_a_look_at));
            viewHolder.setPrice((TextView) convertView.findViewById(R.id.price));
            convertView.setTag(viewHolder);
        }else {
            viewHolder= (SortViewHolder) convertView.getTag();
        }
        if (list.get(position).getImg()!=null){
            viewHolder.getItemImg().setImageBitmap(list.get(position).getImg());
        }
        viewHolder.getName().setText(list.get(position).getName());
        viewHolder.getPrice().setText(list.get(position).getPrice());
        viewHolder.getLocation().setText(list.get(position).getLocation());
        viewHolder.getType().setText(list.get(position).getType());
        return convertView;
    }
}
