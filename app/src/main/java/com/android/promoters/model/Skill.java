package com.android.promoters.model;

public class Skill {

    private String skillName;
    private int requiredPromoters;

    public String getSkillName() {
        return skillName;
    }

    public void setSkillName(String skillName) {
        this.skillName = skillName;
    }

    public int getRequiredPromoters() {
        return requiredPromoters;
    }

    public void setRequiredPromoters(int requiredPromoters) {
        this.requiredPromoters = requiredPromoters;
    }
}
