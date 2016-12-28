package com.haochibao.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.haochibao.MyApplication;
import com.haochibao.R;
import com.haochibao.utill.dialog.DialogUtill;
import com.haochibao.utill.http.GetHttp;
import com.haochibao.utill.model.UserInfo;
import com.haochibao.utill.view.CircleImageView;

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
    private CircleImageView userIcon;
    private TextView userName,sex,location,signature,birthday;
    private UserInfo userInfo;
    private Context context;
    private EditText editDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mine_information);
        context=getApplicationContext();
        init();
        setUserInfo();
    }
    public void init(){
        userIcon= (CircleImageView) findViewById(R.id.user_icon);
        userName= (TextView) findViewById(R.id.user_name);
        sex= (TextView) findViewById(R.id.user_sex);
        location= (TextView) findViewById(R.id.location);
        signature= (TextView) findViewById(R.id.signature);
        birthday= (TextView) findViewById(R.id.user_birthday);

        backBtn= (ImageView) findViewById(R.id.back_btn);
        backBtn.setOnClickListener(onClickListener);
        userName.setOnClickListener(onClickListener);
    }

    View.OnClickListener onClickListener=new View.OnClickListener() {
        @Override
        public void onClick(View v) {
        switch (v.getId()){
            case R.id.back_btn:
                finish();
                break;
            case R.id.user_name:
                updateNameDialog();
                break;
        }
        }
    };
  public void setUserInfo(){
      Intent intent=getIntent();
      if (intent!=null){
          Bundle bundle=intent.getExtras();
          if (bundle!=null){
              userInfo= (UserInfo)bundle.getSerializable("userInfo");
              if (userInfo!=null){
                  userIcon.setImageBitmap(userInfo.getBitmap());
                  userName.setText(userInfo.getName());
                  sex.setText(userInfo.getSex());
                  signature.setText(userInfo.getSignature());
                  location.setText(userInfo.getLocation());
                  birthday.setText(userInfo.getBirthday());
              }
          }
      }
  }

    public void updateNameDialog(){
        LayoutInflater inflater=LayoutInflater.from(context);
        View view=inflater.inflate(R.layout.dialog_edit_text,null);
        editDialog= (EditText) view.findViewById(R.id.edit_dialog);
        AlertDialog.Builder  builder=new AlertDialog.Builder(context);
        builder.setTitle("昵称修改")//
        .setPositiveButton("保存", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (editDialog.getText()!=null){
                    userName.setText(editDialog.getText());
                }
                dialog.dismiss();
            }
        })
        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        }).setView(view);
            AlertDialog alertDialog=builder.create();
            alertDialog.show();

    }
}
