package com.example.cocktail_android.screenactivities.admin.cocktails;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.cocktail_android.R;
import com.example.cocktail_android.objects.Cocktail;
import com.example.cocktail_android.redis.controllers.CocktailController;

import java.util.ArrayList;

public class CocktailEditActivity extends AppCompatActivity implements View.OnClickListener {

    private Cocktail cocktail;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        int currentNightMode = getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK;

        if (currentNightMode == Configuration.UI_MODE_NIGHT_YES)
            setTheme(R.style.darktheme);
        else
            setTheme(R.style.AppTheme);

        ArrayList<Cocktail> cocktails = new ArrayList<Cocktail>(CocktailController.cocktails.values());
        cocktail = cocktails.get(getIntent().getIntExtra("cocktailPosition", 0));

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_cocktails_edit);

        final ImageButton backButton = findViewById(R.id.editcocktail_btBack);
        backButton.setOnClickListener(this);

        ((TextView) findViewById(R.id.editcocktail_tvTitle)).setText(cocktail.getName());
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.editcocktail_btBack:
                onBackPressed();
                break;

            default:
                break;
        }
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, ManageActivity.class);

        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        startActivity(intent);
    }
}
