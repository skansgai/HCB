package com.haochibao.utill.http;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

/**
 * Created by Administrator on 2016/12/22.
 */

public class GetImage {
    private  String uri;
    public GetImage (String uri){
        this.uri=uri;
    }
    public  void getResponsebitmap( ResultListener resultListener) {
        URL url;
        Bitmap map = null;
        try {
            // 创建url对象
            url = new URL(uri);
            // 通过url获取对象
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            // 设置连接超时
            conn.setConnectTimeout(10 * 1000);
            // 设置请求方式
            conn.setRequestMethod("GET");
            // 连接
            conn.connect();
            if (conn.getResponseCode() == 200) {
                InputStream inputStream = conn.getInputStream();
                map = BitmapFactory.decodeStream(inputStream);
                resultListener.onClick(map);
                inputStream.close();
            }
        } catch (MalformedURLException e) {
            Log.e("ur-->", "URL不正确");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    ResultListener resultListener;
    public  interface ResultListener{
        void onClick(Bitmap bitmap);
    }
}
