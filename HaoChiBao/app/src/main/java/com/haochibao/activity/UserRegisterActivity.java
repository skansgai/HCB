package com.haochibao.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
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
import java.util.Timer;
import java.util.TimerTask;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;
import cn.smssdk.utils.SMSLog;

/**
 * Created by Administrator on 2016/12/26.
 */

public class UserRegisterActivity extends Activity implements Handler.Callback {
    private static String APPKEY = "1a7dafd4de7ee";
    // 填写从短信SDK应用后台注册得到的APPSECRET
    private static String APPSECRET = "db158aeb251610f36076e414727ae227";
    private final int RESULT_CODE = 101;
    private EditText phoneEdit, yanzhengEdit, passwordEdit, rePasswordEdit;
    private Button sendSMS;//验证按钮
    private TextView registerBtn, userProtocol;
    private Context context;
    private String requestCode, message, password, phone;
    private String phoneNumber, number;
    private boolean haveSMS = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_register);
        SMSSDK.initSDK(this, APPKEY, APPSECRET);
        context = getApplicationContext();
        initView();
        initSDK();
    }

    public void initView() {
        phoneEdit = (EditText) findViewById(R.id.register_number);
        yanzhengEdit = (EditText) findViewById(R.id.SMS_number);
        passwordEdit = (EditText) findViewById(R.id.register_password);
        rePasswordEdit = (EditText) findViewById(R.id.register_re_password);
        registerBtn = (TextView) findViewById(R.id.register_button);
        userProtocol = (TextView) findViewById(R.id.user_Protocol);
        sendSMS = (Button) findViewById(R.id.send_SMS);
        sendSMS.setOnClickListener(onClickListener);
        registerBtn.setOnClickListener(onClickListener);
    }

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            phoneNumber = phoneEdit.getText().toString();
            number = yanzhengEdit.getText().toString();
            switch (v.getId()) {
                case R.id.register_button:
                    registerHttp();
                    boolean isPhone = checkPhoneNumber(phoneNumber);
                    if (isPhone) {
                        if (number != null) {
                            SMSSDK.submitVerificationCode("86", phoneNumber, number);//短信验证
                            if (haveSMS) {
                                if (requestCode!=null){
                                    if (Integer.valueOf(requestCode) == 200) {
                                        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
                                        finish();
                                    } else {
                                        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
                                    }
                                }else {
                                    Toast.makeText(context, "注册失败，请检查网络！", Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                Toast.makeText(context, "验证失败！", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(context, "验证码不能为空！", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(context, "请输入手机号码！", Toast.LENGTH_SHORT).show();
                    }
                    break;
                case R.id.send_SMS:
                    sendTime();
                    sendSMS.setClickable(false);
                    sendSMS.setBackgroundResource(R.drawable.get_false);
                    phoneNumber = phoneEdit.getText().toString();
                    if (phoneNumber != null) {
                        SMSSDK.getVerificationCode("86", phoneNumber);//获取短信
                        //SMSSDK.getVoiceVerifyCode("86",phoneNumber);
                    } else {
                        Toast.makeText(UserRegisterActivity.this, "电话号码不能为空", Toast.LENGTH_SHORT).show();
                    }
                    break;
            }
        }
    };

    /**
     * 验证手机号码
     *
     * @param phoneNumber 手机号码
     * @return boolean
     */
    public boolean checkPhoneNumber(String phoneNumber) {
        Pattern pattern = Pattern.compile("^1[0-9]{10}$");
        Matcher matcher = pattern.matcher(phoneNumber);
        return matcher.matches();
    }

    public void registerHttp() {
        phone = phoneEdit.getText().toString();
        password = passwordEdit.getText().toString();
        String rePassword = rePasswordEdit.getText().toString();
        String uri = "http://119.29.60.248/index.php/home/user/register?phone_num=" + phone + "&password=" + password + "&repassword=" + rePassword;
        try {
            URL url = new URL(uri);
            GetHttp getHttp = new GetHttp(context, url);
            getHttp.setOnClicklistener(new GetHttp.onResultListener() {
                @Override
                public void onClick(String data) throws JSONException, IOException {
                    if (data != null) {
                        Log.i("==========444", data);
                        JSONObject object = new JSONObject(data);
                        requestCode = object.optString("requestCode");
                        message = object.optString("message");
                    }
                }
            });
            getHttp.start();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

    }

    public void initSDK() {
        try {
            final Handler handler = new Handler(this);
            EventHandler eventHandler = new EventHandler() {
                @Override
                public void afterEvent(int event, int result, Object data) {
                    Message msg = new Message();
                    msg.arg1 = event;
                    msg.arg2 = result;
                    msg.obj = data;
                    handler.sendMessage(msg);
                }
            };
            SMSSDK.registerEventHandler(eventHandler);//注册后的回调
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    protected void onDestroy() {
        SMSSDK.unregisterAllEventHandler();
        super.onDestroy();
    }

    @Override
    public boolean handleMessage(Message msg) {
        int event = msg.arg1;
        int result = msg.arg2;
        Object data = msg.obj;
        if (result == SMSSDK.RESULT_COMPLETE) {
            System.out.print("------result" + event);
            //回调完成
            haveSMS = true;
            Toast.makeText(UserRegisterActivity.this, "提交验证码成功", Toast.LENGTH_SHORT).show();
        } else if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE) {
            //获取验证码成功
            Toast.makeText(UserRegisterActivity.this, "获取验证码成功", Toast.LENGTH_SHORT).show();
        } else if (event == SMSSDK.EVENT_GET_SUPPORTED_COUNTRIES) {

        } else {
            ((Throwable) data).printStackTrace();
//				Toast.makeText(MainActivity.this, "验证码错误", Toast.LENGTH_SHORT).show();
//					Toast.makeText(MainActivity.this, "123", Toast.LENGTH_SHORT).show();
            int status = 0;
            try {
                ((Throwable) data).printStackTrace();
                Throwable throwable = (Throwable) data;

                JSONObject object = new JSONObject(throwable.getMessage());
                String des = object.optString("detail");
                status = object.optInt("status");
                if (!TextUtils.isEmpty(des)) {
                    Toast.makeText(UserRegisterActivity.this, des, Toast.LENGTH_SHORT).show();
                    return false;
                }
            } catch (Exception e) {
                SMSLog.getInstance().w(e);
            }
        }
        return false;
    }
    //到计是
    public void sendTime(){
        new Thread(){
            @Override
            public void run() {
                int i=50;
                while (i>-1){
                    try {
                        Thread.sleep(1000);
                        Message message=new Message();
                        message.what=i;
                        handler.sendMessage(message);
                        i--;
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }.start();
    }
    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            Log.i("---------------",msg.what+"");
            sendSMS.setText(msg.what+"秒后重发");
            if (msg.what==0){
                sendSMS.setBackgroundResource(R.drawable.get_yanzheng);
                sendSMS.setText("获取验证码");
                sendSMS.setClickable(true);
            }
        }
    };
}
