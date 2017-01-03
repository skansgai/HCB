package com.haochibao.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.hardware.Sensor;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.model.LatLng;
import com.haochibao.R;
import com.haochibao.utill.http.BaiduLocation;
import com.haochibao.utill.http.Location;
import com.haochibao.utill.model.LocationInfo;
import com.haochibao.utill.model.MyOrientationListener;

/**
 * Created by Administrator on 2016/12/30.
 */

public class BaiduMapActivity extends Activity{
    private TextView normal_map,satellite_map,hot_map,traffic;
    private BaiduMap baiduMap;
    private MapView mapView = null;
    private Marker mMark;
    private Location location;
    private Context context;
    double latitude=116.357428;
    double longitude=39.93923;
    private boolean istraffic=false;
    private boolean isFirstIn=true;
    private LocationClient locationClient = null;
    private MyOrientationListener myOrientationListener;
    private BitmapDescriptor iconBitmap;
    float mCurrentX;

    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
        if (msg.what==0){
            moveAnnotation(longitude,latitude);
            setMapCenter(longitude,latitude);
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SDKInitializer.initialize(getApplicationContext());
        setContentView(R.layout.activity_baidu_map);
        context = getApplicationContext();
        initView();
        initMapView();
        getLocation();
    }
    //初始化控件
    public void initView(){
        normal_map= (TextView) findViewById(R.id.normal_map);
        satellite_map= (TextView) findViewById(R.id.satellite_map);
        hot_map= (TextView) findViewById(R.id.hot_map);
        traffic= (TextView) findViewById(R.id.traffic);

        normal_map.setOnClickListener(onClickListener);
        satellite_map.setOnClickListener(onClickListener);
        hot_map.setOnClickListener(onClickListener);
        traffic.setOnClickListener(onClickListener);
    }

    //初始化地图控件
    public void initMapView(){
        //获取地图控件引用
        mapView = (MapView) findViewById(R.id.bmapView);
        //对地图的设置
        baiduMap = mapView.getMap();

        iconBitmap =BitmapDescriptorFactory.fromResource(R.mipmap.arrow);

        myOrientationListener = new MyOrientationListener(this);
        myOrientationListener.setmOnOrientationListener(new MyOrientationListener.OnOrientationListener() {
            @Override
            public void onOrirentationChanged(float x) {
                mCurrentX = x;
            }
        });
    }

    //控件监听
    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
        switch (v.getId()){
            case R.id.normal_map:
                //普通地图
                baiduMap.setMapType(BaiduMap.MAP_TYPE_NORMAL);
                break;
            case R.id.satellite_map:
                //卫星地图
                baiduMap.setMapType(BaiduMap.MAP_TYPE_SATELLITE);
                break;
            case R.id.hot_map:
                if (istraffic){
                    baiduMap.setBaiduHeatMapEnabled(false);
                }else {
                    baiduMap.setBaiduHeatMapEnabled(true);
                }

                break;
            case R.id.traffic:
                if (istraffic){
                    baiduMap.setTrafficEnabled(false);
                }else {
                    baiduMap.setTrafficEnabled(true);
                }
                break;
        }
        }
    };


    public void getLocation() {
        locationClient = new LocationClient(this);
        LocationClientOption option = new LocationClientOption();
        option.setCoorType("bd0911");
        option.setScanSpan(0);
        option.setIsNeedAddress(true);
        option.setOpenGps(true);
        option.setLocationNotify(true);
        option.setIsNeedLocationDescribe(true);
        option.setIsNeedLocationPoiList(true);
        option.setIgnoreKillProcess(false);
        option.SetIgnoreCacheException(true);
        option.setEnableSimulateGps(true);
        locationClient.setLocOption(option);
        locationClient.registerLocationListener(new BDLocationListener() {
            @Override
            public void onReceiveLocation(BDLocation bdLocation) {
                MyLocationData data = new MyLocationData.Builder()//
                .direction(mCurrentX)
                .accuracy(bdLocation.getRadius())//
                .latitude(bdLocation.getLatitude())//
                .longitude(bdLocation.getLongitude())//
                .build();
                baiduMap.setMyLocationData(data);
                //自定义
                MyLocationConfiguration config = new MyLocationConfiguration(
                        MyLocationConfiguration.LocationMode.NORMAL,true,iconBitmap);
                baiduMap.setMyLocationConfigeration(config);
                if (isFirstIn){
                    moveAnnotation(bdLocation.getLatitude(),bdLocation.getLongitude());
                    setMapCenter(bdLocation.getLatitude(),bdLocation.getLongitude());
                    isFirstIn=false;
                }
                Log.i("================","latitude"+bdLocation.getLatitude()+"longitude"+bdLocation.getLongitude()+"\n城市名"+bdLocation.getCity());
            }
        });

    }
    //地图标注、覆盖物
    public void annotation(){
        //定义坐标点
        LatLng point = new LatLng(39.963175, 116.400244);
        //构建图标样式
        BitmapDescriptor bitmap = BitmapDescriptorFactory.fromResource(R.mipmap.location_icon);
        //构建MakerOption用于在地图上添加maker
        OverlayOptions options = new MarkerOptions()
                .position(point)
                .icon(bitmap);
        baiduMap.addOverlay(options);
    }

    //拖拉覆盖物
    public void moveAnnotation(double latitude,double longitude){
        //定义坐标点
        LatLng point = new LatLng(latitude,longitude);
        //构建图标样式
        BitmapDescriptor bitmap = BitmapDescriptorFactory.fromResource(R.mipmap.location_icon);
        //构建MakerOption用于在地图上添加maker
        OverlayOptions options = new MarkerOptions()
                .position(point)//设置marker的位置
                .icon(bitmap)//设置marker图标
                .zIndex(9)//设置marker所在层级
                .draggable(true); //设置手势拖拽
        mMark = (Marker) (baiduMap.addOverlay(options));

        //调用BaiduMap对象的setOnMarkerDragListener方法设置marker拖拽的监听
        baiduMap.setOnMarkerDragListener(new BaiduMap.OnMarkerDragListener() {
            @Override
            public void onMarkerDrag(Marker marker) {
                Log.i("onMarkerDrag","拖拽中"); //拖拽中
            }

            @Override
            public void onMarkerDragEnd(Marker marker) {
                Log.i("onMarkerDragEnd","拖拽结束"); //拖拽结束

            }
            @Override
            public void onMarkerDragStart(Marker marker) {
                Log.i("onMarkerDragStart","开始拖拽"); //开始拖拽
            }
        });

    }
    //设置地图中心点
    public void setMapCenter(double latitude,double longitude){
        //先创建个坐标对象，往里面传递经纬度
        LatLng point = new LatLng(latitude,longitude);
        //定义地图状态
        MapStatus mapStatus = new MapStatus.Builder()
                .target(point)
                .zoom(18)
                .build();
        //定义MapStatusUpdate对象，以便描述地图状态将要发生的变化
        MapStatusUpdate mMapStatusUpdate = MapStatusUpdateFactory.newMapStatus(mapStatus);
        baiduMap.setMapStatus(mMapStatusUpdate);
    }

    @Override
    protected void onStart() {
        super.onStart();
        baiduMap.setMyLocationEnabled(true);
        if (!locationClient.isStarted()){
            locationClient.start();
        }
        //开启方向传感器
        myOrientationListener.start();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
        baiduMap.setMyLocationEnabled(false);
        locationClient.stop();
        //停止方向传感器
        myOrientationListener.stop();
    }
}