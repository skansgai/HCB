package com.haochibao.fragment;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.haochibao.MyApplication;
import com.haochibao.R;
import com.haochibao.activity.HomeViewPagerActivity;
import com.haochibao.activity.MineCollectionActivity;
import com.haochibao.activity.MineInformationActivity;
import com.haochibao.activity.RegisterActivity;
import com.haochibao.activity.Settingactivity;
import com.haochibao.utill.http.GetHttp;
import com.haochibao.utill.http.GetImage;
import com.haochibao.utill.model.UserInfo;
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

/**
 * Created by Administrator on 2016/12/13.
 */

public class MineFragment extends Fragment {
    private RelativeLayout minePage,setting,mineCollection;
    private Intent intent;
    private Context context;
    private View view;
    private TextView userName;
    private ImageView userIcon;
    private UserInfo userInfo;
    private com.tencent.connect.UserInfo userInfo1; //qq用户信息

    public ResuQQltListener getQqListener() {
        return qqListener;
    }

    public ResuQQltListener qqListener;
    public void OnResult(ResuQQltListener listener){
        this.qqListener=listener;
    }

    private Tencent mTencent; //qq主操作对象
    private IUiListener loginListener; //授权登录监听器
    private IUiListener userInfoListener; //获取用户信息监听器
    private String scope; //获取信息的范围参数
    public MineFragment (Intent intent,ResuQQltListener resuQQltListener){
        this.intent=intent;
        this.qqListener=resuQQltListener;
    }
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Nullable
    @Override
    public View onCreateView(
            LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            view=inflater.inflate(R.layout.activity_mine_home_page,null);
            context=getActivity();
            init();
       //根据登陆类型显示用户资料（QQ,weibo,weixin,zhengchang）
       /* switch (MyApplication.getLoginStyle()){
            case "Normal":
                getUserInfor();
                break;
            case "QQ":
                getQQInfo();
                break;
            case "WeiBo":

                break;
            case "WeiXin":

                break;
        }*/
        userInfo1 = new com.tencent.connect.UserInfo(context,mTencent.getQQToken());
        userInfo1.getUserInfo(userInfoListener);
        return view;
    }

