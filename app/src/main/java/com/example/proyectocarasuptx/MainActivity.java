package com.example.proyectocarasuptx;

import androidx.appcompat.app.AppCompatActivity;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.content.Intent;

import java.util.TimerTask;
import java.util.Timer;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final long delay = 2000;

        TimerTask loadingLogin = new TimerTask(){
            @Override
            public void run(){
                Intent load = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(load);
                finish();
            }
        };

        Timer loadLogin = new Timer();
        loadLogin.schedule(loadingLogin, delay);
    }
}