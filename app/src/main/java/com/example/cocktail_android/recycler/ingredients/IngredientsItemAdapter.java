package com.example.cocktail_android.recycler.ingredients;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cocktail_android.R;

import java.util.ArrayList;

public class IngredientsItemAdapter extends RecyclerView.Adapter<IngredientsItemAdapter.IngredientsItemViewHolder> {
    private ArrayList<IngredientsItem> mExampleList;

    public static class IngredientsItemViewHolder extends RecyclerView.ViewHolder {
        public TextView mIngredientName, mPump, mAmount;
        public ProgressBar mProgress;

        public IngredientsItemViewHolder(@NonNull View itemView) {
            super(itemView);
            mIngredientName = itemView.findViewById(R.id.ingredientsItem_tvTitle);
            mPump = itemView.findViewById(R.id.ingredientsItem__tvPump);
            mAmount = itemView.findViewById(R.id.ingredientsItem_amount);
            mProgress = itemView.findViewById(R.id.ingredientsItem_stats_progressbar);
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
        holder.mPump.setText("Pumpe Nr. " + currentItem.getPump());

        int percentage = getPercentage(currentItem.getCurrentMl(), currentItem.getFullMl());
        holder.mAmount.setText(percentage + " %");
        holder.mProgress.setProgress(percentage);
    }

    @Override
    public int getItemCount() {
        return mExampleList.size();
    }

    /**
     * Returns percentage of fill level to fill capacity.
     * @param current Fill level.
     * @param capacity Fill capacity.
     * @return int Returns percentage.
     */
    private int getPercentage(int current, int capacity) {
        float percentage = ((float) current / (float) capacity) * (float) 100;
        return Math.round(percentage);
    }
}


