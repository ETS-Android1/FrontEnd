package com.example.BroadCast;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.WifiManager;

import com.example.CustomeAlertDialog;

public class wifiBroadCast extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        WifiManager wifiManager = (WifiManager) context.getSystemService(Service.WIFI_SERVICE);
        if(wifiManager.isWifiEnabled())
        {
            CustomeAlertDialog InternetNoConnection = new CustomeAlertDialog(context,"Error","Please check your internet connection");
        }
    }
}
