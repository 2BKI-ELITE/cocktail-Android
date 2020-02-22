package com.example.cocktail_android;

import android.content.Context;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.cocktail_android.redis.controllers.AdminAuthController;
import com.example.cocktail_android.screenactivities.MainActivity;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private static Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mContext = this.getApplicationContext();

        if(!MainActivity.DUMMY_MODE)
            AdminAuthController.start();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rfidlogin);
    }

    public static Context getAppContext() {
        return mContext;
    }


    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.rfidlogin_btBack) {
            AdminAuthController.cancel();
            finish();
        }
    }
}
