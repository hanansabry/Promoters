package com.android.promoters.model;

import java.util.ArrayList;

public class Organizer {

    private String id;
    private String name;
    private String history;
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

    public String getHistory() {
        return history;
    }

    public void setHistory(String history) {
        this.history = history;
    }

    public ArrayList<String> getEventsList() {
        return eventsList;
    }

    public void setEventsList(ArrayList<String> eventsList) {
        this.eventsList = eventsList;
    }
}
