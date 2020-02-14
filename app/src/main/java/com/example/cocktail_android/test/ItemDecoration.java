package com.example.cocktail_android.test;

import android.graphics.Rect;
import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

public class ItemDecoration extends RecyclerView.ItemDecoration {

    private final int startPadding;
    private final int endPadding;

    public ItemDecoration(int startPadding, int endPadding) {
        this.startPadding = startPadding;
        this.endPadding = endPadding;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent,
                               RecyclerView.State state) {

        int totalWidth = parent.getWidth();

        if (parent.getChildAdapterPosition(view) == 0) {
            int firstPadding = (totalWidth - startPadding) / 2;
            firstPadding = Math.max(0, firstPadding);
            outRect.set(firstPadding, 0, 0, 0);
        }

        if (parent.getChildAdapterPosition(view) == parent.getAdapter().getItemCount() - 1 &&
                parent.getAdapter().getItemCount() > 1) {
            int lastPadding = (totalWidth - endPadding) / 2;
            lastPadding = Math.max(0, lastPadding);
            outRect.set(0, 0, lastPadding, 0);
        }
    }

}