package com.haochibao.utill.http;

import android.content.Context;
import android.util.Log;

import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Administrator on 2016/12/20.
 */

public class GetHttp extends Thread{
    final static String TAG="HaoChiBao";
    private HttpURLConnection connection=null;
    private URL url=null;
    private Context context;

    String data=null;
    public GetHttp(Context context,URL url){
        this.context=context;
        this.url=url;
    }
    public void getHttp(){
        try {
            connection= (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setReadTimeout(5000);
            connection.connect();
            if (connection.getResponseCode()==HttpURLConnection.HTTP_OK){
                StringBuilder builder=new StringBuilder();
                BufferedReader buffer=new
                        BufferedReader(new InputStreamReader(connection.getInputStream(),"utf-8"));
                String s;
                while ((s=buffer.readLine())!=null){
                    builder.append(s);
                }
                data=builder.toString();
                Log.i(TAG,"Request"+data);
                resultListener.onClick(data);
            }else {
                Log.i(TAG,"错误码"+connection.getResponseCode());
                resultListener.onClick(connection.getRequestMethod());
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void run() {
        getHttp();
    }
    public String getData() {
        if (data!=null){
            return data;
        }
        return "0";
    }
    onResultListener resultListener;
    public void setOnClicklistener(onResultListener onResultListener){
        this.resultListener=onResultListener;
    }
    public interface onResultListener{
        void onClick(String data) throws JSONException;
    }
}
