package com.example.cocktail_android.screenactivities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.example.cocktail_android.R;
import com.example.cocktail_android.redis.controllers.MachineController;

public class FailedActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_failedscreen);

        MachineController.currentActivity = "failed";

        ((TextView) findViewById(R.id.successscreen_tvTitle)).setText(getIntent().getStringExtra("title"));
        ((TextView) findViewById(R.id.successscreen_tvDescription)).setText(getIntent().getStringExtra("description"));

        findViewById(R.id.successscreen_btFinishmaking).setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        });
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}
