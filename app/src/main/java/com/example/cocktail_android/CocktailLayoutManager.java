package com.example.cocktail_android;


import android.content.Context;

import androidx.recyclerview.widget.LinearLayoutManager;

public class CocktailLayoutManager extends LinearLayoutManager {
    private int mParentWidth;
    private int mItemWidth;

    public CocktailLayoutManager(Context context, int parentWidth, int itemWidth) {
        super(context);
        mParentWidth = parentWidth;
        mItemWidth = itemWidth;
    }

    @Override
    public int getPaddingLeft() {
        return Math.round(mParentWidth / 2f - mItemWidth / 2f);
    }

    @Override
    public int getPaddingRight() {
        return getPaddingLeft();
    }
}
