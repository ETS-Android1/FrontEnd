package com.example.webService;

import android.content.SharedPreferences;

import com.example.entity.Task;
import com.example.entity.User;
import com.google.gson.JsonObject;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.HTTP;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;

public interface TaskAPI {
    public static final String BASE_URL="https://shanbe-back.herokuapp.com/";
    @Headers({"Accept: application/json",
    "Content-Type: application/json"
    })

    @POST("task-create/")
    Call<TaskSession> createTask(@Header("Authorization") String user_token, @Body Task task);


    @POST("task-get-day/")
    Call<List<Task>> getTasksDay(@Header("Authorization") String user_token, @Body JsonObject jsonObject);

    @PUT("task-edit/")
    Call<JsonObject> editTask(@Header("Authorization") String user_token,@Body Task task);

    @PUT("task-finish/")
    Call<JsonObject> finishTask(@Header("Authorization") String user_token, @Body JsonObject jsonObject);

    @HTTP(method = "DELETE", path = "task-delete/", hasBody = true)
    Call<JsonObject> deleteTask(@Header("Authorization") String user_token, @Body JsonObject jsonObject);
}
