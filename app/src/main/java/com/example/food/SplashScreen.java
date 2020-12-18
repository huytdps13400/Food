package com.example.food;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;

import com.example.food.Onboard.IntroActivity;

public class SplashScreen extends AppCompatActivity {
    public static int SPLASH_SCREEN = 5000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent i = new Intent(SplashScreen.this, IntroActivity.class);
                startActivity(i);
//                overridePendingTransition(R.anim.slide_up_left,R.anim.slide_up_left);
                finish();
            }
        },SPLASH_SCREEN);
    }
    }
