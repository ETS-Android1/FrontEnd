package com.example;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.format.Time;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.adapter.SessionAdapter;
import com.example.adapter.SessionJoinAdapter;
import com.example.entity.Event;
import com.example.entity.Session;
import com.example.myapplication.R;
import com.example.webService.EventAPI;
import com.example.webService.TaskAPI;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class EditEventActivity extends AppCompatActivity {
    Spinner category;
    EditText title;
    Spinner privacy;
    Spinner location;
    EditText description;
    Button addEvent;

    EventAPI eventAPI;
    String eventToken;
    Event currentEvent;

    String titleStr;
    String categoryStr;
    String locationStr;
    String privacyStr;
    String descriptionStr;
    String userToken;

    ConstraintLayout titleSpace;
    ConstraintLayout categorySpace;
    ConstraintLayout privacySpace;
    ConstraintLayout locationSpace;
    ConstraintLayout descriptionSpace;

    ListView sessionsList;

    List<Session> mySessions;
    List<Session> addedSessions;

    SessionAdapter sessionAdap;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_event);
        getSupportActionBar().hide();
        init();
        load();
    }

    public void init()
    {
        title = findViewById(R.id.titleevent);
        category = findViewById(R.id.categoryevent);
        location = findViewById(R.id.locationevent);
        privacy = findViewById(R.id.privacyevent);
        description = findViewById(R.id.descript);
        addEvent = findViewById(R.id.addEventButton);
        sessionsList = findViewById(R.id.sessionsListView);

        titleSpace = findViewById(R.id.titleSpace);
        categorySpace = findViewById(R.id.categorySpace);
        privacySpace = findViewById(R.id.privacylayout);
        locationSpace = findViewById(R.id.locationSpace);
        descriptionSpace = findViewById(R.id.descriptionSpace);

        mySessions = new ArrayList<Session>();
        addedSessions = new ArrayList<Session>();


        SharedPreferences sharedPreferences = getSharedPreferences("authentication", MODE_PRIVATE);
        userToken = sharedPreferences.getString("token", "");

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();

        Retrofit EditEvent = new Retrofit.Builder()
                .baseUrl(TaskAPI.BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        eventAPI = EditEvent.create(EventAPI.class);

        eventToken = getIntent().getStringExtra("token");
    }

    public void AddEvent(View view) {
        titleStr = title.getText().toString();
        categoryStr = category.getSelectedItem().toString();
        locationStr = location.getSelectedItem().toString();
        privacyStr = privacy.getSelectedItem().toString();
        descriptionStr = description.getText().toString();

        boolean valid = true;
        if(titleStr.equals(""))
        {
            CustomErrorAlertDialog titleEmpyt = new CustomErrorAlertDialog(this,"Error","please fill the title field");
            titleSpace.setBackgroundResource(R.drawable.border_event_error);
            valid = false;
        }
        if(categoryStr.equals(""))
        {
            CustomErrorAlertDialog categortyEmpyt = new CustomErrorAlertDialog(this,"Error","please fill the category field");
            categorySpace.setBackgroundResource(R.drawable.border_event_error);
            valid = false;
        }
        if(locationStr.equals(""))
        {
            CustomErrorAlertDialog locationEmpyt = new CustomErrorAlertDialog(this,"Error","please fill the location field");
            locationSpace.setBackgroundResource(R.drawable.border_event_error);
            valid = false;
        }
        if(privacyStr.equals(""))
        {
            CustomErrorAlertDialog privacyEmpyt = new CustomErrorAlertDialog(this,"Error","please fill the privacy field");
            privacySpace.setBackgroundResource(R.drawable.border_event_error);
            valid = false;
        }
        if(descriptionStr.equals(""))
        {
            CustomErrorAlertDialog privacyEmpyt = new CustomErrorAlertDialog(this,"Error","please fill the descrption field");
            descriptionSpace.setBackgroundResource(R.drawable.border_event_error);
            valid = false;
        }

        if(valid)
        {
            //String session = "20_2021-12-14_18:30";
            ArrayList<String> sessionsStr = new ArrayList<>();
            for(int i=0;i<addedSessions.size();i++)
            {
                String session = "";
                Session currentSession = addedSessions.get(i);
                String limit = String.valueOf(currentSession.getLimit());
                String year = currentSession.getYear();
                String month = currentSession.getMonth();
                String day = currentSession.getDay();
                String hour = currentSession.getHour();
                String min = currentSession.getMin();

                String thisSession = limit+"_"+year+"-"+month+"-"+day+"_"+hour+":"+min;
                sessionsStr.add(thisSession);
            }
            boolean pv = false;
            if(privacyStr.equals("Public"))
            {
                pv = false;
            }
            else if(privacyStr.equals("Private"))
            {
                pv = true;
            }
            Event newEvent = new Event(titleStr, pv, categoryStr, descriptionStr,false, locationStr,sessionsStr, eventToken);
            Call<Event> callBack = eventAPI.event_edit("token "+userToken,newEvent);
            callBack.enqueue(new Callback<Event>() {
                @Override
                public void onResponse(Call<Event> call, Response<Event> response) {
                    if(!response.isSuccessful())
                    {
                        CustomErrorAlertDialog errorConnecting = new CustomErrorAlertDialog(EditEventActivity.this,"Error","there is a problem connecting to server");
                    }
                    else{
                        String code = Integer.toString(response.code());
                        Event addedEvent = response.body();
                  //      Toast.makeText(EditEventActivity.this, code, Toast.LENGTH_SHORT).show();
                        CustomSuccessAlertDialog saved = new CustomSuccessAlertDialog(EditEventActivity.this,"Successful","event saved");
                        saved.btnOk.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Intent event = new Intent(EditEventActivity.this, my_created_events.class);
                                startActivity(event);
                                finish();
                            }
                        });
                    }
                }

                @Override
                public void onFailure(Call<Event> call, Throwable t) {
                    CustomErrorAlertDialog errorConnecting = new CustomErrorAlertDialog(EditEventActivity.this,"Error","there is a problem connecting to server");
                }
            });
        }
    }

    public void addSessionBtn(View view) {

        EditText limit = findViewById(R.id.added_session_limit);
        Spinner year = findViewById(R.id.added_date_year);
        Spinner month = findViewById(R.id.added_date_month);
        Spinner day = findViewById(R.id.added_date_day);
        Spinner hour = findViewById(R.id.added_hour);
        Spinner min = findViewById(R.id.added_min);

        String limitStr = limit.getText().toString();
        String yearStr = year.getSelectedItem().toString();
        String monthStr = month.getSelectedItem().toString();
        int monthNumber = Integer.parseInt(monthStr);
        monthNumber--;
        monthStr = Integer.toString(monthNumber);
        String dayStr = day.getSelectedItem().toString();
        String hourStr = hour.getSelectedItem().toString();
        String minStr = min.getSelectedItem().toString();

        int yearNum = Integer.parseInt(yearStr);
        int monthNum = Integer.parseInt(monthStr);
        int dayNum = Integer.parseInt(dayStr);
        int hourNum = Integer.parseInt(hourStr);
        int minNum = Integer.parseInt(minStr);

        if(checkDate(yearNum,monthNum,dayNum,hourNum,minNum))
        {
            Session newSession = new Session(yearStr,monthStr,dayStr,hourStr,minStr,limitStr);
            mySessions.add(newSession);
            addedSessions.add(newSession);
            sessionAdap = new SessionAdapter(EditEventActivity.this,mySessions,addedSessions);
            sessionsList.setAdapter(sessionAdap);
        }
        else{
            CustomErrorAlertDialog errorDate = new CustomErrorAlertDialog(EditEventActivity.this,"Error","you can not select a date in past");
        }
    }

    public boolean checkDate(int year, int month , int day,int hour,int min)
    {

        Calendar c = Calendar.getInstance();
        int currentDay = c.get(Calendar.DAY_OF_MONTH);
        int currentMonth = c.get(Calendar.MONTH);
        int currentYear = c.get(Calendar.YEAR);


        Time now = new Time();
        now.setToNow();

        int currentHour = now.hour;
        int currentMinuate = now.minute;

        String currentDateTime = Integer.toString(currentYear)+Integer.toString(currentMonth)+Integer.toString(currentDay)+"_"+Integer.toString(currentHour)+":"+Integer.toString(currentMinuate);
     //   Toast.makeText(this, currentDateTime, Toast.LENGTH_SHORT).show();
        if(currentYear<year)
        {
            return true;
        }
        else if(currentYear==year && currentMonth<month)
        {
            return true;
        }
        else if(currentYear == year && currentMonth == month && currentDay < day)
        {
            return true;
        }
        else if (currentYear == year && currentMonth == month && currentDay==day && currentHour<hour)
        {
            return true;
        }
        else if(currentYear == year && currentMonth == month && currentDay==day && currentHour==hour && currentMinuate<min)
        {
            return true;
        }
        CustomErrorAlertDialog dateAlert = new CustomErrorAlertDialog(this,"Error","you can't set a task for past");
        //Toast.makeText(this, "you can set a task for past", Toast.LENGTH_SHORT).show();
        return false;

    }


    public void load()
    {
        JsonObject body = new JsonObject();
        body.addProperty("event_token",eventToken);
        Call<Event> callBack = eventAPI.enter_event_token("token "+ userToken,body);
        callBack.enqueue(new Callback<Event>() {
            @Override
            public void onResponse(Call<Event> call, Response<Event> response) {
                if(!response.isSuccessful())
                {
                    Toast.makeText(EditEventActivity.this, "Some Field Wrong", Toast.LENGTH_SHORT).show();
                }
                else{
                    String code = Integer.toString(response.code());
                    currentEvent = response.body();

                    title.setText(currentEvent.getTitle().toString());
                    loadSpinners();
                    description.setText(currentEvent.getDescription().toString());
                    loadSessions();
                }
            }

            @Override
            public void onFailure(Call<Event> call, Throwable t) {
                Toast.makeText(EditEventActivity.this, "error is :"+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


    public void loadSpinners()
    {
        String[] categoryArr = getResources().getStringArray(R.array.category);
        int categoryPos = searchIndex(currentEvent.getCategory().toString(),categoryArr);
        category.setSelection(categoryPos);

        String[] locationArr = getResources().getStringArray(R.array.category);
        int locationPos = searchIndex(currentEvent.getCategory().toString(),locationArr);
        location.setSelection(locationPos);

        String[] privacyArr = getResources().getStringArray(R.array.privacy);
        int privacyPos = searchIndex(currentEvent.getCategory().toString(),privacyArr);
        privacy.setSelection(privacyPos);
    }

    public int searchIndex(String x,String [] array)
    {
        int index = 0;
        for(int i=0;i<array.length;i++)
        {
            if(array[i].equals(x))
            {
                index = i;
                break;
            }
        }

        return index;
    }

    public void loadSessions()
    {
        List<Session> sessions = currentEvent.getSessionsArr();
        mySessions = new ArrayList<>();

        for(Session item:sessions)
        {
            String [] dateTime = item.getTime().split("_");
            String date = dateTime[0];
            String time = dateTime[1];
            String limit = Integer.toString(item.getLimit());
            String session_token = item.getSession_token();

            String [] dateInfo = date.split("-");
            String year = dateInfo[0];
            String month = dateInfo[1];
            String day = dateInfo[2];

            String [] timeInfo = time.split(":");
            String hour = timeInfo[0];
            String min = timeInfo[1];

            Session newSession = new Session(year,month,day,hour,min,limit,session_token);
            mySessions.add(newSession);
        }

        sessionAdap = new SessionAdapter(EditEventActivity.this,mySessions,addedSessions);
        sessionsList.setAdapter(sessionAdap);
    }
}
