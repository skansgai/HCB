package com.haochibao.utill.http;

import android.util.Log;

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
    String http;
    String cityName;
    
    public Weather(String uri, String cityName ){
        this.http=uri;
        this.cityName=cityName;
    }
    @Override
    public void run() {
        try {
            String city= URLEncoder.encode(cityName,"utf-8");
            String uri=http+city;
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
                String data=builder.toString();
                Log.i("Result",data);
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
