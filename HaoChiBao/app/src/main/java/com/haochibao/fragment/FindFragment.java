package com.haochibao.fragment;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.haochibao.R;
import com.haochibao.utill.adapter.FindListViewAdapter;
import com.haochibao.utill.http.GetHttp;
import com.haochibao.utill.http.URIBitmap;
import com.haochibao.utill.model.FindModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/12/13.
 */

public class FindFragment extends Fragment {
    final static String TAG="HaoChiBao";
    private View view;
    private TextView location;
    private RadioGroup radioGroup;
    private RadioButton newsBtn,attentionBtn;
    private ImageView shareBtn;
    private ListView listView;
    private Context context;
    private List<HashMap<String,Object>> attentionList=new ArrayList<HashMap<String,Object>>();
    private Intent intent;
    private FindListViewAdapter findAdapter;
    private SimpleAdapter simpleAdapter;
    private String[] from={"userImage","userName","LeftIcon","RightIcon"};
    private int[] to={R.id.user_image,R.id.user_name,R.id.left_icon,R.id.right_icon};
    private Map<String,Object> map=new HashMap<String,Object>();
    private RelativeLayout layout;
    private List<FindModel> findList=new ArrayList<FindModel>();
    FindModel findModel=new FindModel();
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context=getActivity();
        getShare();
    }
    @Nullable
    @Override
    public View onCreateView(
            LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.activity_find_lv,null);
        init();
        return view;
    }

    public void init(){
        layout= (RelativeLayout) view.findViewById(R.id.home_layout);
        location= (TextView) view.findViewById(R.id.location);
        radioGroup= (RadioGroup) view.findViewById(R.id.group);
        newsBtn= (RadioButton) view.findViewById(R.id.news);
        attentionBtn= (RadioButton) view.findViewById(R.id.attention);
        shareBtn= (ImageView) view.findViewById(R.id.share);
        listView= (ListView) view.findViewById(R.id.find_list_view);
        findAdapter=new FindListViewAdapter(context,findList,listView);
        location.setOnClickListener(onClickListener);
        radioGroup.setOnCheckedChangeListener(onCheckedChangeListener);
        sharedPreferences=context.getSharedPreferences("findData",Context.MODE_APPEND);
        editor=sharedPreferences.edit();
        getAttentionData();
        simpleAdapter=new SimpleAdapter(
                context,
                attentionList,
                R.layout.activity_find_lv_attention_item,
                from,
                to
        );
    }
    View.OnClickListener onClickListener=new View.OnClickListener() {
        @Override
        public void onClick(View v) {
        switch (v.getId()){
            case R.id.location:
                break;
            case R.id.share:

                break;
            }
        }
    };
    RadioGroup.OnCheckedChangeListener onCheckedChangeListener=new RadioGroup.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId){
            case R.id.news:
                newsBtn.setTextColor(getResources().getColor(R.color.mainRed));
                attentionBtn.setTextColor(getResources().getColor(R.color.white));
                listView.setAdapter(findAdapter);
                break;
            case R.id.attention:
                newsBtn.setTextColor(getResources().getColor(R.color.white));
                attentionBtn.setTextColor(getResources().getColor(R.color.mainRed));
                listView.setAdapter(simpleAdapter);
                break;
        }
        }
    };
    public void getAttentionData(){
        for (int i=0;i<20;i++){
            map.put("userImage",R.mipmap.my_head_portrait);
            map.put("userName","娃哈哈！！！");
            int r= (int) (Math.random()*3+1);
            if (r==1){
                map.put("LeftIcon",R.mipmap.found_interactive_arro);
            }else if (r==2){
                map.put("LeftIcon",R.mipmap.found_interactive_arr);
            }else if (r==3){
                map.put("LeftIcon",R.mipmap.found_interactive_arrow);
            }
            map.put("RightIcon",R.mipmap.my_location);
            attentionList.add((HashMap<String, Object>) map);
        }
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    public void getShare(){
        String uri="http://192.168.7.23/index.php/home/index/getShare";
        try {
            URL url=new URL(uri);
            GetHttp getHttp=new GetHttp(context,url);
            getHttp.setOnClicklistener(new GetHttp.onResultListener() {
                @Override
                public void onClick(String data) throws JSONException {
                    Log.i("4444444444444444444444",data);
                    //数据解析
                    if (sharedPreferences.getString("findData",null)==null){
                        editor.putString("findData",null);
                        editor.commit();
                    }else {
                        data=sharedPreferences.getString("findData",null);
                    }
                    JSONObject object = null;
                    try {
                        object = new JSONObject(data);
                        JSONArray resultArray = object.optJSONArray("result");
                        for (int i=0;i<resultArray.length();i++){
                            JSONObject subJson=resultArray.getJSONObject(i);
                            findModel.setUser_icon(subJson.optString("icon_path"));
                            findModel.setUser_name(subJson.optString("user_name"));
                            findModel.setTitle(subJson.optString("title"));
                            findModel.setTime(subJson.optString("time"));
                            findModel.setHotel_name(subJson.optString("name"));
                            findModel.setDescribe(subJson.optString("describe"));
                            findList.add(findModel);
                            Message message=new Message();
                            message.what=2;
                            handler.sendMessage(message);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
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
            switch (msg.what){
                case 2:
                    listView.setAdapter(findAdapter);
                    break;
            }
        }
    };
}
