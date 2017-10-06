package com.mjjam.attendanceapp.dashboard;

import com.mjjam.attendanceapp.common.BaseContract;
import com.mjjam.attendanceapp.data.models.UserLoginResponse;
import com.mjjam.attendanceapp.data.models.UserResponse;

public interface FacultyHomeContract {
    interface FacultyHomeView extends BaseContract.BaseView {
        void onLogin(UserLoginResponse userResponse);

        void onLogout(UserResponse userResponse);

    }

    interface LoginPresenter {
        void login(String username, String password, int type);

        void logout(String accessToken);
    }
}
