package com.android.promoters.model;

import java.util.ArrayList;
import java.util.HashMap;

public class Event {

    public enum EventStatus {
        Active, Closed, Upcoming
    }

    private String id;
    private String title;
    private String organizerId;
    private String startDate;
    private String endDate;
    private String region;
    private ArrayList<Skill> requiredSkills;
    private HashMap<String, Boolean> acceptedPromotersIds;
    private HashMap<String, Boolean> candidatePromotersIds;
    private EventStatus status;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getOrganizerId() {
        return organizerId;
    }

    public void setOrganizerId(String organizerId) {
        this.organizerId = organizerId;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public ArrayList<Skill> getRequiredSkills() {
        return requiredSkills;
    }

    public void setRequiredSkills(ArrayList<Skill> requiredSkills) {
        this.requiredSkills = requiredSkills;
    }

    public HashMap<String, Boolean> getAcceptedPromotersIds() {
        return acceptedPromotersIds;
    }

    public void setAcceptedPromotersIds(HashMap<String, Boolean> acceptedPromotersIds) {
        this.acceptedPromotersIds = acceptedPromotersIds;
    }

    public HashMap<String, Boolean> getCandidatePromotersIds() {
        return candidatePromotersIds;
    }

    public void setCandidatePromotersIds(HashMap<String, Boolean> candidatePromotersIds) {
        this.candidatePromotersIds = candidatePromotersIds;
    }

    public EventStatus getStatus() {
        return status;
    }

    public void setStatus(EventStatus status) {
        this.status = status;
    }
}
