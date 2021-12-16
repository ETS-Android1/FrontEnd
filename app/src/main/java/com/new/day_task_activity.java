package com.example;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.BroadCast.AlarmController;
import com.example.DataBase.tasksDB;
import com.example.adapter.taskAdapter;
import com.example.entity.Task;
import com.example.myapplication.R;
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

public class day_task_activity extends AppCompatActivity {

    String userToken;
    String username;
    EditText searchEditText;
    ListView list;
    taskAdapter tasksAdap;
    TaskAPI taskAPI;
    String FinalDate;

    @Override
    protected void onResume() {
        super.onResume();
        fillList();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_day_task);
        getSupportActionBar().hide();

        init();

        //active alarms..
        AlarmController alarm = new AlarmController(day_task_activity.this);
        alarm.StartAlarm();

        String date = getIntent().getStringExtra("Date").toString();
        String [] dateInfo = date.split(" ");
        String month = dateInfo[1];
        String day = dateInfo[2];
        String year = dateInfo[5];

        String monthNum = "00";

        if(month.equals("Jan"))
        {
            monthNum = "00";
        }
        else if(month.equals("Feb"))
        {
            monthNum="01";
        }
        else if(month.equals("Mar"))
        {
            monthNum="02";
        }
        else if(month.equals("Apr"))
        {
            monthNum="03";
        }
        else if(month.equals("May"))
        {
            monthNum="04";
        }
        else if(month.equals("June"))
        {
            monthNum="05";
        }
        else if(month.equals("July"))
        {
            monthNum="06";
        }else if(month.equals("Aug"))
        {
            monthNum="07";
        }else if(month.equals("Sept"))
        {
            monthNum="08";
        }else if(month.equals("Oct"))
        {
            monthNum="09";
        }
        else if(month.equals("Nov"))
        {
            monthNum="10";
        }
        else if(month.equals("Dec"))
        {
            monthNum="11";
        }

        FinalDate = year + "-"+ monthNum +"-"+day;
        //Toast.makeText(this, FinalDate, Toast.LENGTH_SHORT).show();
        fillList();



        searchEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                tasksAdap.getFilter().filter(charSequence);
            }

            @Override
            public void afterTextChanged(Editable editable) {

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

    public void addTaskBtn(View view) {
        Intent intent = new Intent(day_task_activity.this,AddTask.class);
        intent.putExtra("date",FinalDate);
        startActivity(intent);
    }


    public void fillList()
    {
//        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
//        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
//        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();
//
//        Retrofit createTask = new Retrofit.Builder()
//                .baseUrl(TaskAPI.BASE_URL)
//                .client(client)
//                .addConverterFactory(GsonConverterFactory.create())
//                .build();
//        taskAPI = createTask.create(TaskAPI.class);


        //todo
//        JsonObject jsonObject = new JsonObject();
//        jsonObject.addProperty("task_token", userToken);
//        jsonObject.addProperty("alarm_check", FinalDate);
//        Call<List<Task>> request = taskAPI.getTasksDay("token "+userToken,jsonObject);
//
//        request.enqueue(new Callback<List<Task>>() {
//            @Override
//            public void onResponse(Call<List<Task>> call, Response<List<Task>> response) {
//                if(response.isSuccessful()==false)
//                {
//                    CustomeAlertDialog getTasksDayError = new CustomeAlertDialog(day_task_activity.this,"Error","there is a problem with your internet connection");
//                }
//                else{
//                    int responseCode = response.code();
//                    //Toast.makeText(day_task_activity.this, Integer.toString(responseCode), Toast.LENGTH_SHORT).show();
//                    List<Task> listOfTasks = response.body();
//                    tasksAdap = new taskAdapter(day_task_activity.this,listOfTasks);
//                    list.setAdapter(tasksAdap);
//
//                }
//            }
//
//            @Override
//            public void onFailure(Call<List<Task>> call, Throwable t) {
//                CustomeAlertDialog getTasksDayError = new CustomeAlertDialog(day_task_activity.this,"Error","there is a problem with your internet connection");
//            }
//        });

        //offline part ....
        tasksDB tasksdb = new tasksDB(day_task_activity.this);
        List<Task> listOfTasks = tasksdb.select(FinalDate);
        tasksAdap = new taskAdapter(day_task_activity.this,listOfTasks);
        list.setAdapter(tasksAdap);
    }
}