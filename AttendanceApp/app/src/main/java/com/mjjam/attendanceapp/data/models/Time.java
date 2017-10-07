package com.mjjam.attendanceapp.data.models;

/**
 * Created by Archish on 10/7/2017.
 */

public class Time {
    int tid;
    String lecturename;
    String lecturetime;
    String day;

    public int getTid() {
        return tid;
    }

    public void setTid(int tid) {
        this.tid = tid;
    }

    public String getLecturename() {
        return lecturename;
    }

    public void setLecturename(String lecturename) {
        this.lecturename = lecturename;
    }

    public String getLecturetime() {
        return lecturetime;
    }

    public void setLecturetime(String lecturetime) {
        this.lecturetime = lecturetime;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }
}
