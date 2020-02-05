package com.example.cocktail_android;

import androidx.appcompat.app.AppCompatActivity;

import android.content.res.Configuration;
import android.os.Bundle;
import android.os.StrictMode;

import com.example.cocktail_android.redis.CommunicationManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.UUID;

import redis.clients.jedis.JedisPubSub;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        System.out.println("0");

        CommunicationManager.establishConnection();

        System.out.println("1");

        CommunicationManager.setupSubscriber();

        System.out.println("2");

        int currentNightMode = getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK;

        System.out.println("3");

        switch(currentNightMode) {
            case Configuration.UI_MODE_NIGHT_YES:
                setTheme(R.style.darktheme);
                break;
            default:
                setTheme(R.style.AppTheme);
        }

        System.out.println("4");

        super.onCreate(savedInstanceState);
        System.out.println("5");
        setContentView(R.layout.activity_main_screen);

    }
}
