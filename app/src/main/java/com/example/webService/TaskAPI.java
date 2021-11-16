package com.example.webService;

import android.content.SharedPreferences;

import com.example.entity.Task;
import com.example.entity.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface TaskAPI {
    public static final String BASE_URL="https://shanbe-back.herokuapp.com/";
    @Headers({"Accept: application/json",
    "Content-Type: application/json"
    })

    @POST("task-create/")
    Call<TaskSession> createTask(@Header("Authorization") String user_token, @Body Task task);

    @POST("task-get-day/")
    Call<List<Task>> getTasksDay(@Header("Authorization") String user_token, @Body String userToken, @Body String dateTime);
}
