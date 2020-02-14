package com.example.cocktail_android;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;




public class MainCocktailItemAdapter extends RecyclerView.Adapter<MainCocktailItemAdapter.MainCocktailViewHolder> {
    private ArrayList<MainCocktailItem> mExampleList;
    public static class MainCocktailViewHolder extends RecyclerView.ViewHolder{
        public ImageView mImageView;
        public TextView mTextView1;
        public MainCocktailViewHolder(@NonNull View itemView) {
            super(itemView);
            mImageView = itemView.findViewById(R.id.mainitem_ivCocktail);
            mTextView1 = itemView.findViewById(R.id.mainitem_tvTitle);


        }
    }

    public MainCocktailItemAdapter(ArrayList<MainCocktailItem> examplelist){
        mExampleList = examplelist;
    }

    @NonNull
    @Override
    public MainCocktailViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_maincocktails,parent,false);
        MainCocktailViewHolder evh = new MainCocktailViewHolder(v);
        return evh;
    }

    @Override
    public void onBindViewHolder(@NonNull MainCocktailViewHolder holder, int position) {
        MainCocktailItem currentItem = mExampleList.get(position);
        holder.mImageView.setImageResource(currentItem.getmImageResource());
        holder.mTextView1.setText(currentItem.getmText1());

    }

    @Override
    public int getItemCount() {
        return mExampleList.size();
    }
}

