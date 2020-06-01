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

    /**
     * Returns ingredient fill level.
     * @return int Returns level in ml.
     */
    public int getCurrentMl() {
        return currentMl;
    }

    /**
     * Returns ingredient fill capacity.
     * @return int Returns capacity in ml.
     */
    public int getFullMl() {
        return fullMl;
    }

    /**
     * Returns ingredient name.
     * @return String
     */
    public String getIngredientName() {
        return ingredientName;
    }

    /**
     * Returns ingredient pump id.
     * @return int
     */
    public int getPump() {
        return pump;
    }
}