package com.mjjam.attendanceapp.data.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Created by Archish on 10/6/2017.
 */

public class UserLoginResponse implements Parcelable {
    boolean success;
    String message;
    String token;
    int type;
    int rollno;
    String firstname;
    String username;
    String lastname;
    ArrayList<String> courses;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public ArrayList<String> getCourses() {
        return courses;
    }

    public void setCourses(ArrayList<String> courses) {
        this.courses = courses;
    }

    public int getRollno() {
        return rollno;
    }

    public void setRollno(int rollno) {
        this.rollno = rollno;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public static Creator<UserLoginResponse> getCREATOR() {
        return CREATOR;
    }

    protected UserLoginResponse(Parcel in) {
        success = in.readByte() != 0;
        message = in.readString();
        token = in.readString();
        type = in.readInt();
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

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public UserLoginResponse(boolean success, String message, String token) {

        this.success = success;
        this.message = message;
        this.token = token;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeByte((byte) (success ? 1 : 0));
        dest.writeString(message);
        dest.writeString(token);
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
