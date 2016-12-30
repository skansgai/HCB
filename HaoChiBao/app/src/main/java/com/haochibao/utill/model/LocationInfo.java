package com.haochibao.utill.model;

/**
 * Created by Administrator on 2016/12/30.
 */

public class LocationInfo {
    private String cityName;
    private double longitude;
    private double latitude;
    private String time;
    private float spree;

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public float getSpree() {
        return spree;
    }

    public void setSpree(float spree) {
        this.spree = spree;
    }
}
