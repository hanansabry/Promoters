package com.android.promoters.model;

import java.util.ArrayList;

public class Promoter {

    private String id;
    private String name;
    private String region;
    private int experience;
    private int rank;
    private ArrayList<String> skills;
    private ArrayList<String> eventsList;

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

    public ArrayList<String> getEventsList() {
        return eventsList;
    }

    public void setEventsList(ArrayList<String> eventsList) {
        this.eventsList = eventsList;
    }
}
