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

        CommunicationManager.establishConnection();

        CommunicationManager.onMessageReceive(new JedisPubSub() {
            @Override
            public void onMessage(String channel, String message) {
                try {
                    JSONObject object = new JSONObject(message);

                    System.out.println("Received message on channel [" + channel + "]");
                    System.out.println(object.toString());
                } catch (JSONException ex) {
                    ex.printStackTrace();
                }
            }
        }, "general");

        int currentNightMode = getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK;

        switch(currentNightMode) {
            case Configuration.UI_MODE_NIGHT_YES:
                setTheme(R.style.darktheme);
                break;
            default:
                setTheme(R.style.AppTheme);
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen);

    }
}
