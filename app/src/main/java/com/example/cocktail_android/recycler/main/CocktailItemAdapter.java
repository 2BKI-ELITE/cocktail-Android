package com.example.cocktail_android.recycler.main;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cocktail_android.R;
import com.example.cocktail_android.screenactivities.cocktail.ChooseSizeActvitity;

import java.util.ArrayList;

public class CocktailItemAdapter extends RecyclerView.Adapter<CocktailItemAdapter.CocktailViewHolder> {

    public ArrayList<CocktailItem> cocktails;

    public static class CocktailViewHolder extends RecyclerView.ViewHolder {
        public ImageView mImageView;
        public TextView mTextView1;
        private final Context context;

        public CocktailViewHolder(@NonNull View itemView) {
            super(itemView);
            context = itemView.getContext();
            mImageView = itemView.findViewById(R.id.mainitem_ivCocktail);
            mTextView1 = itemView.findViewById(R.id.mainitem_tvTitle);

            itemView.setOnClickListener(v -> {
                final Intent intent = new Intent(itemView.getContext(), ChooseSizeActvitity.class);

                intent.putExtra("alcoholic", ((StickyRecyclerView) itemView.getParent()).getId() == R.id.main_rv_alcoholic);

                intent.putExtra("cocktailPosition", this.getAdapterPosition());
                itemView.getContext().startActivity(intent);
            });
        }
    }

    public CocktailItemAdapter(ArrayList<CocktailItem> cocktails) {
        this.cocktails = cocktails;
    }

    @Override
    public int getItemCount() {
        return cocktails.size();
    }

    @NonNull
    @Override
    public CocktailItemAdapter.CocktailViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_maincocktails,parent,false);
        CocktailItemAdapter.CocktailViewHolder evh = new CocktailItemAdapter.CocktailViewHolder(v);
        return evh;
    }

    @Override
    public void onBindViewHolder(@NonNull CocktailItemAdapter.CocktailViewHolder holder, int position) {
        CocktailItem currentItem = cocktails.get(position);
        holder.mImageView.setImageBitmap(currentItem.getImage());
        holder.mTextView1.setText(currentItem.getmText1());
    }
}