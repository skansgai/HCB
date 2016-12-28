package com.haochibao.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.haochibao.R;
import com.haochibao.utill.http.GetHttp;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Administrator on 2016/12/26.
 */

public class UserRegisterActivity extends Activity {
    private final int RESULT_CODE=101;
    private EditText phoneEdit,yanzhengEdit,passwordEdit,rePasswordEdit;
    private TextView registerBtn,userProtocol;
    private Context context;
    private String requestCode,message,password,phone;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_register);
        context=getApplicationContext();
        initView();

    }
    public void initView(){
        phoneEdit= (EditText) findViewById(R.id.register_number);
        yanzhengEdit= (EditText) findViewById(R.id.register_yanzheng);
        passwordEdit= (EditText) findViewById(R.id.register_password);
        rePasswordEdit= (EditText) findViewById(R.id.register_re_password);
        registerBtn= (TextView) findViewById(R.id.register_button);
        userProtocol= (TextView) findViewById(R.id.user_Protocol);

        registerBtn.setOnClickListener(onClickListener);
    }

    View.OnClickListener onClickListener=new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.register_button:
                    registerHttp();
                    boolean isPhone=checkPhoneNumber(phoneEdit.getText().toString());
                    if (isPhone){
                        if (yanzhengEdit.getText()!=null){
                                if (Integer.valueOf(requestCode)==200){
                                    Toast.makeText(context,message,Toast.LENGTH_SHORT).show();
                                    finish();
                                }else{
                                    Toast.makeText(context,message,Toast.LENGTH_SHORT).show();
                                }
                            }else {
                            Toast.makeText(context,"验证码不能为空！",Toast.LENGTH_SHORT).show();
                        }
                    }else{
                        Toast.makeText(context,"请输入手机号码！",Toast.LENGTH_SHORT).show();
                    }
                    break;
            }
        }
    };
    /**
     * 验证手机号码
     * @param phoneNumber 手机号码
     * @return boolean
     */
    public boolean checkPhoneNumber(String phoneNumber){
        Pattern pattern=Pattern.compile("^1[0-9]{10}$");
        Matcher matcher=pattern.matcher(phoneNumber);
        return matcher.matches();
    }
    public void registerHttp(){
        phone=phoneEdit.getText().toString();
        password=passwordEdit.getText().toString();
        String rePassword=rePasswordEdit.getText().toString();
        String uri="http://192.168.7.23/index.php/home/user/register?phone_num="+phone+"&password="+password+"&repassword="+rePassword;
        try {
            URL url=new URL(uri);
            GetHttp getHttp=new GetHttp(context,url);
            getHttp.setOnClicklistener(new GetHttp.onResultListener() {
                @Override
                public void onClick(String data) throws JSONException, IOException {
                    if (data!=null){
                        Log.i("==========444",data);
                        JSONObject object=new JSONObject(data);
                        requestCode=object.optString("requestCode");
                        message=object.optString("message");
                    }
                }
            });
            getHttp.start();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

    }

}
