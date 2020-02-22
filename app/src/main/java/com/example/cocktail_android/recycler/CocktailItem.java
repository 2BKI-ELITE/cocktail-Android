package com.example.cocktail_android.recycler;

import android.graphics.Bitmap;

import com.example.cocktail_android.objects.Cocktail;

public class CocktailItem {
    private Bitmap image;
    private String mText1;
    private Cocktail cocktail;

    public CocktailItem(Bitmap image, String mText1, Cocktail cocktail) {
        this.image = image;
        this.mText1 = mText1;
        this.cocktail = cocktail;
    }

    public Bitmap getImage() {
        return image;
    }


    public String getmText1() {
        return mText1;
    }

    public Cocktail getCocktail() {
        return cocktail;
    }
}
