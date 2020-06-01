package com.example.cocktail_android.redis.controllers;

import com.example.cocktail_android.mysql.DatabaseManager;
import com.example.cocktail_android.objects.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserController {

    /**
     * Checks if given user exists.
     * @param userId ID of user to be checked.
     * @return boolean Returns true if user exists.
     */
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

    /**
     * Load user information from database.
     * @param userId ID of user to be loaded.
     * @return User Returns null when user couldn't be found.
     */
    public static User getUser(String userId) {
        try {
            final PreparedStatement statement = DatabaseManager.getConnection().prepareStatement("SELECT * FROM users WHERE userId = ?");
            statement.setString(1, userId);
            final ResultSet result = statement.executeQuery();

            if(result.next())
                return new User(result.getString("userId"), result.getBoolean("isAdult"), result.getBoolean("isAdmin"));
            else
                return null;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return null;
    }

    /**
     * Creates a new user.
     * @param user User to be created.
     * @return Nothing.
     */
    public static void createUser(User user) {
        try {
            final PreparedStatement statement = DatabaseManager.getConnection().prepareStatement("INSERT INTO users (userId, isAdult, isAdmin) VALUES (?, ?, ?)");
            statement.setString(1, user.getUserId());
            statement.setBoolean(2, user.isAdult());
            statement.setBoolean(3, user.isAdmin());
            statement.execute();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Updates a existing user.
     * @param user User to be updated.
     * @return Nothing.
     */
    public static void updateUser(User user) {
        try {
            final PreparedStatement statement = DatabaseManager.getConnection().prepareStatement("UPDATE users SET isAdult = ?, isAdmin = ? WHERE userId = ?");
            statement.setBoolean(1, user.isAdult());
            statement.setBoolean(2, user.isAdmin());
            statement.setString(3, user.getUserId());
            statement.execute();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Deletes a existing user.
     * @param user User to be deleted.
     * @return Nothing.
     */
    public static void deleteUser(User user) {
        try {
            final PreparedStatement statement = DatabaseManager.getConnection().prepareStatement("DELETE * FROM users WHERE userId = ?");
            statement.setString(1, user.getUserId());
            statement.execute();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
}
