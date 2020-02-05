package com.example.cocktail_android.redis;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.UUID;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPubSub;

public class CommunicationManager {

    public static UUID uuid;

    private static Jedis jedis;

    public static void establishConnection() {
        System.out.println("Establishing connection to redis server...");
        uuid = UUID.randomUUID();

        jedis = new Jedis("192.168.1.1");
        //jedis.auth("projektarbeitPasswort");

        if(jedis.isConnected()) {
            System.out.println("Connected to Redis server!");
        }
    }

    public static void publishMessage(String channel, JSONObject object) {
        JSONObject sender = new JSONObject();

        try {
            sender.put("uuid", uuid.toString());
            sender.put("type", "app_android");

            object.put("sender", sender);
        } catch (JSONException ex) {}

        jedis.publish(channel, object.toString());
    }

    public static void onMessageReceive(JedisPubSub jedisPubSub, String channel) {
        jedis.subscribe(jedisPubSub, channel);
    }
}
