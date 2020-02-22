package com.example.cocktail_android.screenactivities;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.StrictMode;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

import com.example.cocktail_android.R;
import com.example.cocktail_android.mysql.DatabaseManager;
import com.example.cocktail_android.redis.CommunicationManager;

public class LoadingActivity extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        int currentNightMode = getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK;

        if (currentNightMode == Configuration.UI_MODE_NIGHT_YES)
            setTheme(R.style.darktheme);
        else
            setTheme(R.style.AppTheme);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);

        if(!MainActivity.DUMMY_MODE) {
            boolean databaseConnected = DatabaseManager.connect();

            if(databaseConnected) {
                boolean jedisConnected = CommunicationManager.establishConnection();

                if(jedisConnected) {
                    CommunicationManager.setupSubscriber();
                } else {
                    openErrorActivity();
                }
            } else {
                openErrorActivity();
            }
        } else {
            openMainActivity();
        }
    }

    private void openErrorActivity() {
        Thread welcomeThread = new Thread() {

            @Override
            public void run() {
                try {
                    super.run();
                    sleep(2000);
                } catch (Exception e) {
                } finally {
                    Intent intent = new Intent(LoadingActivity.this, NoConnectionActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        };
        welcomeThread.start();
    }

    private void openMainActivity() {
        Thread welcomeThread = new Thread() {

            @Override
            public void run() {
                try {
                    super.run();
                    sleep(5000);
                } catch (Exception e) {
                } finally {
                    Intent intent = new Intent(LoadingActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        };
        welcomeThread.start();
    }
}