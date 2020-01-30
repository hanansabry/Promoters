package com.android.promoters.model;

public class Skill {

    private String name;
    private int requiredPromoters;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getRequiredPromoters() {
        return requiredPromoters;
    }

    public void setRequiredPromoters(int requiredPromoters) {
        this.requiredPromoters = requiredPromoters;
    }
}
