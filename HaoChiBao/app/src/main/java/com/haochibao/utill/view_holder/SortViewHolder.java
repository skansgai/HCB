package com.haochibao.utill.view_holder;

import android.util.SparseArray;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

/**
 * Created by Administrator on 2016/12/20.
 */
public class SortViewHolder {
    private TextView name,distance,price,location,type,look;
    private ImageView itemImg;

    public RatingBar getRoomRatingBar() {
        return roomRatingBar;
    }

    public void setRoomRatingBar(RatingBar roomRatingBar) {
        this.roomRatingBar = roomRatingBar;
    }

    public TextView getName() {
        return name;
    }

    public void setName(TextView name) {
        this.name = name;
    }

    public TextView getDistance() {
        return distance;
    }

    public void setDistance(TextView distance) {
        this.distance = distance;
    }

    public TextView getPrice() {
        return price;
    }

    public void setPrice(TextView price) {
        this.price = price;
    }

    public TextView getLocation() {
        return location;
    }

    public void setLocation(TextView location) {
        this.location = location;
    }

    public TextView getType() {
        return type;
    }

    public void setType(TextView type) {
        this.type = type;
    }

    public TextView getLook() {
        return look;
    }

    public void setLook(TextView look) {
        this.look = look;
    }

    public ImageView getItemImg() {
        return itemImg;
    }

    public void setItemImg(ImageView itemImg) {
        this.itemImg = itemImg;
    }

    private RatingBar roomRatingBar;
    /*private final SparseArray<View> views;
    private View convertView;

    public SortViewHolder(View convertView) {
        this.views = new SparseArray<View>();
        this.convertView = convertView;
        convertView.setTag(this);
    }
    public static SortViewHolder get(View convertView){
        if (convertView == null){
            return new SortViewHolder(convertView);
        }
        SortViewHolder existedHolder = (SortViewHolder)convertView.getTag();
        return existedHolder;
    }
    public <T extends View> T getView(int viewId){
        View view = views.get(viewId);
        if (view == null){
            view =convertView.findViewById(viewId);
            views.put(viewId,view);
        }
        return (T) view;
    }*/
}
