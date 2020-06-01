package com.example.cocktail_android.redis;

import android.content.Context;
import android.content.Intent;

import com.example.cocktail_android.redis.controllers.MachineController;
import com.example.cocktail_android.redis.controllers.auth.AdminAuthController;
import com.example.cocktail_android.redis.controllers.CocktailController;
import com.example.cocktail_android.redis.controllers.auth.ConfirmAgeController;
import com.example.cocktail_android.redis.controllers.auth.MaintenanceAuthController;
import com.example.cocktail_android.redis.controllers.PingController;
import com.example.cocktail_android.redis.controllers.auth.UserAddAuthController;
import com.example.cocktail_android.redis.controllers.auth.UserEditAuthController;
import com.example.cocktail_android.screenactivities.error.NoConnectionActivity;

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

    public static void publishBroadcastMessage(JSONObject object) {
        JSONObject sender = new JSONObject();
        JSONObject to = new JSONObject();

        try {
            sender.put("uuid", uuid.toString());
            sender.put("type", "app_android");

            to.put("uuid", "broadcast");
            to.put("type", "all");

            object.put("sender", sender);
            object.put("to", to);
        } catch (JSONException ignored) {}

        jedisPub.publish("general", object.toString());
    }

    public static void setupSubscriber(Context context) {
        Thread.UncaughtExceptionHandler h = (th, ex) -> {
            Intent intent = new Intent(context, NoConnectionActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
            context.startActivity(intent);
        };

        Thread t = new Thread(() -> jedisSub.subscribe(new JedisPubSub() {
            @Override
            public void onMessage(String channel, String message) {
                try {
                    JSONObject object = new JSONObject(message);
                    System.out.println(object.toString());

                    if(object.getJSONObject("to").has("uuid")) {
                        if(object.getJSONObject("to").getString("uuid").equalsIgnoreCase(uuid.toString()) || object.getJSONObject("to").getString("uuid").equalsIgnoreCase("broadcast")) {
                            switch(object.getString("action")) {
                                case "ping":
                                    PingController.pong(context, object);
                                    break;

                                case "admin_auth_response":
                                    AdminAuthController.response(context, object);
                                    break;

                                case "maintenance_auth_response":
                                    MaintenanceAuthController.response(context, object);
                                    break;

                                case "confirm_age_response":
                                    ConfirmAgeController.response(context, object);
                                    break;

                                case "user_add_auth_response":
                                    UserAddAuthController.response(context, object);
                                    break;

                                case "user_edit_auth_response":
                                    UserEditAuthController.response(context, object);
                                    break;

                                case "make_cocktail_confirmation":
                                    CocktailController.makeCocktailConfirmation(context, object);
                                    break;

                                case "make_cocktail_finished":
                                    CocktailController.makeCocktailFinished(context, object);
                                    break;

                                case "make_cocktail_cancelled":
                                    CocktailController.makeCocktailCancelled(context, object);
                                    break;

                                case "machine_clean_confirmation":
                                    MachineController.cleanMachineConfirmation(context, object);
                                    break;

                                case "machine_clean_finished":
                                    MachineController.cleanMachineFinished(context, object);
                                    break;

                                case "machine_clean_cancelled":
                                    MachineController.cleanMachineCancelled(context, object);
                                    break;

                                default:
                                    break;
                            }
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
