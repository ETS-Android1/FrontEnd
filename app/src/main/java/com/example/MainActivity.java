package com.example;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.example.myapplication.R;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity {
    CustomCalendarView customCalendarView;
    DrawerLayout drawerLayout;
    NavigationView monthNavigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();
        customCalendarView = (CustomCalendarView)findViewById(R.id.custom_calendar_view);
        drawerLayout = findViewById(R.id.drawerMonthLayout);
        findViewById(R.id.imageMenu).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                drawerLayout.openDrawer(GravityCompat.START);
            }
        });
        monthNavigationView = findViewById(R.id.navigationView);
        monthNavigationView.setItemIconTintList(null);
    }
}