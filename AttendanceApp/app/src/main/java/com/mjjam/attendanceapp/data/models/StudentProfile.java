package com.mjjam.attendanceapp.data.models;

/**
 * Created by Johns on 10/6/2017.
 */

public class StudentProfile {

    private int roll_no;
    private UserProfile user;
    private char div;
    private String contact_no;

    public int getRoll_no() {
        return roll_no;
    }

    public void setRoll_no(int roll_no) {
        this.roll_no = roll_no;
    }

    public UserProfile getUser() {
        return user;
    }

    public void setUser(UserProfile user) {
        this.user = user;
    }

    public char getDiv() {
        return div;
    }

    public void setDiv(char div) {
        this.div = div;
    }

    public String getContact_no() {
        return contact_no;
    }

    public void setContact_no(String contact_no) {
        this.contact_no = contact_no;
    }


}
