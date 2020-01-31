package com.android.promoters.organizer_section.events_history;

import com.android.promoters.backend.events.EventsRepository;
import com.android.promoters.backend.promoters.PromotersRepository;
import com.android.promoters.model.Event;
import com.android.promoters.model.Promoter;
import com.android.promoters.organizer_section.events_history.events_list.EventsAdapter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class EventsHistoryPresenter implements EventsHistoryContract.Presenter {

    private ArrayList<Event> events = new ArrayList<>();
    private ArrayList<Promoter> candidatePromoters = new ArrayList<>();
    private ArrayList<Promoter> acceptedPromoters = new ArrayList<>();
    private final EventsRepository eventsRepository;
    private final PromotersRepository promotersRepository;

    public EventsHistoryPresenter(EventsRepository eventsRepository, PromotersRepository promotersRepository) {
        this.eventsRepository = eventsRepository;
        this.promotersRepository = promotersRepository;
    }

    @Override
    public void bindEventsList(ArrayList<Event> events) {
        this.events = events;
    }

    @Override
    public void onBindEventItemAtPosition(EventsAdapter.EventViewHolder viewHolder, int position) {
        Event event = events.get(position);
        event.setStatus(getEventStatus(event));

        viewHolder.setEventData(event);
    }

    @Override
    public int getEventsListSize() {
        return events.size();
    }

    @Override
    public void getEventsOfOrganizer(String organizerId, EventsRepository.EventsRetrievingCallback callback) {
        eventsRepository.getAllEventsOfOrganizer(organizerId, callback);
    }

    public void setPromoterRank(int rank, int adapterPosition) {
        Promoter promoter = candidatePromoters.get(adapterPosition);
        promoter.setRank(rank);
    }

    public Event.EventStatus getEventStatus(Event event) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        try {
            Date startDate = sdf.parse(event.getStartDate());
            Date endDate = sdf.parse(event.getEndDate());
            if (startDate.after(new Date())) {
                return Event.EventStatus.Upcoming;
            } else if (endDate.before(new Date())) {
                return Event.EventStatus.Closed;
            } else if (startDate.before(new Date()) && endDate.after(new Date())) {
                return Event.EventStatus.Active;
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }


    private ArrayList<Promoter> getPromoters1() {
        ArrayList<Promoter> promoters = new ArrayList<>();
        Promoter p1 = new Promoter();
        p1.setName("Promoter 1");
        p1.setRank(2);
        promoters.add(p1);

        Promoter p2 = new Promoter();
        p2.setName("Promoter 2");
        p2.setRank(2);
        promoters.add(p2);

        return promoters;
    }

    private ArrayList<Promoter> getPromoters2() {
        ArrayList<Promoter> promoters = new ArrayList<>();
        Promoter p1 = new Promoter();
        p1.setName("Promoter 3");
        p1.setRank(2);
        promoters.add(p1);

        return promoters;
    }
}
