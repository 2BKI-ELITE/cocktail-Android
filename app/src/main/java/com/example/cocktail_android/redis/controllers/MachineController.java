package com.example.cocktail_android.redis.controllers;

import com.example.cocktail_android.redis.CommunicationManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.UUID;

public class MachineController {

    public static void startCleaning() {
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
}
