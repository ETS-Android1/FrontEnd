package com.example;

import android.app.Application;
import android.content.SharedPreferences;

public class ShanbehApp extends Application {
    public SharedPreferences sharedPreferences;

    @Override
    public void onCreate() {
        super.onCreate();

        sharedPreferences = getSharedPreferences("authentication",MODE_PRIVATE);
    }
}
