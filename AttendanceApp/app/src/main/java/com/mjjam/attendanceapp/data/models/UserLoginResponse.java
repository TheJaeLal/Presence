package com.mjjam.attendanceapp.data.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Archish on 10/6/2017.
 */

public class UserLoginResponse implements Parcelable {
    boolean status;
    String message;
    String accessToken;
    FacultyProfile facultyProfile;
    StudentProfile studentProfile;

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

    }
}
