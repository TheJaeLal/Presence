package com.mjjam.attendanceapp.data.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Created by Archish on 10/6/2017.
 */

public class UserLoginResponse implements Parcelable {
    boolean status;
    String message;
    String accessToken;
    int category;

    FacultyProfile facultyProfile;
    StudentProfile studentProfile;
    ArrayList<String> courseList;

    public ArrayList<String> getCourseList() {
        return courseList;
    }

    public void setCourseList(ArrayList<String> courseList) {
        this.courseList = courseList;
    }

    public int getCategory() {
        return category;
    }

    public void setCategory(int category) {
        this.category = category;
    }

    public FacultyProfile getFacultyProfile() {
        return facultyProfile;
    }

    public void setFacultyProfile(FacultyProfile facultyProfile) {
        this.facultyProfile = facultyProfile;
    }

    public StudentProfile getStudentProfile() {
        return studentProfile;
    }

    public void setStudentProfile(StudentProfile studentProfile) {
        this.studentProfile = studentProfile;
    }


    protected UserLoginResponse(Parcel in) {
        status = in.readByte() != 0;
        message = in.readString();
        accessToken = in.readString();
    }

    public static final Creator<UserLoginResponse> CREATOR = new Creator<UserLoginResponse>() {
        @Override
        public UserLoginResponse createFromParcel(Parcel in) {
            return new UserLoginResponse(in);
        }

        @Override
        public UserLoginResponse[] newArray(int size) {
            return new UserLoginResponse[size];
        }
    };

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public UserLoginResponse(boolean status, String message, String accessToken) {

        this.status = status;
        this.message = message;
        this.accessToken = accessToken;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeByte((byte) (status ? 1 : 0));
        dest.writeString(message);
        dest.writeString(accessToken);
    }
}
