package com.haochibao.activity;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.haochibao.R;
import com.haochibao.utill.adapter.CommentListAdater;
import com.haochibao.utill.model.CommentModel;
import com.haochibao.utill.view.FlowLayout;

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
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/12/10.
 */
public class CommentActivity extends Activity {
    LinearLayout commentSquare;
    RadioGroup rgTop;
    RadioButton rgTopLeft;
    RadioButton rgTopRight;
    ImageView btnBack;
    TextView btnSearch;
    List<CommentModel> list = new ArrayList<CommentModel>();
    ListView commentList;
    FlowLayout searchList;
    String[] searchNames = {"全部","好评","差评","最新点评","团购点评（81）", "分量较少（51）",
            "请客（4）","锅底不错（6）","店面大","口味真不错（66）","服务热情" , "位置不错"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);
        LayoutInflater inflater = LayoutInflater.from(this);
        LinearLayout listHead = (LinearLayout) inflater.inflate(R.layout.comment_list_head,null);
        commentList = (ListView) findViewById(R.id.comment_list);
        commentList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                commentList.deferNotifyDataSetChanged();
            }
        });
        searchList = (FlowLayout) listHead.findViewById(R.id.comment_search_list);
        rgTop = (RadioGroup) findViewById(R.id.rg_top);
        rgTopLeft = (RadioButton) findViewById(R.id.rg_top_left);
        rgTopRight = (RadioButton) findViewById(R.id.rg_top_right);
        btnBack = (ImageView) findViewById(R.id.btn_back);
        btnSearch = (TextView) findViewById(R.id.btn_top_search);
        startThread();
        addSearchList();
        //设置RadioButton的初始状态
        resetRadioButton(1);
        rgTopLeft.setChecked(true);

        commentList.addHeaderView(listHead,null,true);
        rgTop.setOnCheckedChangeListener(getOnCheckedChangeListener());
        btnBack.setOnClickListener(getOnClickListener());
        btnSearch.setOnClickListener(getOnClickListener());
    }
    public void startThread(){
        new Thread(){
            @Override
            public void run() {
                getListData();
                Message message = new Message();
                message.what = 2000;
                handler.sendMessage(message);
            }
        }.start();
    }
    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 2000:
                    commentList.setAdapter(new CommentListAdater(CommentActivity.this,list));
                    break;
            }
        }
    };
    public void getListData(){
        String httpUrl = "http://119.29.60.248/index.php/home/comment/getUserComment?service_id=1";
        try {
            URL url = new URL(httpUrl);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
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
                    String comment = object.optString("comment");
                    String time = object.optString("time");
                    String portraitPath = object.optString("icon_path");
                    String imgPath = object.optString("img");
                    String userName = object.optString("user_name");
                    String support = object.optString("support");
                    Bitmap bitmap = getBitmap(portraitPath);
                    Bitmap bitmap1 = getBitmap(imgPath);
                    CommentModel model = new CommentModel();
                    model.portrait = bitmap;
                    model.bitmaps.add(bitmap1);
                    model.scan="134";
                    model.userName = userName;
                    model.userLevel = "99";
                    model.content = comment;
                    model.time = time;
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

    /**
     * 为顶部的热门评论页面添加视图  的方法
     */
    public void addSearchList(){
        LayoutInflater inflater = LayoutInflater.from(this);
        for (int i=0;i<searchNames.length;i++){
            TextView hotSearchChild = (TextView) inflater.inflate(R.layout.hot_search_child,searchList,false);
            hotSearchChild.setText(searchNames[i]);
            searchList.addView(hotSearchChild);
        }
    }
    public RadioGroup.OnCheckedChangeListener getOnCheckedChangeListener(){
        RadioGroup.OnCheckedChangeListener onCheckedChangeListener = new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                switch (checkedId){
                    case R.id.rg_top_left:
                        resetRadioButton(1);
                        break;
                    case R.id.rg_top_right:
                        resetRadioButton(2);
                        break;
                }
            }
        };
        return onCheckedChangeListener;
    }
    public void resetRadioButton(int i){
        switch (i){
            case 1:
                rgTopLeft.setTextColor(getResources().getColor(R.color.mainRed));
                rgTopRight.setTextColor(getResources().getColor(R.color.white));
                break;
            case 2:
                rgTopRight.setTextColor(getResources().getColor(R.color.mainRed));
                rgTopLeft.setTextColor(getResources().getColor(R.color.white));
                break;
        }
    }
    public View.OnClickListener getOnClickListener(){
        View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent;
                switch (v.getId()){
                    case R.id.btn_back:
                        finish();
                        break;
                    case R.id.btn_top_search:
                        intent = new Intent(CommentActivity.this,SearchActivity.class);
                        startActivity(intent);
                        break;
                }
            }
        };
        return onClickListener;
    }
    public Bitmap getBitmap(String url){
        Bitmap bitmap = null;
        try {
            URL mUrl = new URL(url);
            bitmap = BitmapFactory.decodeStream(mUrl.openStream());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bitmap;
    }
}
