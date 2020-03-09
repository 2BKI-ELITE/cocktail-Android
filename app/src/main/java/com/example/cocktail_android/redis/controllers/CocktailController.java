package com.example.cocktail_android.redis.controllers;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.example.cocktail_android.R;
import com.example.cocktail_android.mysql.DatabaseManager;
import com.example.cocktail_android.objects.Cocktail;
import com.example.cocktail_android.objects.Ingredient;
import com.example.cocktail_android.recycler.CocktailItem;
import com.example.cocktail_android.redis.CommunicationManager;
import com.example.cocktail_android.screenactivities.FailedActivity;
import com.example.cocktail_android.screenactivities.InProgressActivity;
import com.example.cocktail_android.screenactivities.MainActivity;
import com.example.cocktail_android.screenactivities.SuccessActivity;
import com.example.cocktail_android.screenactivities.admin.AdminPanelActivity;

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

    public static ArrayList<CocktailItem> fillDummyCocktails(Context context) {
        ArrayList<CocktailItem> cocktails = new ArrayList<>();

        for(int i = 0; i < 20; i++) {
            Cocktail cocktail = new Cocktail(context, UUID.randomUUID(), "Dummy Cocktail " + (i + 1), "Dummy Cocktail Description", new HashMap<>(), true, new Date());
            cocktails.add(convertToCocktailItem(cocktail));
        }

        return cocktails;
    }

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
                } catch (JSONException ignored) {}
            }
        } catch (SQLException e) {}
    }

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
                bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.test_cocktail_pic);
            }
        } catch (IOException e) {
            e.printStackTrace();
            bitmap = BitmapFactory.decodeResource(context.getResources(),R.drawable.test_cocktail_pic);
        }

        return bitmap;
    }

    public static CocktailItem convertToCocktailItem(Cocktail cocktail) {
        return new CocktailItem(cocktail.getImage(), cocktail.getName(), cocktail);
    }

    public static void makeCocktail(Cocktail cocktail) {
        JSONObject message = new JSONObject();
        UUID actionId = UUID.randomUUID();

        try {
            message.put("action", "make_cocktail_start");
            message.put("action_id", actionId);
            message.put("cocktail_id", cocktail.getCocktailId());
        } catch(JSONException ignored) {}

        CommunicationManager.activeActions.remove("make_cocktail");
        CommunicationManager.activeActions.put("make_cocktail", actionId);
        CommunicationManager.publishMessage(message);
    }

    public static void makeCocktailConfirmation(Context context, JSONObject object) {
        try {
            makingBlocked = true;

            if(CommunicationManager.activeActions.containsValue(UUID.fromString(object.getString("action_id")))) {
                Intent intent = new Intent(context, InProgressActivity.class);

                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);

                intent.putExtra("title", "Zubereitung");
                intent.putExtra("description", "Ihr Cocktail wird zubereitet");

                context.startActivity(intent);
                InProgressActivity.allowBack = false;
            } else {
                // TODO: 09.03.2020 check if choosecocktail screen is opened

                chooseActivity.findViewById(R.id.confirm_smallSize).setAlpha(.5f);
                chooseActivity.findViewById(R.id.confirm_bigSize).setAlpha(.5f);
                chooseActivity.findViewById(R.id.confirm_smallSize).setClickable(false);
                chooseActivity.findViewById(R.id.confirm_bigSize).setClickable(false);
            }
        } catch (JSONException ignored) {}
    }

    public static void makeCocktailFinished(Context context, JSONObject object) {
        try {
            makingBlocked = false;

            if(CommunicationManager.activeActions.containsValue(UUID.fromString(object.getString("action_id")))) {
                CommunicationManager.activeActions.remove("make_cocktail");
                InProgressActivity.allowBack = true;

                Intent intent = new Intent(context, SuccessActivity.class);

                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                intent.putExtra("title", "Zubereitung abgeschlossen");
                intent.putExtra("description", "Bitte nehme dein Glas nun aus dem Gerät hinaus.");
                intent.putExtra("buttonText", "Zubereitung beenden");
                intent.putExtra("buttonLink", "main");

                context.startActivity(intent);
            } else {
                // TODO: 09.03.2020 check if choosecocktail screen is opened

                chooseActivity.findViewById(R.id.confirm_smallSize).setAlpha(1);
                chooseActivity.findViewById(R.id.confirm_bigSize).setAlpha(1);
                chooseActivity.findViewById(R.id.confirm_smallSize).setClickable(true);
                chooseActivity.findViewById(R.id.confirm_bigSize).setClickable(true);
            }
        } catch (JSONException ignored) {}
    }

    public static void makeCocktailCancelled(Context context, JSONObject object) {
        try {
            makingBlocked = false;

            if(CommunicationManager.activeActions.containsValue(UUID.fromString(object.getString("action_id")))) {
                CommunicationManager.activeActions.remove("make_cocktail");
                InProgressActivity.allowBack = true;

                Intent intent = new Intent(context, FailedActivity.class);

                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                intent.putExtra("title", "Zubereitung abgebrochen");
                intent.putExtra("description", "Die Zubereitung wurde vorzeitig abgebrochen. Bitte nehme dein Glas nun aus dem Gerät hinaus.");
                intent.putExtra("buttonText", "Zubereitung beenden");
                intent.putExtra("buttonLink", "main");

                context.startActivity(intent);
            } else {
                // TODO: 09.03.2020 check if choosecocktail screen is opened
                chooseActivity.findViewById(R.id.confirm_smallSize).setAlpha(1);
                chooseActivity.findViewById(R.id.confirm_bigSize).setAlpha(1);
                chooseActivity.findViewById(R.id.confirm_smallSize).setClickable(true);
                chooseActivity.findViewById(R.id.confirm_bigSize).setClickable(true);
            }
        } catch (JSONException ignored) {}
    }
}
