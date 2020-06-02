package com.example.cocktail_android.redis.controllers;

import android.content.Context;
import android.content.Intent;

import com.example.cocktail_android.redis.CommunicationManager;
import com.example.cocktail_android.screenactivities.admin.cleaning.CleaningCancelledActivity;
import com.example.cocktail_android.screenactivities.admin.cleaning.CleaningFinishedActivity;
import com.example.cocktail_android.screenactivities.admin.cleaning.CleaningInProgressActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.UUID;

public class MachineController {

    public static String currentActivity;

    /**
     * Initiates machine cleaning process
     * @return Nothing.
     */
    public static void cleanMachine() {
        JSONObject message = new JSONObject();
        UUID actionId = UUID.randomUUID();

        try {
            message.put("action", "machine_clean");
            message.put("action_id", actionId);
        } catch (JSONException ignored) {}

        CommunicationManager.activeActions.remove("machine_clean");
        CommunicationManager.activeActions.put("machine_clean", actionId);
        CommunicationManager.publishMessage(message);
    }

    /**
     * Incoming message which confirms that the machine is getting cleaned.
     * @param context Application context needed for intents.
     * @param object Content of response.
     * @return Nothing.
     */
    public static void cleanMachineConfirmation(Context context, JSONObject object) {
        try {
            CocktailController.makingBlocked = true;

            if(CommunicationManager.activeActions.containsValue(UUID.fromString(object.getString("action_id")))) {
                Intent intent = new Intent(context, CleaningInProgressActivity.class);

                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);

                context.startActivity(intent);
                CleaningInProgressActivity.allowBack = false;
            } else {
                CocktailController.setButtonBlur(context);
            }
        } catch (JSONException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Incoming message which tells the app that the machine was cleaned successfully.
     * @param context Application context needed for intents.
     * @param object Content of response.
     * @return Nothing.
     */
    public static void cleanMachineFinished(Context context, JSONObject object) {
        try {
            CocktailController.makingBlocked = false;

            if(CommunicationManager.activeActions.containsValue(UUID.fromString(object.getString("action_id")))) {
                CommunicationManager.activeActions.remove("machine_clean");
                CleaningInProgressActivity.allowBack = true;

                Intent intent = new Intent(context, CleaningFinishedActivity.class);

                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                context.startActivity(intent);
            } else {
                CocktailController.setButtonBlur(context);
            }
        } catch (JSONException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Incoming message which tells the app that the machine cleaning was cancelled.
     * @param context Application context needed for intents.
     * @param object Content of response.
     * @return Nothing.
     */
    public static void cleanMachineCancelled(Context context, JSONObject object) {
        try {
            CocktailController.makingBlocked = false;

            if(CommunicationManager.activeActions.containsValue(UUID.fromString(object.getString("action_id")))) {
                CommunicationManager.activeActions.remove("machine_clean");
                CleaningInProgressActivity.allowBack = true;

                Intent intent = new Intent(context, CleaningCancelledActivity.class);

                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                context.startActivity(intent);
            } else {
                CocktailController.setButtonBlur(context);
            }
        } catch (JSONException ex) {
            ex.printStackTrace();
        }
    }
}
