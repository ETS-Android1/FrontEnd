package com.example;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;

import java.util.List;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {
    private List<RecyclerModelClass> eventList;
    public RecyclerAdapter(List<RecyclerModelClass> eventListCons){
        this.eventList = eventListCons;
    }




    @NonNull
    @Override
    public RecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerAdapter.ViewHolder holder, int position) {
        String title = eventList.get(position).getItemTitle();
        String time = eventList.get(position).getItemHour();
        holder.setData(title,time);
    }

    @Override
    public int getItemCount() {
        return eventList.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView titleTextView;
        private TextView timeTextView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            titleTextView = itemView.findViewById(R.id.recycler_view_item_title);
            timeTextView = itemView.findViewById(R.id.recycler_view_item_hours);
        }

        public void setData(String title, String time) {
            titleTextView.setText(title);
            timeTextView.setText(time);
        }
    }
}
