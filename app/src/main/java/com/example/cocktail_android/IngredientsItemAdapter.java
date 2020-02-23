package com.example.cocktail_android;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cocktail_android.screenactivities.IngredientsItem;

import java.util.ArrayList;

public class IngredientsItemAdapter extends RecyclerView.Adapter<IngredientsItemAdapter.IngredientsItemViewHolder> {
    private ArrayList<IngredientsItem> mExampleList;
    public static class IngredientsItemViewHolder extends RecyclerView.ViewHolder{
        public TextView mIngredientName, mPump;
        public IngredientsItemViewHolder(@NonNull View itemView) {
            super(itemView);
            mIngredientName = itemView.findViewById(R.id.ingredientsItem_tvTitle);
            mPump = itemView.findViewById(R.id.ingredientsItem__tvPump);
        }
    }

    public IngredientsItemAdapter(ArrayList<IngredientsItem> examplelist){
        mExampleList = examplelist;
    }

    @NonNull
    @Override
    public IngredientsItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_ingredients,parent,false);
        IngredientsItemViewHolder evh = new IngredientsItemViewHolder(v);
        return evh;
    }

    @Override
    public void onBindViewHolder(@NonNull IngredientsItemViewHolder holder, int position) {
        IngredientsItem currentItem = mExampleList.get(position);
        holder.mIngredientName.setText(currentItem.getIngredientName());
        holder.mPump.setText(currentItem.getPump());



    }

    @Override
    public int getItemCount() {
        return mExampleList.size();
    }
}


