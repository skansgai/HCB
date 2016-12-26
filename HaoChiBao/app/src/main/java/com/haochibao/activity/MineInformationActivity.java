package com.haochibao.activity;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.haochibao.MyApplication;
import com.haochibao.R;
import com.haochibao.utill.http.GetHttp;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Administrator on 2016/12/12.
 * 我的资料
 */

public class MineInformationActivity extends Activity {
    private ImageView backBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mine_information);
        init();
        getUserInfor();
    }
    public void init(){
        backBtn= (ImageView) findViewById(R.id.back_btn);
        backBtn.setOnClickListener(onClickListener);
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
    public void getUserInfor(){
        String uri="http://192.168.7.23/index.php/home/user/getUserAllInfor?user_id="+ MyApplication.getUserId();
        try {
            URL url=new URL(uri);
            GetHttp getHttp=new GetHttp(this,url);
            getHttp.setOnClicklistener(new GetHttp.onResultListener() {
                @Override
                public void onClick(String data) throws JSONException, IOException {
                    if (data!=null){
                        Log.i("getUserAllInfor",data);
                        JSONObject object=new JSONObject(data);
                        JSONArray array=object.optJSONArray("result");
                        for(int i=0;i<array.length();i++){
                            JSONObject subObject=array.optJSONObject(i);
                            String userName=subObject.optString("user_name");
                            String birthday=subObject.optString("birthday");
                            String district=subObject.optString("district");
                            String ic_paht=subObject.optString("icon_path");
                            String signature=subObject.optString("signature");
                            String sex=subObject.optString("sex");
                            String type=subObject.optString("type");
                        }
                    }
                }
            });
            getHttp.start();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }
}
