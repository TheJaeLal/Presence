package com.mjjam.attendanceapp.data.remote;


import com.mjjam.attendanceapp.data.models.AttendanceResponse;
import com.mjjam.attendanceapp.data.models.UserLoginResponse;
import com.mjjam.attendanceapp.data.models.UserResponse;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import rx.Observable;


public interface UserRestService {

    @FormUrlEncoded
    @POST("auth/register")
    Observable<UserResponse> setUser(@Field("fcm_token") String fcm_token,
                                     @Field("fname") String fname,
                                     @Field("lname") String lname,
                                     @Field("emailid") String emailid,
                                     @Field("phoneno") String phoneno,
                                     @Field("password") String password,
                                     @Field("role") int role
    );


    @GET("maintotd_json.php")
    Observable<UserResponse> getUser();


    @FormUrlEncoded
    @POST("auth/login")
    Observable<UserLoginResponse> setLogin(@Field("username") String username, @Field("password") String password, @Field("type") int type);

    @FormUrlEncoded
    @POST("auth/logout")
    Observable<UserResponse> logout(@Field("accessToken") String accessToken);


    @FormUrlEncoded
    @POST("timetable")
    Observable<UserResponse> getTimeTable(@Field("token") String token, @Field("day") String day);

    @FormUrlEncoded
    @POST("timetable")
    Observable<UserResponse> getTimeTable(@Field("token") String token);

    @FormUrlEncoded
    @POST("attend/mark")
    Observable<UserResponse> sendData(@Field("token") String token, @Field("mark") String mark);

    @FormUrlEncoded
    @POST("attend/get")
    Observable<AttendanceResponse> attendanceQuery(@Field("roll_no") int rollNo, @Field("month") String month, @Field("course") String courseName);

    //TODO Login "auth/login" success token true false
}
