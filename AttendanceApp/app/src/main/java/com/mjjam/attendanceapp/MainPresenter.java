package com.mjjam.attendanceapp;

import android.util.Log;

import com.mjjam.attendanceapp.auth.LoginContract;
import com.mjjam.attendanceapp.data.models.UserLoginResponse;
import com.mjjam.attendanceapp.data.repository.UserRepository;
import com.mjjam.attendanceapp.helper.TimeTable;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


public class MainPresenter implements LoginContract.MainPresenter {

    LoginContract.LoginView view;
    UserRepository userRepository;

    public MainPresenter(UserRepository userRepository, LoginContract.LoginView view) {
        this.view = view;
        this.userRepository = userRepository;
    }



    @Override
    public void getTimeTable(String token) {
//        userRepository
//                .getTimeTableData(token)
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribeOn(Schedulers.newThread())
//                .subscribe(new Observer<MTimeTable>() {
//                    @Override
//                    public void onCompleted() {
//                        Log.d("UserMessageComplete", "Complete");
//
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//                        if (view != null)
//                            view.onNetworkException(e);
//
//                    }
//
//                    @Override
//                    public void onNext(UserLoginResponse userLoginResponse) {
//                        if (view != null)
//                            view.onLogin(userLoginResponse);
//
//                    }
//
//                });
    }
}
