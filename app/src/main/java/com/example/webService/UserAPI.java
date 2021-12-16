package com.example.webService;

import com.example.entity.User;

import org.json.JSONObject;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;

public interface UserAPI {
    public static final String BASE_URL="https://shanbe-back.herokuapp.com/";
    @Headers({"Accept: application/json",
            "Content-Type: application/json"
    })

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

    @POST("user/")
    Call<User> showProfile(@Header("Authorization") String user_token);

    @PUT("edit-profile/")
    Call<User> editProfile(@Header("Authorization") String user_token , @Body User user);
    @Multipart
    @PUT("edit-profile/")
    Call<User> editProfile(@Header("Authorization") String user_token , @Part("email")RequestBody email,
                           @Part("first_name")RequestBody firstName,
                           @Part("last_name")RequestBody lastName,
                           @Part("phone_number")RequestBody phoneNumber,
                           @Part MultipartBody.Part image);
}
