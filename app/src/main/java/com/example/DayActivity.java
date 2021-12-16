package com.example;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.example.myapplication.R;

public class DayActivity extends AppCompatActivity {
    Bundle extras ;
    TextView pageTitle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // day activity
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_day);
        extras = getIntent().getExtras();
        pageTitle = findViewById(R.id.day_activity_title_id);
        pageTitle.setText(extras.getString("Date"));
    }
}