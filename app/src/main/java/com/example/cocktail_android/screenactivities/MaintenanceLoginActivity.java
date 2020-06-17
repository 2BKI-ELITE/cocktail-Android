package com.example.cocktail_android.screenactivities;

import android.content.res.Configuration;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.cocktail_android.R;
import com.example.cocktail_android.redis.controllers.MachineController;
import com.example.cocktail_android.redis.controllers.auth.MaintenanceAuthController;

public class MaintenanceLoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        int currentNightMode = getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK;

        if (currentNightMode == Configuration.UI_MODE_NIGHT_YES)
            setTheme(R.style.darktheme);
        else
            setTheme(R.style.AppTheme);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maintenance_login);

        MachineController.currentActivity = "maintenance_login";

        MaintenanceAuthController.start();
    }

    @Override
    public void onBackPressed() {
        MaintenanceAuthController.cancel();
        finish();
    }
}
