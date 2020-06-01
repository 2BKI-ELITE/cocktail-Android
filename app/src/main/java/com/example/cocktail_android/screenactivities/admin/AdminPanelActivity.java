package com.example.cocktail_android.screenactivities.admin;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

import com.example.cocktail_android.R;
import com.example.cocktail_android.redis.controllers.MachineController;
import com.example.cocktail_android.screenactivities.MainActivity;
import com.example.cocktail_android.screenactivities.admin.cleaning.CleaningActivity;
import com.example.cocktail_android.screenactivities.admin.cocktails.ManageActivity;
import com.example.cocktail_android.screenactivities.admin.users.UserActivity;

public class AdminPanelActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        int currentNightMode = getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK;

        if (currentNightMode == Configuration.UI_MODE_NIGHT_YES)
            setTheme(R.style.darktheme);
        else
            setTheme(R.style.AppTheme);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adminpanel);

        MachineController.currentActivity = "admin_panel";

        // Listener for Manage Button
        final ImageButton mManageBt = findViewById(R.id.adminpanel_btManage);
        mManageBt.setOnClickListener(this);

        // Listener for Clean Button
        final ImageButton mCleanBt = findViewById(R.id.adminpanel_btClean);
        mCleanBt.setOnClickListener(this);

        // Listener for Users Button
        final ImageButton mUserBt = findViewById(R.id.adminpanel_btUser);
        mUserBt.setOnClickListener(this);

        // Listener for Settings Button
        final ImageButton mSettingsBt = findViewById(R.id.adminpanel_btSettings);
        mSettingsBt.setOnClickListener(this);

        // Listener for Ingredients Button
        final ImageButton mIngredientsBt = findViewById(R.id.adminpanel_btIngredients);
        mIngredientsBt.setOnClickListener(this);

        // Listener for Back Button
        final ImageButton mBackBt = findViewById(R.id.adminpanel_btBack);
        mBackBt.setOnClickListener(this);
      }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {

            case R.id.adminpanel_btClean:
                Intent intent1 = new Intent(AdminPanelActivity.this, CleaningActivity.class);
                startActivity(intent1);
                break;

            case R.id.adminpanel_btManage:
                Intent intent2 = new Intent(AdminPanelActivity.this, ManageActivity.class);
                startActivity(intent2);
                break;

            case R.id.adminpanel_btUser:
                Intent intent4 = new Intent(AdminPanelActivity.this, UserActivity.class);
                startActivity(intent4);
                break;

            case R.id.adminpanel_btIngredients:
                Intent intent3 = new Intent(AdminPanelActivity.this, IngredientsActivity.class);
                startActivity(intent3);
                break;

            case R.id.adminpanel_btSettings:
                Intent intent5 = new Intent(AdminPanelActivity.this, SettingsActivity.class);
                startActivity(intent5);
                break;

            case R.id.adminpanel_btBack:
                onBackPressed();
                break;

            default:
                break;
        }
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}
