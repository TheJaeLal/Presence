package com.mjjam.attendanceapp.helper;

public interface TimeTable {
    String NAME = "timetable";
    String AID = "aid";
    String A_ID = "a_id";
    String A_DAY = "a_day";
    String A_START_TIME = "a_start_time";
    String A_END_TIME = "a_end_time";
    String A_NAME = "a_name";
    String[] PROJECTION = new String[]{A_ID, A_DAY, A_START_TIME,A_END_TIME,A_NAME};

    String CREATE = "CREATE TABLE " + NAME + " ( "
            + AID + " INTEGER AUTO INCREMENT, "
            + A_ID + " INTEGER, "
            + A_DAY + " TEXT, "
            + A_START_TIME + " TEXT, "
            + A_END_TIME + " TEXT, "
            + A_NAME + " TEXT, " +
            " PRIMARY KEY ( " + A_ID + " ) );";
}

