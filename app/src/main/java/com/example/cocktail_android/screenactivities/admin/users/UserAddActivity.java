package com.example.cocktail_android.screenactivities.admin.users;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Switch;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.cocktail_android.R;
import com.example.cocktail_android.objects.User;
import com.example.cocktail_android.redis.controllers.MachineController;
import com.example.cocktail_android.redis.controllers.UserController;

public class UserAddActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        int currentNightMode = getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK;

        if (currentNightMode == Configuration.UI_MODE_NIGHT_YES)
            setTheme(R.style.darktheme);
        else
            setTheme(R.style.AppTheme);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_users_add);

        ((TextView) findViewById(R.id.useraddscreen_rfidCode)).setText(getIntent().getStringExtra("rfidCode"));

        MachineController.currentActivity = "admin_users_add";

        final ImageButton mConfirmBt = findViewById(R.id.useraddscreen_ibConfirm);
        mConfirmBt.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.useraddscreen_ibConfirm:
                Switch volljaehrig = findViewById(R.id.useraddscreen_swAge);
                Switch admin = findViewById(R.id.useraddscreen_swAdmin);

                UserController.createUser(new User(getIntent().getStringExtra("rfidCode"), volljaehrig.isChecked(), admin.isChecked()));

                Intent intent = new Intent(getApplicationContext(), UserActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                break;

            default:
                break;
        }
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(getApplicationContext(), UserActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}