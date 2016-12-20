package com.haochibao.utill.http;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.ImageView;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Administrator on 2016/12/16.
 */

public class LoadPictrue {
    ImageView imageView;
    byte[] picByte;
   // String uri;
    public void getPicture(ImageView imageView){
       // this.uri = uri;
        this.imageView = imageView;
        new Thread(runnable).start();
    }

    Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 1){
                if (picByte != null){
                    Bitmap bitmap= BitmapFactory.decodeByteArray(picByte,0,picByte.length);
                    imageView.setImageBitmap(bitmap);
                }
            }
        }
    };
Runnable runnable=new Runnable() {
    @Override
    public void run() {
        String uri="http://192.168.7.23/image/image1.jpeg";
        try {
            URL url=new URL(uri);
            HttpURLConnection connection= (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setReadTimeout(5000);
            connection.connect();
            if (connection.getResponseCode()==200){
                InputStream fis=connection.getErrorStream();
                ByteArrayOutputStream bos=new ByteArrayOutputStream();
                byte[] bytes=new byte[1024];
                int length=-1;
                while ((length=fis.read(bytes))!=-1){
                    bos.write(bytes,0,length);
                }
                picByte=bos.toByteArray();
                Log.i("request",picByte+"");
                bos.close();
                fis.close();
                Message message=new Message();
                message.what=1;
                handler.sendMessage(message);
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
};
}
