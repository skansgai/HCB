package com.haochibao.utill.model;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

/**
 * Created by Administrator on 2017/1/3.
 */

public class MyOrientationListener implements SensorEventListener {
    private Context context;
    private SensorManager mSensorManage;
    private Sensor mSensor;
    private float lastX;
    private OnOrientationListener mOnOrientationListener;

    public void setmOnOrientationListener(OnOrientationListener mOnOrientationListener){
        this.mOnOrientationListener = mOnOrientationListener;
    }

    public MyOrientationListener(Context context){
        this.context=context;
    }

    public void start(){
        mSensorManage = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        if (mSensorManage !=null){
            //获得方向传感器
            mSensor = mSensorManage.getDefaultSensor(Sensor.TYPE_ORIENTATION);
        }
        if (mSensor !=null){
            mSensorManage.registerListener(this,mSensor,SensorManager.SENSOR_DELAY_NORMAL);
        }
    }

    public void stop(){
        //停止定位
        mSensorManage.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
    if (event.sensor.getType() == Sensor.TYPE_ORIENTATION){
        float x = event.values[SensorManager.DATA_X];
        if (Math.abs(x-lastX)>1.0){
            if (mOnOrientationListener != null){
                mOnOrientationListener.onOrirentationChanged(x);
            }
        }
        lastX=x;
    }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
    public interface OnOrientationListener{
        void onOrirentationChanged(float x);
    }
}
