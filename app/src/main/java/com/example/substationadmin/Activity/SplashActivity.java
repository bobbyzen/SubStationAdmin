package com.example.substationadmin.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.example.substationadmin.R;

public class SplashActivity extends AppCompatActivity {
    private int waktu_loading=3000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent main = new Intent(SplashActivity.this, MainActivity.class);
                startActivity(main);
                finish();
            }
        },waktu_loading);
    }
}
