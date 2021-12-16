package com.example;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.adapter.SessionJoinAdapter;
import com.example.entity.Event;
import com.example.myapplication.R;
import com.example.webService.EventAPI;
import com.example.webService.TaskAPI;
import com.google.gson.JsonObject;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class JoinPrivateEvent extends AppCompatActivity {
    Button joinButton;
    EditText eventTokenEditText;
    EventAPI eventAPI;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join_private_event);
        joinButton = findViewById(R.id.PrivateEventJoinButton);
        eventTokenEditText = findViewById(R.id.EnterEventToken);
        joinButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String enteredSessionToken = eventTokenEditText.getText().toString();
                HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
                interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
                OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();

                Retrofit createTask = new Retrofit.Builder()
                        .baseUrl(TaskAPI.BASE_URL)
                        .client(client)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
                eventAPI = createTask.create(EventAPI.class);
                SharedPreferences shP = getSharedPreferences("userInformation", MODE_PRIVATE);
                String token = shP.getString("token", "");
                JsonObject body = new JsonObject();
                body.addProperty("event_token", enteredSessionToken);
                Call<Event> callBack = eventAPI.enter_event_token("token " + token, body);
                callBack.enqueue(new Callback<Event>() {
                    @Override
                    public void onResponse(Call<Event> call, Response<Event> response) {
                        if (!response.isSuccessful()) {
                          //  Toast.makeText(JoinPrivateEvent.this, "Some Field Wrong", Toast.LENGTH_SHORT).show();
                        } else {
                            String code = Integer.toString(response.code());
                            Event event = response.body();
                            String title = event.getTitle();
                            String location = event.getLocation();
                            String category = event.getCategory();
                            Log.i("","");
                            Intent eventGo = new Intent(JoinPrivateEvent.this, JoinEvent.class);
                            eventGo.putExtra("title",title);
                            eventGo.putExtra("location",location);
                            eventGo.putExtra("category",category);
                            eventGo.putExtra("token",enteredSessionToken);
                            startActivity(eventGo);
                            finish();
                        }
                    }

                    @Override
                    public void onFailure(Call<Event> call, Throwable t) {
                     //   Toast.makeText(JoinPrivateEvent.this, "error is :" + t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
}