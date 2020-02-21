package com.example.cocktail_android.recycler.alcoholic;

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
import com.example.cocktail_android.recycler.CocktailItem;
import com.example.cocktail_android.screenactivities.ChooseSizeActvitity;
import com.example.cocktail_android.screenactivities.MainActivity;

public class AlcoholicCocktailItemAdapter extends RecyclerView.Adapter<AlcoholicCocktailItemAdapter.AlcoholicCocktailViewHolder> {

    public static class AlcoholicCocktailViewHolder extends RecyclerView.ViewHolder {
        public ImageView mImageView;
        public TextView mTextView1;
        private final Context context;

        public AlcoholicCocktailViewHolder(@NonNull View itemView) {
            super(itemView);
            context = itemView.getContext();
            mImageView = itemView.findViewById(R.id.mainitem_ivCocktail);
            mTextView1 = itemView.findViewById(R.id.mainitem_tvTitle);

            itemView.setOnClickListener(v -> {
                final Intent intent = new Intent(itemView.getContext(), ChooseSizeActvitity.class);
                intent.putExtra("cocktailPosition", this.getAdapterPosition());
                itemView.getContext().startActivity(intent);
            });
        }
    }


    @Override
    public int getItemCount() {
        return MainActivity.alcoholicCocktails.size();
    }

    @NonNull
    @Override
    public AlcoholicCocktailViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_maincocktails,parent,false);
        AlcoholicCocktailItemAdapter.AlcoholicCocktailViewHolder evh = new AlcoholicCocktailItemAdapter.AlcoholicCocktailViewHolder(v);
        return evh;
    }

    @Override
    public void onBindViewHolder(@NonNull AlcoholicCocktailItemAdapter.AlcoholicCocktailViewHolder holder, int position) {
        CocktailItem currentItem = MainActivity.alcoholicCocktails.get(position);
        holder.mImageView.setImageResource(currentItem.getmImageResource());
        holder.mTextView1.setText(currentItem.getmText1());

    }
}
