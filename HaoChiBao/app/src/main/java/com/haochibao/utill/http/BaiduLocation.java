package com.haochibao.utill.http;

import android.content.Context;
import android.util.Log;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.haochibao.utill.model.LocationInfo;
/**
 * Created by Administrator on 2016/12/30.
 */

public class BaiduLocation extends Thread{
    private Context context;
    private LocationInfo locationInfo;
    private LocationClient mLocationClient ;
    private LocationClientOption mLocationOption = new LocationClientOption();//ingwei参数
    public BaiduLocation(Context context){
        this.context=context;
        mLocationClient = new LocationClient(context);
        Log.i("@@@@@@@@@@@@@@@@@","成功1");
    }
    public void init(){
        mLocationClient.registerLocationListener(new BDLocationListener() {
            @Override
            public void onReceiveLocation(BDLocation location) {
                    locationInfo = new LocationInfo();
                    locationInfo.setCityName(location.getCity());
                    locationInfo.setLongitude(location.getLongitude());
                    locationInfo.setLatitude(location.getLatitude());
                    locationInfo.setTime(location.getTime());
                    locationInfo.setSpree(location.getSpeed());
                    resultListener.onClick(locationInfo);
                    Log.i("@@@@@@@@@@@@@@@@@","成功"+locationInfo.getLongitude());

            }
        });//注册监听函数
        setLocationOption();

    }
    //设置定位参数
    public void setLocationOption(){
            //设置高耗能模式
            mLocationOption.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);
            int span=1000;
            mLocationOption.setScanSpan(span);//可选，默认0，即仅定位一次，设置发起定位请求的间隔需要大于等于1000ms才是有效的
            mLocationOption.setIsNeedAddress(true);//可选，设置是否需要地址信息，默认不需要
            mLocationOption.setOpenGps(true);//可选，默认false,设置是否使用gps
            mLocationOption.setLocationNotify(true);//可选，默认false，设置是否当GPS有效时按照1S/1次频率输出GPS结果
            mLocationOption.setIsNeedLocationDescribe(true);//可选，默认false，设置是否需要位置语义化结果，可以在BDLocation.getLocationDescribe里得到，结果类似于“在北京天安门附近”
            mLocationOption.setIsNeedLocationPoiList(true);//可选，默认false，设置是否需要POI结果，可以在BDLocation.getPoiList里得到
            mLocationOption.setIgnoreKillProcess(false);//可选，默认true，定位SDK内部是一个SERVICE，并放到了独立进程，设置是否在stop的时候杀死这个进程，默认不杀死
            mLocationOption.SetIgnoreCacheException(false);//可选，默认false，设置是否收集CRASH信息，默认收集
            mLocationOption.setEnableSimulateGps(false);//可选，默认false，设置是否需要过滤GPS仿真结果，默认需要
            mLocationClient.setLocOption(mLocationOption);
        Log.i("@@@@@@@@@@@@@@@@@","成功0");
    }
    @Override
    public void run() {

        init();
        mLocationClient.start();
        Log.i("@@@@@@@@@@@@@@@@@","成功3");
    }
    public onResultListener resultListener;
    public void setOnClicklistener(onResultListener onResultListener){
        this.resultListener=onResultListener;
    }
    public interface onResultListener{
        void onClick(LocationInfo data);
    }
}
