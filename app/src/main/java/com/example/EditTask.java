package com.example;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.format.Time;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.DataBase.tasksDB;
import com.example.entity.Task;
import com.example.myapplication.R;
import com.example.webService.TaskAPI;
import com.google.gson.JsonObject;

import java.util.Calendar;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class EditTask extends AppCompatActivity {

    EditText editTitle;
    EditText editdesc;
    DatePicker editDatePicker;
    TimePicker editTimePicker;
    Spinner editCategory;
    String userToken;
    String username;
    ConstraintLayout editTitleCons;
    ConstraintLayout editDescCons;
    TaskAPI taskAPI;
    String status;
    String task_token;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_task);

        getSupportActionBar().hide();

        init();

        String title = getIntent().getStringExtra("title");
        String category = getIntent().getStringExtra("category");
        String desc = getIntent().getStringExtra("desc");
        String dateTime = getIntent().getStringExtra("dateTime");
        status = getIntent().getStringExtra("status");
        task_token = getIntent().getStringExtra("taskToken");

        editTitle.setText(title);
        if(category.equals("Sport"))
        {
            editCategory.setSelection(1);
        }
        else if(category.equals("Study"))
        {
            editCategory.setSelection(2);
        }
        else if(category.equals("Meeting"))
        {
            editCategory.setSelection(3);
        }
        else if(category.equals("Work"))
        {
            editCategory.setSelection(4);
        }
        else if(category.equals("hang out"))
        {
            editCategory.setSelection(5);
        }

        editdesc.setText(desc);

        String [] dateTimeInfo = dateTime.split("_");
        String [] dateInfo = dateTimeInfo[0].split("-");
        String [] timeInfo = dateTimeInfo[1].split(":");

        int hour = Integer.parseInt(timeInfo[0]);
        int min = Integer.parseInt(timeInfo[1]);

        int year = Integer.parseInt(dateInfo[0]);
        int month = Integer.parseInt(dateInfo[1]);
        int day = Integer.parseInt(dateInfo[2]);

        editTimePicker.setCurrentHour(hour);
        editTimePicker.setCurrentMinute(min);
        editDatePicker.init(year,month,day,null);


        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();

        Retrofit editTask = new Retrofit.Builder()
                .baseUrl(TaskAPI.BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        taskAPI = editTask.create(TaskAPI.class);


        editDescCons.setBackgroundResource(R.drawable.border_red_task_error);
        editTitleCons.setBackgroundResource(R.drawable.border_red_task_error);

        editTitle.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(editTitle.getText().toString()!="")
                {
                    editTitleCons.setBackgroundResource(R.drawable.border_task);
                }
                else
                {
                    editTitleCons.setBackgroundResource(R.drawable.border_red_task_error);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(editTitle.getText().toString()=="")
                {
                    editTitleCons.setBackgroundResource(R.drawable.border_red_task_error);
                }
            }
        });

        editdesc.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(editdesc.getText().toString()!="")
                {
                    editDescCons.setBackgroundResource(R.drawable.border_task);
                }
                else{
                    editDescCons.setBackgroundResource(R.drawable.border_red_task_error);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(editdesc.getText().toString()=="")
                {
                    editDescCons.setBackgroundResource(R.drawable.border_red_task_error);
                }
            }
        });
    }


    public void init()
    {
        editTitle = findViewById(R.id.editTitleEt);
        editdesc = findViewById(R.id.editDescriptionEt);
        editDatePicker = findViewById(R.id.editDateTimeDatePicker);
        editTimePicker = findViewById(R.id.edit_time_picker);
        editCategory = findViewById(R.id.edit_Spinner_category);
        SharedPreferences sharedPreferences = getSharedPreferences("authentication", MODE_PRIVATE);
        userToken = sharedPreferences.getString("token", "");
        username = sharedPreferences.getString("username","");
        editTitleCons = findViewById(R.id.editTitleCons);
        editDescCons = findViewById(R.id.editDescCons);
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

    public void EditTask(View view) {
        int year = editDatePicker.getYear();
        int month = editDatePicker.getMonth();
        int day = editDatePicker.getDayOfMonth();
        int hour = editTimePicker.getCurrentHour();
        int min = editTimePicker.getCurrentMinute();

        String categoryStr =  editCategory.getSelectedItem().toString();

        if(categoryStr.equals(""))
        {
            CustomErrorAlertDialog categortyEmpyt = new CustomErrorAlertDialog(this,"Error","please fill the category field");
        }

        String titleStr = editTitle.getText().toString();
        String descStr = editdesc.getText().toString();
        if(titleStr.equals(""))
        {
            CustomErrorAlertDialog titleError = new CustomErrorAlertDialog(this,"Error","fill the title field");
            //Toast.makeText(this, "fill the title field", Toast.LENGTH_SHORT).show();
            editTitleCons.setBackgroundResource(R.drawable.border_red_task_error);
        }
        if(descStr.equals(""))
        {
            editDescCons.setBackgroundResource(R.drawable.border_red_task_error);
            //Toast.makeText(this, "fill the description field", Toast.LENGTH_SHORT).show();
            CustomErrorAlertDialog messageError = new CustomErrorAlertDialog(this,"Error","fill the description field");
        }

        if(checkDate(year,month,day,hour,min) && !titleStr.equals("") && !descStr.equals("")&& !categoryStr.equals(""))
        {
            Toast.makeText(this,"task saved",Toast.LENGTH_SHORT).show();
            String hours = Integer.toString(hour);
            if(hours.length()==1)
            {
                hours = "0"+hours;
            }
            String mins = Integer.toString(min);
            if(mins.length()==1)
            {
                mins = "0"+mins;
            }
            String datetime = Integer.toString(year)+"-"+Integer.toString(month)+"-"+Integer.toString(day)+"_"+hours+":"+mins;
            //Toast.makeText(this, datetime, Toast.LENGTH_SHORT).show();
            Task newTask = new Task(titleStr,descStr, datetime, categoryStr, "time for "+categoryStr,task_token);
//
//            Call<JsonObject> callback = taskAPI.editTask("token "+userToken,newTask);
//            callback.enqueue(new Callback<JsonObject>() {
//                @Override
//                public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
//                    if(!response.isSuccessful())
//                    {
//                        CustomeAlertDialog errorConnecting = new CustomeAlertDialog(EditTask.this,"error","there is a problem with your internet connection");
//                    }
//                    else{
//                        String code = Integer.toString(response.code());
//                        //Toast.makeText(EditTask.this, code, Toast.LENGTH_SHORT).show();
//                        String task_token = newTask.getTaskToken();
//                        String title = newTask.getTitle();
//                        String dateTime = newTask.getDateTime();
//                        String [] infos = dateTime.split("_");
//                        String date = infos[0];
//                        String time = infos[1];
//                        tasksDB tasksdb = new tasksDB(EditTask.this);
//                        tasksdb.updateTask(task_token,title,date,time);
//                        //Toast.makeText(EditTask.this,task_token, Toast.LENGTH_SHORT).show();
//                        CustomeAlertDialog saved = new CustomeAlertDialog(EditTask.this,"Successful","task updated");
//                        saved.btnOk.setOnClickListener(new View.OnClickListener() {
//                            @Override
//                            public void onClick(View view) {
//                                finish();
//                            }
//                        });
//                    }
//                }
//
//                @Override
//                public void onFailure(Call<JsonObject> call, Throwable t) {
//                    CustomeAlertDialog errorConnecting = new CustomeAlertDialog(EditTask.this,"error","there is a problem connecting to server");
//
//                }
//            });

            //offline part....
            tasksDB taskdb = new tasksDB(EditTask.this);
            String [] dateTimeInfo = datetime.split("_");
            String date = dateTimeInfo[0];
            String time = dateTimeInfo[1];
            taskdb.updateTask(task_token,titleStr,date,time,descStr,status,categoryStr);
            //Toast.makeText(this, "saved", Toast.LENGTH_SHORT).show();
            CustomeAlertDialog saved = new CustomeAlertDialog(EditTask.this,"Successful","task saved");
            saved.btnOk.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    finish();
                
                }
            });
        }
    }
}