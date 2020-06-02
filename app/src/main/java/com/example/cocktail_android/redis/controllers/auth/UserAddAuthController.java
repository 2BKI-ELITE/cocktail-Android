package com.example.cocktail_android.redis.controllers.auth;

import android.content.Context;
import android.content.Intent;

import com.example.cocktail_android.objects.User;
import com.example.cocktail_android.redis.CommunicationManager;
import com.example.cocktail_android.redis.controllers.UserController;
import com.example.cocktail_android.screenactivities.admin.users.UserActivity;
import com.example.cocktail_android.screenactivities.admin.users.UserAddActivity;
import com.example.cocktail_android.screenactivities.error.UserAddAuthFailedActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.UUID;

public class UserAddAuthController {

    /**
     * Starts scanning for RFID-Tag to add.
     * @return Nothing.
     */
    public static void start() {
        JSONObject message = new JSONObject();
        UUID actionId = UUID.randomUUID();

        try {
            message.put("action", "user_add_auth_start");
            message.put("action_id", actionId);
        } catch (JSONException ex) {
            ex.printStackTrace();
        }

        CommunicationManager.activeActions.remove("admin_auth");
        CommunicationManager.activeActions.remove("maintenance_auth");
        CommunicationManager.activeActions.remove("confirm_age");
        CommunicationManager.activeActions.remove("user_add_auth");
        CommunicationManager.activeActions.remove("user_edit_auth");

        CommunicationManager.activeActions.put("user_add_auth", actionId);
        CommunicationManager.publishMessage(message);
    }

    /**
     * Cancels scanning for RFID-Tag.
     * @return Nothing.
     */
    public static void cancel() {
        if(CommunicationManager.activeActions.containsKey("user_add_auth")) {
            UUID actionId = CommunicationManager.activeActions.get("user_add_auth");
            JSONObject message = new JSONObject();

            try {
                message.put("action", "user_add_auth_cancel");
                message.put("action_id", actionId);
            } catch (JSONException ex) {
                ex.printStackTrace();
            }

            CommunicationManager.activeActions.remove("user_add_auth");
            CommunicationManager.publishMessage(message);
        }
    }

    /**
     * Handles response on RFID-Tag scanning.
     * @param context Needs Context to create intents.
     * @param object Content of response.
     * @return Nothing.
     */
    public static void response(Context context, JSONObject object) {
        try {
            if(CommunicationManager.activeActions.containsValue(UUID.fromString(object.getString("action_id")))) {
                if(object.getString("response").equalsIgnoreCase("cancel")) {
                    CommunicationManager.activeActions.remove("user_add_auth");

                    Intent intent = new Intent(context, UserActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                } else {
                    User user = UserController.getUser(object.getString("response"));

                    if(user == null) {
                        UUID actionId = CommunicationManager.activeActions.get("user_add_auth");
                        JSONObject message = new JSONObject();

                        try {
                            message.put("action", "user_add_auth_finish");
                            message.put("action_id", actionId);
                        } catch (JSONException ex) {
                            ex.printStackTrace();
                        }

                        CommunicationManager.activeActions.remove("user_add_auth");
                        CommunicationManager.publishMessage(message);

                        Intent intent = new Intent(context, UserAddActivity.class);

                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                        intent.putExtra("rfidCode", object.getString("response"));

                        context.startActivity(intent);
                    } else {
                        UUID actionId = CommunicationManager.activeActions.get("user_add_auth");
                        JSONObject message = new JSONObject();

                        try {
                            message.put("action", "user_add_auth_cancel");
                            message.put("action_id", actionId);
                        } catch (JSONException ex) {
                            ex.printStackTrace();
                        }

                        CommunicationManager.activeActions.remove("user_add_auth");
                        CommunicationManager.publishMessage(message);

                        Intent intent = new Intent(context, UserAddAuthFailedActivity.class);

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
