package com.mjjam.attendanceapp.timetable;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatSpinner;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.mjjam.attendanceapp.R;
import com.mjjam.attendanceapp.common.AttendanceApp;
import com.mjjam.attendanceapp.data.local.SharedPreferenceManager;
import com.mjjam.attendanceapp.data.models.UserResponse;
import com.mjjam.attendanceapp.data.repository.UserRepository;
import com.mjjam.attendanceapp.helper.TimeTable;
import com.mjjam.attendanceapp.widgets.BaseButton;
import com.mjjam.attendanceapp.widgets.BaseRadioButton;

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

    @Override
    public void onData(UserResponse userResponse) {
        if (userResponse.isStatus()) ;
            //TODO Download
        else
            Toast.makeText(getApplicationContext(), userResponse.getMessage(), Toast.LENGTH_SHORT).show();
    }
}
