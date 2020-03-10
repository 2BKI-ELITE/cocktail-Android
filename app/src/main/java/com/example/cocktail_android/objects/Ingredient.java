package com.example.cocktail_android.objects;

import android.provider.ContactsContract;

import com.example.cocktail_android.mysql.DatabaseManager;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class Ingredient {

    private UUID ingredientId;
    private String name;
    private boolean containsAlcohol;
    private int pump;
    private int fillLevel;
    private int fillCapacity;

    public Ingredient(UUID ingredientId, String name, boolean containsAlcohol, int pump, int fillLevel, int fillCapacity) {
        this.ingredientId = ingredientId;
        this.name = name;
        this.containsAlcohol = containsAlcohol;
        this.pump = pump;
        this.fillLevel = fillLevel;
        this.fillCapacity = fillCapacity;
    }

    public UUID getIngredientId() {
        return ingredientId;
    }

    public String getName() {
        return name;
    }

    public boolean containsAlcohol() {
        return containsAlcohol;
    }

    public int getPump() {
        return pump;
    }

    public int getFillLevel() {
        try {
            ResultSet resultSet = DatabaseManager.getConnection().prepareStatement("SELECT * FROM `ingredients` WHERE `ingredientId`='" + ingredientId + "'").executeQuery();

            while (resultSet.next())
                return resultSet.getInt("fillLevel");
        } catch (SQLException ignored) {}

        return 0;
    }

    public int getFillCapacity() {
        try {
            ResultSet resultSet = DatabaseManager.getConnection().prepareStatement("SELECT * FROM `ingredients` WHERE `ingredientId`='" + ingredientId + "'").executeQuery();

            while (resultSet.next())
                return resultSet.getInt("fillCapacity");
        } catch (SQLException ignored) {}

        return 0;
    }
}
