package com.example.dementedcare;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.content.Intent;

import androidx.multidex.MultiDex;

public class SplashScreen extends AppCompatActivity {

    private static int SPLASH_TIME_OUT = 3000;    //Splash screen timer

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        //code start
        MultiDex.install(this);

        // Delay and start the MainActivity after the splash screen time out
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // Start the MainActivity
                Intent intent = new Intent(SplashScreen.this, LoginScreen.class);
                startActivity(intent);


                // Close the splash screen activity
                finish();
            }
        }, SPLASH_TIME_OUT);

        //code end
    }
}