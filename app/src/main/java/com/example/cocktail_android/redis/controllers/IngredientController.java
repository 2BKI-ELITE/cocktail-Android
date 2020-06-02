package com.example.cocktail_android.redis.controllers;

import android.content.Context;

import com.example.cocktail_android.mysql.DatabaseManager;
import com.example.cocktail_android.objects.Ingredient;
import com.example.cocktail_android.recycler.ingredients.IngredientsItem;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.TreeMap;
import java.util.UUID;

public class IngredientController {

    public static TreeMap<UUID, Ingredient> ingredients = new TreeMap<>();

    /**
     * Loads ingredients from database.
     * @return Nothing.
     */
    public static void getIngredients() {
        ingredients.clear();

        try {
            final PreparedStatement statement = DatabaseManager.getConnection().prepareStatement("SELECT * FROM ingredients ORDER BY pump+0");
            final ResultSet resultSet = statement.executeQuery();

            while(resultSet.next()) {
                ingredients.put(UUID.fromString(resultSet.getString("ingredientId")), new Ingredient(UUID.fromString(resultSet.getString("ingredientId")), resultSet.getString("name"), resultSet.getBoolean("containsAlcohol"), resultSet.getInt("pump"), resultSet.getInt("fillLevel"), resultSet.getInt("fillCapacity")));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Converts a ingredient into a recycler-friendly object.
     * @param ingredient Ingredient to be converted.
     * @return IngredientsItem This is a recycler-readable object.
     */
    public static IngredientsItem convertToIngredientItem(Ingredient ingredient) {
        return new IngredientsItem(ingredient.getFillLevel(), ingredient.getFillCapacity(), ingredient.getName(), ingredient.getPump());
    }

    /**
     * Creates a new ingredient and stores it in the database
     * @param context Application context needed to update cocktail list.
     * @param ingredient Ingredient object to be created.
     * @return Nothing.
     */
    public static void createIngredient(Context context, Ingredient ingredient) {
        if(!ingredients.containsKey(ingredient.getIngredientId())) {
            try {
                final PreparedStatement statement1 = DatabaseManager.getConnection().prepareStatement("INSERT INTO ingredients (ingredientId, name, containsAlcohol, pump, fillLevel, fillCapacity) VALUES (?, ?, ?, ?, ?, ?);");
                statement1.setString(1, ingredient.getIngredientId().toString());
                statement1.setString(2, ingredient.getName());
                statement1.setBoolean(3, ingredient.containsAlcohol());
                statement1.setInt(4, ingredient.getPump());
                statement1.setInt(5, ingredient.getFillLevel());
                statement1.setInt(6, ingredient.getFillCapacity());
                statement1.execute();

                final PreparedStatement statement2 = DatabaseManager.getConnection().prepareStatement("DELETE * FROM ingredients WHERE pump = ? AND ingredientId != ?");
                statement2.setInt(1, ingredient.getPump());
                statement2.setString(2, ingredient.getIngredientId().toString());
                statement2.execute();

                getIngredients();
                CocktailController.getCocktails(context);
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

    /**
     * Updates a specified ingredient.
     * @param context Application context needed to update cocktail list.
     * @param ingredient Ingredient object to be updated.
     * @return Nothing.
     */
    public static void updateIngredient(Context context, Ingredient ingredient) {
        if(ingredients.containsKey(ingredient.getIngredientId())) {
            try {
                final PreparedStatement statement1 = DatabaseManager.getConnection().prepareStatement("UPDATE ingredients SET name = ?, containsAlcohol = ?, pump = ?, fillLevel = ?, fillCapacity = ? WHERE ingredientId = ?");
                statement1.setString(1, ingredient.getName());
                statement1.setBoolean(2, ingredient.containsAlcohol());
                statement1.setInt(3, ingredient.getPump());
                statement1.setInt(4, ingredient.getFillLevel());
                statement1.setInt(5, ingredient.getFillCapacity());
                statement1.setString(6, ingredient.getIngredientId().toString());

                final PreparedStatement statement2 = DatabaseManager.getConnection().prepareStatement("DELETE * FROM ingredients WHERE pump = ? AND ingredientId != ?");
                statement2.setInt(1, ingredient.getPump());
                statement2.setString(2, ingredient.getIngredientId().toString());
                statement2.execute();

                getIngredients();
                CocktailController.getCocktails(context);
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

    /**
     * Deletes a specified ingredient.
     * @param context Application context needed to update cocktail list.
     * @param ingredient Ingredient object to be deleted.
     * @return Nothing.
     */
    public static void deleteIngredient(Context context, Ingredient ingredient) {
        if(ingredients.containsKey(ingredient.getIngredientId())) {
            try {
                DatabaseManager.getConnection().prepareStatement("DELETE * FROM `ingredients` WHERE `ingredientId`='" + ingredient.getIngredientId() + "'");

                getIngredients();
                CocktailController.getCocktails(context);
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

    public static Ingredient getIngredientByPump(int pump) {
        Ingredient ingredient = null;
        try {
            final PreparedStatement statement = DatabaseManager.getConnection().prepareStatement("SELECT * FROM ingredients WHERE `pump` = ?");
            statement.setInt(1, pump);
            final ResultSet resultSet = statement.executeQuery();

            while(resultSet.next())
                ingredient = new Ingredient(UUID.fromString(resultSet.getString("ingredientId")), resultSet.getString("name"), resultSet.getBoolean("containsAlcohol"), resultSet.getInt("pump"), resultSet.getInt("fillLevel"), resultSet.getInt("fillCapacity"));
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return ingredient;
    }
}
