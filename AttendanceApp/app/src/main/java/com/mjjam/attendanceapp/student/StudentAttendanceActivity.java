package com.mjjam.attendanceapp.student;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.mjjam.attendanceapp.R;
import com.mjjam.attendanceapp.common.AttendanceApp;
import com.mjjam.attendanceapp.data.local.SharedPreferenceManager;
import com.mjjam.attendanceapp.data.models.AttendanceResponse;
import com.mjjam.attendanceapp.data.repository.UserRepository;

import java.util.ArrayList;

public class StudentAttendanceActivity extends AppCompatActivity implements StudentAttendanceContract.StudentAttendanceView {

    private String[] monthList;
    private ArrayList<String> courseList;
    private String month, course;
    private SharedPreferenceManager prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_attendance);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final Spinner monthSelector = (Spinner) findViewById(R.id.spSelectMonth);
        final Spinner courseSelector = (Spinner) findViewById(R.id.spSelectMonth);

        this.monthList = new String[]{"Select Month", "January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
        this.courseList = new SharedPreferenceManager(this).getCourseList();
        this.courseList.add(0, "Select Course");

        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(this, R.layout.activity_attendance, monthList);
        monthSelector.setAdapter(adapter1);
        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this, R.layout.activity_attendance, courseList);
        courseSelector.setAdapter(adapter2);

        UserRepository userRepository = ((AttendanceApp) getApplication()).getComponent().userRepository();
        final StudentAttendancePresenter studentAttendancePresenter = new StudentAttendancePresenter(userRepository, (StudentAttendanceContract.StudentAttendanceView) this);
        prefs = new SharedPreferenceManager(getApplicationContext());

        monthSelector.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //Add query to fetch student monthly attendance
                if (position != 0) {
                    month = monthSelector.getItemAtPosition(position).toString();
                    studentAttendancePresenter.fetchData(prefs.getRollNo(), month, null);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                monthSelector.setPrompt("Select Course");
            }
        });

        courseSelector.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //Add query to fetch student course attendance
                if (monthSelector.isSelected() && monthSelector.getSelectedItemPosition() != 0) {
                    month = monthSelector.getSelectedItem().toString();
                    course = courseSelector.getItemAtPosition(position).toString();
                    studentAttendancePresenter.fetchData(prefs.getRollNo(), month, course);
                } else {
                    Toast.makeText(getApplicationContext(), "Select Month First..", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                courseSelector.setPrompt("Select Course");
            }

        });
    }


    @Override
    public void onNetworkException(Throwable e) {

    }

    @Override
    public void onResponse(AttendanceResponse attendanceResponse) {
        Toast.makeText(getApplicationContext(), String.valueOf(attendanceResponse.isSuccess()), Toast.LENGTH_LONG).show();
        TextView overall_tv = (TextView) findViewById(R.id.tvOverallAttendance);
        TextView course_tv = (TextView) findViewById(R.id.tvCourseAttendance);
        overall_tv.append(String.valueOf(attendanceResponse.getOverall_apc()) + "%");
        course_tv.append(String.valueOf(attendanceResponse.getCourse_apc()) + "%");
    }
}
