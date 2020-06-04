package com.example.cocktail_android.screenactivities.admin.cleaning;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.example.cocktail_android.R;
import com.example.cocktail_android.redis.controllers.CocktailController;
import com.example.cocktail_android.redis.controllers.MachineController;
import com.example.cocktail_android.screenactivities.MainActivity;
import com.example.cocktail_android.screenactivities.admin.AdminPanelActivity;

public class CleaningActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        int currentNightMode = getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK;

        if (currentNightMode == Configuration.UI_MODE_NIGHT_YES)
            setTheme(R.style.darktheme);
        else
            setTheme(R.style.AppTheme);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_cleaning);

        MachineController.currentActivity = "admin_clean";

        CocktailController.cleanActivity = this;

        // Listener for start button
        final ImageButton mBtStart = findViewById(R.id.cleanscreen_btStart);
        mBtStart.setOnClickListener(this);

        final ImageButton backButton = findViewById(R.id.cleanscreen_btBack);
        backButton.setOnClickListener(this);

        CocktailController.setButtonBlur(getApplicationContext());
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.cleanscreen_btStart:
                MachineController.cleanMachine();
                break;

            case R.id.cleanscreen_btBack:
                onBackPressed();
                break;

            default:
                break;
        }
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, AdminPanelActivity.class);

        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        startActivity(intent);
    }
}
