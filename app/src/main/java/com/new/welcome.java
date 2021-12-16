package com.example;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.example.myapplication.R;

public class welcome extends AppCompatActivity {

    ImageView logo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        getSupportActionBar().hide();

        Intent in = getIntent();
        String token = in.getStringExtra("token");
        String username = in.getStringExtra("username");
        logo = findViewById(R.id.logo2);
        Animation animation = AnimationUtils.loadAnimation(welcome.this, R.anim.zoomin);
        logo.startAnimation(animation);
        SystemClock.sleep(200);

        Intent out = new Intent(welcome.this, MainActivity.class);
        out.putExtra("token",token);
        out.putExtra("username",username);
        startActivity(out);
    }
}