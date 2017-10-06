package com.mjjam.attendanceapp.data.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Archish on 10/6/2017.
 */

public class MTimeTable implements Parcelable {
    int tid;
    int t_id;
    String tday;
    String ttime;

    public MTimeTable(int t_id, int tid, String tday, String ttime) {
        this.t_id = t_id;
        this.tid = tid;
        this.tday = tday;
        this.ttime = ttime;
    }

    protected MTimeTable(Parcel in) {
        t_id = in.readInt();
        tid = in.readInt();
        tday = in.readString();
        ttime = in.readString();
    }

    public static final Creator<MTimeTable> CREATOR = new Creator<MTimeTable>() {
        @Override
        public MTimeTable createFromParcel(Parcel in) {
            return new MTimeTable(in);
        }

        @Override
        public MTimeTable[] newArray(int size) {
            return new MTimeTable[size];
        }
    };

    public int getTid() {
        return tid;
    }

    public void setTid(int tid) {
        this.tid = tid;
    }

    public String getTday() {
        return tday;
    }

    public void setTday(String tday) {
        this.tday = tday;
    }

    public String getTtime() {
        return ttime;
    }

    public void setTtime(String ttime) {
        this.ttime = ttime;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(t_id);
        dest.writeInt(tid);
        dest.writeString(tday);
        dest.writeString(ttime);
    }
}
