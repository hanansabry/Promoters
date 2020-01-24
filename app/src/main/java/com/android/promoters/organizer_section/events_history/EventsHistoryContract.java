package com.android.promoters.organizer_section.events_history;

import com.android.promoters.BasePresenter;
import com.android.promoters.model.Event;
import com.android.promoters.model.Promoter;
import com.android.promoters.organizer_section.events_history.events_list.EventsAdapter;
import com.android.promoters.organizer_section.events_history.promoters_list.AcceptedPromotersAdapter;
import com.android.promoters.organizer_section.events_history.promoters_list.CandidatePromotersAdapter;

import java.util.ArrayList;

public interface EventsHistoryContract {

    interface Presenter extends BasePresenter {

        void bindEventsList(ArrayList<Event> events);

        void onBindEventItemAtPosition(EventsAdapter.EventViewHolder viewHolder, int position);

        int getEventsListSize();

        ArrayList<Event> retrieveEvents();

        void bindCandidatePromotersList(ArrayList<Promoter> promoters);

        void bindAcceptedPromotersList(ArrayList<Promoter> promoters);

        void onBindCandidatePromoterAtPosition(CandidatePromotersAdapter.PromoterViewHolder viewHolder, int position);

        void onBindAcceptedPromoterAtPosition(AcceptedPromotersAdapter.PromoterViewHolder viewHolder, int position);

        int getCandidatePromotersListSize();

        int getAcceptedPromotersListSize();

        ArrayList<Promoter> retrieveCandidatePromoters();
    }
}
