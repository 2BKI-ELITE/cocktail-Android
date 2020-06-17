package com.example.cocktail_android.screenactivities.admin.cocktails;

import android.app.AlertDialog;
import android.content.res.Configuration;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cocktail_android.R;
import com.example.cocktail_android.objects.Ingredient;
import com.example.cocktail_android.recycler.cocktailIngredients.CocktailIngredientsItem;
import com.example.cocktail_android.recycler.cocktailIngredients.CocktailIngredientsItemAdapter;
import com.example.cocktail_android.redis.controllers.IngredientController;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.List;

public class CocktailEditIngredientsActivity extends AppCompatActivity implements View.OnClickListener {

    private RecyclerView mRecyclerView;
    private CocktailIngredientsItemAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private ArrayList<CocktailIngredientsItem> exampleList = new ArrayList<>();
    private String name;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        int currentNightMode = getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK;

        if (currentNightMode == Configuration.UI_MODE_NIGHT_YES)
            setTheme(R.style.darktheme);
        else
            setTheme(R.style.AppTheme);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_cocktails_edit_ingredients);

        ArrayList<String> ingredientsList = getIntent().getStringArrayListExtra("cocktailIngredients");

        for(int i = 0; i < 0; i++)
            exampleList.add(CocktailIngredientsItem.fromString(ingredientsList.get(i)));

        mRecyclerView = findViewById(R.id.rv_item_list);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mAdapter = new CocktailIngredientsItemAdapter(exampleList);

        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(mLayoutManager);

        final ImageButton btAddIngredient = findViewById(R.id.editcocktailingredients_btAdd);
        btAddIngredient.setOnClickListener(this);
    }

    private void showNewDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();

        View view = inflater.inflate(R.layout.dialog_ingredient, null);

        builder.setView(view);

        builder.setPositiveButton("Hinzufügen", (dialog, which) -> {
            TextInputEditText amount = view.findViewById(R.id.editcocktailingredients_tietAmount);

            Ingredient ingredient = IngredientController.getIngredientByName(name);
            CocktailIngredientsItem item = IngredientController.convertToCocktailIngredientItem(ingredient, Integer.valueOf(amount.getText().toString()));

            mAdapter.mExampleList.add(item);
            exampleList.add(item);
            mAdapter.notifyDataSetChanged();
        });

        builder.setNegativeButton("Abbrechen", (dialog, which) -> {
            dialog.dismiss();
        });

        AlertDialog d = builder.create();
        d.show();

        List<String> items = new ArrayList<>();
        List<Ingredient> ingredients = new ArrayList<>(IngredientController.ingredients.values());
        List<Ingredient> eIngredients = new ArrayList<>();

        for(int j = 0; j < exampleList.size(); j++) {
            eIngredients.add(exampleList.get(j).getIngredient());
        }

        for(int i = 0; i < IngredientController.ingredients.size(); i++) {
            if(!eIngredients.contains(ingredients.get(i)))
                items.add(ingredients.get(i).getName());
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.dropdown_item, items);

        AutoCompleteTextView editTextFilledExposedDropdown = d.findViewById(R.id.editcocktailingredients_actvIngredient);
        editTextFilledExposedDropdown.setAdapter(adapter);

        editTextFilledExposedDropdown.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                name = s.toString();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.editcocktailingredients_btAdd:
                showNewDialog();
                break;

            default:
                break;
        }
    }

    @Override
    public void onBackPressed() {
        // TODO: 16.06.2020 Back with info
    }
}