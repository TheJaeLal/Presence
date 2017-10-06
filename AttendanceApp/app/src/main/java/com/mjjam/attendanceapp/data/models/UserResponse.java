package com.mjjam.attendanceapp.data.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Archish on 10/6/2017.
 */

public class UserResponse  implements Parcelable{
    boolean status;
    String message;

    public UserResponse(boolean status, String message) {
        this.status = status;
        this.message = message;
    }

    protected UserResponse(Parcel in) {
        status = in.readByte() != 0;
        message = in.readString();
    }

    public static final Creator<UserResponse> CREATOR = new Creator<UserResponse>() {
        @Override
        public UserResponse createFromParcel(Parcel in) {
            return new UserResponse(in);
        }

        @Override
        public UserResponse[] newArray(int size) {
            return new UserResponse[size];
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeByte((byte) (status ? 1 : 0));
        dest.writeString(message);
    }
}
