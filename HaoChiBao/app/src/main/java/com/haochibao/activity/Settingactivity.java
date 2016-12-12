package com.haochibao.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.haochibao.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/12/12.
 */

public class Settingactivity extends Activity{
    private ImageView backBtn;
    private RelativeLayout aboutApp,advise,shareApp;
    private Intent intent;
    private Context context;
    TextView shareBtn,canceBtn;
    AlertDialog alertDialog;
   // private List<Map<String, Object>> iconList;
    /*private int[] icon={
            R.mipmap.my_qq,
            R.mipmap.my_weixin,
            R.mipmap.my_weibo,
            R.mipmap.share_for_specifics,
            R.mipmap.share_momo,
            R.mipmap.share_space};*/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //iconList=new ArrayList<Map<String,Object>>();
        setContentView(R.layout.activity_setting);
        init();
    }
    public void init(){
        backBtn= (ImageView) findViewById(R.id.back_btn);
        aboutApp= (RelativeLayout) findViewById(R.id.about_app);
        advise= (RelativeLayout) findViewById(R.id.advise);
        shareApp= (RelativeLayout) findViewById(R.id.share_app);
        context=this;
        backBtn.setOnClickListener(onClickListener);
        aboutApp.setOnClickListener(onClickListener);
        advise.setOnClickListener(onClickListener);
        shareApp.setOnClickListener(onClickListener);
    }

    View.OnClickListener onClickListener=new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.back_btn:
                    finish();
                    break;
                case R.id.about_app:
                    intent=new Intent(Settingactivity.this,AboutAppActivity.class);
                    startActivity(intent);
                    break;
                case R.id.advise:
                    intent=new Intent(Settingactivity.this,UserAdviseActvity.class);
                    startActivity(intent);
                    break;
                case R.id.share_app:
                    createShareAppDialog();
                    break;
                case R.id.share_btn:
                    Toast.makeText(context,"分享成功",Toast.LENGTH_SHORT).show();
                    alertDialog.dismiss();
                    break;
                case R.id.cancel_btn:
                    alertDialog.dismiss();
                    break;
            }
        }
    };
    public void createShareAppDialog(){
        LayoutInflater inflater=LayoutInflater.from(context);
        View shareView=inflater.inflate(R.layout.dialog_share_app,null);
        shareBtn= (TextView) shareView.findViewById(R.id.share_btn);
        canceBtn=(TextView) shareView.findViewById(R.id.cancel_btn);
        shareBtn.setOnClickListener(onClickListener);
        canceBtn.setOnClickListener(onClickListener);
        AlertDialog.Builder builder=new AlertDialog.Builder(context);
        builder.setView(shareView);
        alertDialog=builder.create();
        alertDialog.show();

    }

}
