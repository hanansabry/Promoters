package com.android.promoters.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Promoter extends User {

    private String region;
    private int experience;
    private ArrayList<String> skills;
    private ArrayList<String> eventsList;

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public int getExperience() {
        return experience;
    }

    public void setExperience(int experience) {
        this.experience = experience;
    }

    public ArrayList<String> getSkills() {
        return skills;
    }

    public void setSkills(ArrayList<String> skills) {
        this.skills = skills;
    }

    public ArrayList<String> getEventsList() {
        return eventsList;
    }

    public void setEventsList(ArrayList<String> eventsList) {
        this.eventsList = eventsList;
    }
}
