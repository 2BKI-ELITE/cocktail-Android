package com.example.cocktail_android.screenactivities.admin.cocktails;

import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.cocktail_android.R;
import com.example.cocktail_android.objects.Cocktail;
import com.example.cocktail_android.objects.Ingredient;
import com.example.cocktail_android.redis.controllers.CocktailController;
import com.example.cocktail_android.redis.controllers.IngredientController;
import com.google.android.material.textfield.TextInputEditText;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;

public class CocktailEditActivity extends AppCompatActivity implements View.OnClickListener {

    private Cocktail cocktail;
    private ArrayList<String> editIngredientsList = new ArrayList<>();

    public static final int PICK_IMAGE = 1;
    private static Bitmap newImage = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        int currentNightMode = getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK;

        if (currentNightMode == Configuration.UI_MODE_NIGHT_YES)
            setTheme(R.style.darktheme);
        else
            setTheme(R.style.AppTheme);

        ArrayList<Cocktail> cocktails = new ArrayList<Cocktail>(CocktailController.cocktails.values());
        cocktail = cocktails.get(getIntent().getIntExtra("cocktailPosition", 0));
        ArrayList<Ingredient> ingredientsList = new ArrayList<>(cocktail.getIngredients().keySet());

        for(int i = 0; i < cocktail.getIngredients().size(); i++) {
            editIngredientsList.add(IngredientController.convertToCocktailIngredientItem(ingredientsList.get(i), cocktail.getIngredients().get(ingredientsList.get(i))).toString());
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_cocktails_edit);

        final ImageButton backButton = findViewById(R.id.editcocktail_btBack);
        backButton.setOnClickListener(this);

        final Button chooseButton = findViewById(R.id.editcocktail_btChoose);
        chooseButton.setOnClickListener(this);

        final Button removeButton = findViewById(R.id.editcocktail_btRemove);
        removeButton.setOnClickListener(this);

        final Button editIngredientsButton = findViewById(R.id.editcocktail_btEditIngredients);
        editIngredientsButton.setOnClickListener(this);

        final ImageButton deleteButton = findViewById(R.id.editcocktail_btDelete);
        deleteButton.setOnClickListener(this);

        ((TextView) findViewById(R.id.editcocktail_tvTitle)).setText(cocktail.getName());
        ((TextInputEditText) findViewById(R.id.editcocktail_tietName)).setText(cocktail.getName());
        ((TextInputEditText) findViewById(R.id.editcocktail_tietDescription)).setText(cocktail.getDescription());
        ((ImageView) findViewById(R.id.editcocktail_ivImage)).setImageBitmap(cocktail.getImage());
        ((TextView) findViewById(R.id.editcocktail_tvIngredientsSubtitle)).setText(cocktail.getIngredients().size() + " Zutaten ausgewählt");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == PICK_IMAGE) {
            try {
                InputStream inputStream = getContentResolver().openInputStream(data.getData());
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);

                ImageView imageView = findViewById(R.id.editcocktail_ivImage);
                imageView.setImageBitmap(bitmap);

                newImage = bitmap;
            } catch (FileNotFoundException ex) {
                ex.printStackTrace();
            } catch (NullPointerException ex) {}
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.editcocktail_btChoose:
                Intent getIntent = new Intent(Intent.ACTION_GET_CONTENT);
                getIntent.setType("image/*");

                Intent pickIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                pickIntent.setType("image/*");

                Intent chooserIntent = Intent.createChooser(getIntent, "Bild auswählen");
                chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, new Intent[] {pickIntent});

                startActivityForResult(chooserIntent, PICK_IMAGE);
                break;

            case R.id.editcocktail_btRemove:
                ((ImageView) findViewById(R.id.editcocktail_ivImage)).setImageResource(R.drawable.cocktails_cocktail_icon);

                newImage = null;
                break;

            case R.id.editcocktail_btEditIngredients:
                Intent intent3 = new Intent(this, CocktailEditIngredientsActivity.class);

                intent3.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                intent3.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                intent3.putExtra("cocktailPosition", getIntent().getIntExtra("cocktailPosition", 0));
                intent3.putExtra("cocktailId", cocktail.getCocktailId().toString());
                intent3.putStringArrayListExtra("cocktailIngredients", editIngredientsList);

                startActivity(intent3);
                break;

            case R.id.editcocktail_btDelete:
                CocktailController.deleteCocktail(this, cocktail);

                Intent intent2 = new Intent(getApplicationContext(), ManageActivity.class);

                intent2.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                intent2.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                startActivity(intent2);
                break;

            case R.id.editcocktail_btBack:
                onBackPressed();
                break;

            default:
                break;
        }
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, ManageActivity.class);

        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        startActivity(intent);
    }
}
