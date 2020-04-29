package com.example.cocktail_android.objects;

public class User {

    private String userId;
    private boolean isAdult;
    private boolean isAdmin;

    public User(String userId, boolean isAdult, boolean isAdmin) {
        this.userId = userId;
        this.isAdult = isAdult;
        this.isAdmin = isAdmin;
    }

    public String getUserId() {
        return userId;
    }

    public boolean isAdult() {
        return isAdult;
    }

    public boolean isAdmin() {
        return isAdmin;
    }
}
