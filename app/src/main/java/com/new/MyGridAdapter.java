package com.example;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.myapplication.R;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class MyGridAdapter extends ArrayAdapter {
    List<Date> dates;
    Calendar currentDate;
    List<Events> events;
    LayoutInflater inflater;

    public MyGridAdapter(@NonNull Context context,List<Date> dates,Calendar currentDate,List<Events> events) {
        super(context, R.layout.single_cell_layout);
        this.dates=dates;
        this.currentDate=currentDate;
        this.events=events;
        inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Date monthDate = dates.get(position);
        Calendar realCalendar = Calendar.getInstance();
        Calendar dateCalendar = Calendar.getInstance();
        dateCalendar.setTime(monthDate);
        int DayNo = dateCalendar.get(Calendar.DAY_OF_MONTH);
        int displayMonth = dateCalendar.get(Calendar.MONTH)+1;
        int displayYear = dateCalendar.get(Calendar.YEAR);
        int currentMonth = currentDate.get(Calendar.MONTH)+1;
        int currentYear = currentDate.get(Calendar.YEAR);
        int currentDay = currentDate.get(Calendar.DAY_OF_MONTH);
        View view = convertView;
        if(view == null){
            view = inflater.inflate(R.layout.single_cell_layout,parent,false);
        }
        if(displayMonth == currentMonth && displayYear==currentYear){
            /*ShapeAppearanceModel shapeAppearanceModelLL1 = new ShapeAppearanceModel()
                    .toBuilder()
                    .setAllCorners(CornerFamily.ROUNDED,16)
                    .build();
            MaterialShapeDrawable shapeDrawableLL1 = new MaterialShapeDrawable(shapeAppearanceModelLL1);
            shapeDrawableLL1.setFillColor(
                    ContextCompat.getColorStateList(view.getContext(),R.color.purple_500));
            ViewCompat.setBackground(view,shapeDrawableLL1);*/
            if(DayNo == realCalendar.get(Calendar.DAY_OF_MONTH) && displayMonth == (realCalendar.get(Calendar.MONTH)+1) && displayYear==realCalendar.get(Calendar.YEAR)){
                view.setBackgroundResource(R.drawable.border_today);
            }
        }
        else {
            view.setAlpha(0.5f);
        }
        TextView dayNumber = view.findViewById(R.id.calendar_day);
        if(DayNo == 10) {
            TextView dayEvents = view.findViewById(R.id.events_id);
            dayEvents.setText(".");
        }
        dayNumber.setText(String.valueOf(DayNo));
        return view;
    }


    @Override
    public int getCount() {
        return dates.size();
    }

    @Override
    public int getPosition(@Nullable Object item) {
        return dates.indexOf(item);
    }

    @Nullable
    @Override
    public Object getItem(int position) {
        return dates.get(position);
    }
}
