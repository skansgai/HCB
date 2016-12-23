package com.haochibao.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.haochibao.R;
import com.haochibao.activity.AttractionActivity;
import com.haochibao.activity.EntertainmentActivity;
import com.haochibao.activity.HotelActivity;
import com.haochibao.activity.InterctionActivity;
import com.haochibao.activity.ParkingActivity;
import com.haochibao.activity.RecommendActivity;
import com.haochibao.activity.SeekHelpActivity;
import com.haochibao.activity.ShopingActivity;
import com.haochibao.utill.http.AsyncImageLoader;
import com.haochibao.utill.http.GetHttp;
import com.haochibao.utill.http.GetImage;
import com.haochibao.utill.http.Location;
import com.haochibao.utill.http.URIBitmap;
import com.haochibao.utill.http.Weather;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by Administrator on 2016/12/13.
 * 我的页面
 */

public class HomeFragment extends Fragment {
    TextView home_recommend, home_interaction, home_seek_help, home_recreation,
            home_grogshop, home_scenic_spots, home_shopping, home_park, homepage_address,
            homepage_weather;
    private View view;
    private TextView homeRecommend;
    private Context context;
    private Activity mactivity;
    Intent intent;
    ListView listView;
    String tianqi;
    private Location location;
    String cituName="重庆";
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private final static String TAG="HaoChoBao";
    private Weather weather;
    private String bannerPath;
    private ImageView image_view;
    final static String HTTPUTI = "http://api.avatardata.cn/Weather/Query?key=d14a6039b7f7420c82d7d487eaa38bbe&cityname=";

    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            if (msg.what==0){
             homepage_address.setText(cituName);
            }
            if (msg.what==1){
                homepage_weather.setText(tianqi);
            }
            if (msg.what==3){
                Log.i("Handler========","image_view.setImageBitmap");
                Bitmap bitmap=(Bitmap) msg.obj;
                image_view.setImageBitmap(bitmap);
            }
        }
    };
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mactivity = getActivity();

    }

    @Nullable
    @Override
    public View onCreateView(
            LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.activity_homepage, null);
        context = getActivity();
        init();
        home_recommend = (TextView) view.findViewById(R.id.home_recommend);
        home_interaction = (TextView) view.findViewById(R.id.home_interaction);
        home_seek_help = (TextView) view.findViewById(R.id.home_seek_help);
        home_recreation = (TextView) view.findViewById(R.id.home_recreation);
        home_grogshop = (TextView) view.findViewById(R.id.home_grogshop);
        home_scenic_spots = (TextView) view.findViewById(R.id.home_scenic_spots);
        home_shopping = (TextView) view.findViewById(R.id.home_shopping);
        home_park = (TextView) view.findViewById(R.id.home_park);
        listView = (ListView) view.findViewById(R.id.homepage_lv);
        homepage_address = (TextView) view.findViewById(R.id.homepage_address);
        homepage_weather = (TextView) view.findViewById(R.id.homepage_weather);
        HomePageAdapter homePageAdapter = new HomePageAdapter(this.getActivity(), list);
        listView.setAdapter(homePageAdapter);
        home_recommend.setOnClickListener(onClickListener);
        home_interaction.setOnClickListener(onClickListener);
        home_seek_help.setOnClickListener(onClickListener);
        home_recreation.setOnClickListener(onClickListener);
        home_grogshop.setOnClickListener(onClickListener);
        home_scenic_spots.setOnClickListener(onClickListener);
        home_shopping.setOnClickListener(onClickListener);
        home_park.setOnClickListener(onClickListener);
        getLocation();
        getWeath();
        getHomeBanner();
        return view;
    }

    public void init() {
        homeRecommend = (TextView) view.findViewById(R.id.home_recommend);
        homeRecommend.setOnClickListener(onClickListener);
        sharedPreferences=context.getSharedPreferences("Weather",Context.MODE_APPEND);
        editor=sharedPreferences.edit();
        image_view= (ImageView) view.findViewById(R.id.image_view);
        image_view.setScaleType(ImageView.ScaleType.CENTER_CROP);
    }

    View.OnClickListener onClickListener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.home_recommend:
                    intent = new Intent(mactivity, RecommendActivity.class);
                    startActivity(intent);
                    break;
                case R.id.home_interaction:
                    intent = new Intent(mactivity, InterctionActivity.class);
                    startActivity(intent);
                    break;
                case R.id.home_seek_help:
                    intent = new Intent(mactivity, SeekHelpActivity.class);
                    startActivity(intent);
                    break;
                case R.id.home_recreation:
                    intent = new Intent(mactivity, EntertainmentActivity.class);
                    startActivity(intent);
                    break;
                case R.id.home_grogshop:
                    intent = new Intent(mactivity, HotelActivity.class);
                    startActivity(intent);
                    break;
                case R.id.home_scenic_spots:
                    intent = new Intent(mactivity, AttractionActivity.class);
                    startActivity(intent);
                    break;
                case R.id.home_shopping:
                    intent = new Intent(mactivity, ShopingActivity.class);
                    startActivity(intent);
                    break;
                case R.id.home_park:
                    intent = new Intent(mactivity, ParkingActivity.class);
                    startActivity(intent);
                    break;
            }

        }
    };
    List<String> list = new ArrayList<String>();

    class HomePageAdapter extends BaseAdapter {
        Context context;
        List<String> list;
        LayoutInflater layoutInflater;

        public HomePageAdapter(Context context, List<String> list) {
            this.context = context;
            this.list = list;
        }

        @Override
        public int getCount() {
            return 10;
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
            layoutInflater = LayoutInflater.from(context);
            if (convertView == null) {
                convertView = layoutInflater.inflate(R.layout.item_homepage_lv, null);
            }
            return convertView;
        }
    }
    public void getLocation(){
        location=new Location(context,handler);
        location.setOnClicklistener(new Location.onResultListener() {
            @Override
            public void onClick(String data) {
                editor.putString("location",location.getCityname());
                editor.commit();
            }
        });
        location.start();

    }
    public  void getWeath(){
        if (sharedPreferences.getString("location",null)!=null){
            cituName=sharedPreferences.getString("location",null);
            Log.i(TAG,cituName);
        }else{
            cituName="重庆";
        }
        weather = new Weather(HTTPUTI,"重庆",handler);
        weather.setOnClicklistener(new Weather.onResultListener() {
            @Override
            public void onClick(String data) {
                tianqi=data;
            }
        });
        weather.start();
    }
    public String  getHomeBanner(){
            try {
                String uri="http://192.168.7.23/index.php/home/index/getImage?state=1";
                URL url=new URL(uri);
                GetHttp http=new GetHttp(context,url);
                http.setOnClicklistener(new GetHttp.onResultListener() {
                    @Override
                    public void onClick(String data) throws JSONException, IOException {
                        Log.i("4444444444444444444444",data);
                        //数据解析
                        JSONObject object=new JSONObject(data);
                        JSONArray array=object.optJSONArray("result");
                        for (int i=0;i<array.length();i++){
                            JSONObject sunObject=array.getJSONObject(i);
                            bannerPath=sunObject.optString("path",null);
                            Bitmap bitmap=BitmapFactory.decodeStream(new URL(bannerPath).openStream());
                            Message message=new Message();
                            message.what=3;
                            message.obj=bitmap;
                            handler.sendMessage(message);
                        }
                    }
                });
                http.start();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
        return bannerPath;
    }

}
