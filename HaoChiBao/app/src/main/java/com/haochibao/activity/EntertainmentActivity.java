package com.haochibao.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.haochibao.R;
import com.haochibao.utill.adapter.EntertainmentAdapter;
import com.haochibao.utill.http.GetHttp;
import com.haochibao.utill.model.EntertainmentModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/12/12.
 */
public class EntertainmentActivity extends Activity {
    ListView enterList;
    ImageView imgLeft;
    List<EntertainmentModel> list;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entertainment);
        imgLeft = (ImageView) findViewById(R.id.img_left);
        enterList = (ListView) findViewById(R.id.entertainment_list);
        list = new ArrayList<EntertainmentModel>();
        new Thread(){
            @Override
            public void run() {
                getInternetData();
                Message message=new Message();
                message.what=1111;
                handler.sendMessage(message);
            }
        }.start();

        imgLeft.setOnClickListener(getOnClickListener());
        enterList.setAdapter(new EntertainmentAdapter(EntertainmentActivity.this,list));
        enterList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(EntertainmentActivity.this,HotPotDetailsActivity.class);
                startActivity(intent);
            }
        });
    }
    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 1111:
                    enterList.setAdapter(new EntertainmentAdapter(EntertainmentActivity.this,list));
                    break;
            }

        }
    };
    public View.OnClickListener getOnClickListener(){
        View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()){
                    case R.id.img_left:
                        finish();
                        break;
                }
            }
        };
        return onClickListener;
    }
    public void getInternetData(){
        HttpURLConnection httpURLConnection = null;
        String httpUrl="http://192.168.7.22/index.php/home/index/getServiceType?typename="+ URLEncoder.encode("娱乐");
        try {
            URL url = new URL(httpUrl);
            httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("GET");
            httpURLConnection.setConnectTimeout(5000);
            httpURLConnection.connect();
            if (httpURLConnection.getResponseCode()==HttpURLConnection.HTTP_OK){
                StringBuilder stringBuilder = new StringBuilder();
                InputStream inputStream = httpURLConnection.getInputStream();
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream,"utf-8");
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String s;
                while ((s=bufferedReader.readLine())!=null){
                    stringBuilder.append(s);
                }
                Log.i("data====>",stringBuilder.toString());
                JSONObject jsonObject = new JSONObject(stringBuilder.toString());
                JSONArray jsonArray = jsonObject.getJSONArray("result");
                for (int i=0;i<jsonArray.length();i++){
                    JSONObject object = jsonArray.getJSONObject(i);
                    String name = object.optString("name");
                    Log.i("name===>",name);
                    String img = object.optString("img");
                    String price = object.optString("price");
                    String location = object.optString("location");
                    String type = object.optString("type_name");
                    EntertainmentModel model = new EntertainmentModel();
                    Bitmap imgBitmap = getBitmap(img);
                    model.setImg(imgBitmap);
                    model.setName(name);
                    model.setLocation(location);
                    model.setPrice(price);
                    model.setType(type);
                    list.add(model);
                }
                inputStream.close();
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    public Bitmap getBitmap(String imgUrl) {
        Bitmap bitmap = null;
        try {
            URL url = new URL(imgUrl);
            bitmap = BitmapFactory.decodeStream(url.openStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bitmap;
    }
}
