package com.example.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.EditTask;
import com.example.entity.Event;
import com.example.entity.Session;
import com.example.myapplication.R;

import java.util.ArrayList;
import java.util.List;
import com.example.reserverd_session;

public class ReservedSessionAdapter extends BaseAdapter implements Filterable {
    private Context context;
    private List<Session> list;
    private List<Session> temp;

    public ReservedSessionAdapter(Context context, List<Session> list) {
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
            view = LayoutInflater.from(context).inflate(R.layout.reserved_session_item,null);
        }

        Session currentSession = list.get(i);

        TextView title = view.findViewById(R.id.titleEventView);
        TextView date = view.findViewById(R.id.dateTimeEventView);
        TextView location = view.findViewById(R.id.LocationEventView);
        TextView desc = view.findViewById(R.id.descEventView);
        ImageView imageViewCategory = view.findViewById(R.id.categoryImageItemEventView);

        title.setText(currentSession.getEvent().getTitle().toString());
        String [] dateTimeInfo = currentSession.getTime().split("_");
        date.setText(dateTimeInfo[0]);
        location.setText(currentSession.getEvent().getLocation());
        desc.setText(currentSession.getEvent().getDescription().toString());

        String category = currentSession.getEvent().getCategory().toString();

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

        Button edit = view.findViewById(R.id.moreBtn);
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, reserverd_session.class);
                intent.putExtra("title",currentSession.getEvent().getTitle());
                intent.putExtra("category",currentSession.getEvent().getCategory());
                intent.putExtra("location",currentSession.getEvent().getLocation());
                intent.putExtra("desc",currentSession.getEvent().getDescription());
                intent.putExtra("datetime",currentSession.getTime().toString());
                intent.putExtra("session_token",currentSession.getSession_token());
                intent.putExtra("limit",Integer.toString(currentSession.getLimit()));
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

                ArrayList<Session> filterList = new ArrayList<>();
                for(Session item:temp)
                {
                    if(item.getEvent().getTitle().toString().toLowerCase().contains(charSequence.toString().toLowerCase()))
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
                list = (List<Session>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }
}
