package com.haochibao.activity;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Build;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.haochibao.MyApplication;
import com.haochibao.R;
import com.haochibao.fragment.MineFragment;
import com.haochibao.utill.http.GetHttp;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Administrator on 2016/12/10.
 */

public class RegisterActivity extends Activity {
    private final int RESULT_CODE=101;
    private final int REQUEST_CODE=1;
    private EditText phoneEdit,passwordEdit;
    private TextView forgetPassword,fastLogin,loginBtn,register;
    private Context context;
    private String phone,password,requestCode,message;
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
        register= (TextView) findViewById(R.id.user_register);
        forgetPassword.setOnClickListener(onClickListener);
        loginBtn.setOnClickListener(onClickListener);
        fastLogin.setOnClickListener(onClickListener);
        register.setOnClickListener(onClickListener);
        context=this;
    }
    View.OnClickListener onClickListener=new View.OnClickListener() {
        @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
        @Override
        public void onClick(View v) {
        switch (v.getId()){
            case R.id.forget_password:
                //忘记密码
                break;
            case R.id.login_btn:
                //登陆按钮
                userLogin();
                boolean isPhone=checkPhoneNumber(phone);
                if (isPhone){
                    if (password!=null){
                        Intent intent=new Intent(RegisterActivity.this, HomeViewPagerActivity.class);
                        intent.putExtra("isLogin",true);
                        startActivity(intent);
                    }else{
                        Toast.makeText(RegisterActivity.this,"请输入正确的手机号码！",Toast.LENGTH_SHORT).show();
                        phoneEdit.setText(null);
                    }
                }else {
                    Toast.makeText(RegisterActivity.this,"请输入正确的手机号码！",Toast.LENGTH_SHORT).show();
                    phoneEdit.setText(null);
                    passwordEdit.setText(null);
                }

                break;
            case R.id.fast_login:
                //快速登陆
                createLoginMoreDialog();
                break;
            case R.id.user_register:
                //用户注册
                Intent intent1=new Intent(RegisterActivity.this,UserRegisterActivity.class);
                Bundle bundle=new Bundle();
                bundle.putString("phone",null);
                bundle.putString("password",null);
                intent1.putExtras(bundle);
                startActivityForResult(intent1,REQUEST_CODE);
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.i("resultCode","onActivityResult"+"requestCode"+requestCode+"\n resultCode="+resultCode);
        if (requestCode==REQUEST_CODE){
            if (resultCode==RESULT_CODE){
                Bundle bundle=data.getExtras();
                String phone=bundle.getString("phone");
                String password=bundle.getString("password");
                if (phone!=null){
                    phoneEdit.setText(phone);
                }
                if (password!=null){
                    passwordEdit.setText(password);
                }
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
    public void userLogin(){
        phone=phoneEdit.getText().toString();
        password=passwordEdit.getText().toString();
        String uri="http://192.168.7.23/index.php/home/user/login?phone_num="+phone+"&password="+password;
        try {
            URL url=new URL(uri);
            GetHttp getHttp=new GetHttp(context,url);
            getHttp.setOnClicklistener(new GetHttp.onResultListener() {
                @Override
                public void onClick(String data) throws JSONException, IOException {
                if (data!=null){
                    Log.i("LoginResult",data);
                    JSONObject object=new JSONObject(data);
                    requestCode=object.optString("requestCode",null);
                    message=object.optString("message",null);
                    JSONObject subObject=object.optJSONObject("result");
                    MyApplication.setUserId(Integer.valueOf(subObject.optString("id")));
                    MyApplication.setUserToken(subObject.optString("password"));
                }
                }
            });
            getHttp.start();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }
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
}