    //初始化
    public void init(){
        minePage= (RelativeLayout) view.findViewById(R.id.mine_page_btn);
        setting= (RelativeLayout)view. findViewById(R.id.setting);
        mineCollection= (RelativeLayout)view. findViewById(R.id.mine_collection);
        userName= (TextView) view.findViewById(R.id.user_name);
        userIcon= (ImageView) view.findViewById(R.id.user_icon);
        userInfo=new UserInfo();
        minePage.setOnClickListener(onClickListener);
        setting.setOnClickListener(onClickListener);
        mineCollection.setOnClickListener(onClickListener);
        getQQInfo();
    }
    View.OnClickListener onClickListener=new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.mine_page_btn:
                    intent=new Intent(context,MineInformationActivity.class);
                    Bundle bundle=new Bundle();
                    bundle.putSerializable("userInfo",userInfo);
                    startActivity(intent);
                    break;
                case R.id.setting:
                    intent=new Intent(context,Settingactivity.class);
                    startActivity(intent);
                    break;
                case R.id.mine_collection:
                    intent=new Intent(context,MineCollectionActivity.class);
                    startActivity(intent);
                    break;
            }
        }
    };

    //获得微博基本信息
    public void getWeiBoInfo() {
        String uri="https://api.weibo.com/2/users/show.json?access_token="+MyApplication.getUserToken();
        try {
            URL url=new URL(uri);
            GetHttp getHttp=new GetHttp(context,url);
            getHttp.start();
            getHttp.setOnClicklistener(new GetHttp.onResultListener() {
                @Override
                public void onClick(String data) throws JSONException, IOException {
                    if (data!=null){
                        Log.i("WeiBo==========>",data);
                    }
                }
            });
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    //获得QQ信息
    private void getQQInfo() {
        String accessToken = null;
        String expires = null;
        if (intent != null) {
            Bundle bundle = intent.getExtras();
            if (bundle != null) {
                accessToken = bundle.getString("accessToken");
                expires = bundle.getString("expires");
            }
        }
        mTencent = Tencent.createInstance("1105841940", context);
        scope = "all";
        mTencent.setAccessToken(accessToken, expires);

        userInfoListener = new IUiListener() {
            @Override
            public void onError(UiError arg0) {
                // TODO Auto-generated method stub
            }

            /**
             * {"is_yellow_year_vip":"0","ret":0,
             * "figureurl_qq_1":"http:\/\/q.qlogo.cn\/qqapp\/1104732758\/015A22DED93BD15E0E6B0DDB3E59DE2D\/40",
             * "figureurl_qq_2":"http:\/\/q.qlogo.cn\/qqapp\/1104732758\/015A22DED93BD15E0E6B0DDB3E59DE2D\/100",
             * "nickname":"攀爬←蜗牛","yellow_vip_level":"0","is_lost":0,"msg":"",
             * "city":"黄冈","
             * figureurl_1":"http:\/\/qzapp.qlogo.cn\/qzapp\/1104732758\/015A22DED93BD15E0E6B0DDB3E59DE2D\/50",
             * "vip":"0","level":"0",
             * "figureurl_2":"http:\/\/qzapp.qlogo.cn\/qzapp\/1104732758\/015A22DED93BD15E0E6B0DDB3E59DE2D\/100",
             * "province":"湖北",
             * "is_yellow_vip":"0","gender":"男",
             * "figureurl":"http:\/\/qzapp.qlogo.cn\/qzapp\/1104732758\/015A22DED93BD15E0E6B0DDB3E59DE2D\/30"}
             */
            @Override
            public void onComplete(Object arg0) {
                // TODO Auto-generated method stub
                if (arg0 == null) {
                    return;
                }
                try {
                    JSONObject jo = (JSONObject) arg0;
                    int ret = jo.getInt("ret");
                    System.out.println("json=" + String.valueOf(jo));
                    String nickName = jo.getString("nickname");
                    String gender = jo.getString("gender");
                    String cityName = jo.getString("city");
                    String smollIcon = jo.getString("figureurl_qq_1");
                    String Icon = jo.getString("figureurl_qq_2");
                    userInfo.setName(nickName);
                    userInfo.setLocation(cityName);
                    Bitmap bitmap = BitmapFactory.decodeStream(new URL(Icon).openStream());
                    userInfo.setBitmap(bitmap);
                    userInfo.setBirthday("0");
                    userInfo.setSex("保密");
                    userInfo.setSignature("null");
                    Log.i("回调成功",smollIcon);
                    //设置显示信息
                    userIcon.setImageBitmap(bitmap);
                    userName.setText(nickName);

                } catch (Exception e) {
                    // TODO: handle exception
                }
            }

            @Override
            public void onCancel() {
                // TODO Auto-generated method stub

            }
        };
    }
    //获得微博微信基本信息
    private void getWeiXionInfo() {
    }

    public void getUserInfor(){
        String uri="http://192.168.7.23/index.php/home/user/getUserAllInfor?user_id="+ MyApplication.getUserId();
        try {
            URL url=new URL(uri);
            GetHttp getHttp=new GetHttp(context,url);
            getHttp.setOnClicklistener(new GetHttp.onResultListener() {
                @Override
                public void onClick(String data) throws JSONException, IOException {
                    if (data!=null){
                        Log.i("getUserAllInfor",data);
                        JSONObject object=new JSONObject(data);
                        JSONArray array=object.optJSONArray("result");
                        for(int i=0;i<array.length();i++){
                            userInfo=new UserInfo();
                            JSONObject subObject=array.optJSONObject(i);
                            String userName=subObject.optString("user_name");
                            String birthday=subObject.optString("birthday");
                            String district=subObject.optString("district");
                            String ic_paht=subObject.optString("icon_path");
                            String signature=subObject.optString("signature");
                            String sex=subObject.optString("sex");
                            String type=subObject.optString("type");
                            String location=subObject.optString("location");
                            userInfo.setName(userName);
                            userInfo.setBirthday(birthday);
                            userInfo.setSignature(signature);
                            userInfo.setSex(sex);
                            userInfo.setLocation(location);
                            Bitmap bitmap= BitmapFactory.decodeStream(new URL(ic_paht).openStream());
                            userInfo.setBitmap(bitmap);
                            Message message=new Message();
                            message.what=0;
                            message.obj=userInfo;
                            handler.sendMessage(message);
                        }
                    }
                }
            });
            getHttp.start();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            if (msg.what==0){
                userInfo= (UserInfo) msg.obj;
                Bitmap bitmap=userInfo.getBitmap();
                userIcon.setImageBitmap(bitmap);
                userName.setText(userInfo.getName());
            }
        }
    };

    public interface ResuQQltListener{
    void Onclick(IUiListener listener,Tencent tencent);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == com.tencent.connect.common.Constants.REQUEST_API){
            if (resultCode == com.tencent.connect.common.Constants.REQUEST_LOGIN){
                mTencent.handleLoginData(data,userInfoListener);
            }
        }
        Tencent.onActivityResultData(requestCode,resultCode,data,userInfoListener);
        super.onActivityResult(requestCode, resultCode, data);
    }
}
