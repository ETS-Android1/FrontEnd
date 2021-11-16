package com.example.adapter;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.core.widget.CompoundButtonCompat;

import com.example.entity.Task;
import com.example.myapplication.R;

import java.util.List;

public class taskAdapter extends BaseAdapter {

    private Context context;
    private List<Task> list;

    public taskAdapter(Context context, List<Task> list) {
        this.context = context;
        this.list = list;
    }


    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if(view == null)
        {
            view = LayoutInflater.from(context).inflate(R.layout.task_view,null);

        }

        Task currentTask = list.get(i);

        TextView textViewTitle = view.findViewById(R.id.titleTv);
        TextView textViewdateTime = view.findViewById(R.id.dateTimeTv);
        TextView textViewDescTv = view.findViewById(R.id.descTv);
        ImageView imageViewCategory = view.findViewById(R.id.categoryImageItem);
        Button editBtn = view.findViewById(R.id.editBtn);
        CheckBox statusCB = view.findViewById(R.id.statusCheckBox);

        textViewTitle.setText(currentTask.getTitle());
        textViewDescTv.setText(currentTask.getDesc());
        textViewdateTime.setText(currentTask.getDateTime());
        String category = currentTask.getCategory().toString();


        if(category.equals("Sport"))
        {
            imageViewCategory.setImageResource(R.drawable.sport4);
        }
        else if(category.equals("Study")){
            imageViewCategory.setImageResource(R.drawable.study1);
        }
        else if(category.equals("Meeting"))
        {
            imageViewCategory.setImageResource(R.drawable.meeting1);
        }
        else if(category.equals("Work"))
        {
            imageViewCategory.setImageResource(R.drawable.work1);
        }
        else if(category.equals("hang out"))
        {
            imageViewCategory.setImageResource(R.drawable.hang_out2);
        }

        String statusStr = currentTask.getStatus();
        if(statusStr.equals("done"))
        {
            CompoundButtonCompat.setButtonTintList(statusCB, ColorStateList.valueOf(Color.GREEN));
            statusCB.setChecked(true);
        }
        else if(statusStr.equals("pending"))
        {
            CompoundButtonCompat.setButtonTintList(statusCB, ColorStateList.valueOf(Color.WHITE));
            statusCB.setChecked(false);
        }
        else if(statusStr.equals("overdue"))
        {
            statusCB.setChecked(false);
            CompoundButtonCompat.setButtonTintList(statusCB, ColorStateList.valueOf(Color.RED));
            textViewdateTime.setTextColor(view.getResources().getColor(R.color.warning_task_color));
        }

        return view;
    }
}
