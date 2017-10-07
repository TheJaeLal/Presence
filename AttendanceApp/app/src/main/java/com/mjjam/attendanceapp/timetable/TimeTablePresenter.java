package com.mjjam.attendanceapp.timetable;

import android.util.Log;

import com.mjjam.attendanceapp.auth.LoginContract;
import com.mjjam.attendanceapp.data.models.UserLoginResponse;
import com.mjjam.attendanceapp.data.models.UserResponse;
import com.mjjam.attendanceapp.data.repository.UserRepository;
import com.mjjam.attendanceapp.timetable.TimeTableContracts;

import okhttp3.ResponseBody;
import retrofit2.Response;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Archish on 10/6/2017.
 */

public class TimeTablePresenter implements TimeTableContracts.TimePresenter {
    TimeTableContracts.TimeTableView view;
    UserRepository userRepository;

    public TimeTablePresenter(TimeTableContracts.TimeTableView view, UserRepository userRepository) {
        this.view = view;
        this.userRepository = userRepository;
    }

    @Override
    public void getTimeTable(String token, String day) {
        userRepository
                .getTimeTable(token, day)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.newThread())
                .subscribe(new Observer<Response<ResponseBody>>() {
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
                    public void onNext(Response<ResponseBody> responseBodyResponse) {
                        if (view != null)
                            view.onData(responseBodyResponse);

                    }
                });


    }

    @Override
    public void getTimeTable(String token) {
        userRepository
                .getTimeTable(token)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.newThread())
                .subscribe(new Observer<Response<ResponseBody>>() {
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
                    public void onNext(Response<ResponseBody> responseBodyResponse) {
                        if (view != null)
                            view.onData(responseBodyResponse);

                    }
                });

    }
}
