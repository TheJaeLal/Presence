package com.mjjam.attendanceapp.dashboard;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;


import com.mjjam.attendanceapp.R;
import com.mjjam.attendanceapp.common.AttendanceApp;
import com.mjjam.attendanceapp.common.BaseFragment;
import com.mjjam.attendanceapp.data.local.SharedPreferenceManager;
import com.mjjam.attendanceapp.data.repository.UserRepository;

import java.util.ArrayList;

public class FacultyHomeFragment extends BaseFragment{

    SwipeRefreshLayout swipeRefreshLayout;
    RecyclerView rvHome;

    ProgressBar pgProgress;

    @Override
    public void onNetworkException(Throwable e) {
        super.onNetworkException(e);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);


//        rvHome = (RecyclerView) view.findViewById(R.id.rvHome);
//        pgProgress = (ProgressBar) view.findViewById(R.id.pgProgress);
//        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.srlHome);
//        rvHome.setHasFixedSize(true);
//        rvHome.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext(), LinearLayoutManager.VERTICAL, false));
//        UserRepository userRepository = ((AttendanceApp) getActivity().getApplication()).getComponent().userRepository();
//        homePresenter = new HomePresenter(userRepository, this);
//        homePresenter.fetchHomeData(new SharedPreferenceManager(getActivity().getApplicationContext()).getAccessToken());
//        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
//            @Override
//            public void onRefresh() {
//                homePresenter.fetchHomeData(new SharedPreferenceManager(getActivity().getApplicationContext()).getAccessToken());
//            }
//        });

        return view;
    }


}