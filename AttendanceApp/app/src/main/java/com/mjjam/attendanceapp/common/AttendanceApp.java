package com.mjjam.attendanceapp.common;

import android.app.Application;

import com.mjjam.attendanceapp.di.AppComponent;
import com.mjjam.attendanceapp.di.AppModule;
import com.mjjam.attendanceapp.di.DaggerAppComponent;

/**
 * Created by Archish on 1/10/2017.
 */

public class AttendanceApp extends Application {

    private AppComponent component;

    @Override
    public void onCreate() {
        super.onCreate();
        initComponent();
    }

    private void initComponent() {
        component = DaggerAppComponent.builder().appModule(new AppModule()).build();
    }

    public AppComponent getComponent() {
        return component;
    }
}
