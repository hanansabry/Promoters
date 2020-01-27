package com.android.promoters.organizer_section.events_history;

import com.android.promoters.backend.events.EventsRepository;
import com.android.promoters.backend.promoters.PromotersRepository;
import com.android.promoters.model.Event;
import com.android.promoters.model.Promoter;
import com.android.promoters.organizer_section.events_history.events_list.EventsAdapter;
import com.android.promoters.organizer_section.events_history.promoters_list.AcceptedPromotersAdapter;
import com.android.promoters.organizer_section.events_history.promoters_list.CandidatePromotersAdapter;

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
    public void retrieveCandidatePromoters(int position, PromotersRepository.PromotersRetrievingCallback callback) {
        final Event event = events.get(position);
        if (event.getCandidatePromotersIds() != null) {
            promotersRepository.getCandidatePromotersListByIds(
                    new ArrayList<>(event.getCandidatePromotersIds().keySet())
                    , callback);
        }
    }

    @Override
    public void retrieveAcceptedPromoters(int position, PromotersRepository.PromotersRetrievingCallback callback) {
        final Event event = events.get(position);
        if (event.getAcceptedPromotersIds() != null) {
            promotersRepository.getAcceptedPromotersListByIds(
                    new ArrayList<>(event.getAcceptedPromotersIds().keySet())
                    , callback);
        }
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
}
