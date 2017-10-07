package com.mjjam.attendanceapp.data.local;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.ArrayList;
import java.util.HashSet;

public class SharedPreferenceManager {
    private SharedPreferences settings;

    private static final String PREFS_NAME = "AttendancePrefs";
    private static final String PREFS_FIRSTNAME = "firstName";
    private static final String PREFS_LASTNAME = "lastName";
    private static final String PREFS_ROLLNO = "rollNo";
    private static final String PREFS_CATEGORY = "category";
    private static final String PREFS_USERNAME = "userName";
    private static final String PREFS_COURSELIST = "courseList";

    private static final String PREFS_ACCESS_TOKEN = "accessToken";
    private static final String PREFS_MAINPAGE = "mainpage";
    private static String PREFS_DEVICE_TOKEN = "devicetoken";


    public SharedPreferenceManager(Context mContext) {
        settings = mContext.getSharedPreferences(PREFS_USERNAME, Context.MODE_PRIVATE);
    }

    public void saveCategory(int category) {
        settings.edit().putInt(PREFS_CATEGORY, category).apply();
    }

    public void saveRollNo(int roll_no) {
        settings.edit().putInt(PREFS_ROLLNO, roll_no).apply();
    }

    public void saveFirstName(String first_name) {
        settings.edit().putString(PREFS_FIRSTNAME, first_name).apply();
    }

    public void saveLastName(String last_name) {
        settings.edit().putString(PREFS_LASTNAME, last_name).apply();
    }

    public void saveUserName(String user_name) {
        settings.edit().putString(PREFS_USERNAME, user_name).apply();
    }

    public void saveCourseList(ArrayList<String> course_list) {
        settings.edit().putStringSet(PREFS_COURSELIST, new HashSet<String>(course_list)).apply();
    }


    public void saveMainPage(int page) {
        settings.edit().putInt(PREFS_MAINPAGE, page).apply();
    }

    public void saveAccessToken(String accessToken) {
        settings.edit().putString(PREFS_ACCESS_TOKEN, accessToken).apply();
    }

    public String getAccessToken() {
        return settings.getString(PREFS_ACCESS_TOKEN, null);
    }

    public int getCategory() {
        return settings.getInt(PREFS_CATEGORY, 0);
    }

    public int getRollNo() {
        return settings.getInt(PREFS_ROLLNO, 0);
    }

    public String getFirstName() {
        return settings.getString(PREFS_FIRSTNAME, "");
    }

    public String getLastName() {
        return settings.getString(PREFS_LASTNAME, "");
    }

    public String getUserName() {
        return settings.getString(PREFS_USERNAME, "");
    }

    public ArrayList<String> getCourseList() {
        return new ArrayList<String>(settings.getStringSet(PREFS_COURSELIST, null));
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
