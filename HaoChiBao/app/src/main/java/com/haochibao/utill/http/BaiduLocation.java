package com.haochibao.utill.http;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.haochibao.utill.model.LocationInfo;
/**
 * Created by Administrator on 2016/12/30.
 */

public class BaiduLocation  {
    private LocationClient locationClient = null;
    private  Context context;
    private LocationInfo locationInfo;
    double longitude=116.357428;
    double latitude=39.93923;


    public BaiduLocation(Context context){
        this.context=context;
    }

    public void location(final onResultListener resultListener) {
        locationInfo = new LocationInfo();

        locationClient = new LocationClient(context);

        LocationClientOption option = new LocationClientOption();
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);
        option.setCoorType("bd0911");
        option.setScanSpan(0);
        option.setIsNeedAddress(true);
        option.setOpenGps(true);
        option.setLocationNotify(false);
        option.setIsNeedLocationDescribe(true);
        option.setIsNeedLocationPoiList(true);
        option.setIgnoreKillProcess(true);
        option.SetIgnoreCacheException(true);
        option.setEnableSimulateGps(true);
        locationClient.setLocOption(option);
        locationClient.registerLocationListener(new BDLocationListener() {
            @Override
            public void onReceiveLocation(BDLocation bdLocation) {
                locationInfo.setLongitude(bdLocation.getLongitude());
                locationInfo.setLatitude(bdLocation.getLatitude());
                locationInfo.setTime(bdLocation.getTime());
                locationInfo.setCityName(bdLocation.getCity());
                locationInfo.setSpree(bdLocation.getSpeed());
                latitude=bdLocation.getLatitude();
                longitude=bdLocation.getLongitude();
                resultListener.onClick(latitude,longitude);
                Log.i("================","latitude"+latitude+"longitude"+longitude+"\n城市名"+bdLocation.getCity());
            }
        });
    }
    public void locationStart(){
        locationClient.start();
        Log.i("================","执行1111");
    }
    public void locationStop(){
        locationClient.stop();
    }
    public interface onResultListener{
        void onClick(double data1,double data2);
    }

}
