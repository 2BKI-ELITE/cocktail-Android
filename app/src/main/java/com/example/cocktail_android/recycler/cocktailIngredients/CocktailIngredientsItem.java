package com.example.cocktail_android.recycler.cocktailIngredients;

import com.example.cocktail_android.objects.Ingredient;
import com.example.cocktail_android.redis.controllers.IngredientController;

import java.util.UUID;

public class CocktailIngredientsItem {

    private Ingredient ingredient;
    private int amount;

    public CocktailIngredientsItem(Ingredient ingredient, int amount) {
        this.ingredient = ingredient;
        this.amount = amount;
    }

    public Ingredient getIngredient() {
        return ingredient;
    }

    public void setIngredient(Ingredient ingredient) {
        this.ingredient = ingredient;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    @Override
    public String toString() {
        return "cit:" + ingredient.getIngredientId().toString() + ":" + amount;
    }

    public static CocktailIngredientsItem fromString(String s) {
        UUID ingredientId = UUID.fromString(s.split(":")[1]);
        int amount1 = Integer.valueOf(s.split(":")[2]);

        Ingredient ingredient1 = IngredientController.ingredients.get(ingredientId);
        return new CocktailIngredientsItem(ingredient1, amount1);
    }
}
