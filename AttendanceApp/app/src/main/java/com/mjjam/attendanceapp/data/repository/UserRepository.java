package com.mjjam.attendanceapp.data.repository;

import com.mjjam.attendanceapp.data.models.UserResponse;

import rx.Observable;

/**
 * Created by Archish on 1/14/2017.
 */

public interface UserRepository {
    Observable<UserResponse> setUser(String fcm_token,
                                     String fname,
                                     String lname,
                                     String emailid,
                                     String phoneno,
                                     String password,
                                     int role);


    Observable<UserResponse> getUser();


    Observable<UserResponse> setLogin(String fcm_token, String phoneno, String password);
    Observable<UserResponse> logout(String accessToken);


}
