package com.haochibao.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.Toast;
/**
 * Created by Administrator on 2016/12/10.
 */
public class SplashActivity extends Activity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        boolean mFirst = isFirstEnter(SplashActivity.this,SplashActivity.this.getClass().getName());
        Toast.makeText(this,mFirst+"",Toast.LENGTH_SHORT).show();
        if (mFirst){
            mHandler.sendEmptyMessageDelayed(SWITCH_GUIDACTIVITY,100);
        }else {
            mHandler.sendEmptyMessageDelayed(SWITCH_MAINACTIVITY,100);
        }
        SharedPreferences sharedPreferences = this.getSharedPreferences("my_pref",MODE_PRIVATE);
        sharedPreferences.edit().putString("guide_activity","false").commit();
    }

    /**
     * 判断是否初次加载，读取SharedPreference中的guide_activity字段
     */
    private static final String SHAREDPREFERENCE_NAME = "my_pref";
    private static final String KEY_GUIDE_ACTIVITY = "guide_activity";
    private boolean isFirstEnter(Context context,String className){
        if (context == null||className == null||"".equalsIgnoreCase(className)){
            return false;
        }
        String mResultStr = context.getSharedPreferences(SHAREDPREFERENCE_NAME,Context.MODE_WORLD_WRITEABLE)
                .getString(KEY_GUIDE_ACTIVITY,"");//取得所有类名
        if (mResultStr.equals("false")){
            return false;
        }else {
            return true;
        }
    }
    private final static int SWITCH_MAINACTIVITY = 1000;
    private final static int SWITCH_GUIDACTIVITY = 1001;
    /**
     * Handler：跳转至不同页面
     */
    public Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            Intent mIntent;
            switch (msg.what){
                case SWITCH_MAINACTIVITY:
                    mIntent = new Intent();
                    mIntent.setClass(SplashActivity.this, EntertainmentActivity.class);
                    SplashActivity.this.startActivity(mIntent);
                    SplashActivity.this.finish();
                    break;
                case SWITCH_GUIDACTIVITY:
                    mIntent = new Intent();
                    mIntent.setClass(SplashActivity.this, LoginActivity.class);
                    SplashActivity.this.startActivity(mIntent);
                    SplashActivity.this.finish();
                    break;
            }
            super.handleMessage(msg);
        }
    };
}
