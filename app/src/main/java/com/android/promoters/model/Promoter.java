package com.android.promoters.model;

import java.util.ArrayList;
import java.util.HashMap;

public class Promoter {

    private String id;
    private String name;
    private String region;
    private int experience;
    private int rank;
    private ArrayList<String> skills;
    private HashMap<String, PromoterEvent> events;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

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

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public ArrayList<String> getSkills() {
        return skills;
    }

    public void setSkills(ArrayList<String> skills) {
        this.skills = skills;
    }

    public HashMap<String, PromoterEvent> getEvents() {
        return events;
    }

    public void setEvents(HashMap<String, PromoterEvent> events) {
        this.events = events;
    }
}
