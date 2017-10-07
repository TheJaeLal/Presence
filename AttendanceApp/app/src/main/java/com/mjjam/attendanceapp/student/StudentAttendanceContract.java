package com.mjjam.attendanceapp.student;

import com.mjjam.attendanceapp.common.BaseContract;
import com.mjjam.attendanceapp.data.models.AttendanceResponse;

/**
 * Created by Johns on 10/6/2017.
 */

public interface StudentAttendanceContract {

    interface StudentAttendanceView extends BaseContract.BaseView {
        void onResponse(AttendanceResponse attendanceResponse);

    }

    interface StudentAttendancePresenter {
        void fetchData(int rollNo, String month, String courseName);

    }
}

