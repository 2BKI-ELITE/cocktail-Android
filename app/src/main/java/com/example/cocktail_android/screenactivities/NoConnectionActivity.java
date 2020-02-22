package com.example.cocktail_android.screenactivities;

import android.content.res.Configuration;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.cocktail_android.R;

public class NoConnectionActivity extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        int currentNightMode = getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK;

        if (currentNightMode == Configuration.UI_MODE_NIGHT_YES)
            setTheme(R.style.darktheme);
        else
            setTheme(R.style.AppTheme);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_noconnection);
    }
}
