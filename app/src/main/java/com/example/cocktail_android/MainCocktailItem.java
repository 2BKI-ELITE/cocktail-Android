package com.example.cocktail_android;

import com.example.cocktail_android.objects.Cocktail;

public class MainCocktailItem {
    private int mImageResource;
    private String mText1;
    private Cocktail cocktail;

    public MainCocktailItem(int mImageResource, String mText1, Cocktail cocktail) {
        this.mImageResource = mImageResource;
        this.mText1 = mText1;
        this.cocktail = cocktail;
    }

    public int getmImageResource() {
        return mImageResource;
    }


    public String getmText1() {
        return mText1;
    }

    public Cocktail getCocktail() {
        return cocktail;
    }
}

