package com.example.cocktail_android.screenactivities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.example.cocktail_android.R;

public class ChooseSizeActvitity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cocktaildetails);

        // Listener for Shot size
        final ImageButton mBtShotSize = findViewById(R.id.confirm_shotSize);
        mBtShotSize.setOnClickListener(this);

        // Listener for Small size
        final ImageButton mBtSmallSize = findViewById(R.id.confirm_smallSize);
        mBtSmallSize.setOnClickListener(this);

        // Listener for Large size
        final ImageButton mBtBigSize = findViewById(R.id.confirm_bigSize);
        mBtBigSize.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.confirm_shotSize:
                Intent intent1 = new Intent(ChooseSizeActvitity.this, ConfirmCocktail.class);
                startActivity(intent1);
                break;

            case R.id.confirm_smallSize:
                Intent intent2 = new Intent(ChooseSizeActvitity.this, ConfirmCocktail.class);
                startActivity(intent2);
                break;

            case R.id.confirm_bigSize:
                Intent intent4 = new Intent(ChooseSizeActvitity.this, ConfirmCocktail.class);
                startActivity(intent4);
                break;

            default:
                break;
        }
    }
}
