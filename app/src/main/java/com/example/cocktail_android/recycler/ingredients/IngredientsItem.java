package com.example.cocktail_android.recycler.ingredients;

public class IngredientsItem {
    private int currentMl, fullMl, pump;
    private String ingredientName;


    public IngredientsItem(int currentMl, int fullMl, String ingredientName, int pump) {
        this.currentMl = currentMl;
        this.fullMl = fullMl;
        this.ingredientName = ingredientName;
        this.pump = pump;
    }


    public int getCurrentMl() {
        return currentMl;
    }

    public int getFullMl() {
        return fullMl;
    }

    public String getIngredientName() {
        return ingredientName;
    }

    public int getPump() {
        return pump;
    }
}