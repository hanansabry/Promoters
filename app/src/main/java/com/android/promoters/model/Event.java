package com.android.promoters.model;

import java.util.ArrayList;

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
    private ArrayList<Promoter> acceptedPromoters;
    private ArrayList<Promoter> candidatePromoters;
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

    public ArrayList<Promoter> getAcceptedPromoters() {
        return acceptedPromoters;
    }

    public void setAcceptedPromoters(ArrayList<Promoter> acceptedPromoters) {
        this.acceptedPromoters = acceptedPromoters;
    }

    public ArrayList<Promoter> getCandidatePromoters() {
        return candidatePromoters;
    }

    public void setCandidatePromoters(ArrayList<Promoter> candidatePromoters) {
        this.candidatePromoters = candidatePromoters;
    }

    public EventStatus getStatus() {
        return status;
    }

    public void setStatus(EventStatus status) {
        this.status = status;
    }
}
