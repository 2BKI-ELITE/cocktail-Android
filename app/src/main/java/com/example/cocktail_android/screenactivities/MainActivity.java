package com.example.cocktail_android.screenactivities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSnapHelper;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;

import android.content.res.Configuration;
import android.os.Bundle;
import android.os.StrictMode;

import android.widget.ImageButton;
import android.content.Intent;
import android.widget.LinearLayout;

import com.example.cocktail_android.MainCocktailItem;
import com.example.cocktail_android.MainCocktailItemAdapter;
import com.example.cocktail_android.ManageItem;
import com.example.cocktail_android.MangeItemAdapter;
import com.example.cocktail_android.R;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private RecyclerView mRecyclerView2;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private RecyclerView.Adapter mAdapter2;
    private RecyclerView.LayoutManager mLayoutManager2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        //CommunicationManager.establishConnection();
        //CommunicationManager.setupSubscriber();

        int currentNightMode = getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK;

        if (currentNightMode == Configuration.UI_MODE_NIGHT_YES)
            setTheme(R.style.darktheme);
        else
            setTheme(R.style.AppTheme);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        final ImageButton adminPanelBt = findViewById(R.id.main_btAdminPanel);
        adminPanelBt.setOnClickListener(view -> {
            Intent intent1 = new Intent(MainActivity.this, AdminPanelActivity.class);
            startActivity(intent1);
        });

        ArrayList<MainCocktailItem> exampleList = new ArrayList<>();
        exampleList.add(new MainCocktailItem(R.drawable.test_cocktail_pic,"Ich"));
        exampleList.add(new MainCocktailItem(R.drawable.test_cocktail_pic,"Ich"));
        exampleList.add(new MainCocktailItem(R.drawable.test_cocktail_pic,"Ich"));
        exampleList.add(new MainCocktailItem(R.drawable.test_cocktail_pic,"Ich"));
        exampleList.add(new MainCocktailItem(R.drawable.test_cocktail_pic,"Ich"));
        exampleList.add(new MainCocktailItem(R.drawable.test_cocktail_pic,"Ich"));
        exampleList.add(new MainCocktailItem(R.drawable.test_cocktail_pic,"Ich"));
        exampleList.add(new MainCocktailItem(R.drawable.test_cocktail_pic,"Ich"));
        exampleList.add(new MainCocktailItem(R.drawable.test_cocktail_pic,"Ich"));
        exampleList.add(new MainCocktailItem(R.drawable.test_cocktail_pic,"Ich"));
        exampleList.add(new MainCocktailItem(R.drawable.test_cocktail_pic,"Ich"));
        exampleList.add(new MainCocktailItem(R.drawable.test_cocktail_pic,"Ich"));
        exampleList.add(new MainCocktailItem(R.drawable.test_cocktail_pic,"Ich"));
        exampleList.add(new MainCocktailItem(R.drawable.test_cocktail_pic,"Ich"));
        exampleList.add(new MainCocktailItem(R.drawable.test_cocktail_pic,"Ich"));
        exampleList.add(new MainCocktailItem(R.drawable.test_cocktail_pic,"Ich"));
        exampleList.add(new MainCocktailItem(R.drawable.test_cocktail_pic,"Ich"));
        exampleList.add(new MainCocktailItem(R.drawable.test_cocktail_pic,"Ich"));


        mRecyclerView = findViewById(R.id.main_rv1);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this,RecyclerView.HORIZONTAL,false);
        mAdapter = new MainCocktailItemAdapter(exampleList);

        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(mLayoutManager);
        SnapHelper snapHelper = new LinearSnapHelper();
        snapHelper.attachToRecyclerView(mRecyclerView);

        mRecyclerView2 = findViewById(R.id.main_rv2);
        mRecyclerView2.setHasFixedSize(true);

        mLayoutManager2 = new LinearLayoutManager(this,RecyclerView.HORIZONTAL,false);
        mAdapter2 = new MainCocktailItemAdapter(exampleList);

        mRecyclerView2.setAdapter(mAdapter2);
        mRecyclerView2.setLayoutManager(mLayoutManager2);
        SnapHelper snapHelper2 = new LinearSnapHelper();
        snapHelper2.attachToRecyclerView(mRecyclerView2);


    }
}
