package com.haochibao.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;

import com.haochibao.R;

/**
 * Created by Administrator on 2016/12/10.
 */

public class RegisterActivity extends Activity {
    private EditText phoneEdit,passwordEdit;
    private TextView forgetPassword,fastLogin,loginBtn;
    private Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        init();
    }
    //初始化方法
    public void init(){
        phoneEdit= (EditText) findViewById(R.id.phone_edit);
        passwordEdit= (EditText) findViewById(R.id.password_edit);
        forgetPassword= (TextView) findViewById(R.id.forget_password);
        loginBtn= (TextView) findViewById(R.id.login_btn);
        fastLogin= (TextView) findViewById(R.id.fast_login);

        forgetPassword.setOnClickListener(onClickListener);
        loginBtn.setOnClickListener(onClickListener);
        fastLogin.setOnClickListener(onClickListener);
        context=this;
    }

    View.OnClickListener onClickListener=new View.OnClickListener() {
        @Override
        public void onClick(View v) {
        switch (v.getId()){
            case R.id.forget_password:
                //忘记密码
                break;
            case R.id.login_btn:
                //登陆按钮
                break;
            case R.id.fast_login:
                //快速登陆
                createLoginMoreDialog();
                break;

        }
        }
    };

    public void createLoginMoreDialog(){
        AlertDialog.Builder builder=new AlertDialog.Builder(context);
        LayoutInflater inflater=LayoutInflater.from(context);

        View view=inflater.inflate(R.layout.dialog_fast_login,null);
        TextView quxiao= (TextView) view.findViewById(R.id.quxiao_btn);

        builder.setView(view);
        final AlertDialog alertDialog=builder.create();
        alertDialog.show();
        WindowManager.LayoutParams params=alertDialog.getWindow().getAttributes();
        params.y=100;
        alertDialog.getWindow().setAttributes(params);
        quxiao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            alertDialog.dismiss();
            }
        });
    }
}
