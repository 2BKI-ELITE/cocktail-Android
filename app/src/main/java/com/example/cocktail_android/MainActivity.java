package com.example.cocktail_android;

import androidx.appcompat.app.AppCompatActivity;

import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.content.Intent;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        int currentNightMode = getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK;

        switch(currentNightMode) {
            case Configuration.UI_MODE_NIGHT_YES:
                setTheme(R.style.darktheme);
                break;
            default:
                setTheme(R.style.AppTheme);
        }



        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen);
        final ImageButton adminPanelBt = findViewById(R.id.adminPanelBt);
        adminPanelBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(MainActivity.this, AdminPanelActivity.class);
                startActivity(intent1);

            }
        });

    }
}
