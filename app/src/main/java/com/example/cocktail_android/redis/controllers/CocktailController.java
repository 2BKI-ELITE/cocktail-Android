package com.example.cocktail_android.redis.controllers;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.example.cocktail_android.recycler.manage.ManageItem;
import com.example.cocktail_android.R;
import com.example.cocktail_android.enums.CocktailSize;
import com.example.cocktail_android.mysql.DatabaseManager;
import com.example.cocktail_android.objects.Cocktail;
import com.example.cocktail_android.objects.Ingredient;
import com.example.cocktail_android.recycler.main.CocktailItem;
import com.example.cocktail_android.redis.CommunicationManager;
import com.example.cocktail_android.screenactivities.cocktail.ChooseSizeActvitity;
import com.example.cocktail_android.screenactivities.cocktail.CocktailInProgressActivity;
import com.example.cocktail_android.screenactivities.error.CocktailCancelledActivity;
import com.example.cocktail_android.screenactivities.success.CocktailFinishedActivity;

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
import java.util.LinkedHashMap;
import java.util.UUID;

public class CocktailController {

    public static LinkedHashMap<UUID, Cocktail> cocktails = new LinkedHashMap<>();
    public static boolean makingBlocked = false;
    public static Activity chooseActivity;
    public static Activity cleanActivity;
    public static UUID chooseCocktail;
    public static Cocktail cocktail;

    /**
     * Creates some dummy cocktails for test purposes.
     * @param context Application context needed to create cocktails.
     * @return ArrayList<CocktailItem> with the created dummy cocktails.
     */
    public static ArrayList<CocktailItem> fillDummyCocktails(Context context) {
        ArrayList<CocktailItem> cocktails = new ArrayList<>();

        for(int i = 0; i < 20; i++) {
            Cocktail cocktail = new Cocktail(context, UUID.randomUUID(), "Dummy Cocktail " + (i + 1), "Dummy Cocktail Description", new HashMap<>(), true, new Date());
            cocktails.add(convertToCocktailItem(cocktail));
        }

        return cocktails;
    }

