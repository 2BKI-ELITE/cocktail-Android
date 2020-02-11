package com.example.cocktail_android;

public class ManageItem {

    private int mImageResource;
    private String mTest1, mText2;

    public ManageItem(int mImageResource, String mTest1, String mText2){
        this.mImageResource = mImageResource;
        this.mTest1 = mTest1;
        this.mText2 = mText2;
    }

    public int getmImageResource(){
        return mImageResource;
    }

    public String getmText2(){
        return mText2;
    }

    public String getmTest1(){
        return mTest1;
    }
}
