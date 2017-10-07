package com.mjjam.attendanceapp.data.implementation;


import com.mjjam.attendanceapp.data.models.AttendanceResponse;
import com.mjjam.attendanceapp.data.models.UserLoginResponse;
import com.mjjam.attendanceapp.data.models.UserResponse;
import com.mjjam.attendanceapp.data.remote.UserRestService;
import com.mjjam.attendanceapp.data.repository.UserRepository;

import okhttp3.ResponseBody;
import retrofit2.Response;
import rx.Observable;

public class UserRepositoryImpl implements UserRepository {

    private UserRestService userRestService;

    public UserRepositoryImpl(UserRestService userRestService) {
        this.userRestService = userRestService;
    }


    @Override
    public Observable<UserResponse> getUser() {
        return userRestService.getUser();
    }

    @Override
    public Observable<UserLoginResponse> setLogin(String username, String password, int type) {
        return userRestService.setLogin(username, password, type);
    }



    @Override
    public Observable<Response<ResponseBody>> getTimeTable(String token) {
        return userRestService.getTimeTable(token);
    }

    @Override
    public Observable<UserResponse> getTimeTableData(String token) {
        return userRestService.getTimeTableData(token);
    }

    @Override
    public Observable<Response<ResponseBody>> getTimeTable(String token, String day) {
        return userRestService.getTimeTable(token, day);
    }

    @Override
    public Observable<UserResponse> sendData(String token, String mark) {
        return userRestService.sendData(token, mark);
    }

    @Override
    public Observable<AttendanceResponse> attendanceQuery(int rollNo, String month, String courseName) {
        return userRestService.attendanceQuery(rollNo, month, courseName);
    }


}
