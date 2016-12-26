package com.haochibao.utill.adapter;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.haochibao.R;
import com.haochibao.utill.http.AsyncImageLoader;
import com.haochibao.utill.http.HttpUtils;
import com.haochibao.utill.http.Location;
import com.haochibao.utill.http.URIBitmap;
import com.haochibao.utill.model.FindModel;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

/**
 * Created by Administrator on 2016/12/13.
 */
public class FindListViewAdapter extends BaseAdapter {
    private AsyncImageLoader asyncImageLoader;
    Context context;
    List<FindModel> list;
    ListView listView;
    public FindListViewAdapter(Context context,List<FindModel> list,ListView listView){
        this.context=context;
        this.list=list;
        this.listView=listView;
        asyncImageLoader=new AsyncImageLoader();
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
        final  ViewHodler viewHodler;
        LayoutInflater layoutInflater=LayoutInflater.from(context);
        if (convertView==null) {
            viewHodler=new ViewHodler();
            convertView = layoutInflater.inflate(R.layout.activity_find_lv_item, null);
            viewHodler.user_image= (ImageView) convertView.findViewById(R.id.user_image);
            viewHodler.user_name= (TextView) convertView.findViewById(R.id.user_name);
            viewHodler.time= (TextView) convertView.findViewById(R.id.time);

            viewHodler.title= (TextView) convertView.findViewById(R.id.title);
            viewHodler.hotel_image= (ImageView) convertView.findViewById(R.id.hotel_image);
            viewHodler.hotel_name= (TextView) convertView.findViewById(R.id.hotel_name);
            viewHodler.describe= (TextView) convertView.findViewById(R.id.describe);

            convertView.setTag(viewHodler);
        }else {
            viewHodler=(ViewHodler) convertView.getTag();
        }

        FindModel findModel=list.get(position);
        viewHodler.user_name.setText(findModel.getUser_name());
        viewHodler.title.setText(findModel.getTitle());
        viewHodler.time.setText(findModel.getTime());
        viewHodler.hotel_name.setText(findModel.getHotel_name());
        viewHodler.describe.setText(findModel.getDescribe());
        String uri=findModel.getUser_icon();
        //获得用户头像
        ImageView imageView= viewHodler.user_image;
        imageView.setTag(uri);
        Drawable cacheImage=asyncImageLoader.loadDrawable(uri, new AsyncImageLoader.ImageCallback() {
            @Override
            public void imageLoaded(Drawable imageDrawable, String imageUrl) {
                ImageView imageViewByTog=(ImageView)listView.findViewWithTag(imageUrl);
                if (imageViewByTog != null){
                    imageViewByTog.setImageDrawable(imageDrawable);
                }
            }
        });
        if (cacheImage == null){
            imageView.setImageResource(R.mipmap.ic_launcher);
        }else {
            imageView.setImageDrawable(cacheImage);
        }
        //获得商铺图片
        ImageView imageView1=viewHodler.hotel_image;
        String uri1=findModel.getImg();
        imageView.setTag(uri1);
        Drawable cacheImage1=asyncImageLoader.loadDrawable(uri1, new AsyncImageLoader.ImageCallback() {
            @Override
            public void imageLoaded(Drawable imageDrawable, String imageUrl) {
                ImageView imageViewByTog=(ImageView)listView.findViewWithTag(imageUrl);
                if (imageViewByTog != null){
                    imageViewByTog.setImageDrawable(imageDrawable);
                }
            }
        });
        if (cacheImage1 == null){
            imageView1.setImageResource(R.mipmap.ic_launcher);
        }else {
            imageView1.setImageDrawable(cacheImage1);
        }

        return convertView;
    }
    class ViewHodler{
        ImageView user_image;
        TextView user_name;
        TextView time;
        TextView title;
        ImageView hotel_image;
        TextView hotel_name;
        TextView describe;
    }
}
