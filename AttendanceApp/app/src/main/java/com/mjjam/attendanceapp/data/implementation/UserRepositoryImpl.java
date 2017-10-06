package com.mjjam.attendanceapp.data.implementation;


import com.mjjam.attendanceapp.data.models.UserLoginResponse;
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
    public Observable<UserResponse> getUser() {
        return userRestService.getUser();
    }

    @Override
    public Observable<UserLoginResponse> setLogin(String username, String password, int type) {
        return userRestService.setLogin(username,password,type);
    }



    @Override
    public Observable<UserResponse> logout(String accessToken) {
        return userRestService.logout(accessToken);
    }


}
