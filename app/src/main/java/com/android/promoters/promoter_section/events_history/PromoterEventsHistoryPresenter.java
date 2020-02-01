package com.android.promoters.promoter_section.events_history;

import com.android.promoters.backend.events.EventsRepository;
import com.android.promoters.backend.promoters.PromotersRepository;
import com.android.promoters.model.Event;
import com.android.promoters.model.PromoterEvent;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class PromoterEventsHistoryPresenter {

    private ArrayList<PromoterEvent> events = new ArrayList<>();
    private final PromotersRepository promotersRepository;
    private final EventsRepository eventsRepository;

    public PromoterEventsHistoryPresenter(PromotersRepository promotersRepository, EventsRepository eventsRepository) {
        this.promotersRepository = promotersRepository;
        this.eventsRepository = eventsRepository;
    }

    public void bindEvents(ArrayList<PromoterEvent> events) {
        this.events = events;
    }

    public void onBindEventItemOnPosition(final PromoterEventsHistoryAdapter.EventViewHolder holder, int position) {
        PromoterEvent promoterEvent = events.get(position);
        holder.setEventRank(promoterEvent.getEventRank());
        eventsRepository.getEventDataById(promoterEvent.getEventId(), new EventsRepository.EventsRetrievingCallback() {
            @Override
            public void onEventsRetrievedSuccessfully(ArrayList<Event> events) {
                Event event = events.get(0);
                event.setStatus(getEventStatus(event));
                holder.setEventData(event);
            }

            @Override
            public void onEventsRetrievedFailed(String errmsg) {

            }
        });
    }

    public int getEventsCount() {
        return events.size();
    }

    public void getPromoterEvents(PromotersRepository.PromoterEventRetrievingCallback callback) {
        promotersRepository.getPromoterEvents(callback);
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
