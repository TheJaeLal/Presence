package com.mjjam.attendanceapp.helper;

public interface StudentTable {
    String NAME = "studenttable";
    String SID = "sid";
    String S_ID = "s_id";
    String S_NAME = "s_name";
    String S_ROLLNO = "s_rollno";
    String[] PROJECTION = new String[]{SID,S_ID, S_NAME, S_ROLLNO};

    String CREATE = "CREATE TABLE " + NAME + " ( "
            + SID + " INTEGER AUTOINCREMENT, "
            + S_ID + " INTEGER, "
            + S_NAME + " TEXT, "
            + S_ROLLNO + " TEXT, " +
            " PRIMARY KEY ( " + SID + " ) );";
}

