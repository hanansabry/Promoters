package com.android.promoters.model;

import java.util.ArrayList;

public class Organizer extends User{

    private String history;
    private ArrayList<String> eventsList;

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
