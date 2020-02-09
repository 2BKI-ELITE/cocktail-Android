package com.example.cocktail_android;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class RecyclerActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler);

        ArrayList<ExampleItem> exampleList = new ArrayList<>();
        exampleList.add(new ExampleItem(R.drawable.test_cocktail_pic,"moin", "servis"));
        exampleList.add(new ExampleItem(R.drawable.test_cocktail_pic,"moin", "servis"));
        exampleList.add(new ExampleItem(R.drawable.test_cocktail_pic,"moin", "servis"));
        exampleList.add(new ExampleItem(R.drawable.test_cocktail_pic,"moin", "servis"));
        exampleList.add(new ExampleItem(R.drawable.test_cocktail_pic,"moin", "servis"));
        exampleList.add(new ExampleItem(R.drawable.test_cocktail_pic,"moin", "servis"));
        exampleList.add(new ExampleItem(R.drawable.test_cocktail_pic,"moin", "servis"));
        exampleList.add(new ExampleItem(R.drawable.test_cocktail_pic,"moin", "servis"));
        exampleList.add(new ExampleItem(R.drawable.test_cocktail_pic,"moin", "servis"));

        mRecyclerView = findViewById(R.id.recyle);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mAdapter = new ExampleAdapter(exampleList);

        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(mLayoutManager);
    }
}
