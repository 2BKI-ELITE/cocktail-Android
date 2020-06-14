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

public class UserEditActivity extends AppCompatActivity implements View.OnClickListener {

    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        int currentNightMode = getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK;

        if (currentNightMode == Configuration.UI_MODE_NIGHT_YES)
            setTheme(R.style.darktheme);
        else
            setTheme(R.style.AppTheme);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_users_edit);

        user = UserController.getUser(getIntent().getStringExtra("rfidCode"));

        ((TextView) findViewById(R.id.usereditscreen_rfidCode)).setText(user.getUserId());
        ((Switch) findViewById(R.id.usereditscreen_swAge)).setChecked(user.isAdult());
        ((Switch) findViewById(R.id.usereditscreen_swAdmin)).setChecked(user.isAdmin());

        MachineController.currentActivity = "admin_users_edit";

        final ImageButton mConfirmBt = findViewById(R.id.usereditscreen_ibConfirm);
        mConfirmBt.setOnClickListener(this);

        final ImageButton mDeleteBt = findViewById(R.id.usereditscreen_ibDelete);
        mDeleteBt.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.usereditscreen_ibConfirm:
                Switch volljaehrig = findViewById(R.id.usereditscreen_swAge);
                Switch admin = findViewById(R.id.usereditscreen_swAdmin);

                UserController.updateUser(new User(getIntent().getStringExtra("rfidCode"), volljaehrig.isChecked(), admin.isChecked()));

                Intent intent = new Intent(getApplicationContext(), UserActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                break;

            case R.id.usereditscreen_ibDelete:
                UserController.deleteUser(user);
                Intent intent2 = new Intent(getApplicationContext(), UserActivity.class);
                intent2.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                intent2.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent2);
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
