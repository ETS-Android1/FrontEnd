package com.example;


import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.DataBase.tasksDB;
import com.example.entity.Task;
import com.example.myapplication.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
//test branch
//test branch
public class CustomCalendarView extends LinearLayout {
    ImageButton nextButton , previousButton;
    TextView currentDate;
    GridView gridView;
    private static final int MAX_CALENDAR_DAYS = 42;
    Calendar calendar = Calendar.getInstance(Locale.ENGLISH);
    Context context;
    SimpleDateFormat dateFormat = new SimpleDateFormat("MMMM yyyy",Locale.ENGLISH);
    SimpleDateFormat monthFormat = new SimpleDateFormat("MMMM",Locale.ENGLISH);
    SimpleDateFormat yearFormat = new SimpleDateFormat("yyyy",Locale.ENGLISH);
    MyGridAdapter myGridAdapter;
    AlertDialog alertDialog;
    List<Date> dates = new ArrayList<>();
    List<Events> eventsList = new ArrayList<>();
    RecyclerView recyclerView;
    LinearLayoutManager linearLayoutManager;
    List<RecyclerModelClass> recyclerEventList;
    RecyclerAdapter recyclerAdapter;

    public CustomCalendarView(Context context) {
        super(context);
    }

    public CustomCalendarView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

    }

    public CustomCalendarView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        InitializeLayout();
        InitializeData();
        InitializeRecycler();
        SetUpCalendar();
        previousButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                calendar.add(Calendar.MONTH,-1);
                SetUpCalendar();
            }
        });
        nextButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                calendar.add(Calendar.MONTH,1);
                SetUpCalendar();
            }
        });
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String transferDate = gridView.getItemAtPosition(position).toString();
                Intent i = new Intent(context, day_task_activity.class);
                i.putExtra("Date",transferDate);
                context.startActivity(i);
            }
        });
    }
    String convertTo12Hours(String militaryTime) throws ParseException {
        //in => "14:00:00"
        //out => "02:00 PM"
        SimpleDateFormat inputFormat = new SimpleDateFormat("hh:mm:ss", Locale.getDefault());
        SimpleDateFormat outputFormat = new SimpleDateFormat("hh:mm aa", Locale.getDefault());
        Date date = inputFormat.parse(militaryTime);
        return outputFormat.format(date);
    }
    private void InitializeData() {
        tasksDB tasksdb = new tasksDB(context);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.MONTH, -1);
        Date currentDateMinusOne = c.getTime();
        String dates = dateFormat.format(currentDateMinusOne);
        ArrayList<Task> tasksRecycler = tasksdb.select(dates);
        recyclerEventList = new ArrayList<>();
        for(int i = 0 ; i < tasksRecycler.size();i++){
            try {
                recyclerEventList.add(new RecyclerModelClass(tasksRecycler.get(i).getTitle(), convertTo12Hours(tasksRecycler.get(i).getDateTime().split("_")[1]+":00")));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        if(tasksRecycler.size() == 0){
            recyclerEventList.add(new RecyclerModelClass("NO TASK","--:--"));
        }
    }

    private void InitializeRecycler() {
        recyclerView = findViewById(R.id.events_recycler_view);
        linearLayoutManager = new LinearLayoutManager(context);
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerAdapter = new RecyclerAdapter(recyclerEventList);
        recyclerView.setAdapter(recyclerAdapter);
        recyclerAdapter.notifyDataSetChanged();
    }

    private void InitializeLayout(){
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.calendar_layout,this);
        nextButton = view.findViewById(R.id.nextBtn);
        previousButton = view.findViewById(R.id.previousBtn);
        currentDate = view.findViewById(R.id.current_date);
        gridView = view.findViewById(R.id.calendarGridView);
    }
    private void SetUpCalendar(){
        String currentDateLocal = monthFormat.format(calendar.getTime());
        currentDate.setText(currentDateLocal);
        dates.clear();
        Calendar monthCalendar = (Calendar) calendar.clone();
        monthCalendar.set(Calendar.DAY_OF_MONTH,1);
        int FirstDayOfMonth = monthCalendar.get(Calendar.DAY_OF_WEEK)-1;
        monthCalendar.add(Calendar.DAY_OF_MONTH,-FirstDayOfMonth);

        while(dates.size() < MAX_CALENDAR_DAYS){
            dates.add(monthCalendar.getTime());
            monthCalendar.add(Calendar.DAY_OF_MONTH,1);
        }
        myGridAdapter = new MyGridAdapter(context,dates,calendar,eventsList);
        gridView.setAdapter(myGridAdapter);
    }
}
