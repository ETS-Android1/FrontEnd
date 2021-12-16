package com.example.adapter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.widget.CompoundButtonCompat;

import com.example.ConfirmationAlertDialog;
import com.example.CustomeAlertDialog;
import com.example.DataBase.tasksDB;
import com.example.EditTask;
import com.example.entity.Task;
import com.example.myapplication.R;
import com.example.myapplication.databinding.ConfirmationAlertDialogBinding;
import com.example.webService.TaskAPI;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.Header;

public class taskAdapter extends BaseAdapter implements Filterable {

    private Context context;
    private List<Task> list;
    private List<Task> temp;
    private TaskAPI taskAPI;
    private String userToken;
    private String username;

    public taskAdapter(Context context, List<Task> list) {
        this.context = context;
        this.list = list;
        this.temp= list;
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

        String dateTime = currentTask.getDateTime().toString();
        String [] dateTimeInfo = dateTime.split("_");
        String time = dateTimeInfo[1];
        TextView textViewTitle = view.findViewById(R.id.titleTv);
        TextView textViewdateTime = view.findViewById(R.id.dateTimeTv);
        TextView textViewDescTv = view.findViewById(R.id.descTv);
        ImageView imageViewCategory = view.findViewById(R.id.categoryImageItem);
        Button editBtn = view.findViewById(R.id.editBtn);
        Button deleteBtn = view.findViewById(R.id.deleteBtn);
        CheckBox statusCB = view.findViewById(R.id.statusCheckBox);

        textViewTitle.setText(currentTask.getTitle());
        textViewDescTv.setText(currentTask.getDesc());
        textViewdateTime.setText(time);
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
            CompoundButtonCompat.setButtonTintList(statusCB, ColorStateList.valueOf(Color.GRAY));
            statusCB.setChecked(false);
        }
        else if(statusStr.equals("overdue"))
        {
            statusCB.setChecked(false);
            CompoundButtonCompat.setButtonTintList(statusCB, ColorStateList.valueOf(Color.RED));
            textViewdateTime.setTextColor(view.getResources().getColor(R.color.warning_task_color));
        }
        SharedPreferences sharedPreferences = context.getSharedPreferences("authentication", Context.MODE_PRIVATE);
        userToken = sharedPreferences.getString("token", "");
        username = sharedPreferences.getString("username","");

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();

        Retrofit taskStatus = new Retrofit.Builder()
                .baseUrl(TaskAPI.BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        taskAPI = taskStatus.create(TaskAPI.class);

        statusCB.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b)
                {
                    JsonObject jsonObject = new JsonObject();
                    jsonObject.addProperty("task_token", currentTask.getTaskToken());
                    jsonObject.addProperty("status", "done");
                    Call<JsonObject> request = taskAPI.finishTask("token "+userToken,jsonObject);
                    request.enqueue(new Callback<JsonObject>() {
                        @Override
                        public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                            if(!response.isSuccessful())
                            {
                                CustomeAlertDialog errorConnecting = new CustomeAlertDialog(context,"error","there is a problem with your internet connection");
                            }
                            else{
                                String code = Integer.toString(response.code());
                                //Toast.makeText(context, code, Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<JsonObject> call, Throwable t) {
                            CustomeAlertDialog errorConnecting = new CustomeAlertDialog(context,"error","there is a problem with your internet connection");
                        }
                    });
                }
                else{
                    JsonObject jsonObject = new JsonObject();
                    jsonObject.addProperty("task_token", currentTask.getTaskToken());
                    jsonObject.addProperty("status", "pending");
                    Call<JsonObject> request = taskAPI.finishTask("token "+userToken,jsonObject);
                    request.enqueue(new Callback<JsonObject>() {
                        @Override
                        public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                            if(!response.isSuccessful())
                            {
                                CustomeAlertDialog errorConnecting = new CustomeAlertDialog(context,"error","there is a problem with your internet connection");

                            }
                            else{
                                String code = Integer.toString(response.code());
                                //Toast.makeText(context, code, Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<JsonObject> call, Throwable t) {
                            CustomeAlertDialog errorConnecting = new CustomeAlertDialog(context,"error","there is a problem with your internet connection");

                        }
                    });
                }
            }
        });


        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ConfirmationAlertDialog confirmCancel = new ConfirmationAlertDialog(context,"Confirmation","Do you want to delete this task("+currentTask.getTitle().toString()+")?");
                confirmCancel.btnOk.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        confirmCancel.alertDialog.dismiss();
                        JsonObject jsonObject = new JsonObject();
                        jsonObject.addProperty("task_token", currentTask.getTaskToken());
                        Call<JsonObject> request = taskAPI.deleteTask("token "+userToken,jsonObject);
                        request.enqueue(new Callback<JsonObject>() {
                            @Override
                            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                                if(!response.isSuccessful())
                                {
                                    CustomeAlertDialog errorConnecting = new CustomeAlertDialog(context,"error","there is a problem with your internet connection");

                                }
                                else{
                                    String code = Integer.toString(response.code());
                                    //Toast.makeText(context, code, Toast.LENGTH_SHORT).show();
                                    remove(i);
                                }
                            }

                            @Override
                            public void onFailure(Call<JsonObject> call, Throwable t) {
                                CustomeAlertDialog errorConnecting = new CustomeAlertDialog(context,"error","there is a problem with your internet connection");
                            }
                        });
                    }
                });

                confirmCancel.cancelBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        confirmCancel.alertDialog.dismiss();
                    }
                });
            }
        });


        editBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context,EditTask.class);
                intent.putExtra("title",textViewTitle.getText().toString());
                intent.putExtra("dateTime",currentTask.getDateTime().toString());
                intent.putExtra("desc",textViewDescTv.getText().toString());
                intent.putExtra("category",category);
                intent.putExtra("status",statusStr);
                intent.putExtra("status",statusStr);
                intent.putExtra("taskToken",currentTask.getTaskToken());
                context.startActivity(intent);

            }
        });
        return view;
    }

    public void remove(int position){
        list.remove(list.get(position));
        this.notifyDataSetChanged();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                FilterResults filterResults = new FilterResults();

                ArrayList<Task> filterList = new ArrayList<>();
                for(Task item:temp)
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
                list = (List<Task>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }
}