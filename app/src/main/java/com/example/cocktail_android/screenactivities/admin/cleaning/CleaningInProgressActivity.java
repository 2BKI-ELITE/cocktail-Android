package com.example.cocktail_android.screenactivities.admin.cleaning;

import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;

import com.example.cocktail_android.R;
import com.example.cocktail_android.redis.controllers.MachineController;

public class CleaningInProgressActivity extends AppCompatActivity {

    public static boolean allowBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        int currentNightMode = getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK;

        if (currentNightMode == Configuration.UI_MODE_NIGHT_YES)
            setTheme(R.style.darktheme);
        else
            setTheme(R.style.AppTheme);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_cleaning_inprogress);

        MachineController.currentActivity = "admin_clean_inProgress";

        ProgressBar pgsBar = findViewById(R.id.cleaningInProgress_pBar);
        pgsBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void onBackPressed() {
        if(allowBack)
            super.onBackPressed();
    }
}
