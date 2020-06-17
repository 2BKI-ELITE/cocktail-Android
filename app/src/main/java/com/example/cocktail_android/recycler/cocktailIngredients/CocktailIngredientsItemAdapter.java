package com.example.cocktail_android.recycler.cocktailIngredients;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cocktail_android.R;

import java.util.ArrayList;

public class CocktailIngredientsItemAdapter extends RecyclerView.Adapter<CocktailIngredientsItemAdapter.CocktailIngredientsItemViewHolder> {

    public static ArrayList<CocktailIngredientsItem> mExampleList;

    public static class CocktailIngredientsItemViewHolder extends RecyclerView.ViewHolder {
        public TextView mName;
        public ImageButton mBtDelete, mBtEdit;

        public CocktailIngredientsItemViewHolder(@NonNull View itemView) {
            super(itemView);
            mName = itemView.findViewById(R.id.list_name);
            mBtDelete = itemView.findViewById(R.id.list_btDelete);
            mBtEdit = itemView.findViewById(R.id.list_btEdit);
        }
    }

    public CocktailIngredientsItemAdapter(ArrayList<CocktailIngredientsItem> examplelist) {
        mExampleList = examplelist;
    }

    @NonNull
    @Override
    public CocktailIngredientsItemAdapter.CocktailIngredientsItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent,false);
        CocktailIngredientsItemAdapter.CocktailIngredientsItemViewHolder evh = new CocktailIngredientsItemAdapter.CocktailIngredientsItemViewHolder(v);
        return evh;
    }

    @Override
    public void onBindViewHolder(@NonNull CocktailIngredientsItemAdapter.CocktailIngredientsItemViewHolder holder, int position) {
        CocktailIngredientsItem currentItem = mExampleList.get(position);
        holder.mName.setText(currentItem.getIngredient().getName());

        holder.mBtDelete.setOnClickListener(v -> {
            mExampleList.remove(position);
            notifyDataSetChanged();
        });
    }

    @Override
    public int getItemCount() {
        return mExampleList.size();
    }
}
