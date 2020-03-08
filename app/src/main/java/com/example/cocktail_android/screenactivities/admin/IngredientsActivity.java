package com.example.cocktail_android.screenactivities.admin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.res.Configuration;
import android.os.Bundle;

import com.example.cocktail_android.IngredientsItemAdapter;
import com.example.cocktail_android.R;
import com.example.cocktail_android.screenactivities.IngredientsItem;

import java.util.ArrayList;

public class IngredientsActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        int currentNightMode = getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK;

        if (currentNightMode == Configuration.UI_MODE_NIGHT_YES)
            setTheme(R.style.darktheme);
        else
            setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_ingredients);

        ArrayList<IngredientsItem> exampleList = new ArrayList<>();
        for(int i =0; i<20; i++){
            exampleList.add(new IngredientsItem(20,20,"1","pump 2"));

        }

        mRecyclerView = findViewById(R.id.ingredientsview__rv);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mAdapter = new IngredientsItemAdapter(exampleList);

        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(mLayoutManager);
    }
}
