package com.example.webService;

import com.example.entity.User;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface UserAPI {
    public static final String BASE_URL="https://shanbe-back.herokuapp.com/";
    @Headers({"Accept: application/json",
            "Content-Type: application/json"
    })
    // salam
    @POST("register/")
    Call<UserSession> createUser(@Header("Content-Type") String content_type, @Body User user);

    @POST("login/")
    Call<UserSession> UserLogin(@Header("Content-Type") String content_type,@Body User user);

    @POST("password-reset/")
    Call<Void> sendEmail(@Header("Content-Type") String content_type,@Body User user);

    @POST("password-reset/confirm/")
    Call<UserSession> resetPassword(@Header("Content-Type") String content_type,@Body User user);

    @POST("logout/")
    Call<UserSession> logOut(@Header("Authorization") String user_token);
}
