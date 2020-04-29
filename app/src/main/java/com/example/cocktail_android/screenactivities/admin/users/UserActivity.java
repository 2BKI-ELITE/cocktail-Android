package com.example.cocktail_android.screenactivities.admin.users;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.example.cocktail_android.R;
import com.example.cocktail_android.redis.controllers.MachineController;
import com.example.cocktail_android.screenactivities.admin.AdminPanelActivity;

public class UserActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        int currentNightMode = getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK;

        if (currentNightMode == Configuration.UI_MODE_NIGHT_YES)
            setTheme(R.style.darktheme);
        else
            setTheme(R.style.AppTheme);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_users);

        MachineController.currentActivity = "admin_users";

        final ImageButton mUserAddButton = findViewById(R.id.user_btAddUser);
        mUserAddButton.setOnClickListener(this);

        final ImageButton mUserEditButton = findViewById(R.id.user_btEditUser);
        mUserEditButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()) {
            case R.id.user_btAddUser:
                Intent intent1 = new Intent(UserActivity.this, UserAddRfidActivity.class);
                startActivity(intent1);
                break;

            case R.id.user_btEditUser:
                Intent intent2 = new Intent(UserActivity.this, UserEditRfidActivity.class);
                startActivity(intent2);
                break;
        }
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(getApplicationContext(), AdminPanelActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}
