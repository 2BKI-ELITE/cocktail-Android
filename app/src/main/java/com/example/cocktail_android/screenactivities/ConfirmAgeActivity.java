package com.example.cocktail_android.screenactivities;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.cocktail_android.R;
import com.example.cocktail_android.enums.CocktailSize;
import com.example.cocktail_android.objects.Cocktail;
import com.example.cocktail_android.redis.controllers.CocktailController;
import com.example.cocktail_android.redis.controllers.auth.ConfirmAgeController;
import com.example.cocktail_android.redis.controllers.MachineController;
import com.example.cocktail_android.screenactivities.admin.cocktails.ManageActivity;

import java.util.UUID;

public class ConfirmAgeActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        int currentNightMode = getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK;

        if (currentNightMode == Configuration.UI_MODE_NIGHT_YES)
            setTheme(R.style.darktheme);
        else
            setTheme(R.style.AppTheme);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_age);

        MachineController.currentActivity = "confirm_age";

        Cocktail cocktail = CocktailController.cocktails.get(UUID.fromString(getIntent().getStringExtra("cocktailId")));
        CocktailSize size = CocktailSize.valueOf(getIntent().getStringExtra("size"));

        ConfirmAgeController.start(cocktail, size);
    }


    @Override
    public void onClick(View view) {

    }

    @Override
    public void onBackPressed() {
        ConfirmAgeController.cancel();
        Intent intent = new Intent(this, MainActivity.class);

        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        startActivity(intent);
    }
}