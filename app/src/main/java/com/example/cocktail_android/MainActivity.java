package com.example.cocktail_android;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.res.Configuration;
import android.os.Bundle;
import android.os.StrictMode;

import com.example.cocktail_android.redis.CommunicationManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.UUID;

import redis.clients.jedis.JedisPubSub;
import android.view.View;
import android.widget.ImageButton;
import android.content.Intent;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        System.out.println("0");

      //  CommunicationManager.establishConnection();

        System.out.println("1");

      //  CommunicationManager.setupSubscriber();

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



        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final ImageButton adminPanelBt = findViewById(R.id.btAdminPanel);
        adminPanelBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(MainActivity.this, RecyclerActivity.class);
                startActivity(intent1);

            }
        });

    }
}
