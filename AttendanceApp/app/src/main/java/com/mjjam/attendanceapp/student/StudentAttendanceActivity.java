package com.mjjam.attendanceapp.student;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.mjjam.attendanceapp.R;
import com.mjjam.attendanceapp.data.local.SharedPreferenceManager;

import java.util.ArrayList;

public class StudentAttendanceActivity extends AppCompatActivity {

    private String[] monthList;
    private ArrayList<String> courseList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_attendance);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final Spinner monthSelector = (Spinner) findViewById(R.id.spSelectMonth);
        final Spinner courseSelector = (Spinner) findViewById(R.id.spSelectMonth);

        this.monthList = new String[]{"Select Month","January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
        this.courseList = new SharedPreferenceManager(this).getCourseList();
        this.courseList.add(0, "Select Course");

        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(this, R.layout.activity_attendance, monthList);
        monthSelector.setAdapter(adapter1);
        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this, R.layout.activity_attendance, courseList);
        courseSelector.setAdapter(adapter2);

        monthSelector.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //Add json query to fetch student monthly attendance
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                monthSelector.setPrompt("Select Course");
            }

        });

        courseSelector.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //Add json query to fetch student course attendance
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                courseSelector.setPrompt("Select Course");
            }

        });
    }


}
