package com.haochibao.fragment;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.haochibao.MyApplication;
import com.haochibao.R;
import com.haochibao.activity.MineCollectionActivity;
import com.haochibao.activity.MineInformationActivity;
import com.haochibao.activity.Settingactivity;
import com.haochibao.utill.http.GetHttp;
import com.haochibao.utill.model.UserInfo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Administrator on 2016/12/13.
 */

public class MineFragment extends Fragment {
    private RelativeLayout minePage,setting,mineCollection;
    private Intent intent;
    private Context context;
    private View view;
    private TextView userName;
    private ImageView userIcon;
    private UserInfo userInfo;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Nullable
    @Override
    public View onCreateView(
            LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            view=inflater.inflate(R.layout.activity_mine_home_page,null);
            context=getActivity();
            init();
            getUserInfo();
        return view;
    }
    //初始化
    public void init(){
        minePage= (RelativeLayout) view.findViewById(R.id.mine_page_btn);
        setting= (RelativeLayout)view. findViewById(R.id.setting);
        mineCollection= (RelativeLayout)view. findViewById(R.id.mine_collection);
        userName= (TextView) view.findViewById(R.id.user_name);
        userIcon= (ImageView) view.findViewById(R.id.user_icon);
        userInfo=new UserInfo();


        minePage.setOnClickListener(onClickListener);
        setting.setOnClickListener(onClickListener);
        mineCollection.setOnClickListener(onClickListener);
    }
    View.OnClickListener onClickListener=new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.mine_page_btn:
                    intent=new Intent(context,MineInformationActivity.class);
                    startActivity(intent);
                    break;
                case R.id.setting:
                    intent=new Intent(context,Settingactivity.class);
                    startActivity(intent);
                    break;
                case R.id.mine_collection:
                    intent=new Intent(context,MineCollectionActivity.class);
                    startActivity(intent);
                    break;
            }
        }
    };
    public void getUserInfo(){
        String uri="http://192.168.7.23/index.php/home/user/getProtraitAndName?user_id="+ MyApplication.getUserId();
        try {
            URL url=new URL(uri);
            GetHttp getHttp=new GetHttp(context,url);
            getHttp.setOnClicklistener(new GetHttp.onResultListener() {
                @Override
                public void onClick(String data) throws JSONException, IOException {
                    if (data!=null){
                        Log.i("getUserInfo",data);
                        JSONObject object=new JSONObject(data);
                        JSONArray array=object.optJSONArray("result");
                        for (int i=0;i<array.length();i++){
                            JSONObject sub=array.optJSONObject(i);
                            userInfo.setName(sub.optString("user_name","null"));
                            String path=sub.optString("icon_path");
                            Bitmap bitmap=BitmapFactory.decodeStream(new URL(path).openStream());
                            userInfo.setBitmap(bitmap);
                            Message message=new Message();
                            message.what=0;
                            message.obj=userInfo;
                            handler.sendMessage(message);
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
                userInfo= (UserInfo) msg.obj;
                Bitmap bitmap=userInfo.getBitmap();
                userIcon.setImageBitmap(bitmap);
                userName.setText(userInfo.getName());
            }
        }
    };
}
