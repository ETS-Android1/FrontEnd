package com.example.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.JoinEvent;
import com.example.entity.Event;
import com.example.entity.Task;
import com.example.event_activity;
import com.example.myapplication.R;

import java.util.ArrayList;
import java.util.List;

public class eventAdapter extends BaseAdapter implements Filterable {
    private Context context;
    private List<Event> list;
    private List<Event> temp;

    public eventAdapter(Context context, List<Event> list) {
        this.context = context;
        this.list = list;
        this.temp = list;
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
            view = LayoutInflater.from(context).inflate(R.layout.event_view,null);
        }

        Event currentEvent = list.get(i);

        TextView title = view.findViewById(R.id.titleEventView);
        TextView date = view.findViewById(R.id.dateTimeEventView);
        TextView location = view.findViewById(R.id.LocationEventView);
        TextView desc = view.findViewById(R.id.descEventView);
        Button joinBtn = view.findViewById(R.id.joinBtn);
        ImageView imageViewCategory = view.findViewById(R.id.categoryImageItemEventView);
        String dateTime = currentEvent.getTime().toString();
        String [] dateTimeInfo = dateTime.split("T");
        dateTime = dateTimeInfo[0];
        title.setText(currentEvent.getTitle().toString());
        date.setText(dateTime);
        location.setText(currentEvent.getLocation().toString());
        desc.setText(currentEvent.getDescription().toString());

        String category = currentEvent.getCategory();

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

        joinBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, JoinEvent.class);
                intent.putExtra("title", currentEvent.getTitle());
                intent.putExtra("category", currentEvent.getCategory());
                intent.putExtra("location", currentEvent.getLocation());
                intent.putExtra("token", currentEvent.getEvent_token());
                context.startActivity(intent);
            }
        });

        return view;
    }

    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                FilterResults filterResults = new FilterResults();

                ArrayList<Event> filterList = new ArrayList<>();
                for(Event item:temp)
                {
                    if(item.getTitle().toString().toLowerCase().contains(charSequence.toString().toLowerCase()))
                    {
                        filterList.add(item);
                    }
                }

                filterResults.count = filterList.size();
                filterResults.values = filterList;

                return  filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                list = (List<Event>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }


}
