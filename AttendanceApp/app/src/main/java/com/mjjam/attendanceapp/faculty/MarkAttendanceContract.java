package com.mjjam.attendanceapp.faculty;

import com.mjjam.attendanceapp.common.BaseContract;
import com.mjjam.attendanceapp.data.models.MainWrapper;
import com.mjjam.attendanceapp.data.models.UserLoginResponse;
import com.mjjam.attendanceapp.data.models.UserResponse;

public interface MarkAttendanceContract {
    interface MarkView extends BaseContract.BaseView {
        void onPush(UserResponse userResponse);

    }

    interface MarkPresenter {
        void sendData(String token,String mark);
    }
}
