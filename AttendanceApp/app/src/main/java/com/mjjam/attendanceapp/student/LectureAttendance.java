package com.mjjam.attendanceapp.student;

import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.icu.util.Calendar;
import android.os.Build;
import android.support.annotation.RequiresApi;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Johns on 10/6/2017.
 */

public class LectureAttendance {

    private BluetoothAdapter bluetoothAdapter = null;
    private String defaultName = bluetoothAdapter.getName();


    @RequiresApi(api = Build.VERSION_CODES.N)
    public String HashGenerator(){
        Date currentTime = Calendar.getInstance().getTime();
        SimpleDateFormat format = new SimpleDateFormat("hh:mm");
        format.format(currentTime);


    }

    public void ChangeBTName(Context context, int status) {
        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (status == 1) {
            bluetoothAdapter.enable();
            if (bluetoothAdapter.getState() == BluetoothAdapter.STATE_ON) {
                bluetoothAdapter.setName(context.getSharedPreferences("roll_no", )); //Set RollNo_RollingOTP as temporary device name
            }
        } else {
            bluetoothAdapter.setName(defaultName);

        }
    }
}
