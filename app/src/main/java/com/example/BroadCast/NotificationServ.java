package com.example.BroadCast;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.app.TaskStackBuilder;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.IBinder;
import android.text.format.Time;
import android.util.Log;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.example.DataBase.tasksDB;
import com.example.MainActivity;
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
                // Create an Intent for the activity you want to start
                Intent notificationIntent = new Intent(this, com.example.login.class);
                TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
                stackBuilder.addNextIntentWithParentStack(notificationIntent);
                notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
                        | Intent.FLAG_ACTIVITY_SINGLE_TOP);

                PendingIntent Intent = PendingIntent.getActivity(this, 0,
                        notificationIntent, 0);
                Log.e("notification_TAG","in the notification");
                NotificationManager notificationManager = (NotificationManager) getSystemService(Service.NOTIFICATION_SERVICE);

                String channelId = "channelId";
                String channelName = "channelName";

                android.app.Notification notification;
                NotificationCompat.Builder builder = new NotificationCompat.Builder(NotificationServ.this);
                String TimeTask = task.getDateTime().split("_")[1];
                String hourStr = TimeTask.split(":")[0];
                String minStr = TimeTask.split(":")[1];
                int hour = Integer.parseInt(hourStr);
                int min = Integer.parseInt(minStr);
                if(hour>=5 && hour<= 10)
                {
                    builder.setContentTitle("Good morning .be careful about oversleeping");
                    builder.setContentText("Wake up."+task.getCategory().toString()+" time has arrived.");
                    builder.setSmallIcon(R.drawable.logo_second);
                    builder.setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.icon));
                    builder.setChannelId(channelId);
                }
                else{
                    builder.setContentTitle("the best time for a new beginning is right now");
                    builder.setContentText(task.getCategory().toString()+" time has arrived.");
                    builder.setSmallIcon(R.drawable.logo_second);
                    builder.setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.icon));
                    builder.setChannelId(channelId);
                }


                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                    NotificationChannel notificationChannel = new NotificationChannel(channelId,channelName,NotificationManager.IMPORTANCE_HIGH);
                    notificationManager.createNotificationChannel(notificationChannel);
                    builder.setChannelId(channelId);
                }

                notification = builder.build();

                notificationManager.notify(1,notification);
                builder.setContentIntent(Intent);
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