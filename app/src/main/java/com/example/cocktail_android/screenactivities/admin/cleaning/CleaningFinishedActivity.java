package com.example.cocktail_android.screenactivities.admin.cleaning;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.cocktail_android.R;
import com.example.cocktail_android.redis.controllers.MachineController;
import com.example.cocktail_android.screenactivities.admin.AdminPanelActivity;
import com.example.cocktail_android.screenactivities.admin.users.UserActivity;

public class CleaningFinishedActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        int currentNightMode = getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK;

        if (currentNightMode == Configuration.UI_MODE_NIGHT_YES)
            setTheme(R.style.darktheme);
        else
            setTheme(R.style.AppTheme);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_cleaning_finished);

        MachineController.currentActivity = "admin_clean_finished";

        findViewById(R.id.cleaningfinishedscreen_btFinish).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.cleaningfinishedscreen_btFinish:
                Intent intent = new Intent(getApplicationContext(), AdminPanelActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                break;

            default:
                break;
        }
    }
}
