package com.example.cocktail_android.recycler.manage;

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
import com.example.cocktail_android.screenactivities.admin.cocktails.CocktailEditActivity;

import java.util.ArrayList;

public class ManageItemAdapter extends RecyclerView.Adapter<ManageItemAdapter.CocktailViewHolder> {

    public ArrayList<ManageItem> cocktails;

    public static class CocktailViewHolder extends RecyclerView.ViewHolder {
        public ImageView mImageView;
        public TextView mTextView1;
        public TextView mTextView2;
        private final Context context;

        public CocktailViewHolder(@NonNull View itemView) {
            super(itemView);
            context = itemView.getContext();
            mImageView = itemView.findViewById(R.id.manageitem_ivCocktail);
            mTextView1 = itemView.findViewById(R.id.manageitem_tvTitle);
            mTextView2 = itemView.findViewById(R.id.manageitem_tvDescription);

            itemView.setOnClickListener(v -> {
                final Intent intent = new Intent(itemView.getContext(), CocktailEditActivity.class);

                intent.putExtra("cocktailPosition", this.getAdapterPosition());
                itemView.getContext().startActivity(intent);
            });
        }
    }

    public ManageItemAdapter(ArrayList<ManageItem> cocktails) {
        this.cocktails = cocktails;
    }

    @Override
    public int getItemCount() {
        return cocktails.size();
    }

    @NonNull
    @Override
    public ManageItemAdapter.CocktailViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_managecocktails,parent,false);
        CocktailViewHolder evh = new CocktailViewHolder(v);
        return evh;
    }

    @Override
    public void onBindViewHolder(@NonNull ManageItemAdapter.CocktailViewHolder holder, int position) {
        ManageItem currentItem = cocktails.get(position);
        holder.mImageView.setImageBitmap(currentItem.getImage());
        holder.mTextView1.setText(currentItem.getmText1());
        holder.mTextView2.setText(currentItem.getmText2());
    }
}
