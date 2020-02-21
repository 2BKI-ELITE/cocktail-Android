package com.example.cocktail_android.recycler;

import com.example.cocktail_android.objects.Cocktail;

public class CocktailItem {
    private int mImageResource;
    private String mText1;
    private Cocktail cocktail;

    public CocktailItem(int mImageResource, String mText1, Cocktail cocktail) {
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
