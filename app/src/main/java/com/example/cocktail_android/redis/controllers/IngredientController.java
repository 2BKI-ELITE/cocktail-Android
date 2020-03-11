package com.example.cocktail_android.redis.controllers;

import android.content.Context;
import android.provider.ContactsContract;

import com.example.cocktail_android.mysql.DatabaseManager;
import com.example.cocktail_android.objects.Cocktail;
import com.example.cocktail_android.objects.Ingredient;
import com.example.cocktail_android.recycler.CocktailLayoutManager;

import java.sql.DriverManager;
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

    public static void createIngredient(Context context, Ingredient ingredient) {
        if(!ingredients.containsKey(ingredient.getIngredientId())) {
            try {
                DatabaseManager.getConnection().prepareStatement("INSERT INTO `ingredients` (`ingredientId`, `name`, `containsAlcohol`, `pump`, `fillLevel`, `fillCapacity`) VALUES ('" + ingredient.getIngredientId().toString() + "', '" + ingredient.getName() + "', '" + ingredient.containsAlcohol() + "', '" + ingredient.getPump() + "', '" + ingredient.getFillLevel() + "', '" + ingredient.getFillCapacity() + "')").execute();
                DatabaseManager.getConnection().prepareStatement("DELETE * FROM `ingredients` WHERE `pump`='" + ingredient.getPump() + "' AND `ingredientId`!='" + ingredient.getIngredientId().toString() + "'").execute();

                getIngredients();
                CocktailController.getCocktails(context);
            } catch (SQLException ignored) {}
        }
    }

    public static void updateIngredient(Context context, Ingredient ingredient) {
        if(ingredients.containsKey(ingredient.getIngredientId())) {
            try {
                DatabaseManager.getConnection().prepareStatement("UPDATE `ingredients` SET `name`='" + ingredient.getName() + "', `containsAlcohol`='" + ingredient.containsAlcohol() + "', `pump`='" + ingredient.getPump() + "', `fillLevel`='" + ingredient.getFillLevel() + "', `fillCapacity`='" + ingredient.getFillCapacity() + "'").execute();
                DatabaseManager.getConnection().prepareStatement("DELETE * FROM `ingredients` WHERE `pump`='" + ingredient.getPump() + "' AND `ingredientId`!='" + ingredient.getIngredientId().toString() + "'").execute();

                getIngredients();
                CocktailController.getCocktails(context);
            } catch (SQLException ignored) {}
        }
    }

    public static void deleteIngredient(Context context, Ingredient ingredient) {
        if(ingredients.containsKey(ingredient.getIngredientId())) {
            try {
                DatabaseManager.getConnection().prepareStatement("DELETE * FROM `ingredients` WHERE `ingredientId`='" + ingredient.getIngredientId() + "'");

                getIngredients();
                CocktailController.getCocktails(context);
            } catch (SQLException ignored) {}
        }
    }
}
