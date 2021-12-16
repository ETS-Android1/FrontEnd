package com.example.BroadCast;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;
import android.util.Log;

public class AlarmController {
    private Context m_Context;
    private AlarmManager mgr;
    private static final long PERIOD = 59000;

    public AlarmController(Context context){

        m_Context = context;
        mgr = (AlarmManager)m_Context.getSystemService(Context.ALARM_SERVICE);
    }

    public void StartAlarm(){

        StopAlarm();

        Intent i = new Intent(m_Context, OnAlarmReceiver.class);
        PendingIntent pi=PendingIntent.getBroadcast(m_Context, 0,i, PendingIntent.FLAG_UPDATE_CURRENT);
        mgr.setRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP, SystemClock.elapsedRealtime(),PERIOD,pi);

        Log.i("AlarmController", "StartAlarm");
    }

    public void StopAlarm(){
        Intent i = new Intent(m_Context, OnAlarmReceiver.class);
        PendingIntent pi=PendingIntent.getBroadcast(m_Context, 0,i, PendingIntent.FLAG_UPDATE_CURRENT);
        mgr.cancel(pi);

        Log.i("AlarmController", "StopAlarm");
    }
}
