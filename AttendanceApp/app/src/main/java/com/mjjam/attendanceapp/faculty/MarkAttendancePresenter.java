package com.mjjam.attendanceapp.faculty;

import android.util.Log;

import com.mjjam.attendanceapp.auth.LoginContract;
import com.mjjam.attendanceapp.data.models.UserLoginResponse;
import com.mjjam.attendanceapp.data.models.UserResponse;
import com.mjjam.attendanceapp.data.repository.UserRepository;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


public class MarkAttendancePresenter implements MarkAttendanceContract.MarkPresenter {

    MarkAttendanceContract.MarkView view;
    UserRepository userRepository;

    public MarkAttendancePresenter(UserRepository userRepository, MarkAttendanceContract.MarkView view) {
        this.view = view;
        this.userRepository = userRepository;
    }


    @Override
    public void sendData(String token, String mark) {
        userRepository.sendData(token,mark)
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
                            view.onPush(userResponse);
                    }
                });

    }
}
