package com.mjjam.attendanceapp.timetable;

import com.mjjam.attendanceapp.common.BaseContract;
import com.mjjam.attendanceapp.data.models.UserResponse;

/**
 * Created by Archish on 10/6/2017.
 */

public interface TimeTableContracts {

    interface TimeTableView extends BaseContract.BaseView{
        void onData(UserResponse userResponse);
    }
    interface TimePresenter{
        void getTimeTable(String token,String day);
        void getTimeTable(String token);
    }
}
