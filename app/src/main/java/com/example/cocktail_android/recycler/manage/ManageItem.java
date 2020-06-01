package com.example.cocktail_android.recycler.manage;

import android.graphics.Bitmap;

import com.example.cocktail_android.objects.Cocktail;

public class ManageItem {

    private Bitmap image;
    private String mText1;
    private String mText2;
    private Cocktail cocktail;

    public ManageItem(Bitmap image, String mText1, String mText2, Cocktail cocktail) {
        this.image = image;
        this.mText1 = mText1;
        this.mText2 = mText2;
        this.cocktail = cocktail;
    }

    /**
     * Returns cocktail image.
     * @return Bitmap
     */
    public Bitmap getImage() {
        return image;
    }

    /**
     * Returns cocktail name.
     * @return String
     */
    public String getmText1() {
        return mText1;
    }

    /**
     * Returns cocktail description.
     * @return String
     */
    public String getmText2() { return mText2; }

    /**
     * Returns cocktail object.
     * @return Cocktail
     */
    public Cocktail getCocktail() {
        return cocktail;
    }
}
