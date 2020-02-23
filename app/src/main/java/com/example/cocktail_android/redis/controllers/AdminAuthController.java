package com.example.cocktail_android.redis.controllers;

import android.content.Context;
import android.content.Intent;

import com.example.cocktail_android.screenactivities.AdminPanelActivity;
import com.example.cocktail_android.LoginActivity;
import com.example.cocktail_android.redis.CommunicationManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.UUID;

public class AdminAuthController {

    public static void start() {
        JSONObject message = new JSONObject();
        UUID actionId = UUID.randomUUID();

        try {
            message.put("action", "admin_auth_start");
            message.put("action_id", actionId);
        } catch (JSONException ignored) {}

        CommunicationManager.activeActions.remove("admin_auth");

        CommunicationManager.activeActions.put("admin_auth", actionId);
        CommunicationManager.publishMessage(message);
    }

    public static void cancel() {
        if(CommunicationManager.activeActions.containsKey("admin_auth")) {
            UUID actionId = CommunicationManager.activeActions.get("admin_auth");
            JSONObject message = new JSONObject();

            try {
                message.put("action", "admin_auth_cancel");
                message.put("action_id", actionId);
            } catch(JSONException ignored) {}

            CommunicationManager.activeActions.remove("admin_auth");
            CommunicationManager.publishMessage(message);
        }
    }

    public static void response(Context context, JSONObject object) {
        try {
            if(CommunicationManager.activeActions.get("admin_auth").toString().equalsIgnoreCase(object.getString("action_id"))) {
                if(object.getString("response").equalsIgnoreCase("test")) {
                    CommunicationManager.activeActions.remove("admin_auth");

                    Intent intent = new Intent(context, AdminPanelActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                } else {
                    // TODO: 07.02.2020 DO SOMETHING FOR WRONG RFID CHIP
                }
            }
        } catch (JSONException ignored) {}
    }
}
