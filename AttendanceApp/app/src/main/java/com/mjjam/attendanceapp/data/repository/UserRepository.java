package com.mjjam.attendanceapp.data.repository;

import com.mjjam.attendanceapp.data.models.AttendanceResponse;
import com.mjjam.attendanceapp.data.models.UserLoginResponse;
import com.mjjam.attendanceapp.data.models.UserResponse;

import retrofit2.http.Field;
import rx.Observable;

/**
 * Created by Archish on 1/14/2017.
 */

public interface UserRepository {


    Observable<UserResponse> getUser();


    Observable<UserLoginResponse> setLogin(String username, String password, int type);

    Observable<UserResponse> logout(String accessToken);
    Observable<UserResponse> getTimeTable(String token);
    Observable<UserResponse> getTimeTable(String token,String day);
    Observable<UserResponse> sendData(String token,String mark);
    Observable<AttendanceResponse> attendanceQuery(int rollNo, String month, String courseName);


}
