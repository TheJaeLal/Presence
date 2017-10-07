package com.mjjam.attendanceapp.data.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Johns on 10/7/2017.
 */

public class AttendanceResponse implements Parcelable {

    boolean success;
    int overall_apc;
    int course_apc;

    public int getOverall_apc() {
        return overall_apc;
    }

    public void setOverall_apc(int overall_apc) {
        this.overall_apc = overall_apc;
    }

    public int getCourse_apc() {
        return course_apc;
    }

    public void setCourse_apc(int course_apc) {
        this.course_apc = course_apc;
    }

    public AttendanceResponse(int overall_apc, int course_apc) {
        this.overall_apc = overall_apc;
        this.course_apc = course_apc;
    }

    protected AttendanceResponse(Parcel in) {
        overall_apc = in.readInt();
        course_apc = in.readInt();
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public static final Creator<AttendanceResponse> CREATOR = new Creator<AttendanceResponse>() {
        @Override
        public AttendanceResponse createFromParcel(Parcel in) {
            return new AttendanceResponse(in);
        }

        @Override
        public AttendanceResponse[] newArray(int size) {
            return new AttendanceResponse[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(overall_apc);
        dest.writeInt(course_apc);
    }
}
