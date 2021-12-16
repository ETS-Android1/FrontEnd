package com.example;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.entity.Event;
import com.example.myapplication.R;
import com.example.webService.EventAPI;
import com.example.webService.TaskAPI;
import com.google.gson.JsonObject;

import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class FilterEvents extends AppCompatActivity {
    Spinner category;
    Spinner locationfilter;
    DatePicker datefilter;
    Button filterBtn;
    String userToken;
    EventAPI eventAPI;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.filters_evant);
        getSupportActionBar().hide();

        init();
    }


    public void init()
    {
        category = findViewById(R.id.categoryfilter);
        locationfilter = findViewById(R.id.locationfilter);
        datefilter = findViewById(R.id.datefilter);
        filterBtn = findViewById(R.id.filterBtn);

        SharedPreferences sharedPreferences = getSharedPreferences("authentication", MODE_PRIVATE);
        userToken = sharedPreferences.getString("token", "");

        datefilter.init(2000,1,1,null);
    }

    public void filter(View view) {
        String categoryStr = category.getSelectedItem().toString();
        String locationStr = locationfilter.getSelectedItem().toString();
//        if(categoryStr.equals("category"))
//        {
//            categoryStr = "";
//        }

//        if(locationStr.equals("location"))
//        {
//            locationStr="";
//        }

        int year = datefilter.getYear();
        int month = datefilter.getMonth();
        int day = datefilter.getDayOfMonth();

        String yearStr = Integer.toString(year);
        String monthStr = Integer.toString(month);
        String dayStr = Integer.toString(day);

        if(monthStr.length() == 1)
        {
            monthStr = "0"+monthStr;
        }

        if(dayStr.length() ==1)
        {
            dayStr = "0"+dayStr;
        }

        String date = yearStr+"-"+monthStr+"-"+dayStr;
        if(date.equals("2000-01-01"))
        {
            date = "";
        }

        Intent intent = new Intent(FilterEvents.this,event_activity.class);
        intent.putExtra("location",locationStr);
        intent.putExtra("category",categoryStr);
        intent.putExtra("s_time",date);
        startActivity(intent);
        finish();
    }
}
