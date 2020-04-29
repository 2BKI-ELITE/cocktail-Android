package com.example.cocktail_android.screenactivities.admin;

import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.cocktail_android.R;
import com.example.cocktail_android.redis.controllers.auth.AdminAuthController;
import com.example.cocktail_android.redis.controllers.MachineController;
import com.example.cocktail_android.screenactivities.MainActivity;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        int currentNightMode = getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK;

        if (currentNightMode == Configuration.UI_MODE_NIGHT_YES)
            setTheme(R.style.darktheme);
        else
            setTheme(R.style.AppTheme);

        if(!MainActivity.DUMMY_MODE)
            AdminAuthController.start();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_login);

        MachineController.currentActivity = "admin_login";
    }


    @Override
    public void onClick(View view) {

    }

    @Override
    public void onBackPressed() {
        AdminAuthController.cancel();
        finish();
    }
}
