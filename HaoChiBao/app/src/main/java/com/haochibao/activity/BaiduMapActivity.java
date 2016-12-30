package com.haochibao.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
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
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.model.LatLng;
import com.haochibao.R;
import com.haochibao.utill.http.BaiduLocation;
import com.haochibao.utill.http.Location;
import com.haochibao.utill.model.LocationInfo;

/**
 * Created by Administrator on 2016/12/30.
 */

public class BaiduMapActivity extends Activity{
    private TextView normal_map,satellite_map,hot_map;
    private BaiduMap baiduMap;
    private MapView mapView = null;
    private Marker mMark;
    private Location location;
    private LocationInfo locationInfo;
    private Context context;
    double latitude=116.357428;
    double longitude=39.93923;
    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
        if (msg.what==0){
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SDKInitializer.initialize(getApplicationContext());
        setContentView(R.layout.activity_baidu_map);
        initView();
        initMapView();
        context = this;
       // locationInfo = new LocationInfo();
       // getLocation();
        Intent intent = getIntent();
        Bundle bundle=intent.getExtras();
        longitude= (double) bundle.get("longitude");
        latitude= (double) bundle.get("latitude");
        Log.i("latitude","======="+latitude+"longitude====="+longitude);
        moveAnnotation(longitude,latitude);
        setMapCenter(longitude,latitude);

    }

    //初始化控件
    public void initView(){
        normal_map= (TextView) findViewById(R.id.normal_map);
        satellite_map= (TextView) findViewById(R.id.satellite_map);
        hot_map= (TextView) findViewById(R.id.hot_map);
        normal_map.setOnClickListener(onClickListener);
        satellite_map.setOnClickListener(onClickListener);
        hot_map.setOnClickListener(onClickListener);
    }
    //初始化地图控件
    public void initMapView(){
        //获取地图控件引用
        mapView = (MapView) findViewById(R.id.bmapView);
        //对地图的设置
        baiduMap = mapView.getMap();
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
                baiduMap.setMapType(BaiduMap.MAP_TYPE_NONE);
                break;
        }
        }
    };
    public void getLocation(){
        location=new Location(context,handler);
        location.setLocationOnClicklistener(new Location.onLocationListener() {
            @Override
            public void onClick(double data1,double data2) {
                latitude=data1;
                longitude=data2;
                Log.i("latitude","======="+latitude+"longitude====="+longitude);
            }
        });
        location.start();

    }


   /* //百度定位
    public void getLocation(){
      location.setOnClicklistener(new BaiduLocation.onResultListener() {
          @Override
          public void onClick(LocationInfo data) {
              locationInfo =data;
          }
      });
        location.start();
    }*/

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
       /* if (locationInfo!=null){
            longitude = locationInfo.getLongitude();
            latitude = locationInfo.getLatitude();
        }*/
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
}
