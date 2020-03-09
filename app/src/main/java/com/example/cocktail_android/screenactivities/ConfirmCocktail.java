package com.example.cocktail_android.screenactivities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageButton;

import com.example.cocktail_android.R;
import com.example.cocktail_android.objects.Cocktail;
import com.example.cocktail_android.redis.controllers.CocktailController;

import java.util.UUID;

public class ConfirmCocktail extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Cocktail cocktail = CocktailController.cocktails.get(UUID.fromString(getIntent().getStringExtra("cocktailId")));
        setContentView(R.layout.activity_cocktail_make_confirm);

        final ImageButton mBtStart = findViewById(R.id.confirm_btStartMixing);
        mBtStart.setOnClickListener(v -> {
            CocktailController.makeCocktail(cocktail);
        });
    }
}
