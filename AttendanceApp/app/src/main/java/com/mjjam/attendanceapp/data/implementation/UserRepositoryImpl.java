package com.mjjam.attendanceapp.data.implementation;


import com.mjjam.attendanceapp.data.models.UserResponse;
import com.mjjam.attendanceapp.data.remote.UserRestService;
import com.mjjam.attendanceapp.data.repository.UserRepository;

import rx.Observable;

public class UserRepositoryImpl implements UserRepository {

    private UserRestService userRestService;

    public UserRepositoryImpl(UserRestService userRestService) {
        this.userRestService = userRestService;
    }


    @Override
    public Observable<UserResponse> setUser(String fcm_token, String fname, String lname, String emailid, String phoneno, String password, int role) {
        return userRestService.setUser(fcm_token, fname, lname, emailid, phoneno, password, role);
    }


    @Override
    public Observable<UserResponse> getUser() {
        return userRestService.getUser();
    }





    @Override
    public Observable<UserResponse> setLogin(String fcm_token, String phoneno, String password) {
        return userRestService.setLogin(fcm_token, phoneno, password);
    }

    @Override
    public Observable<UserResponse> logout(String accessToken) {
        return userRestService.logout(accessToken);
    }


}
