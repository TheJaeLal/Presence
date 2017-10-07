package com.mjjam.attendanceapp.timetable;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.IdRes;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.NotificationCompat;
import android.support.v7.widget.AppCompatSpinner;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TableLayout;
import android.widget.Toast;

import com.mjjam.attendanceapp.R;
import com.mjjam.attendanceapp.common.AttendanceApp;
import com.mjjam.attendanceapp.data.local.SharedPreferenceManager;
import com.mjjam.attendanceapp.data.models.UserResponse;
import com.mjjam.attendanceapp.data.repository.UserRepository;
import com.mjjam.attendanceapp.helper.TimeTable;
import com.mjjam.attendanceapp.widgets.BaseButton;
import com.mjjam.attendanceapp.widgets.BaseRadioButton;

import java.io.File;
import java.io.IOException;

import okhttp3.ResponseBody;
import okio.BufferedSink;
import okio.Okio;
import retrofit2.Response;

public class TimeTableActivity extends AppCompatActivity implements TimeTableContracts.TimeTableView {

    RadioGroup rgSchedule;
    AppCompatSpinner sDay;
    TimeTablePresenter timeTablePresenter;
    BaseRadioButton rbSchedule;
    BaseButton bSubmit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_table);
        initViews();
        UserRepository userRepository = ((AttendanceApp) getApplication()).getComponent().userRepository();
        timeTablePresenter = new TimeTablePresenter(this, userRepository);

        rgSchedule.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                if (checkType()) {
                    sDay.setVisibility(View.GONE);
                } else {
                    sDay.setVisibility(View.VISIBLE);
                }
            }
        });
        bSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkType())
                    timeTablePresenter.getTimeTable(new SharedPreferenceManager(getApplicationContext()).getAccessToken());
                else
                    timeTablePresenter.getTimeTable(new SharedPreferenceManager(getApplicationContext()).getAccessToken(), String.valueOf(dayInt(sDay.getSelectedItem().toString())));
            }
        });

    }

    private int dayInt(String day) {
        switch (day) {
            case "Monday":
                return 1;
            case "Tuesday":
                return 2;
            case "Wednesday":
                return 3;
            case "Thursday":
                return 4;
            case "Friday":
                return 5;
            case "Saturday":
                return 6;
            case "Sunday":
                return 7;
            default:
                return -1;
        }
    }

    private boolean checkType() {
        int selectedId = rgSchedule.getCheckedRadioButtonId();
        rbSchedule = (BaseRadioButton) findViewById(selectedId);
        return rbSchedule.getText().toString().equals(getString(R.string.week_schedule));
    }


    private void initViews() {
        rgSchedule = (RadioGroup) findViewById(R.id.rgSchedule);
        sDay = (AppCompatSpinner) findViewById(R.id.sDay);
        bSubmit = (BaseButton) findViewById(R.id.bSubmit);
    }

    @Override
    public void onNetworkException(Throwable e) {

    }

    private NotificationManager mNotifyManager;
    private NotificationCompat.Builder mBuilder;

    @Override
    public void onData(Response<ResponseBody> responseBodyResponse) {
        try {
            String header = responseBodyResponse.headers().get("Content-Disposition");
            String filename = header.replace("attachment; filename=", "");
            filename = filename.replace("\"", "");
            new File(Environment.getExternalStorageDirectory() + "/AttendanceApp").mkdir();
            File destinationFile = new File(Environment.getExternalStorageDirectory() + "/AttendanceApp/" + filename);
            BufferedSink bufferedSink = Okio.buffer(Okio.sink(destinationFile));
            bufferedSink.writeAll(responseBodyResponse.body().source());
            bufferedSink.close();
            mNotifyManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            Intent openFile = new Intent(Intent.ACTION_VIEW, Uri.fromFile(destinationFile));
            openFile.setDataAndType(Uri.fromFile(destinationFile), "application/pdf");
            openFile.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            PendingIntent p = PendingIntent.getActivity(getApplicationContext(), 0, openFile, 0);
            mBuilder = new NotificationCompat.Builder(TimeTableActivity.this);
            mBuilder.setContentTitle(getString(R.string.app_name))
                    .setContentText("Download Completed")
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setContentIntent(p);

            mNotifyManager.notify(101, mBuilder.build());


        } catch (IOException e) {
            e.printStackTrace();

        }
//        if (userResponse.isStatus()) {
//            Toast.makeText(getApplicationContext(), userResponse.getMessage(), Toast.LENGTH_SHORT).show();
//        } else {
//            Toast.makeText(getApplicationContext(), userResponse.getMessage(), Toast.LENGTH_SHORT).show();
//        }
    }
}
