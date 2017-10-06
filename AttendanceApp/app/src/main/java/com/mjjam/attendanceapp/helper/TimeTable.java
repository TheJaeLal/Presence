package com.mjjam.attendanceapp.helper;

public interface TimeTable {
    String NAME = "timetable";
    String A_ID = "a_id";
    String A_DAY = "a_day";
    String A_TIME = "a_time";
    String[] PROJECTION = new String[]{A_ID, A_DAY, A_TIME};

    String CREATE = "CREATE TABLE " + NAME + " ( "
            + A_ID + " INTEGER, "
            + A_DAY + " TEXT, "
            + A_TIME + " TEXT, " +
            " PRIMARY KEY ( " + A_ID + " ) );";
}

