package com.example.cocktail_android.mysql;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DatabaseManager {

    private static Connection connection;

    public static boolean connect() {
        try {
            Class.forName("org.mariadb.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mariadb://192.168.1.1:3306/projektarbeit", "projektarbeit", "test123");

            createTables();

            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }

    public static Connection getConnection() {
        return connection;
    }

    public static void createTables() {
        try {
            connection.prepareStatement("CREATE TABLE IF NOT EXISTS `cocktails` (`cocktailId` VARCHAR(36) PRIMARY KEY, `name` VARCHAR(50) NOT NULL, `description` TEXT, `ingredients` TEXT, `enabled` BOOLEAN DEFAULT true, `createdAt` TIMESTAMP NOT NULL DEFAULT NOW());").execute();
            connection.prepareStatement("CREATE TABLE IF NOT EXISTS `ingredients` (`ingredientId` VARCHAR(36) PRIMARY KEY, `name` VARCHAR(50) NOT NULL, `containsAlcohol` BOOLEAN DEFAULT false, `pump` INT NOT NULL, `fillLevel` INT UNSIGNED NOT NULL, `fillCapacity` INT UNSIGNED NOT NULL);").execute();
            connection.prepareStatement("CREATE TABLE IF NOT EXISTS `users` (`userId` VARCHAR(45) PRIMARY KEY, `isAdult` BOOLEAN DEFAULT false, `isAdmin` BOOLEAN DEFAULT false);").execute();

            ResultSet resultSet = connection.getMetaData().getTables(null, null, "settings", null);

            if(!resultSet.next()) {
                connection.prepareStatement("CREATE TABLE IF NOT EXISTS `settings` (`key` VARCHAR(100), `value` VARCHAR(100));").execute();

                final PreparedStatement statement = connection.prepareStatement("INSERT INTO settings (`key`, `value`) VALUES (?, ?)");

                statement.setString(1, "maintenance");
                statement.setString(2, "false");
                statement.execute();

                statement.setString(1, "alcohol_age-check");
                statement.setString(2, "false");
                statement.execute();

                statement.setString(1, "light_idle");
                statement.setString(2, "66:144:245");
                statement.execute();

                statement.setString(1, "light_in-progress");
                statement.setString(2, "217:195:30");
                statement.execute();

                statement.setString(1, "light_success");
                statement.setString(2, "80:217:30");
                statement.execute();

                statement.setString(1, "light_error");
                statement.setString(2, "217:30:30");
                statement.execute();
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
}
