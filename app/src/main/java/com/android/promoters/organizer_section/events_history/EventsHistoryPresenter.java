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

    public void setPromoterRank(Promoter promoter, int eventPosition, int newRank) {
        Event event = events.get(eventPosition);
        promotersRepository.updatePromoterRankForEvent(promoter, event.getId(), newRank);
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

    public void acceptCandidatePromoter(Promoter promoter, int eventPosition, EventsRepository.EventsUpdateCallback callback) {
        Event event = events.get(eventPosition);
        //move the promoter from candidates list to accepted list
        eventsRepository.addAcceptedPromoterForEvent(event.getId(), promoter.getId(), callback);
        //add the event to promoter
        promotersRepository.addEventToPromoter(promoter.getId(), event);
    }
}
