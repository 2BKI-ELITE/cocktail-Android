package com.example.cocktail_android.screenactivities.admin;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.cocktail_android.R;
import com.example.cocktail_android.redis.controllers.MachineController;

public class CleanActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_cleaning);

        MachineController.currentActivity = "admin_clean";
    }
}
