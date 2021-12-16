package com.example.BroadCast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class OnBootReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {


        AlarmController alarm = new AlarmController(context);
        alarm.StartAlarm();
    }
}
