package com.example.cocktail_android;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MangeItemAdapter extends RecyclerView.Adapter<MangeItemAdapter.ExampleViewHolder> {
    private ArrayList<ManageItem> mExampleList;
    public static class ExampleViewHolder extends RecyclerView.ViewHolder{
        public ImageView mImageView;
        public TextView mTextView1;
        public TextView mTextView2;
        public ExampleViewHolder(@NonNull View itemView) {
            super(itemView);
            mImageView = itemView.findViewById(R.id.manageitem_ivCocktail);
            mTextView1 = itemView.findViewById(R.id.manageitem_tvTitle);
            mTextView2 = itemView.findViewById(R.id.manageitem_tvDescription);
        }
    }

    public MangeItemAdapter(ArrayList<ManageItem> examplelist){
        mExampleList = examplelist;
    }

    @NonNull
    @Override
    public ExampleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_managecocktails,parent,false);
        ExampleViewHolder evh = new ExampleViewHolder(v);
        return evh;
    }

    @Override
    public void onBindViewHolder(@NonNull ExampleViewHolder holder, int position) {
            ManageItem currentItem = mExampleList.get(position);
            holder.mImageView.setImageResource(currentItem.getmImageResource());
            holder.mTextView1.setText(currentItem.getmTest1());
            holder.mTextView2.setText(currentItem.getmText2());
    }

    @Override
    public int getItemCount() {
        return mExampleList.size();
    }
}
