package com.haochibao.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import com.haochibao.R;
import com.haochibao.utill.adapter.EntertainmentAdapter;
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
public class ParkingActivity extends FragmentActivity {
    ListView parkingList;
    ImageView img_left;
    List<EntertainmentModel> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parking);
        parkingList = (ListView) findViewById(R.id.parking_list);
        img_left = (ImageView) findViewById(R.id.img_left);
        list = new ArrayList<EntertainmentModel>();
        new Thread(){
            @Override
            public void run() {
                getInternetData();
                Message message = new Message();
                message.what=1101;
                handler.sendMessage(message);
            }
        }.start();
        img_left.setOnClickListener(getOnClickListener());
        parkingList.setAdapter(new EntertainmentAdapter(this,list));
        parkingList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(ParkingActivity.this,HotPotDetailsActivity.class);
                startActivity(intent);
            }
        });
    }
    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 1101:
                    parkingList.setAdapter(new EntertainmentAdapter(ParkingActivity.this,list));
                    break;
            }
        }
    };

    public View.OnClickListener getOnClickListener() {
        View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
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
        String httpUrl="http://192.168.7.22/index.php/home/index/getServiceType?typename="+ URLEncoder.encode("停车场");
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
                    Log.i("img===>",img);
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
