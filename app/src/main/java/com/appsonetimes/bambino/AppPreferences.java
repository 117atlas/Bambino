package com.appsonetimes.bambino;

import android.content.Context;
import android.content.SharedPreferences;

public class AppPreferences {
    private String userName;
    private String userAddress;
    private String userMobile;

    public static final String PREF_NAME = "Bambino Preferences";

    public AppPreferences() {
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserAddress() {
        return userAddress;
    }

    public void setUserAddress(String userAddress) {
        this.userAddress = userAddress;
    }

    public String getUserMobile() {
        return userMobile;
    }

    public void setUserMobile(String userMobile) {
        this.userMobile = userMobile;
    }

    public static AppPreferences getPreferences(Context context){
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        AppPreferences appPreferences = new AppPreferences();
        appPreferences.setUserName(sharedPreferences.getString("NAME", ""));
        appPreferences.setUserAddress(sharedPreferences.getString("ADDRESS", ""));
        appPreferences.setUserMobile(sharedPreferences.getString("MOBILE", ""));
        return appPreferences;
    }

    public static void SetUserName(Context context, String userName){
        SharedPreferences.Editor editor = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE).edit();
        editor.putString("NAME", userName);
        editor.apply();
    }

    public static void SetUserAddress(Context context, String userAddress){
        SharedPreferences.Editor editor = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE).edit();
        editor.putString("ADDRESS", userAddress);
        editor.apply();
    }

    public static void SetUserMobile(Context context, String userMobile){
        SharedPreferences.Editor editor = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE).edit();
        editor.putString("MOBILE", userMobile);
        editor.apply();
    }
}
