package com.example.BroadCast;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.text.format.Time;
import android.util.Log;

import androidx.core.app.NotificationCompat;

import com.example.DataBase.tasksDB;
import com.example.entity.Task;
import com.example.myapplication.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;

public class NotificationServ extends Service {

    String TAG = "Timers";



    @Override
    public IBinder onBind(Intent arg0) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.e(TAG, "onStartCommand");
        super.onStartCommand(intent, flags, startId);
        Log.e("notification_TAG","in service");
        tasksDB tasksdb = new tasksDB(NotificationServ.this);
        ArrayList<Task> currentTasks = tasksdb.select();

        Calendar c = Calendar.getInstance();
        int currentDay = c.get(Calendar.DAY_OF_MONTH);
        int currentMonth = c.get(Calendar.MONTH);
        int currentYear = c.get(Calendar.YEAR);


        Time now = new Time();
        now.setToNow();

        int currentHour = now.hour;
        int currentMinuate = now.minute;

        String currentHourStr = Integer.toString(currentHour);
        if(currentHourStr.length()==1)
        {
            currentHourStr = "0"+ currentHourStr;
        }

        String currentMinStr = Integer.toString(currentMinuate);
        if(currentMinStr.length()==1)
        {
            currentMinStr = "0"+currentMinStr;
        }

        String currentTimeStr = Integer.toString(currentYear)+"-"+Integer.toString(currentMonth)+"-"+Integer.toString(currentDay)+"_"+currentHourStr+":"+currentMinStr;

        Log.e("notification_TAG","currentTime: "+currentTimeStr);
        boolean taskTime = false;
        for(Task task : currentTasks )
        {
            Log.e("notification_TAG","time"+task.getDateTime().toString());
            if(currentTimeStr.equals(task.getDateTime().toString()))
            {
                Log.e("notification_TAG","in the notification");
                NotificationManager notificationManager = (NotificationManager) getSystemService(Service.NOTIFICATION_SERVICE);

                String channelId = "channelId";
                String channelName = "channelName";

                android.app.Notification notification;
                NotificationCompat.Builder builder = new NotificationCompat.Builder(NotificationServ.this);
                builder.setContentTitle(task.getTitle().toString());
                builder.setContentText("time for "+task.getTitle());
                builder.setSmallIcon(R.drawable.logo_second);
                builder.setChannelId(channelId);

                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                    NotificationChannel notificationChannel = new NotificationChannel(channelId,channelName,NotificationManager.IMPORTANCE_HIGH);
                    notificationManager.createNotificationChannel(notificationChannel);
                    builder.setChannelId(channelId);
                }

                notification = builder.build();

                notificationManager.notify(1,notification);

            }
        }
        return START_STICKY;
    }


    @Override
    public void onCreate() {
        Log.e(TAG, "onCreate");


    }

    @Override
    public void onDestroy() {
        Log.e(TAG, "onDestroy");
        super.onDestroy();
    }


}