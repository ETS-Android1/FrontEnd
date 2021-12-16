package com.example;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.adapter.myEventsAdapter;
import com.example.entity.Event;
import com.example.myapplication.R;
import com.example.webService.EventAPI;
import com.example.webService.TaskAPI;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class my_created_events extends AppCompatActivity {

        EventAPI eventAPI;
        EditText searchEt;
        String userToken;
        ListView myEventsList;
        myEventsAdapter myEventsAdap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_my_created_events);
        init();
        fillList();
    }

    public void init()
    {
        searchEt = findViewById(R.id.searchEventEditText);
        myEventsList = findViewById(R.id.eventsList);

        SharedPreferences sharedPreferences = getSharedPreferences("authentication",MODE_PRIVATE);
        userToken = sharedPreferences.getString("token","");


        Retrofit myEvents = new Retrofit.Builder()
                .baseUrl(EventAPI.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        eventAPI = myEvents.create(EventAPI.class);


        searchEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                myEventsAdap.getFilter().filter(charSequence);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    public void fillList()
    {
        Call<List<Event>> callBack = eventAPI.event_created_get("token "+userToken);
        callBack.enqueue(new Callback<List<Event>>() {
            @Override
            public void onResponse(Call<List<Event>> call, Response<List<Event>> response) {
                if(!response.isSuccessful())
                {
                    CustomErrorAlertDialog myEvents = new CustomErrorAlertDialog(my_created_events.this,"Error","There is a problem with your internet connection");
                }
                else{
                    int responseCode = response.code();
                   // Toast.makeText(my_created_events.this, Integer.toString(responseCode), Toast.LENGTH_SHORT).show();
                    List<Event> myEvents = response.body();
                    myEventsAdap = new myEventsAdapter(my_created_events.this,myEvents);
                    myEventsList.setAdapter(myEventsAdap);
                }
            }

            @Override
            public void onFailure(Call<List<Event>> call, Throwable t) {
                CustomErrorAlertDialog myEvents = new CustomErrorAlertDialog(my_created_events.this,"Error","There is a problem with your internet connection");
            }
        });
    }

    public void goToAddEvent(View view) {
        Intent intent = new Intent(my_created_events.this,AddEvent.class);
        startActivity(intent);
        finish();
    }
}