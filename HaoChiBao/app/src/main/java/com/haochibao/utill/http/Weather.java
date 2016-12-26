package com.haochibao.utill.http;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

/**
 * Created by Administrator on 2016/12/17.
 */

public class Weather extends Thread {
    private final static String TAG="HaoChoBao";
    String http;
    String cityName;
    String data=null;
    Handler handler;
    String TianQi="哈哈";
    public Weather(String uri, String cityName, Handler handler){
        this.http=uri;
        this.cityName=cityName;
        this.handler=handler;
    }
    @Override
    public void run() {
        try {
            String city= URLEncoder.encode(cityName,"utf-8");
            String uri=http+city;
            Log.i("uri====>",""+uri);
            URL url=new URL(uri);
            HttpURLConnection conn= (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setConnectTimeout(5000);
            conn.connect();
            if (conn.getResponseCode()== HttpURLConnection.HTTP_OK){
                StringBuilder builder=new StringBuilder();
                BufferedReader buf=new BufferedReader(new InputStreamReader(conn.getInputStream(),"utf-8"));
                String s;
                while ((s=buf.readLine())!=null){
                    builder.append(s);
                }
                data=builder.toString();
                Log.i(TAG,data);
                JSONObject jsonObject=new JSONObject(data);
                JSONObject result=jsonObject.optJSONObject("result");
                JSONArray jsonArray=result.optJSONArray("weather");
                JSONObject monday=jsonArray.getJSONObject(0);
                JSONObject info=monday.optJSONObject("info");
                JSONArray night=info.optJSONArray("night");
                TianQi=night.getString(1);
                resultListener.onClick(TianQi);
                Message message=new Message();
                message.what=1;
                handler.sendMessage(message);
                buf.close();
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    public String resultWeather(){
            return TianQi;
    }
    public onResultListener resultListener;
    public void setOnClicklistener(onResultListener onResultListener){
        this.resultListener=onResultListener;
    }
    public interface onResultListener{
        void  onClick(String data);
    }
}
