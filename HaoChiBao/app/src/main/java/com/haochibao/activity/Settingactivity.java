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
import com.haochibao.utill.dialog.DialogUtill;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/12/12.
 */

public class Settingactivity extends Activity{
    private ImageView backBtn;
    private RelativeLayout aboutApp,advise,shareApp,cleanCache;
    private Intent intent;
    private Context context;
    private TextView shareBtn,canceBtn,cleanBtn,cleanCancel;
    AlertDialog shareDialog,cleanDialog;
    DialogUtill dialogUtill;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        init();
    }
    public void init(){
        backBtn= (ImageView) findViewById(R.id.back_btn);
        aboutApp= (RelativeLayout) findViewById(R.id.about_app);
        advise= (RelativeLayout) findViewById(R.id.advise);
        shareApp= (RelativeLayout) findViewById(R.id.share_app);
        cleanCache= (RelativeLayout) findViewById(R.id.clean_cache);
        context=this;
        backBtn.setOnClickListener(onClickListener);
        aboutApp.setOnClickListener(onClickListener);
        advise.setOnClickListener(onClickListener);
        shareApp.setOnClickListener(onClickListener);
        cleanCache.setOnClickListener(onClickListener);
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
                    shareDialog.dismiss();
                    break;
                case R.id.cancel_btn:
                    shareDialog.dismiss();
                    break;
                case R.id.clean_cache:
                    createCleanDialog();
                    break;
                case R.id.clean_btn:
                    Toast.makeText(context,"缓存清除成功",Toast.LENGTH_SHORT).show();
                    cleanDialog.dismiss();
                    break;
                case R.id.clean_cancel:
                    cleanDialog.dismiss();
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
        shareDialog=builder.create();
        shareDialog.show();
    }
    public void createCleanDialog(){
        LayoutInflater inflater=LayoutInflater.from(context);
        View cleanView=inflater.inflate(R.layout.dialog_clean_cache,null);
        cleanBtn= (TextView) cleanView.findViewById(R.id.clean_btn);
        cleanCancel=(TextView)cleanView.findViewById(R.id.clean_cancel);
        cleanCancel.setOnClickListener(onClickListener);
        cleanBtn.setOnClickListener(onClickListener);
        dialogUtill=new DialogUtill(context,cleanView,cleanDialog);
        cleanDialog=dialogUtill.createDialog();
    }
}
