package com.example.cocktail_android.screenactivitys;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.widget.ImageButton;

import com.example.cocktail_android.R;

public class AdminPanelActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {



        int currentNightMode = getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK;

        if (currentNightMode == Configuration.UI_MODE_NIGHT_YES)
            setTheme(R.style.darktheme);
        else
            setTheme(R.style.AppTheme);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adminpanel);

        //Listener for Manage Button
        final ImageButton mManageBt = findViewById(R.id.adminpanel_btManage);
        mManageBt.setOnClickListener(view -> {
            Intent intent1 = new Intent(AdminPanelActivity.this, ManageActivity.class);
            startActivity(intent1);
        });

        //Listener for Clean Button
        final ImageButton mCleanBt = findViewById(R.id.adminpanel_btClean);
        mManageBt.setOnClickListener(view1 -> {
            Intent intent1 = new Intent(AdminPanelActivity.this, CleanActivity.class);
            startActivity(intent1);
        });
    }
}
