package com.haochibao;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;
public class MyApplication extends Application {

    private static int userId;
    private static String userToken;
    private static boolean isLogin=false;
    private static String LoginStyle="Normal";
    public static String getLoginStyle() {
        return LoginStyle;
    }

    public static void setLoginStyle(String loginStyle) {
        LoginStyle = loginStyle;
    }

    public static boolean isLogin() {
        return isLogin;
    }

    public static void setIsLogin(boolean isLogin) {
        MyApplication.isLogin = isLogin;
    }

    public static String getUserToken() {
        return userToken;
    }

    public static void setUserToken(String userToken) {
        MyApplication.userToken = userToken;
    }

    public static int getUserId() {
        return userId;
    }

    public static void setUserId(int userId) {
        MyApplication.userId = userId;
    }
}
