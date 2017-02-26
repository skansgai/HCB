package com.haochibao.activity;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

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
import com.haochibao.utill.model.MyOrientationListener;

/**
 * Created by Administrator on 2016/12/30.
 */

public class BaiduMapActivity extends Activity{
    private TextView normal_map,satellite_map,hot_map,traffic;
    private ImageView mapNavi;
    private BaiduMap baiduMap;
    private MapView mapView = null;
    private Marker mMark;
    private Location location;
    private Context context;
    private double latitude=116.357428;
    private double longitude=39.93923;
    private boolean istraffic=false;
    private boolean isHot=false;
    private boolean isFirstIn=true;
    private LocationClient locationClient = null;
    private MyOrientationListener myOrientationListener;
    private BitmapDescriptor iconBitmap;
    private BaiduLocation baiduLocation;
    //定义坐标点
    private LatLng point;
    float mCurrentX;

    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what==2){
                moveAnnotation(point);
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
        point = new LatLng(longitude,latitude);
        normal_map= (TextView) findViewById(R.id.normal_map);
        satellite_map= (TextView) findViewById(R.id.satellite_map);
        hot_map= (TextView) findViewById(R.id.hot_map);
        traffic= (TextView) findViewById(R.id.traffic);
        mapNavi= (ImageView) findViewById(R.id.map_navi);
        normal_map.setOnClickListener(onClickListener);
        satellite_map.setOnClickListener(onClickListener);
        hot_map.setOnClickListener(onClickListener);
        traffic.setOnClickListener(onClickListener);
        mapNavi.setOnClickListener(onClickListener);
    }

    //初始化地图控件
    public void initMapView(){
        //获取地图控件引用
        mapView = (MapView) findViewById(R.id.bmapView);
        //对地图的设置
        baiduMap = mapView.getMap();
        baiduMap.setMapType(BaiduMap.MAP_TYPE_NORMAL);
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
                if (isHot){
                    baiduMap.setBaiduHeatMapEnabled(false);
                    isHot=false;
                }else {
                    baiduMap.setBaiduHeatMapEnabled(true);
                    isHot=true;
                }
                break;
            case R.id.traffic:
                if (istraffic){
                    baiduMap.setTrafficEnabled(false);
                    istraffic=false;
                }else {
                    baiduMap.setTrafficEnabled(true);
                    istraffic=true;
                }
                break;
            case R.id.map_navi:
                Intent intent = new Intent(BaiduMapActivity.this,BaiduNaviActivity.class);
                Bundle bundle=new Bundle();
                longitude=point.longitude;
                latitude=point.latitude;
                bundle.putDouble("latitude",latitude);
                bundle.putDouble("longitude",longitude);
                intent.putExtras(bundle);
                startActivity(intent);
                break;
        }
        }
    };
    public void getLocation() {
        baiduLocation = new BaiduLocation(context);

        baiduLocation.location(new BaiduLocation.onResultListener() {
            @Override
            public void onClick(double data1, double data2) {
                longitude=data1;
                latitude=data2;
                point = new LatLng(data1,data2);
                setMapCenter(point);
                moveAnnotation(point);
                Log.i("POINT",point.latitude+"****"+point.longitude+"");
            }
        });
        baiduLocation.locationStart();

        //自定义
        MyLocationConfiguration config = new MyLocationConfiguration(
                MyLocationConfiguration.LocationMode.NORMAL,true,iconBitmap);
        baiduMap.setMyLocationConfigeration(config);

        if (isFirstIn){
            MapStatusUpdate mapStatusUpdate=MapStatusUpdateFactory.newLatLng(point);
            baiduMap.animateMapStatus(mapStatusUpdate);
        }

        locationClient = new LocationClient(this);


    }
    //地图标注、覆盖物
    public void annotation(LatLng point){
        //构建图标样式
        BitmapDescriptor bitmap = BitmapDescriptorFactory.fromResource(R.mipmap.arrow);
        //构建MakerOption用于在地图上添加maker
        OverlayOptions options = new MarkerOptions()
                .position(point)
                .icon(bitmap);
        baiduMap.addOverlay(options);
    }

    //拖拉覆盖物
    public void moveAnnotation(LatLng point){
        //构建图标样式
        BitmapDescriptor bitmap = BitmapDescriptorFactory.fromResource(R.mipmap.arrow);
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
    public void setMapCenter(LatLng point){
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
    protected void onResume() {
        super.onResume();
        mapView.onResume();
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
    protected void onStop() {
        super.onStop();
        baiduMap.setMyLocationEnabled(false);
        locationClient.stop();
        //停止方向传感器
        baiduLocation.locationStop();
        myOrientationListener.stop();
    }
}