    /**
     * Loads cocktails from database.
     * @param context Application context needed to create retrieved cocktails.
     * @return Nothing.
     */
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
                    cocktails.put(cocktail.getCocktailId(), cocktail);
                } catch (JSONException ex) {
                    ex.printStackTrace();
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Creates bitmap image from URL stored in database.
     * @param context Application context needed to create bitmap.
     * @param cocktailId ID of cocktail
     * @return Bitmap This is the bitmap image created.
     */
    public static Bitmap getBitmapFromURL(Context context, UUID cocktailId) {
        Bitmap bitmap;

        try {
            URL url = new URL("http://192.168.1.1/images/" + cocktailId.toString() + ".png");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            if(connection.getResponseCode() == 200) {
                InputStream input = connection.getInputStream();
                bitmap = BitmapFactory.decodeStream(input);
            } else {
                bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.cocktails_cocktail_icon);
            }
        } catch (IOException ex) {
            ex.printStackTrace();
            bitmap = BitmapFactory.decodeResource(context.getResources(),R.drawable.cocktails_cocktail_icon);
        }

        return bitmap;
    }

    public static Bitmap getDefaultBitmap(Context context, UUID cocktailId) {
        return BitmapFactory.decodeResource(context.getResources(), R.drawable.cocktails_cocktail_icon);
    }

    /**
     * Converts a cocktail into a recycler-friendly object.
     * @param cocktail Cocktail which should be converted.
     * @return CocktailItem This is a for recycler readable object.
     */
    public static CocktailItem convertToCocktailItem(Cocktail cocktail) {
        return new CocktailItem(cocktail.getImage(), cocktail.getName(), cocktail);
    }

    /**
     * Converts a cocktail into a management recycler-friendly object.
     * @param cocktail Cocktail which should be converted.
     * @return ManageItem This is a for management recycler readable object.
     */
    public static ManageItem convertToManageItem(Cocktail cocktail) {
        return new ManageItem(cocktail.getImage(), cocktail.getName(), cocktail.getDescription(), cocktail);
    }

    /**
     * Checks if there are enough resources to make a specified cocktail.
     * @param cocktail Cocktail to be checked.
     * @return boolean Returns true if cocktail can be made.
     */
    public static boolean checkAvailability(Cocktail cocktail) {
        boolean available = true;

        for(int i = 0; i < cocktail.getIngredients().size(); i++) {
            Ingredient ingredient = (Ingredient) cocktail.getIngredients().keySet().toArray()[i];
            int amount = (int) cocktail.getIngredients().values().toArray()[i];

            if(ingredient.getFillLevel() < amount)
                available = false;
        }

        return available;
    }

    /**
     * Initiates cocktail making process.
     * @param cocktail Cocktail to be made.
     * @param size Size of the choosen glass.
     * @return Nothing.
     */
    public static void makeCocktail(Cocktail cocktail, CocktailSize size) {
        JSONObject message = new JSONObject();
        UUID actionId = UUID.randomUUID();

        try {
            message.put("action", "make_cocktail_start");
            message.put("action_id", actionId);
            message.put("cocktail_id", cocktail.getCocktailId());
            message.put("cocktail_size", size.toString());
        } catch (JSONException ex) {
            ex.printStackTrace();
        }

        CommunicationManager.activeActions.remove("make_cocktail");
        CommunicationManager.activeActions.put("make_cocktail", actionId);
        CommunicationManager.publishMessage(message);
    }

    /**
     * Incoming message which confirms that the cocktail is getting made.
     * @param context Application context needed for intents.
     * @param object Content of response.
     * @return Nothing.
     */
    public static void makeCocktailConfirmation(Context context, JSONObject object) {
        try {
            makingBlocked = true;

            if(CommunicationManager.activeActions.containsValue(UUID.fromString(object.getString("action_id")))) {
                Intent intent = new Intent(context, CocktailInProgressActivity.class);

                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);

                context.startActivity(intent);
                CocktailInProgressActivity.allowBack = false;
            } else {
                setButtonBlur(context);
            }
        } catch (JSONException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Incoming message which tells the app that the cocktail was made successfully.
     * @param context Application context needed for intents.
     * @param object Content of response.
     * @return Nothing.
     */
    public static void makeCocktailFinished(Context context, JSONObject object) {
        try {
            makingBlocked = false;

            if(CommunicationManager.activeActions.containsValue(UUID.fromString(object.getString("action_id")))) {
                CommunicationManager.activeActions.remove("make_cocktail");
                CocktailInProgressActivity.allowBack = true;

                Intent intent = new Intent(context, CocktailFinishedActivity.class);

                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                context.startActivity(intent);
            } else {
                cocktail = cocktails.get(chooseCocktail);
                setButtonBlur(context);
            }
        } catch (JSONException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Incoming message which tells the app that the cocktail making was cancelled.
     * @param context Application context needed for intents.
     * @param object Content of response.
     * @return Nothing.
     */
    public static void makeCocktailCancelled(Context context, JSONObject object) {
        try {
            makingBlocked = false;

            if(CommunicationManager.activeActions.containsValue(UUID.fromString(object.getString("action_id")))) {
                CommunicationManager.activeActions.remove("make_cocktail");
                CocktailInProgressActivity.allowBack = true;

                Intent intent = new Intent(context, CocktailCancelledActivity.class);

                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                context.startActivity(intent);
            } else {
                if(MachineController.currentActivity == "cocktail_choosesize") {
                    cocktail = cocktails.get(chooseCocktail);
                    setButtonBlur(context);
                }
            }
        } catch (JSONException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Creates a new cocktail and stores it in the database.
     * @param context Application context needed to update cocktail list.
     * @param cocktail Cocktail object to be created.
     * @return Nothing.
     */
    public static void createCocktail(Context context, Cocktail cocktail) {
        if(!cocktails.containsKey(cocktail.getCocktailId())) {
            JSONArray ingredients = new JSONArray();

            for(int i = 0; i < cocktail.getIngredients().size(); i++) {
                try {
                    Ingredient ingredient = (Ingredient) cocktail.getIngredients().keySet().toArray()[i];
                    int amount = (int) cocktail.getIngredients().values().toArray()[i];

                    JSONObject object = new JSONObject();

                    object.put("ingredientId", ingredient.getIngredientId().toString());
                    object.put("amount", amount);

                    ingredients.put(object);
                } catch (JSONException ex) {
                    ex.printStackTrace();
                }
            }

            try {
                final PreparedStatement statement = DatabaseManager.getConnection().prepareStatement("INSERT INTO cocktails (cocktailId, name, description, ingredients, enabled) VALUES (?, ?, ?, ?, ?)");
                statement.setString(1, cocktail.getCocktailId().toString());
                statement.setString(2, cocktail.getName());
                statement.setString(3, cocktail.getDescription());
                statement.setString(4, ingredients.toString());
                statement.setBoolean(5, cocktail.isEnabled());
                statement.execute();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }

            getCocktails(context);
        }
    }

    /**
     * Updates a specified cocktail.
     * @param context Application context needed to update cocktail list.
     * @param cocktail Cocktail object to be updated.
     * @return Nothing.
     */
    public static void updateCocktail(Context context, Cocktail cocktail) {
        if(cocktails.containsKey(cocktail.getCocktailId())) {
            JSONArray ingredients = new JSONArray();

            for(int i = 0; i < cocktail.getIngredients().size(); i++) {
                try {
                    Ingredient ingredient = (Ingredient) cocktail.getIngredients().keySet().toArray()[i];
                    int amount = (int) cocktail.getIngredients().values().toArray()[i];

                    JSONObject object = new JSONObject();

                    object.put("ingredientId", ingredient.getIngredientId().toString());
                    object.put("amount", amount);

                    ingredients.put(object);
                } catch (JSONException ex) {
                    ex.printStackTrace();
                }
            }

            try {
                final PreparedStatement statement = DatabaseManager.getConnection().prepareStatement("UPDATE cocktails SET name = ?, description = ?, ingredients = ?, enabled = ? WHERE cocktailId = ?");
                statement.setString(1, cocktail.getName());
                statement.setString(2, cocktail.getDescription());
                statement.setString(3, ingredients.toString());
                statement.setBoolean(4, cocktail.isEnabled());
                statement.setString(5, cocktail.getCocktailId().toString());
                statement.execute();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }

            getCocktails(context);
        }
    }

    /**
     * Deletes a specified cocktail.
     * @param context Application context needed to update cocktail list.
     * @param cocktail Cocktail object to be deleted.
     * @return Nothing.
     */
    public static void deleteCocktail(Context context, Cocktail cocktail) {
        if(cocktails.containsKey(cocktail.getCocktailId())) {
            try {
                final PreparedStatement statement = DatabaseManager.getConnection().prepareStatement("DELETE FROM cocktails WHERE cocktailId = ?");
                statement.setString(1, cocktail.getCocktailId().toString());
                statement.execute();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }

            getCocktails(context);
        }
    }

    /**
     * Checks if any action is in process and blurs buttons.
     * @param context Application context needed for intents.
     * @return Nothing.
     */
    public static void setButtonBlur(Context context) {
        if(CommunicationManager.activeActions.containsKey("make_cocktail") || CommunicationManager.activeActions.containsKey("machine_clean")) {
            if(MachineController.currentActivity == "cocktail_choosesize") {
                blurCocktailButtons();
            } else if(MachineController.currentActivity == "cocktail_confirm") {
                Intent intent = new Intent(context, ChooseSizeActvitity.class);

                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                context.startActivity(intent);
            } else if(MachineController.currentActivity == "admin_clean") {
                blurCleaningButton();
            }
        } else {
            if(MachineController.currentActivity == "cocktail_choosesize") {
                if(checkAvailability(cocktail))
                    unblurCocktailButtons();
                else
                    blurCocktailButtons();
            } else if(MachineController.currentActivity == "admin_clean") {
                unblurCleaningButton();
            }
        }
    }

    /**
     * Blurs cocktail start buttons.
     * @return Nothing.
     */
    public static void blurCocktailButtons() {
        chooseActivity.findViewById(R.id.confirm_smallSize).setAlpha(.5f);
        chooseActivity.findViewById(R.id.confirm_bigSize).setAlpha(.5f);
        chooseActivity.findViewById(R.id.confirm_smallSize).setClickable(false);
        chooseActivity.findViewById(R.id.confirm_bigSize).setClickable(false);
    }

    /**
     * Unblurs cocktail start buttons.
     * @return Nothing.
     */
    public static void unblurCocktailButtons() {
        chooseActivity.findViewById(R.id.confirm_smallSize).setAlpha(1);
        chooseActivity.findViewById(R.id.confirm_bigSize).setAlpha(1);
        chooseActivity.findViewById(R.id.confirm_smallSize).setClickable(true);
        chooseActivity.findViewById(R.id.confirm_bigSize).setClickable(true);
    }

    /**
     * Blurs cleaning start buttons.
     * @return Nothing.
     */
    public static void blurCleaningButton() {
        cleanActivity.findViewById(R.id.cleanscreen_btStart).setAlpha(.5f);
        cleanActivity.findViewById(R.id.cleanscreen_btStart).setClickable(false);
    }

    /**
     * Unblurs cleaning start buttons.
     * @return Nothing.
     */
    public static void unblurCleaningButton() {
        cleanActivity.findViewById(R.id.cleanscreen_btStart).setAlpha(1);
        cleanActivity.findViewById(R.id.cleanscreen_btStart).setClickable(true);
    }
}
