package com.example.cocktail_android.screenactivitys;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.res.Configuration;
import android.os.Bundle;

import com.example.cocktail_android.ManageItem;
import com.example.cocktail_android.MangeItemAdapter;
import com.example.cocktail_android.R;

import java.util.ArrayList;

public class ManageActivity extends AppCompatActivity {
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
        setContentView(R.layout.activity_recycler);

        ArrayList<ManageItem> exampleList = new ArrayList<>();
        exampleList.add(new ManageItem(R.drawable.test_cocktail_pic,"moin", "servis"));
        exampleList.add(new ManageItem(R.drawable.test_cocktail_pic,"moin", "servis"));
        exampleList.add(new ManageItem(R.drawable.test_cocktail_pic,"moin", "servis"));
        exampleList.add(new ManageItem(R.drawable.test_cocktail_pic,"moin", "servis"));
        exampleList.add(new ManageItem(R.drawable.test_cocktail_pic,"moin", "servis"));
        exampleList.add(new ManageItem(R.drawable.test_cocktail_pic,"moin", "servis"));
        exampleList.add(new ManageItem(R.drawable.test_cocktail_pic,"moin", "servis"));
        exampleList.add(new ManageItem(R.drawable.test_cocktail_pic,"moin", "servis"));
        exampleList.add(new ManageItem(R.drawable.test_cocktail_pic,"moin", "servis"));

        mRecyclerView = findViewById(R.id.manageview_rv);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mAdapter = new MangeItemAdapter(exampleList);

        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(mLayoutManager);
    }
}
