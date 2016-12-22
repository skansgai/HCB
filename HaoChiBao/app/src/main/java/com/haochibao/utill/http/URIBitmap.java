package com.haochibao.utill.http;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.widget.ImageView;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;

/**
 * Created by Administrator on 2016/12/20.
 */

public class URIBitmap  {
    /**
     * 通过uri获取图片并进行压缩
     *
     * @param uri
     */
    private String uri;
    public URIBitmap(String uri){
        this.uri=uri;

    }
    public void loadImage(final ImageCallBack imageCallBack){
        final Handler handler=new Handler(){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                Drawable drawable=(Drawable)msg.obj;
                imageCallBack.getDrawable(drawable);
            }
        };
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Drawable drawable=Drawable.createFromStream(new URL(uri).openStream(),"");
                    Message message=new Message();
                    message.obj=drawable;
                    handler.sendMessage(message);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();

    }
    public interface ImageCallBack {
        public void getDrawable(Drawable drawable);
    }
}
