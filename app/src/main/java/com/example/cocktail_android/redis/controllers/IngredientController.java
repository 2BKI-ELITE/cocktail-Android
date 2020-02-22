package com.example.cocktail_android.redis.controllers;

import com.example.cocktail_android.mysql.DatabaseManager;
import com.example.cocktail_android.objects.Ingredient;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.UUID;

public class IngredientController {

    public static HashMap<UUID, Ingredient> ingredients = new HashMap<>();

    public static void getIngredients() {
        ingredients.clear();

        try {
            PreparedStatement statement = DatabaseManager.getConnection().prepareStatement("SELECT * FROM `ingredients`");
            ResultSet resultSet = statement.executeQuery();

            while(resultSet.next()) {
                ingredients.put(UUID.fromString(resultSet.getString("ingredientId")), new Ingredient(UUID.fromString(resultSet.getString("ingredientId")), resultSet.getString("name"), resultSet.getBoolean("containsAlcohol"), resultSet.getInt("pump"), resultSet.getInt("fillLevel"), resultSet.getInt("fillCapacity")));
            }
        } catch (SQLException ignored) {}
    }
}
