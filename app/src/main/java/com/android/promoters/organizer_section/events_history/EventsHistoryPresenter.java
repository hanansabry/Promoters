package com.android.promoters.organizer_section.events_history;

import com.android.promoters.model.Event;
import com.android.promoters.model.Promoter;
import com.android.promoters.organizer_section.events_history.events_list.EventsAdapter;
import com.android.promoters.organizer_section.events_history.promoters_list.AcceptedPromotersAdapter;
import com.android.promoters.organizer_section.events_history.promoters_list.CandidatePromotersAdapter;

import java.util.ArrayList;

public class EventsHistoryPresenter implements EventsHistoryContract.Presenter {

    private ArrayList<Event> events = new ArrayList<>();
    private ArrayList<Promoter> candidatePromoters = new ArrayList<>();
    private ArrayList<Promoter> acceptedPromoters = new ArrayList<>();

    @Override
    public void bindEventsList(ArrayList<Event> events) {
        this.events = events;
    }

    @Override
    public void onBindEventItemAtPosition(EventsAdapter.EventViewHolder viewHolder, int position) {
        Event event = events.get(position);
        viewHolder.setEventData(event);
    }

    @Override
    public int getEventsListSize() {
        return events.size();
    }

    @Override
    public ArrayList<Event> retrieveEvents() {
        ArrayList<Event> list = new ArrayList<>();
        Event e1 = new Event();
        e1.setTitle("My new event1");
        e1.setStartDate("25/1/2020");
        e1.setEndDate("30/1/2020");
        e1.setStatus(Event.EventStatus.Upcoming);
        e1.setCandidatePromoters(retrieveCandidatePromoters());
        list.add(e1);

        Event e2 = new Event();
        e2.setTitle("My new event2");
        e2.setStartDate("20/1/2020");
        e2.setEndDate("27/1/2020");
        e2.setStatus(Event.EventStatus.Active);
        e2.setCandidatePromoters(retrieveCandidatePromoters());
        list.add(e2);

        Event e3 = new Event();
        e3.setTitle("My new event3");
        e3.setStartDate("15/1/2020");
        e3.setEndDate("19/1/2020");
        e3.setStatus(Event.EventStatus.Closed);
        e3.setCandidatePromoters(new ArrayList<Promoter>());
        e3.setAcceptedPromoters(retrieveaccptedpromoters());
        list.add(e3);

        return list;
    }

    @Override
    public void bindCandidatePromotersList(ArrayList<Promoter> promoters) {
        this.candidatePromoters = promoters;
    }

    @Override
    public void bindAcceptedPromotersList(ArrayList<Promoter> promoters) {
        this.acceptedPromoters = promoters;
    }

    @Override
    public void onBindCandidatePromoterAtPosition(CandidatePromotersAdapter.PromoterViewHolder viewHolder, int position) {
        Promoter promoter = candidatePromoters.get(position);
        viewHolder.setPromoterData(promoter);
    }

    @Override
    public void onBindAcceptedPromoterAtPosition(AcceptedPromotersAdapter.PromoterViewHolder viewHolder, int position) {
        Promoter promoter = acceptedPromoters.get(position);
        viewHolder.setPromoterName(promoter);
    }

    @Override
    public int getCandidatePromotersListSize() {
        return candidatePromoters.size();
    }

    @Override
    public int getAcceptedPromotersListSize() {
        return acceptedPromoters.size();
    }

    @Override
    public ArrayList<Promoter> retrieveCandidatePromoters() {
        ArrayList<Promoter> promoters = new ArrayList<>();

        Promoter p1 = new Promoter();
        p1.setName("New Promoter 1");
        p1.setRank(10);
        promoters.add(p1);


        Promoter p2 = new Promoter();
        p2.setName("New Promoter 2");
        p2.setRank(5);
        promoters.add(p2);

        Promoter p3 = new Promoter();
        p3.setName("New Promoter 3");
        p3.setRank(7);
        promoters.add(p3);

        return promoters;
    }

    public ArrayList<Promoter> retrieveaccptedpromoters() {
        ArrayList<Promoter> promoters = new ArrayList<>();

        Promoter p1 = new Promoter();
        p1.setName("Accepted Promoter 1");
//        p1.setRank(10);
        promoters.add(p1);

        Promoter p3 = new Promoter();
        p3.setName("Accepted Promoter 3");
//        p1.setRank(10);
        promoters.add(p3);


        Promoter p2 = new Promoter();
        p2.setName("Accepted Promoter 2");
//        p2.setRank(5);
        promoters.add(p2);

        return promoters;
    }

    public void setPromoterRank(int rank, int adapterPosition) {
        Promoter promoter = candidatePromoters.get(adapterPosition);
        promoter.setRank(rank);
    }

}
