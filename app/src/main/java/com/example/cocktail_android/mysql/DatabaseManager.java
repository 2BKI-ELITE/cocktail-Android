package com.example.cocktail_android.mysql;

import java.sql.Connection;
import java.sql.DriverManager;

public class DatabaseManager {

    private static Connection connection;

    public static boolean connect() {
        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();

            connection = DriverManager.getConnection("jdbc:mysql://192.168.0.1:3306/projektarbeit", "", "");

            return true;

        } catch (Exception ex) {
            return false;
        }
    }

    public static Connection getConnection() {
        return connection;
    }
}
