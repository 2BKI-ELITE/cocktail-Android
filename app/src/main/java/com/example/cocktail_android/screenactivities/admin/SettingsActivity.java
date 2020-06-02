package com.example.cocktail_android.screenactivities.admin;

import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Switch;

import androidx.appcompat.app.AppCompatActivity;

import com.example.cocktail_android.R;
import com.example.cocktail_android.redis.controllers.SettingsController;
import com.skydoves.colorpickerview.ColorPickerDialog;
import com.skydoves.colorpickerview.listeners.ColorEnvelopeListener;

public class SettingsActivity extends AppCompatActivity implements View.OnClickListener {

    Switch maintenance;
    Switch ageCheck;

    View idleView;
    View inProgressView;
    View successView;
    View errorView;

    int[] idleColor;
    int[] inProgressColor;
    int[] successColor;
    int[] errorColor;

    int currentNightMode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        currentNightMode = getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK;

        if (currentNightMode == Configuration.UI_MODE_NIGHT_YES)
            setTheme(R.style.darktheme);
        else
            setTheme(R.style.AppTheme);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_settings);

        System.out.println(SettingsController.getIdleLight());

        String[] idle = SettingsController.getIdleLight().split(":");
        String[] inProgress = SettingsController.getInProgressLight().split(":");
        String[] success = SettingsController.getSuccessLight().split(":");
        String[] error = SettingsController.getErrorLight().split(":");

        maintenance = findViewById(R.id.settings_swMaintenance);
        maintenance.setChecked(SettingsController.isMaintenance());

        ageCheck = findViewById(R.id.settings_swAlcohol);
        ageCheck.setChecked(SettingsController.alcoholAgeCheck());

        idleView = findViewById(R.id.settings_vColorIdle);
        idleView.setBackgroundColor(Color.argb(255, Integer.valueOf(idle[0]), Integer.valueOf(idle[1]), Integer.valueOf(idle[2])));
        idleColor = new int[] {255, Integer.valueOf(idle[0]), Integer.valueOf(idle[1]), Integer.valueOf(idle[2])};

        inProgressView = findViewById(R.id.settings_vColorInProgress);
        inProgressView.setBackgroundColor(Color.argb(255, Integer.valueOf(inProgress[0]), Integer.valueOf(inProgress[1]), Integer.valueOf(inProgress[2])));
        inProgressColor = new int[] {255, Integer.valueOf(inProgress[0]), Integer.valueOf(inProgress[1]), Integer.valueOf(inProgress[2])};

        successView = findViewById(R.id.settings_vColorSuccess);
        successView.setBackgroundColor(Color.argb(255, Integer.valueOf(success[0]), Integer.valueOf(success[1]), Integer.valueOf(success[2])));
        successColor = new int[] {255, Integer.valueOf(success[0]), Integer.valueOf(success[1]), Integer.valueOf(success[2])};

        errorView = findViewById(R.id.settings_vColorError);
        errorView.setBackgroundColor(Color.argb(255, Integer.valueOf(error[0]), Integer.valueOf(error[1]), Integer.valueOf(error[2])));
        errorColor = new int[] {255, Integer.valueOf(error[0]), Integer.valueOf(error[1]), Integer.valueOf(error[2])};

        idleView.setOnClickListener(this);
        inProgressView.setOnClickListener(this);
        successView.setOnClickListener(this);
        errorView.setOnClickListener(this);

        ImageButton mSaveBt = findViewById(R.id.settings_ibConfirm);
        mSaveBt.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.settings_vColorIdle:
                new ColorPickerDialog.Builder(this, R.style.Theme_AppCompat_DayNight_Dialog_Alert)
                        .setTitle("Idle - Farbe auswählen")
                        .attachAlphaSlideBar(false)
                        .setPositiveButton("Bestätigen", (ColorEnvelopeListener) (envelope, fromUser) -> {
                            findViewById(R.id.settings_vColorIdle).setBackgroundColor(envelope.getColor());
                            idleColor = envelope.getArgb();
                        }).show();
                break;

            case R.id.settings_vColorInProgress:
                new ColorPickerDialog.Builder(this, R.style.Theme_AppCompat_DayNight_Dialog_Alert)
                        .setTitle("In Bearbeitung - Farbe auswählen")
                        .attachAlphaSlideBar(false)
                        .setPositiveButton("Bestätigen", (ColorEnvelopeListener) (envelope, fromUser) -> {
                            findViewById(R.id.settings_vColorInProgress).setBackgroundColor(envelope.getColor());
                            inProgressColor = envelope.getArgb();
                        }).show();
                break;

            case R.id.settings_vColorSuccess:
                new ColorPickerDialog.Builder(this, R.style.Theme_AppCompat_DayNight_Dialog_Alert)
                        .setTitle("Erfolg - Farbe auswählen")
                        .attachAlphaSlideBar(false)
                        .setPositiveButton("Bestätigen", (ColorEnvelopeListener) (envelope, fromUser) -> {
                            findViewById(R.id.settings_vColorSuccess).setBackgroundColor(envelope.getColor());
                            successColor = envelope.getArgb();
                        }).show();
                break;

            case R.id.settings_vColorError:
                new ColorPickerDialog.Builder(this, R.style.Theme_AppCompat_DayNight_Dialog_Alert)
                        .setTitle("Fehler - Farbe auswählen")
                        .attachAlphaSlideBar(false)
                        .setPositiveButton("Bestätigen", (ColorEnvelopeListener) (envelope, fromUser) -> {
                            findViewById(R.id.settings_vColorError).setBackgroundColor(envelope.getColor());
                            errorColor = envelope.getArgb();
                        }).show();
                break;

            case R.id.settings_ibConfirm:
                SettingsController.setMaintenance(maintenance.isChecked());
                SettingsController.setAlcoholAgeCheck(ageCheck.isChecked());
                SettingsController.setIdleLight(idleColor[1], idleColor[2], idleColor[3]);
                SettingsController.setInProgressLight(inProgressColor[1], inProgressColor[2], inProgressColor[3]);
                SettingsController.setSuccessLight(successColor[1], successColor[2], successColor[3]);
                SettingsController.setErrorLight(errorColor[1], errorColor[2], errorColor[3]);

                Intent intent = new Intent(getApplicationContext(), AdminPanelActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                break;
        }
    }
}
