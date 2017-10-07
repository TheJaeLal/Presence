package com.mjjam.attendanceapp.student;

import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.icu.util.Calendar;
import android.os.AsyncTask;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.Base64;
import android.util.Log;

import com.mjjam.attendanceapp.MainActivity;
import com.mjjam.attendanceapp.data.local.SharedPreferenceManager;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimerTask;

/**
 * Created by Johns on 10/6/2017.
 */

//Broadcast from Student App to mark attendance
public class LectureAttendance extends AsyncTask<Void, Void, String> {

    private BluetoothAdapter bluetoothAdapter;
    private String defaultName = bluetoothAdapter.getName();
    private SharedPreferenceManager prefs;
    private Context context;

    public LectureAttendance(Context context) {
        this.context = context;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public String HashGenerator(String time) {
        prefs = new SharedPreferenceManager(context);
        String SHAHash = "";

        String custom_checksum = prefs.getAccessToken() + time;
        MessageDigest mdSha1 = null;
        try {
            URLEncoder.encode(custom_checksum, "utf-8");
            mdSha1 = MessageDigest.getInstance("SHA-1");
            mdSha1.update(custom_checksum.getBytes("ASCII"));
            byte[] data = mdSha1.digest();
            SHAHash = convertToHex(data);
            Log.e("Hash", "Generated SHA1 : " + SHAHash);

        } catch (NoSuchAlgorithmException e) {
            Log.e("Hash", "Error initializing SHA1 message digest");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return SHAHash;
    }

    private static String convertToHex(byte[] data) throws java.io.IOException {
        StringBuffer sb = new StringBuffer();
        String hex = Base64.encodeToString(data, 0, data.length, Base64.DEFAULT);
        sb.append(hex);
        return sb.toString();
    }


    @RequiresApi(api = Build.VERSION_CODES.N)
    public void ChangeBTName(Context context, int status) {
        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        prefs = new SharedPreferenceManager(context);
        if (status == 1) {
            defaultName = bluetoothAdapter.getName();
            bluetoothAdapter.enable();
            if (bluetoothAdapter.getState() == BluetoothAdapter.STATE_ON) {
                String temp_identifier = String.valueOf(prefs.getRollNo()) + "_" + HashGenerator("12.55");
                bluetoothAdapter.setName(temp_identifier);
            }
        } else {
            bluetoothAdapter.setName(defaultName);
            bluetoothAdapter.disable();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected String doInBackground(Void... params) {
        ChangeBTName(context, 1);
        try {
            Thread.sleep(600000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        ChangeBTName(context, 0);

        return null;
    }
}
