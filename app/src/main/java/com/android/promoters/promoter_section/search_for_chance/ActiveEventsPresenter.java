package com.android.promoters.promoter_section.search_for_chance;

import com.android.promoters.backend.events.EventsRepository;
import com.android.promoters.backend.promoters.PromotersRepository;
import com.android.promoters.model.Event;
import com.google.firebase.auth.FirebaseAuth;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class ActiveEventsPresenter {

    private final EventsRepository eventsRepository;
    private final PromotersRepository promotersRepository;

    private ArrayList<Event> events = new ArrayList<>();

    public ActiveEventsPresenter(EventsRepository eventsRepository, PromotersRepository promotersRepository) {
        this.eventsRepository = eventsRepository;
        this.promotersRepository = promotersRepository;
    }

    public void retrieveEventsByRegion(String regionName, EventsRepository.EventsRetrievingCallback callback) {
        eventsRepository.getEventsByRegion(regionName, callback);
    }

    public void getPromoterData(PromotersRepository.PromotersRetrievingCallback callback) {
        promotersRepository.getPromoterById(callback);
    }

    public void bindEvents(ArrayList<Event> events) {
        this.events = events;
    }

    public void onBindEventItemOnPosition(ActiveEventsAdapter.ActiveEventViewHolder viewHolder, int position) {
        Event event = events.get(position);
        event.setStatus(getEventStatus(event));
        viewHolder.setEventData(event);
    }

    public int getEventsCount() {
        return events.size();
    }

    public void applyForEvent(int adapterPosition, EventsRepository.EventsUpdateCallback callback) {
        Event event = events.get(adapterPosition);
        eventsRepository.addOrRemoveCandidatePromoterForEvent(event.getId(),
                FirebaseAuth.getInstance().getCurrentUser().getUid(),
                true,
                callback);
    }

    public void removeEventApply(int adapterPosition, EventsRepository.EventsUpdateCallback callback) {
        Event event = events.get(adapterPosition);
        eventsRepository.addOrRemoveCandidatePromoterForEvent(event.getId(),
                FirebaseAuth.getInstance().getCurrentUser().getUid(),
                false,
                callback);
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
}
