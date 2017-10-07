package com.mjjam.attendanceapp.student;

import android.bluetooth.BluetoothAdapter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mjjam.attendanceapp.R;
import com.mjjam.attendanceapp.data.local.SharedPreferenceManager;
import com.mjjam.attendanceapp.data.models.Lecture;
import com.mjjam.attendanceapp.widgets.BaseTextView;

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
        tvSTime.setText("10:30");
        DateFormat dateFormat = new SimpleDateFormat("HH:mm");
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        Date d1 = null;
        try {
            d1 = sdf.parse(tvSName.getText().toString());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Date d2 = null;
        try {
            d2 = sdf.parse(dateFormat.format(date));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        assert d2 != null;
        assert d1 != null;
        long elapsed = d2.getTime() - d1.getTime();
        BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

        if (Integer.parseInt(String.valueOf((elapsed / 1000) / 60)) <= 15) {
            if (!mBluetoothAdapter.isEnabled()) {
                mBluetoothAdapter.enable();
            }
            //Log.i(, "localdevicename : "+bluetoothAdapter.getName()+" localdeviceAddress : "+bluetoothAdapter.getAddress());
            new SharedPreferenceManager(getActivity().getApplicationContext()).saveBluetoothName(mBluetoothAdapter.getName());
            mBluetoothAdapter.setName(String.valueOf(new SharedPreferenceManager(getActivity().getApplicationContext()).getRollNo()));
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

    @Override
    public void onItemCardClicked(String home) {

    }
}
