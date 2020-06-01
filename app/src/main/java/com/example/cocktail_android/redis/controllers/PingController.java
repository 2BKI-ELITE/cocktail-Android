package com.example.cocktail_android.redis.controllers;

import android.content.Context;

import com.example.cocktail_android.redis.CommunicationManager;

import org.json.JSONException;
import org.json.JSONObject;

public class PingController {

    /**
     * Incoming message which tells the app that the ping was successful.
     * @param context Application context, not used in this case.
     * @param object Content of message.
     * @return Nothing.
     */
    public static void pong(Context context, JSONObject object) {
        try {
            JSONObject message = new JSONObject();

            message.put("action", "pong");

            CommunicationManager.publishBroadcastMessage(message);
        } catch (JSONException ignored) {}
    }

    /**
     * Pings raspberry to check connection.
     * @param context Application context, not used in this case.
     * @return Nothing.
     */
    public static void ping(Context context) {
        try {
            JSONObject message = new JSONObject();

            message.put("action", "ping");

            CommunicationManager.publishBroadcastMessage(message);
        } catch (JSONException ignored) {}
    }
}
