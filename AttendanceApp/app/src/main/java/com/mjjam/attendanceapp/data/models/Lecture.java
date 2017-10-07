package com.mjjam.attendanceapp.data.models;

/**
 * Created by Archish on 10/7/2017.
 */

public class Lecture {
    String lectureName;
    String lectureTime;

    public Lecture(String lectureName, String lectureTime) {
        this.lectureName = lectureName;
        this.lectureTime = lectureTime;
    }

    public String getLectureName() {
        return lectureName;
    }

    public void setLectureName(String lectureName) {
        this.lectureName = lectureName;
    }

    public String getLectureTime() {
        return lectureTime;
    }

    public void setLectureTime(String lectureTime) {
        this.lectureTime = lectureTime;
    }
}
