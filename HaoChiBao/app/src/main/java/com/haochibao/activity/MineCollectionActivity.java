package com.haochibao.activity;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;

import com.haochibao.MyApplication;
import com.haochibao.R;
import com.haochibao.utill.adapter.MineCollectionAdapter;
import com.haochibao.utill.http.GetHttp;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * Created by Administrator on 2016/12/10.
 */

public class MineCollectionActivity extends Activity {
    private ListView listView;
    private ImageView backBtn,cleanCollection;
    private List<HashMap<String,Object>> list=new ArrayList<HashMap<String,Object>>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mine_collection_lv);
        init();
        getData();
    }

    public void init(){
        backBtn= (ImageView) findViewById(R.id.back_btn);
        cleanCollection= (ImageView) findViewById(R.id.clean_collection);
        backBtn.setOnClickListener(onClickListener);
        listView= (ListView) findViewById(R.id.my_collection_lv);

    }
    View.OnClickListener onClickListener=new View.OnClickListener() {
        @Override
        public void onClick(View v) {
        switch (v.getId()){
            case R.id.back_btn:
            finish();
            break;
            }
        }
    };
    public void getData(){
        String uri="http://119.29.60.248/index.php/home/collect/getCollection?user_id="+ MyApplication.getUserId();
        try {
            URL url=new URL(uri);
            final GetHttp getHttp=new GetHttp(MineCollectionActivity.this,url);
            getHttp.setOnClicklistener(new GetHttp.onResultListener() {
                @Override
                public void onClick(String data) throws JSONException, IOException {
                    if (data!=null){
                        Log.i("MineCollection",data);
                        final JSONObject object=new JSONObject(data);
                        String requstCode=object.optString("requestCode");
                        if (Integer.valueOf(requstCode)==200){
                            JSONArray array=object.optJSONArray("result");
                            for (int i=0;i<array.length();i++){
                                JSONObject object1=array.optJSONObject(i);
                                String uri1="http://119.29.60.248/index.php/home/index/getServiceInfo?id=" +
                                        ""+Integer.valueOf(object1.optString("service_id"));
                                URL url1=new URL(uri1);
                                GetHttp getHttp1=new GetHttp(MineCollectionActivity.this,url1);
                                getHttp1.setOnClicklistener(new GetHttp.onResultListener() {
                                    @Override
                                    public void onClick(String data) throws JSONException, IOException {
                                        if (data!=null){
                                            Log.i("Collection-ServiceInfo",data);
                                            JSONObject jsonObject=new JSONObject(data);
                                            if (Integer.valueOf(jsonObject.optString("requestCode"))==200){
                                                JSONArray array1=jsonObject.optJSONArray("result");
                                                for (int j=0;j<array1.length();j++){
                                                    JSONObject jsonObject1=array1.getJSONObject(j);
                                                    HashMap<String,Object> map=new HashMap<String,Object>();
                                                    map.put("name",jsonObject1.optString("name"));
                                                    map.put("price",jsonObject1.optString("price"));
                                                    map.put("location",jsonObject1.optString("location"));
                                                    map.put("grade",jsonObject1.optString("grade"));
                                                    Bitmap bitmap= BitmapFactory.decodeStream(
                                                            new URL(jsonObject1.optString("img")).openStream());
                                                    map.put("img",bitmap);
                                                    list.add(map);
                                                    Message message=new Message();
                                                    message.what=0;
                                                    handler.sendMessage(message);
                                                }
                                            }
                                        }
                                    }
                                });
                                getHttp1.start();
                            }

                        }
                    }
                }
            });
            getHttp.start();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }
    Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            if (msg.what==0){
                MineCollectionAdapter mineCollectionAdapter=new MineCollectionAdapter(MineCollectionActivity.this,list);
                listView.setAdapter(mineCollectionAdapter);
            }
        }
    };
}
