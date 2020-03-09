package com.example.cocktail_android.screenactivities.admin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.example.cocktail_android.R;
import com.example.cocktail_android.screenactivities.InProgressActivity;
import com.example.cocktail_android.screenactivities.InformationActivity;

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
      }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {

            case R.id.adminpanel_btClean:
                Intent intent1 = new Intent(AdminPanelActivity.this, CleanActivity.class);
                startActivity(intent1);
                break;

            case R.id.adminpanel_btManage:
                Intent intent2 = new Intent(AdminPanelActivity.this, ManageActivity.class);
                startActivity(intent2);
                break;

            case R.id.adminpanel_btUser:
                Intent intent4 = new Intent(AdminPanelActivity.this, InProgressActivity.class);
                startActivity(intent4);
                break;

            case R.id.adminpanel_btIngredients:
                Intent intent3 = new Intent(AdminPanelActivity.this, IngredientsActivity.class);
                startActivity(intent3);
                break;

            case R.id.adminpanel_btSettings:
                Intent intente4 = new Intent(AdminPanelActivity.this, InformationActivity.class);
                startActivity(intente4);
            default:
                break;
        }
    }
}
