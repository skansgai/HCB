package com.haochibao.activity;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Paint;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.haochibao.MyApplication;
import com.haochibao.R;
import com.haochibao.fragment.MineFragment;
import com.haochibao.utill.http.GetHttp;
import com.sina.weibo.sdk.auth.AuthInfo;
import com.sina.weibo.sdk.auth.Oauth2AccessToken;
import com.sina.weibo.sdk.auth.WeiboAuthListener;
import com.sina.weibo.sdk.auth.sso.SsoHandler;
import com.sina.weibo.sdk.exception.WeiboException;
import com.tencent.connect.UserInfo;
import com.tencent.connect.common.Constants;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;

import org.json.JSONArray;
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
    private Oauth2AccessToken mAccessToken;
    private AuthInfo mAuthInfo;
    private SsoHandler mSsoHandler;
    private com.haochibao.utill.model.UserInfo mUserinfo;
    //QQ登录

    private Tencent mTencent; //qq主操作对象
    private IUiListener loginListener; //授权登录监听器
    private IUiListener userInfoListener; //获取用户信息监听器
    private String scope; //获取信息的范围参数
    private UserInfo userInfo; //qq用户信息
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
        mAuthInfo = new AuthInfo(this, Constants.APP_KEY,
                Constants.REDIRECT_URL, Constants.SCOPE);
        mSsoHandler = new SsoHandler(this, mAuthInfo);




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
                MyApplication.setLoginStyle("Normal");
                Intent intent=new Intent(RegisterActivity.this,HomeViewPagerActivity.class);
                startActivity(intent);
                break;
            case R.id.fast_login:
                //快速登陆
                createLoginMoreDialog();
                break;
            case R.id.user_register:
                //用户注册
                Intent intent1=new Intent(RegisterActivity.this,UserRegisterActivity.class);

               startActivity(intent1);
        }
        }
    };
    public void createLoginMoreDialog(){
        final AlertDialog.Builder builder=new AlertDialog.Builder(context);
        LayoutInflater inflater=LayoutInflater.from(context);
        View view=inflater.inflate(R.layout.dialog_fast_login,null);
        TextView quxiao= (TextView) view.findViewById(R.id.quxiao_btn);
        ImageView loginWeiBo= (ImageView) view.findViewById(R.id.login_weibo);
        ImageView loginQQ= (ImageView) view.findViewById(R.id.qq_login);
        ImageView loginWeiXin= (ImageView) view.findViewById(R.id.qq_weixin);
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
        View.OnClickListener onClickListener1=new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()){
                    case R.id.qq_login:
                        initData();
                        login();
                        alertDialog.dismiss();
                        break;
                    case R.id.login_weibo:
                        mSsoHandler. authorize(new AuthListener());
                        alertDialog.dismiss();
                        break;
                    case R.id.qq_weixin:
                        break;
                }
            }
        };
        loginQQ.setOnClickListener(onClickListener1);
        loginWeiBo.setOnClickListener(onClickListener1);
        loginWeiXin.setOnClickListener(onClickListener1);
    }

    /**
    * 用户正常登陆
    * */
    public void userLogin(){
        phone=phoneEdit.getText().toString();
        password=passwordEdit.getText().toString();
        boolean isPhone=checkPhoneNumber(phone);
        String uri="http://119.29.60.248/index.php/home/user/login?phone_num="+phone+"&password="+password;
        if (phone!=null){
            if (isPhone){
                if (password!=null){
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
                                    Message message1=new Message();
                                    message1.what=0;
                                    message1.obj=message;
                                    handler.sendMessage(message1);
                                    if (Integer.valueOf(requestCode)==200){
                                        JSONObject subObject=object.optJSONObject("result");
                                        MyApplication.setUserId(Integer.valueOf(subObject.optString("id")));
                                        MyApplication.setUserToken(subObject.optString("password"));
                                        Intent intent=new Intent(RegisterActivity.this, HomeViewPagerActivity.class);
                                        Bundle bundle=new Bundle();
                                        bundle.putString("Login","Normal");
                                        intent.putExtras(bundle);
                                        MyApplication.setIsLogin(true);
                                        startActivity(intent);
                                    }
                                }
                            }
                        });
                        getHttp.start();
                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                    }
                }else {
                    Toast.makeText(RegisterActivity.this,"密码不能为空！！",Toast.LENGTH_SHORT).show();
                }
            }else {
                Toast.makeText(RegisterActivity.this,"请输入正确的手机号码！",Toast.LENGTH_SHORT).show();
            }
        }else {
            Toast.makeText(RegisterActivity.this,"手机号码不能为空！",Toast.LENGTH_SHORT).show();
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
    /**
     * 更新视图
     * */
    Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            if (msg.what==0){
                String mesage= (String) msg.obj;
                Toast.makeText(RegisterActivity.this,mesage,Toast.LENGTH_SHORT).show();
            }
        }
    };
    /**
     * 微博登陆配置
     * */
    public interface Constants {
        public static final String APP_KEY      = "558755199";		   // 应用的APP_KEY
        public static final String REDIRECT_URL = "https://www.baidu.com";// 应用的回调页
        public static final String SCOPE = 							   // 应用申请的高级权限
                "email,direct_messages_read,direct_messages_write,"
                        + "friendships_groups_read,friendships_groups_write,statuses_to_me_read,"
                        + "follow_app_official_microblog," + "invitation_write";
    }

    class AuthListener implements WeiboAuthListener {
        @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR1)
        @Override
        public void onComplete(Bundle values) {
            mAccessToken = Oauth2AccessToken.parseAccessToken(values); // 从 Bundle 中解析 Token
            MyApplication.setUserToken(mAccessToken.getToken());
            Log.i("hhh","ssssssssssssssssssss"+mAccessToken.getToken());
            if (mAccessToken.isSessionValid()) {
               // AccessTokenKeeper.writeAccessToken(WBAuthActivity.this, mAccessToken); //保存Token
                MyApplication.setIsLogin(true);
                MyApplication.setLoginStyle("WeiBo");
                MyApplication.setUserToken(mAccessToken.getToken());
            } else {
                // 当您注册的应用程序签名不正确时，就会收到错误Code，请确保签名正确
                String code = values.getString("code", "");
            }
        }
        @Override
        public void onWeiboException(WeiboException e) {

        }
        @Override
        public void onCancel() {
        }
    }
    /**
     * QQ登陆
     * */
    private void initData() {
        mTencent=Tencent.createInstance("1105841940",RegisterActivity.this);
        scope="all";
        loginListener = new IUiListener() {
            @Override
            public void onError(UiError uiError) {
                Log.i("uiError",uiError.errorCode+"");
                Log.i("uiError",uiError.errorMessage+"");
                Log.i("uiError",uiError.errorDetail+"");
            }
            /**
             * 返回json数据样例
             *
             * {"ret":0,"pay_token":"D3D678728DC580FBCDE15722B72E7365",
             * "pf":"desktop_m_qq-10000144-android-2002-",
             * "query_authority_cost":448,
             * "authority_cost":-136792089,
             * "openid":"015A22DED93BD15E0E6B0DDB3E59DE2D",
             * "expires_in":7776000,
             * "pfkey":"6068ea1c4a716d4141bca0ddb3df1bb9",
             * "msg":"",
             * "access_token":"A2455F491478233529D0106D2CE6EB45",
             * "login_cost":499}
             */
            @Override
            public void onComplete( Object value) {
                System.out.println("有数据返回..");
                if (value ==null){
                    return ;
                }
                try {
                    JSONObject jo = (JSONObject) value;
                    int ret=jo.getInt("ret");
                    System.out.println("json=" + String.valueOf(jo));
                    if (ret==0){
                        Toast.makeText(RegisterActivity.this, "登录成功",
                                Toast.LENGTH_LONG).show();
                        String openID = jo.getString("openid");
                        String accessToken = jo.getString("access_token");
                        String expires = jo.getString("expires_in");
                        Log.i("RequestResult",openID+"===="+accessToken+"===="+expires);
                        mTencent.setOpenId(openID);
                        mTencent.setAccessToken(accessToken, expires);
                        MyApplication.setUserToken(accessToken);
                        MyApplication.setIsLogin(true);
                        MyApplication.setLoginStyle("QQ");
                        Intent intent=new Intent(RegisterActivity.this,HomeViewPagerActivity.class);
                        Bundle bundle=new Bundle();
                        bundle.putSerializable("accessToken",accessToken);
                        bundle.putString("expires",expires);
                        intent.putExtras(bundle);
                        startActivity(intent);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onCancel() {
                System.out.println("有数据返回==============");
            }
        };
    }

    private void login() {
        //如果session无效，就开始登录
        if (!mTencent.isSessionValid()) {
            //开始qq授权登录
            mTencent.login(RegisterActivity.this, scope, loginListener);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.i("resultCode","onActivityResult"+"requestCode"+requestCode+"\n resultCode="+resultCode);
        if (mSsoHandler != null) {
            mSsoHandler.authorizeCallBack(requestCode, resultCode, data);
        }
        if (requestCode == com.tencent.connect.common.Constants.REQUEST_API){
            if (resultCode == com.tencent.connect.common.Constants.REQUEST_LOGIN){
                mTencent.handleLoginData(data,loginListener);
            }
        }
        Tencent.onActivityResultData(requestCode,resultCode,data,loginListener);
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onDestroy() {
        if (mTencent != null){
            //注销登陆
            mTencent.logout(RegisterActivity.this);
        }
        super.onDestroy();
    }

}
