package com.example.cocktail_android.screenactivities.admin.cocktails;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cocktail_android.recycler.manage.ManageItem;
import com.example.cocktail_android.recycler.manage.ManageItemAdapter;
import com.example.cocktail_android.R;
import com.example.cocktail_android.objects.Cocktail;
import com.example.cocktail_android.redis.controllers.CocktailController;
import com.example.cocktail_android.redis.controllers.MachineController;
import com.example.cocktail_android.screenactivities.admin.AdminPanelActivity;

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
        setContentView(R.layout.activity_admin_cocktails);

        MachineController.currentActivity = "admin_cocktails";

        ArrayList<ManageItem> exampleList = new ArrayList<>();
        ArrayList<Cocktail> cocktails = new ArrayList<Cocktail>(CocktailController.cocktails.values());

        for(int i = 0; i < CocktailController.cocktails.size(); i++) {
            exampleList.add(CocktailController.convertToManageItem(cocktails.get(i)));
        }

        mRecyclerView = findViewById(R.id.manageview_rv);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mAdapter = new ManageItemAdapter(exampleList);

        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(mLayoutManager);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, AdminPanelActivity.class);

        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        startActivity(intent);
    }
}
