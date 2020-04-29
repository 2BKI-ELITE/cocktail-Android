package com.example.cocktail_android.screenactivities.admin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.res.Configuration;
import android.os.Bundle;

import com.example.cocktail_android.objects.Cocktail;
import com.example.cocktail_android.objects.Ingredient;
import com.example.cocktail_android.recycler.ingredients.IngredientsItemAdapter;
import com.example.cocktail_android.R;
import com.example.cocktail_android.redis.controllers.CocktailController;
import com.example.cocktail_android.redis.controllers.IngredientController;
import com.example.cocktail_android.redis.controllers.MachineController;
import com.example.cocktail_android.recycler.ingredients.IngredientsItem;

import java.util.ArrayList;

public class IngredientsActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        int currentNightMode = getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK;

        if (currentNightMode == Configuration.UI_MODE_NIGHT_YES)
            setTheme(R.style.darktheme);
        else
            setTheme(R.style.AppTheme);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_ingredients);

        MachineController.currentActivity = "admin_ingredients";

        ArrayList<IngredientsItem> exampleList = new ArrayList<>();
        ArrayList<Ingredient> ingredients = new ArrayList<Ingredient>(IngredientController.ingredients.values());

        for(int i = 0; i < ingredients.size(); i++) {
            exampleList.add(IngredientController.convertToIngredientItem(ingredients.get(i)));
        }

        mRecyclerView = findViewById(R.id.ingredientsview__rv);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mAdapter = new IngredientsItemAdapter(exampleList);

        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(mLayoutManager);
    }
}
