package com.mjjam.attendanceapp.student;

import com.mjjam.attendanceapp.data.models.AttendanceResponse;
import com.mjjam.attendanceapp.data.repository.UserRepository;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Johns on 10/6/2017.
 */

public class StudentAttendancePresenter implements StudentAttendanceContract.StudentAttendancePresenter {

    StudentAttendanceContract.StudentAttendanceView view;
    UserRepository userRepository;

    public StudentAttendancePresenter(UserRepository userRepository, StudentAttendanceContract.StudentAttendanceView view) {
        this.view = view;
        this.userRepository = userRepository;
    }

    @Override
    public void fetchData(int rollNo, String month, String courseName) {
        // Call Server to get attendance report for Student (Monthly and CourseWise)
        userRepository.attendanceQuery(rollNo, month, courseName)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<AttendanceResponse>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        if (view != null)
                            view.onNetworkException(e);
                    }

                    @Override
                    public void onNext(AttendanceResponse attendanceResponse) {
                        if (view != null)
                            view.onResponse(attendanceResponse);
                    }

                });
    }
}
