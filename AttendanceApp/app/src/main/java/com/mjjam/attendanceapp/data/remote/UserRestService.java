package com.mjjam.attendanceapp.data.remote;


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
    Observable<UserResponse> setLogin(@Field("fcm_token") String fcm_token, @Field("phoneno") String phoneno, @Field("password") String password);

    @FormUrlEncoded
    @POST("auth/logout")
    Observable<UserResponse> logout(@Field("accessToken") String accessToken);


    //TODO Login "auth/login" succes token true false
}
