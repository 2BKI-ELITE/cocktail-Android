package com.example.cocktail_android.screenactivities;

public class IngredientsItem {
    private int actualMl, fullMl;
    private String ingredientName, pump;


    public IngredientsItem(int actualMl, int fullMl, String ingredientName, String pump){
        this.actualMl = actualMl;
        this.fullMl = fullMl;
        this.ingredientName = ingredientName;
        this.pump = pump;
    }


    public int getActualMl() {
        return actualMl;
    }

    public int getFullMl() {
        return fullMl;
    }

    public String getIngredientName() {
        return ingredientName;
    }

    public String getPump() {
        return pump;
    }
}



