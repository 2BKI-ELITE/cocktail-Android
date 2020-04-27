package com.example.cocktail_android.redis.controllers;

import com.example.cocktail_android.mysql.DatabaseManager;
import com.example.cocktail_android.objects.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserController {

    public static boolean userExists(String userId) {
        try {
            final PreparedStatement statement = DatabaseManager.getConnection().prepareStatement("SELECT * FROM users WHERE userId = ?");
            statement.setString(1, userId);
            final ResultSet result = statement.executeQuery();

            if(result.next())
                return true;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return false;
    }

    public static User getUser(String userId) {
        try {
            final PreparedStatement statement = DatabaseManager.getConnection().prepareStatement("SELECT * FROM users WHERE userId = ?");
            statement.setString(1, userId);
            final ResultSet result = statement.executeQuery();

            while(result.next())
                return new User(result.getString("userId"), result.getBoolean("isAdult"), result.getBoolean("isAdmin"));
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return null;
    }

    public static void createUser(User user) {

    }

    public static void updateUser() {

    }

    public static void deleteUser() {

    }
}
