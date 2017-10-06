package com.mjjam.attendanceapp.faculty;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.mjjam.attendanceapp.MainActivity;
import com.mjjam.attendanceapp.R;

import java.util.ArrayList;


public class AttendanceOverview extends AppCompatActivity implements AttendanceOverviewAdapter.LikeItemUpdateListener {

    RecyclerView rvListOfStudents;
    private BluetoothAdapter mBluetoothAdapter;
    public ArrayList<String> arrayOfFoundBTDevices = new ArrayList<String>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendance);
        initViews();
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        mBluetoothAdapter.startDiscovery();
        IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
        registerReceiver(mReceiver, filter);

    }

    final BroadcastReceiver mReceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            // When discovery finds a device
            if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                // Get the bluetoothDevice object from the Intent
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);

                // Get the "RSSI" to get the signal strength as integer,
                // but should be displayed in "dBm" units
                int rssi = intent.getShortExtra(BluetoothDevice.EXTRA_RSSI, Short.MIN_VALUE);

//                    // Create the device object and add it to the arrayList of devices
//                    BluetoothObject bluetoothObject = new BluetoothObject();
//                    bluetoothObject.setBluetooth_name(device.getName());
//                    bluetoothObject.setBluetooth_address(device.getAddress());
//                    bluetoothObject.setBluetooth_state(device.getBondState());
//                    bluetoothObject.setBluetooth_type(device.getType());    // requires API 18 or higher
//                    bluetoothObject.setBluetooth_uuids(device.getUuids());
//                    bluetoothObject.setBluetooth_rssi(rssi);
//
                arrayOfFoundBTDevices.add(device.getName());
                Log.d("Devices",device.getName());
//                AttendanceOverviewAdapter attendanceOverviewAdapter = new AttendanceOverviewAdapter(arrayOfFoundBTDevices, AttendanceOverview.this);
//                rvListOfStudents.setAdapter(attendanceOverviewAdapter);

            }
        }
    };


    private void initViews() {
        rvListOfStudents = (RecyclerView) findViewById(R.id.rvListOfStudents);
        rvListOfStudents.setHasFixedSize(true);
        rvListOfStudents.setLayoutManager(new LinearLayoutManager(AttendanceOverview.this, LinearLayoutManager.VERTICAL, false));

    }

    @Override
    public void onItemCardClicked(String home) {

    }
}
