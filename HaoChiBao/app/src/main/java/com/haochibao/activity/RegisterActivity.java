package com.haochibao.activity;

import android.app.Activity;
import android.graphics.Paint;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import com.haochibao.R;

/**
 * Created by Administrator on 2016/12/10.
 */

public class RegisterActivity extends Activity {
    private EditText phoneEdit,passwordEdit;
    private TextView forgetPassword;
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
    }
}
