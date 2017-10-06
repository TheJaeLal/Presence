package com.mjjam.attendanceapp.data.local;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPreferenceManager {
    private SharedPreferences settings;

    private static final String PREFS_NAME = "AttendancePrefs";
    private static final String PREFS_MOBILENO = "mobileno";
    private static final String PREFS_CATEGORY = "category";
    private static final String PREFS_ACCESS_TOKEN = "accessToken";
    private static final String PREFS_MAINPAGE = "mainpage";
    private static String PREFS_DEVICE_TOKEN = "devicetoken";


    public SharedPreferenceManager(Context mContext) {
        settings = mContext.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
    }

    public void saveMobileNo(String mobile) {
        settings.edit().putString(PREFS_MOBILENO, mobile).apply();
    }


    public void saveCategory(int category) {
        settings.edit().putInt(PREFS_CATEGORY, category).apply();
    }

    public void saveMainPage(int page) {
        settings.edit().putInt(PREFS_MAINPAGE, page).apply();
    }

    public void saveAccessToken(String accessToken) {
        settings.edit().putString(PREFS_ACCESS_TOKEN, accessToken).apply();
    }

    public String getMobileNo() {
        return settings.getString(PREFS_MOBILENO, null);
    }

    public String getAccessToken() {
        return settings.getString(PREFS_ACCESS_TOKEN, null);
    }

    public int getCategory() {
        return settings.getInt(PREFS_CATEGORY, 0);
    }

    public int getMainPage() {
        return settings.getInt(PREFS_MAINPAGE, 0);
    }

    public void saveDeviceToken(String token) {
        settings.edit().putString(PREFS_DEVICE_TOKEN, token).apply();
    }

    public String getDeviceToken() {
        return settings.getString(PREFS_DEVICE_TOKEN, null);
    }

    public void removeAccessToken() {
        settings.edit().remove(PREFS_ACCESS_TOKEN).apply();
    }

    public void removeCategory() {
        settings.edit().remove(PREFS_CATEGORY).apply();
    }

    public void removeMainPage() {
        settings.edit().remove(PREFS_MAINPAGE).apply();
    }

    public void removeAllToken() {
        settings.edit().clear().apply();
    }


}
