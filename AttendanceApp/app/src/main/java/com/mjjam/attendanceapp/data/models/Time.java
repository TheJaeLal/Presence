package com.mjjam.attendanceapp.data.models;

/**
 * Created by Archish on 10/7/2017.
 */

public class Time {
    int tid;
    String course;
    String start_time;
    String end_time;
    String day;

    public String getStart_time() {
        return start_time;
    }

    public void setStart_time(String start_time) {
        this.start_time = start_time;
    }

    public String getEnd_time() {
        return end_time;
    }

    public void setEnd_time(String end_time) {
        this.end_time = end_time;
    }

    public int getTid() {
        return tid;
    }

    public void setTid(int tid) {
        this.tid = tid;
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }


    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }
}
