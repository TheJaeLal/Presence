package com.mjjam.attendanceapp.auth;

import com.mjjam.attendanceapp.common.BaseContract;
import com.mjjam.attendanceapp.data.models.MainWrapper;
import com.mjjam.attendanceapp.data.models.UserLoginResponse;
import com.mjjam.attendanceapp.data.models.UserResponse;

public interface LoginContract {
    interface LoginView extends BaseContract.BaseView {
        void onLogin(UserLoginResponse userResponse);


    }
    interface MainActivityView extends BaseContract.BaseView{
        void onData(MainWrapper mainWrapper);
    }

    interface MainPresenter {
        void getTimeTable(String token);
    }
    interface LoginPresenter {
        void login(String username, String password,int type);

    }
}
