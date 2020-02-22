package com.example.cocktail_android.redis.controllers;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.example.cocktail_android.R;
import com.example.cocktail_android.mysql.DatabaseManager;
import com.example.cocktail_android.objects.Cocktail;
import com.example.cocktail_android.objects.Ingredient;
import com.example.cocktail_android.recycler.CocktailItem;
import com.example.cocktail_android.screenactivities.ChooseSizeActvitity;
import com.example.cocktail_android.screenactivities.MainActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.UUID;

public class CocktailController {

    public static ArrayList<Cocktail> cocktails = new ArrayList<Cocktail>();

    public static ArrayList<CocktailItem> fillDummyCocktails(Context context) {
        ArrayList<CocktailItem> cocktails = new ArrayList<>();

        for(int i = 0; i < 20; i++) {
            Cocktail cocktail = new Cocktail(context, UUID.randomUUID(), "Dummy Cocktail " + (i + 1), "Dummy Cocktail Description", new HashMap<>(), true, new Date());
            cocktails.add(convertToCocktailItem(cocktail));
        }

        return cocktails;
    }

    public static void getCocktails(Context context) {
        cocktails.clear();

        try {
            PreparedStatement statement = DatabaseManager.getConnection().prepareStatement("SELECT * FROM `cocktails`");
            ResultSet resultSet = statement.executeQuery();

            while(resultSet.next()) {
                try {
                    boolean enabled = resultSet.getBoolean("enabled");

                    JSONArray ingredients = new JSONArray(resultSet.getString("ingredients"));
                    HashMap<Ingredient, Integer> ingredientsList = new HashMap<>();

                    for(int i = 0; i < ingredients.length(); i++) {
                        JSONObject ingredient = ingredients.getJSONObject(i);

                        if(IngredientController.ingredients.containsKey(UUID.fromString(ingredient.getString("ingredientId")))) {
                            ingredientsList.put(IngredientController.ingredients.get(UUID.fromString(ingredient.getString("ingredientId"))), ingredient.getInt("amount"));
                        } else {
                            enabled = false;
                        }
                    }

                    Cocktail cocktail = new Cocktail(context, UUID.fromString(resultSet.getString("cocktailId")), resultSet.getString("name"), resultSet.getString("description"), ingredientsList, enabled, resultSet.getDate("createdAt"));
                    cocktails.add(cocktail);
                } catch (JSONException ignored) {}
            }
        } catch (SQLException e) {}
    }

    public static Bitmap getBitmapFromURL(Context context, UUID cocktailId) {
        Bitmap bitmap;

        try {
            //URL url = new URL("http://192.168.0.1/images/" + cocktailId.toString());
            URL url = new URL("https://www.cocktailwelt.net/wp-content/uploads/2018/06/cocktail-rezept-zombie-500x500.jpg");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            if(connection.getResponseCode() == 200) {
                InputStream input = connection.getInputStream();
                bitmap = BitmapFactory.decodeStream(input);
            } else {
                bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.cocktails_cocktail_icon);
            }
        } catch (IOException e) {
            bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.cocktails_cocktail_icon);
        }

        return bitmap;
    }

    public static CocktailItem convertToCocktailItem(Cocktail cocktail) {
        return new CocktailItem(R.drawable.test_cocktail_pic, cocktail.getName(), cocktail);
    }
}
