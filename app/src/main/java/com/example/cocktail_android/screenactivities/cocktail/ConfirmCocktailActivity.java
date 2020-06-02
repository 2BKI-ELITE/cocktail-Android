package com.example.cocktail_android.screenactivities.cocktail;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.widget.ImageButton;

import com.example.cocktail_android.R;
import com.example.cocktail_android.enums.CocktailSize;
import com.example.cocktail_android.objects.Cocktail;
import com.example.cocktail_android.redis.controllers.CocktailController;
import com.example.cocktail_android.redis.controllers.MachineController;
import com.example.cocktail_android.screenactivities.cocktail.ChooseSizeActvitity;

import java.util.UUID;

public class ConfirmCocktailActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        int currentNightMode = getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK;

        if (currentNightMode == Configuration.UI_MODE_NIGHT_YES)
            setTheme(R.style.darktheme);
        else
            setTheme(R.style.AppTheme);

        super.onCreate(savedInstanceState);
        Cocktail cocktail = CocktailController.cocktails.get(UUID.fromString(getIntent().getStringExtra("cocktailId")));
        setContentView(R.layout.activity_cocktail_make_confirm);

        MachineController.currentActivity = "cocktail_confirm";

        final ImageButton mBtStart = findViewById(R.id.confirm_btStartMixing);
        mBtStart.setOnClickListener(v -> CocktailController.makeCocktail(cocktail, CocktailSize.valueOf(getIntent().getStringExtra("size"))));
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, ChooseSizeActvitity.class);

        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        intent.putExtra("cocktailId", getIntent().getStringExtra("cocktailId"));
        intent.putExtra("size", getIntent().getStringExtra("size"));
        startActivity(intent);
    }
}
