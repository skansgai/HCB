package com.haochibao.utill.view_holder;

import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

/**
 * Created by Administrator on 2016/12/20.
 */
public class SortViewHolder {
    public TextView getName() {
        return name;
    }

    public void setName(TextView name) {
        this.name = name;
    }

    public TextView getType() {
        return type;
    }

    public void setType(TextView type) {
        this.type = type;
    }

    public TextView getDistance() {
        return distance;
    }

    public void setDistance(TextView distance) {
        this.distance = distance;
    }

    public TextView getLook() {
        return look;
    }

    public void setLook(TextView look) {
        this.look = look;
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

    public ImageView getItemImg() {
        return itemImg;
    }

    public void setItemImg(ImageView itemImg) {
        this.itemImg = itemImg;
    }

    public RatingBar getRoomRatingBar() {
        return roomRatingBar;
    }

    public void setRoomRatingBar(RatingBar roomRatingBar) {
        this.roomRatingBar = roomRatingBar;
    }

    TextView name,type,distance,look,price,location;
    ImageView itemImg;
    RatingBar roomRatingBar;

}
