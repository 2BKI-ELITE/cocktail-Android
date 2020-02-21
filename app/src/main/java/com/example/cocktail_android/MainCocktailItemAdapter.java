package com.example.cocktail_android;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cocktail_android.screenactivities.AdminPanelActivity;
import com.example.cocktail_android.screenactivities.ChooseSizeActvitity;
import com.example.cocktail_android.screenactivities.MainActivity;

import java.util.ArrayList;




public class MainCocktailItemAdapter extends RecyclerView.Adapter<MainCocktailItemAdapter.MainCocktailViewHolder> {
    private ArrayList<MainCocktailItem> mExampleList;
    public static class MainCocktailViewHolder extends RecyclerView.ViewHolder{
        public ImageView mImageView;
        public TextView mTextView1;
        private final Context context;
        public MainCocktailViewHolder(@NonNull View itemView) {
            super(itemView);
            context = itemView.getContext();
            mImageView = itemView.findViewById(R.id.mainitem_ivCocktail);
            mTextView1 = itemView.findViewById(R.id.mainitem_tvTitle);
            mImageView.setOnClickListener((view -> {
                final Intent intent;
                intent = new Intent(context, ChooseSizeActvitity.class);

                context.startActivity(intent);
            }));


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

