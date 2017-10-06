package com.mjjam.attendanceapp.auth;

import android.util.Log;


import com.mjjam.attendanceapp.data.models.UserLoginResponse;
import com.mjjam.attendanceapp.data.models.UserResponse;
import com.mjjam.attendanceapp.data.repository.UserRepository;


import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


public class LoginPresenter implements LoginContract.LoginPresenter {

    LoginContract.LoginView view;
    UserRepository userRepository;

    public LoginPresenter(UserRepository userRepository, LoginContract.LoginView view) {
        this.view = view;
        this.userRepository = userRepository;
    }


    @Override
    public void login(String username, String password,int type) {
        userRepository
                .setLogin(username, password, type)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.newThread())
                .subscribe(new Observer<UserLoginResponse>() {
                    @Override
                    public void onCompleted() {
                        Log.d("UserMessageComplete", "Complete");

                    }

                    @Override
                    public void onError(Throwable e) {
                        if (view != null)
                            view.onNetworkException(e);

                        }

                    @Override
                    public void onNext(UserLoginResponse userLoginResponse) {
                        if (view != null)
                            view.onLogin(userLoginResponse);

                    }

                });
    }

    @Override
    public void logout(String accessToken) {
        userRepository.logout(accessToken)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<UserResponse>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        if (view != null)
                            view.onNetworkException(e);
                    }

                    @Override
                    public void onNext(UserResponse userResponse) {
                        if (view != null)
                            view.onLogout(userResponse);
                    }
                });

    }
}
