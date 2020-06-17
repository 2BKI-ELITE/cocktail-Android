package com.example.cocktail_android.screenactivities.cocktail;

import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;

import com.example.cocktail_android.R;
import com.example.cocktail_android.redis.controllers.MachineController;

public class CocktailInProgressActivity extends AppCompatActivity {

    public static boolean allowBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        int currentNightMode = getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK;

        if (currentNightMode == Configuration.UI_MODE_NIGHT_YES)
            setTheme(R.style.darktheme);
        else
            setTheme(R.style.AppTheme);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cocktail_inprogress);

        MachineController.currentActivity = "cocktail_inprogress";

        ProgressBar pgsBar = findViewById(R.id.cocktailInProgress_pBar);
        pgsBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void onBackPressed() {
        if(allowBack)
            super.onBackPressed();
    }
}
