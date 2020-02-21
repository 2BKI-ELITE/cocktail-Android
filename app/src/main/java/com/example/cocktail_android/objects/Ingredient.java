package com.example.cocktail_android.objects;

public class Ingredient {
    private String name;
    private boolean containsAlcohol;
    private int pump;
    private int fillLevel;
    private int fillCapacity;

    public Ingredient(String name, boolean containsAlcohol, int pump, int fillLevel, int fillCapacity) {
        this.name = name;
        this.containsAlcohol = containsAlcohol;
        this.pump = pump;
        this.fillLevel = fillLevel;
        this.fillCapacity = fillCapacity;
    }

    public String getName() {
        return name;
    }

    public boolean containsAlcohol() {
        return containsAlcohol;
    }

    public int getPump() {
        return pump;
    }

    public int getFillLevel() {
        return fillLevel;
    }

    public int getFillCapacity() {
        return fillCapacity;
    }
}
