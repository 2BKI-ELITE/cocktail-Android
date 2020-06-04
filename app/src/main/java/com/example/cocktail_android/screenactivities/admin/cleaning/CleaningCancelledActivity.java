package com.example.cocktail_android.screenactivities.admin.cleaning;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.cocktail_android.R;
import com.example.cocktail_android.objects.Ingredient;
import com.example.cocktail_android.redis.controllers.IngredientController;
import com.example.cocktail_android.redis.controllers.MachineController;
import com.example.cocktail_android.screenactivities.admin.AdminPanelActivity;

public class CleaningCancelledActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        int currentNightMode = getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK;

        if (currentNightMode == Configuration.UI_MODE_NIGHT_YES)
            setTheme(R.style.darktheme);
        else
            setTheme(R.style.AppTheme);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_cleaning_cancelled);

        MachineController.currentActivity = "admin_clean_cancelled";

        findViewById(R.id.cleaningfailedscreen_btFinish).setOnClickListener(this);

        final ImageButton backButton = findViewById(R.id.cleaningfailedscreen_btBack);
        backButton.setOnClickListener(this);

        Ingredient ingredient1 = IngredientController.getIngredientByPump(1);
        if(ingredient1 != null)
            ((TextView) findViewById(R.id.cleaningfailedscreen_pump1Text)).setText(ingredient1.getName());
        else
            ((TextView) findViewById(R.id.cleaningfailedscreen_pump1Text)).setText("- nicht belegt -");

        Ingredient ingredient2 = IngredientController.getIngredientByPump(2);
        if(ingredient2 != null)
            ((TextView) findViewById(R.id.cleaningfailedscreen_pump2Text)).setText(ingredient2.getName());
        else
            ((TextView) findViewById(R.id.cleaningfailedscreen_pump2Text)).setText("- nicht belegt -");

        Ingredient ingredient3 = IngredientController.getIngredientByPump(3);
        if(ingredient3 != null)
            ((TextView) findViewById(R.id.cleaningfailedscreen_pump3Text)).setText(ingredient3.getName());
        else
            ((TextView) findViewById(R.id.cleaningfailedscreen_pump3Text)).setText("- nicht belegt -");

        Ingredient ingredient4 = IngredientController.getIngredientByPump(4);
        if(ingredient4 != null)
            ((TextView) findViewById(R.id.cleaningfailedscreen_pump4Text)).setText(ingredient4.getName());
        else
            ((TextView) findViewById(R.id.cleaningfailedscreen_pump4Text)).setText("- nicht belegt -");

        Ingredient ingredient5 = IngredientController.getIngredientByPump(5);
        if(ingredient5 != null)
            ((TextView) findViewById(R.id.cleaningfailedscreen_pump5Text)).setText(ingredient5.getName());
        else
            ((TextView) findViewById(R.id.cleaningfailedscreen_pump5Text)).setText("- nicht belegt -");

        Ingredient ingredient6 = IngredientController.getIngredientByPump(6);
        if(ingredient6 != null)
            ((TextView) findViewById(R.id.cleaningfailedscreen_pump6Text)).setText(ingredient6.getName());
        else
            ((TextView) findViewById(R.id.cleaningfailedscreen_pump6Text)).setText("- nicht belegt -");
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.cleaningfailedscreen_btFinish:
                Intent intent = new Intent(getApplicationContext(), AdminPanelActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                break;

            case R.id.cleaningfailedscreen_btBack:
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
