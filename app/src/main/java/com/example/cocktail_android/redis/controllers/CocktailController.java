package com.example.cocktail_android.redis.controllers;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.example.cocktail_android.R;
import com.example.cocktail_android.mysql.DatabaseManager;
import com.example.cocktail_android.objects.Cocktail;
import com.example.cocktail_android.recycler.CocktailItem;

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

    public static ArrayList<CocktailItem> fillDummyCocktails() {
        ArrayList<CocktailItem> cocktails = new ArrayList<>();

        for(int i = 0; i < 20; i++) {
            Cocktail cocktail = new Cocktail(UUID.randomUUID(), "Dummy Cocktail " + (i + 1), "Dummy Cocktail Description", new HashMap<>(), new Date());
            cocktails.add(convertToCocktailItem(cocktail));
        }

        return cocktails;
    }

    public static void getCocktails() {
        cocktails.clear();

        try {
            PreparedStatement statement = DatabaseManager.getConnection().prepareStatement("SELECT * FROM `cocktails`");
            ResultSet resultSet = statement.executeQuery();

            while(resultSet.next()) {

            }
        } catch (SQLException e) {}
    }

    public static Bitmap getBitmapFromURL(UUID cocktailId) {
        try {
            URL url = new URL("http://192.168.0.1/images/" + cocktailId.toString());
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap bitmap = BitmapFactory.decodeStream(input);
            return bitmap;
        } catch (IOException e) {}

        return null;
    }

    public static CocktailItem convertToCocktailItem(Cocktail cocktail) {
        return new CocktailItem(R.drawable.test_cocktail_pic, cocktail.getName(), cocktail);
    }
}
