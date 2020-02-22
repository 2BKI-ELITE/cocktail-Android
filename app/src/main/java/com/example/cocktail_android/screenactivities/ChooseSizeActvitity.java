package com.example.cocktail_android.screenactivities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.cocktail_android.R;
import com.example.cocktail_android.objects.Cocktail;
import com.example.cocktail_android.objects.Ingredient;

import java.util.ArrayList;
import java.util.List;

public class ChooseSizeActvitity extends AppCompatActivity implements View.OnClickListener {

    private Cocktail cocktail;
    private static Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        cocktail = MainActivity.alcoholicCocktails.get(getIntent().getIntExtra("cocktailPosition", 0)).getCocktail();
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

        List<Ingredient> ingredientList = new ArrayList<>(cocktail.getIngredients().keySet());
        StringBuilder builder = new StringBuilder();

        for(int i = 0; i < ingredientList.size(); i++) {
            builder.append(ingredientList.get(i).getName());

            if(i != ingredientList.size() - 1) {
                builder.append(", ");
            }
        }

        ImageView imageView = findViewById(R.id.cocktaildetails_ivImage);
        imageView.setImageBitmap(cocktail.getImage());
        imageView.getLayoutParams().height = cocktail.getImage().getHeight();
        imageView.getLayoutParams().width = cocktail.getImage().getWidth();
        imageView.setMaxHeight(180);
        imageView.setMaxWidth(180);

        ((TextView) findViewById(R.id.cocktaildetails_tvTitle)).setText(cocktail.getName());
        ((TextView) findViewById(R.id.cocktaildetails_tvDescription)).setText(cocktail.getDescription());
        ((TextView) findViewById(R.id.cocktaildetails_tvIngredients)).setText(builder.toString());
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

    public static Context getAppContext() {
        return mContext;
    }
}
