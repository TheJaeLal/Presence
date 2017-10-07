package com.mjjam.attendanceapp.student;

import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mjjam.attendanceapp.R;
import com.mjjam.attendanceapp.data.local.SharedPreferenceManager;
import com.mjjam.attendanceapp.data.models.Lecture;
import com.mjjam.attendanceapp.widgets.BaseTextView;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Archish on 10/7/2017.
 */

public class StudentHomeFragment extends Fragment implements StudentHomeAdapter.LikeItemUpdateListener {
    BaseTextView tvSName, tvSTime;
    RecyclerView rvLectures;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_student_home, container, false);
        rvLectures = (RecyclerView) view.findViewById(R.id.rvLectures);
        tvSName = (BaseTextView) view.findViewById(R.id.tvSName);
        tvSTime = (BaseTextView) view.findViewById(R.id.tvSTime);
        tvSName.setText("DWM");
        tvSTime.setText("12:55");
        DateFormat dateFormat = new SimpleDateFormat("HH:mm");
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        Date d1 = null;
        try {
            d1 = sdf.parse(tvSTime.getText().toString());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Date d2 = null;
        try {
            d2 = sdf.parse(dateFormat.format(date));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        long elapsed = d2.getTime() - d1.getTime();
        BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

        if (Integer.parseInt(String.valueOf((elapsed / 1000) / 60)) <= 15) {
            if (!mBluetoothAdapter.isEnabled()) {
                mBluetoothAdapter.enable();
                new SharedPreferenceManager(getActivity().getApplicationContext()).saveBluetoothName(mBluetoothAdapter.getName());
            }
            visible();
            //Log.i(, "localdevicename : "+bluetoothAdapter.getName()+" localdeviceAddress : "+bluetoothAdapter.getAddress());
            String dName = String.valueOf(new SharedPreferenceManager(getActivity().getApplicationContext()).getRollNo()) + "_" + HashGenerator(tvSTime.getText().toString()).substring(0, 5);
            mBluetoothAdapter.setName(dName);
            //Log.i(, "localdevicename : "+bluetoothAdapter.getName()+" localdeviceAddress : "+bluetoothAdapter.getAddress());
        } else {
            mBluetoothAdapter.setName(new SharedPreferenceManager(getActivity().getApplicationContext()).getBluetoothName());
            if (mBluetoothAdapter.isEnabled()) {
                mBluetoothAdapter.disable();
            }

        }
        ArrayList<Lecture> lectures = new ArrayList<>();
        lectures.add(new Lecture("DWM", "11:30"));
        lectures.add(new Lecture("RDBMS", "13:15"));
        lectures.add(new Lecture("RDBMS", "13:15"));
        lectures.add(new Lecture("RDBMS", "13:15"));
        lectures.add(new Lecture("RDBMS", "13:15"));
        lectures.add(new Lecture("RDBMS", "13:15"));
        lectures.add(new Lecture("ADBMS", "14:15"));
        rvLectures.setHasFixedSize(true);
        rvLectures.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        rvLectures.setAdapter(new StudentHomeAdapter(lectures, this));
        return view;
    }

    public void visible() {
        Intent getVisible = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
        getVisible.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, 900);
        startActivityForResult(getVisible, 0);
    }

    @Override
    public void onItemCardClicked(String home) {

    }

    public String HashGenerator(String time) {
        String SHAHash = "";
        String custom_checksum = new SharedPreferenceManager(getActivity().getApplicationContext()).getAccessToken() + time;
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

}
