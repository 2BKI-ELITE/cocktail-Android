package com.example.cocktail_android.redis.controllers.auth;

import android.content.Context;
import android.content.Intent;

import com.example.cocktail_android.enums.CocktailSize;
import com.example.cocktail_android.objects.Cocktail;
import com.example.cocktail_android.objects.User;
import com.example.cocktail_android.redis.CommunicationManager;
import com.example.cocktail_android.redis.controllers.UserController;
import com.example.cocktail_android.screenactivities.ConfirmCocktailActivity;
import com.example.cocktail_android.screenactivities.error.ConfirmAgeFailedActivity;
import com.example.cocktail_android.screenactivities.error.FailedActivity;
import com.example.cocktail_android.screenactivities.MainActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.UUID;

public class ConfirmAgeController {

    private static Cocktail cocktail;
    private static CocktailSize size;

    /**
     * Starts authentication process for age check.
     * @param c Cocktail which a user has to confirm his age for.
     * @param s Size of cocktail to give information later to machine.
     * @return Nothing.
     */
    public static void start(Cocktail c, CocktailSize s) {
        JSONObject message = new JSONObject();
        UUID actionId = UUID.randomUUID();

        cocktail = c;
        size = s;

        try {
            message.put("action", "confirm_age_start");
            message.put("action_id", actionId);
        } catch (JSONException ex) {
            ex.printStackTrace();
        }

        CommunicationManager.activeActions.remove("maintenance_auth");
        CommunicationManager.activeActions.remove("confirm_age");
        CommunicationManager.activeActions.remove("admin_auth");
        CommunicationManager.activeActions.remove("user_add_auth");
        CommunicationManager.activeActions.remove("user_edit_auth");

        CommunicationManager.activeActions.put("confirm_age", actionId);
        CommunicationManager.publishMessage(message);
    }

    /**
     * Cancels age check authentication.
     * @return Nothing.
     */
    public static void cancel() {
        if(CommunicationManager.activeActions.containsKey("confirm_age")) {
            UUID actionId = CommunicationManager.activeActions.get("confirm_age");
            JSONObject message = new JSONObject();

            try {
                message.put("action", "confirm_age_cancel");
                message.put("action_id", actionId);
            } catch (JSONException ex) {
                ex.printStackTrace();
            }

            CommunicationManager.activeActions.remove("confirm_age");
            CommunicationManager.publishMessage(message);
        }
    }

    /**
     * Handles response on admin panel authentication.
     * @param context Needs Context to create intents.
     * @param object Content of response.
     * @return Nothing.
     */
    public static void response(Context context, JSONObject object) {
        try {
            if(CommunicationManager.activeActions.containsValue(UUID.fromString(object.getString("action_id")))) {
                if(object.getString("response").equalsIgnoreCase("cancel")) {
                    CommunicationManager.activeActions.remove("confirm_age");

                    Intent intent = new Intent(context, MainActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                } else {
                    User user = UserController.getUser(object.getString("response"));

                    if(user != null && user.isAdult()) {
                        UUID actionId = CommunicationManager.activeActions.get("confirm_age");
                        JSONObject message = new JSONObject();

                        try {
                            message.put("action", "confirm_age_finish");
                            message.put("action_id", actionId);
                        } catch (JSONException ex) {
                            ex.printStackTrace();
                        }

                        CommunicationManager.activeActions.remove("confirm_age");
                        CommunicationManager.publishMessage(message);

                        Intent intent = new Intent(context, ConfirmCocktailActivity.class);

                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                        intent.putExtra("cocktailId", cocktail.getCocktailId().toString());
                        intent.putExtra("size", size.toString());

                        context.startActivity(intent);
                    } else {
                        UUID actionId = CommunicationManager.activeActions.get("confirm_age");
                        JSONObject message = new JSONObject();

                        try {
                            message.put("action", "confirm_age_cancel");
                            message.put("action_id", actionId);
                        } catch (JSONException ex) {
                            ex.printStackTrace();
                        }

                        CommunicationManager.activeActions.remove("confirm_age");
                        CommunicationManager.publishMessage(message);

                        Intent intent = new Intent(context, ConfirmAgeFailedActivity.class);

                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                        context.startActivity(intent);
                    }
                }
            }
        } catch (JSONException ex) {
            ex.printStackTrace();
        }
    }
}
