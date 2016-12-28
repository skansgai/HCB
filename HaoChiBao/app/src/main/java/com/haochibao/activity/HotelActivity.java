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
import android.widget.Spinner;

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
public class HotelActivity extends FragmentActivity {
    ListView hotelList;
    ImageView img_left;
    List<EntertainmentModel> list;
    Spinner spinnerOne;
    Spinner spinnerTwo;
    String sort;
    String rank;
    String distance;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hotel);
        hotelList = (ListView) findViewById(R.id.hotel_list);
        img_left = (ImageView) findViewById(R.id.img_left);
        spinnerOne = (Spinner) findViewById(R.id.spinner_one);
        spinnerTwo = (Spinner) findViewById(R.id.spinner_two);
        list = new ArrayList<EntertainmentModel>();
        rank = "price";
        img_left.setOnClickListener(getOnClickListener());
        setSelectedListener();
    }
    public void startThread(){
        new Thread(){
            @Override
            public void run() {
                getInternetData();
                Message message = new Message();
                message.what=1101;
                handler.sendMessage(message);
            }
        }.start();
    }
    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 1101:
                    hotelList.setAdapter(new EntertainmentAdapter(HotelActivity.this,list));
                    hotelList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            Intent intent = new Intent(HotelActivity.this,HotPotDetailsActivity.class);
                            startActivity(intent);
                        }
                    });
                    break;
            }
        }
    };
    public void setSelectedListener(){
        spinnerOne.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spinnerTwo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                sort = HotelActivity.this.getResources().getStringArray(R.array.ranking)[position];
                switch (sort){
                    case "排序":
                    case "价格最高":
                    case "价格最低":
                        rank = "price";
                        break;
                    case "人气最高":
                    case "评价最高":
                        rank = "grade";
                        break;
                }
                startThread();
                Log.i("sort:",sort+"has been selected");
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
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
        String httpUrl="http://10.0.2.2/index.php/home/index/getServiceType?typename=酒店&by="+ URLEncoder.encode(rank);
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
                list.clear();
                for (int i=0;i<jsonArray.length();i++){
                    JSONObject object = jsonArray.getJSONObject(i);
                    String imgPath = object.optString("img");
                    String name = object.optString("name");
                    String price = object.optString("price");
                    String location = object.optString("location");
                    String type = object.optString("type_name");
                    Bitmap bitmap = getBitmap(imgPath);
                    EntertainmentModel model = new EntertainmentModel();
                    model.setImg(bitmap);
                    model.setName(name);
                    model.setLocation(location);
                    model.setPrice(price);
                    model.setType(type);
                    list.add(model);
                }
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
