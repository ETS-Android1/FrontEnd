package com.example;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.entity.Session;
import com.example.myapplication.R;
import com.example.webService.EventAPI;
import com.google.gson.JsonObject;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class reserverd_session extends AppCompatActivity {

    TextView title,category , location , desc , date , time , limit;
    String titleStr,categoryStr , locationStr , descStr , dateStr , timeStr , limitStr , dateTimeStr ,sessionTokenStr;
    Button delete;
    String userToken;
    EventAPI eventAPI;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_reserverd_session);

        init();
    }

    public void init()
    {
        titleStr = getIntent().getStringExtra("title");
        categoryStr = getIntent().getStringExtra("category");
        locationStr = getIntent().getStringExtra("location");
        descStr = getIntent().getStringExtra("desc");
        dateTimeStr = getIntent().getStringExtra("datetime");
        sessionTokenStr= getIntent().getStringExtra("session_token");
        limitStr = getIntent().getStringExtra("limit");

        String [] dateTimeInfo = dateTimeStr.split("_");
        dateStr = dateTimeInfo[0];
        timeStr = dateTimeInfo[1];

        title = findViewById(R.id.titleevent);
        category = findViewById(R.id.categoryevent);
        location = findViewById(R.id.locationevent);
        desc = findViewById(R.id.descript);
        date = findViewById(R.id.dateJoin);
        time = findViewById(R.id.timeJoin);
        limit = findViewById(R.id.limitJoin);
        delete = findViewById(R.id.delete);

        SharedPreferences sharedPreferences = getSharedPreferences("authentication", Context.MODE_PRIVATE);
        userToken = sharedPreferences.getString("token", "");

        fill();
    }

    public void fill()
    {
        title.setText(titleStr);
        category.setText(categoryStr);
        location.setText(locationStr);
        desc.setText(descStr);
        date.setText(dateStr);
        time.setText(timeStr);
        limit.setText(limitStr);
    }


    public void session_cancel(View view) {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();

        Retrofit cancelSession = new Retrofit.Builder()
                .baseUrl(EventAPI.BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        eventAPI = cancelSession.create(EventAPI.class);
        JsonObject sessionToken = new JsonObject();
        sessionToken.addProperty("session_token",sessionTokenStr);
        Call<JsonObject> callBack = eventAPI.session_cancel("token "+userToken,sessionToken);
        callBack.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if(!response.isSuccessful())
                {
                    CustomErrorAlertDialog getTasksDayError = new CustomErrorAlertDialog(reserverd_session.this,"Error","there is a problem with your internet connection");
                }
                else{
                   // Toast.makeText(reserverd_session.this, Integer.toString(response.code()), Toast.LENGTH_SHORT).show();
                    CustomSuccessAlertDialog getTasksDayError = new CustomSuccessAlertDialog(reserverd_session.this,"Successful","session canceled");
                    getTasksDayError.btnOk.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            finish();
                        }
                    });
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                CustomErrorAlertDialog getTasksDayError = new CustomErrorAlertDialog(reserverd_session.this,"Error","there is a problem with your internet connection");
            }
        });
    }
}