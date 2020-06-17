package com.example.cocktail_android.screenactivities.admin.ingredients;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageButton;
import android.widget.Switch;

import androidx.appcompat.app.AppCompatActivity;

import com.example.cocktail_android.R;
import com.example.cocktail_android.objects.Ingredient;
import com.example.cocktail_android.redis.controllers.IngredientController;
import com.example.cocktail_android.redis.controllers.MachineController;
import com.google.android.material.textfield.TextInputEditText;

import java.util.UUID;

public class IngredientAddActivity extends AppCompatActivity implements View.OnClickListener {

    int pump;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        int currentNightMode = getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK;

        if (currentNightMode == Configuration.UI_MODE_NIGHT_YES)
            setTheme(R.style.darktheme);
        else
            setTheme(R.style.AppTheme);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_ingredients_add);

        MachineController.currentActivity = "admin_ingredients_add";

        String[] items = new String[6];

        for(int i = 0; i < 6; i++) {
            Ingredient ingredient1 = IngredientController.getIngredientByPump(i + 1);

            if(ingredient1 != null)
                items[i] = "Pumpe " + (i + 1) + " (belegt: " + ingredient1.getName() + ")";
            else
                items[i] = "Pumpe " + (i + 1);
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.dropdown_item, items);

        AutoCompleteTextView editTextFilledExposedDropdown = findViewById(R.id.addingredient_actvPump);
        editTextFilledExposedDropdown.setAdapter(adapter);

        editTextFilledExposedDropdown.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                pump = Integer.valueOf(s.toString().split(" ")[1]);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        final ImageButton backButton = findViewById(R.id.addingredient_btBack);
        backButton.setOnClickListener(this);

        final ImageButton saveButton = findViewById(R.id.addingredient_btSave);
        saveButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.addingredient_btSave:
                TextInputEditText name = findViewById(R.id.addingredient_tietName);
                TextInputEditText level = findViewById(R.id.addingredient_tietLevel);
                TextInputEditText capacity = findViewById(R.id.addingredient_tietCapacity);
                Switch alcoholic = findViewById(R.id.addingredient_swAlcohol);

                IngredientController.createIngredient(this, new Ingredient(UUID.randomUUID(), name.getText().toString(), alcoholic.isChecked(), pump, Integer.valueOf(level.getText().toString()), Integer.valueOf(capacity.getText().toString())));

                Intent intent = new Intent(getApplicationContext(), IngredientsActivity.class);

                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                startActivity(intent);
                break;

            case R.id.addingredient_btBack:
                onBackPressed();
                break;

            default:
                break;
        }
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, IngredientsActivity.class);

        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        startActivity(intent);
    }
}
