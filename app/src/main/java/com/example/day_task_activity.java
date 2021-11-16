package com.example;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.adapter.taskAdapter;
import com.example.entity.Task;
import com.example.myapplication.R;
import com.example.webService.TaskAPI;

import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class day_task_activity extends AppCompatActivity {

    String userToken;
    String username;
    EditText searchEditText;
    RecyclerView list;
    taskAdapter tasksAdap;
    TaskAPI taskAPI;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_day_task);

        init();

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();

        Retrofit createTask = new Retrofit.Builder()
                .baseUrl(TaskAPI.BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        taskAPI = createTask.create(TaskAPI.class);


        //todo
        Call<List<Task>> request = taskAPI.getTasksDay("token "+userToken,userToken,"2021-10-14");

        request.enqueue(new Callback<List<Task>>() {
            @Override
            public void onResponse(Call<List<Task>> call, Response<List<Task>> response) {
                if(response.isSuccessful()==false)
                {
                    CustomeAlertDialog getTasksDayError = new CustomeAlertDialog(day_task_activity.this,"Error","there is a problem with your internet connection");
                }
                else{
                    int responseCode = response.code();
                    Toast.makeText(day_task_activity.this, responseCode, Toast.LENGTH_SHORT).show();
                    List<Task> listOfTasks = response.body();
                    tasksAdap = new taskAdapter(day_task_activity.this,listOfTasks);
                }
            }

            @Override
            public void onFailure(Call<List<Task>> call, Throwable t) {
                CustomeAlertDialog getTasksDayError = new CustomeAlertDialog(day_task_activity.this,"Error","there is a problem with your internet connection");
            }
        });
    }


    void init()
    {
        searchEditText = findViewById(R.id.searchEditText);
        list = findViewById(R.id.tasksLists);
        SharedPreferences sharedPreferences = getSharedPreferences("authentication", MODE_PRIVATE);
        userToken = sharedPreferences.getString("token", "");
        username = sharedPreferences.getString("username","");
    }
}