package com.mjjam.attendanceapp.student;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
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
    StudentAttendancePresenter studentAttendancePresenter;
    private Spinner monthSelector, courseSelector;
    private Button submitButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_attendance);

        monthSelector = (Spinner) findViewById(R.id.spSelectMonth);
        courseSelector = (Spinner) findViewById(R.id.spSelectCourse);
        submitButton = (Button) findViewById(R.id.bSubmit);

        this.monthList = new String[]{"Select Month", "January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
        this.courseList = new SharedPreferenceManager(this).getCourseList();
        this.courseList.add(0, "Select Course");

        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, monthList);
        monthSelector.setAdapter(adapter1);
        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, courseList);
        courseSelector.setAdapter(adapter2);

        UserRepository userRepository = ((AttendanceApp) getApplication()).getComponent().userRepository();
        studentAttendancePresenter = new StudentAttendancePresenter(userRepository, this);
        prefs = new SharedPreferenceManager(getApplicationContext());

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (monthSelector.getSelectedItemPosition() != 0) {
                    month = monthSelector.getSelectedItem().toString();
                    if (courseSelector.getSelectedItemPosition() != 0 && monthSelector.getSelectedItemPosition() != 0) {
                        month = monthSelector.getSelectedItem().toString();
                        course = courseSelector.getSelectedItem().toString();
                        studentAttendancePresenter.fetchData(prefs.getRollNo(), month, course);
                    } else {
                        Toast.makeText(getApplicationContext(), "Select Course", Toast.LENGTH_SHORT).show();
                        studentAttendancePresenter.fetchData(prefs.getRollNo(), month, "");
                    }
                }
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
