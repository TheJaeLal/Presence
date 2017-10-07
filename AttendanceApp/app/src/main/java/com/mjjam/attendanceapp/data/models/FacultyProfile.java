package com.mjjam.attendanceapp.data.models;

/**
 * Created by Johns on 10/6/2017.
 */

public class FacultyProfile {
    private UserProfile user;
    private String contact_no;

    public UserProfile getUser() {
        return user;
    }

    public void setUser(UserProfile user) {
        this.user = user;
    }

    public String getContact_no() {
        return contact_no;
    }

    public void setContact_no(String contact_no) {
        this.contact_no = contact_no;
    }
}
