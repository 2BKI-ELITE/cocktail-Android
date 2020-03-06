package com.example.cocktail_android.redis;

import android.content.Context;
import android.content.Intent;

import com.example.cocktail_android.redis.controllers.AdminAuthController;
import com.example.cocktail_android.screenactivities.NoConnectionActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.UUID;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPubSub;
import redis.clients.jedis.exceptions.JedisConnectionException;

public class CommunicationManager {

    public static UUID uuid = UUID.randomUUID();
    public static HashMap<String, UUID> activeActions = new HashMap<>();

    private static Jedis jedisPub;
    private static Jedis jedisSub;

    public static boolean establishConnection() {
        jedisPub = new Jedis("192.168.1.1");
        jedisSub = new Jedis("192.168.1.1");
        //jedis.auth("projektarbeitPasswort");

        try {
            jedisPub.ping();
            jedisSub.ping();

            return true;
        } catch (JedisConnectionException ex) {
            return false;
        }
    }

    public static void publishMessage(JSONObject object) {
        JSONObject sender = new JSONObject();
        JSONObject to = new JSONObject();

        try {
            sender.put("uuid", uuid.toString());
            sender.put("type", "app_android");

            to.put("uuid", null);
            to.put("type", "controller");

            object.put("sender", sender);
            object.put("to", to);
        } catch (JSONException ignored) {}

        jedisPub.publish("general", object.toString());
    }

    public static void setupSubscriber(Context context) {
        Thread.UncaughtExceptionHandler h = new Thread.UncaughtExceptionHandler() {
            public void uncaughtException(Thread th, Throwable ex) {
                Intent intent = new Intent(context, NoConnectionActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                context.startActivity(intent);
            }
        };

        Thread t = new Thread(() -> jedisSub.subscribe(new JedisPubSub() {
            @Override
            public void onMessage(String channel, String message) {
                try {
                    JSONObject object = new JSONObject(message);

                    if(object.getJSONObject("to").getString("uuid") != null && object.getJSONObject("to").getString("uuid").equalsIgnoreCase(uuid.toString())) {
                        if(object.getString("action").equalsIgnoreCase("admin_auth_response")) {
                            AdminAuthController.response(context, object);
                        }
                    }
                } catch (JSONException ex) {
                    ex.printStackTrace();
                } catch (JedisConnectionException ex) {
                    Intent intent = new Intent(context, NoConnectionActivity.class);
                    context.startActivity(intent);
                }
            }
        }, "general"), "subscriberThread");

        t.setUncaughtExceptionHandler(h);
        t.start();
    }
}
