package com.android.promoters.organizer_section.events_history;

import com.android.promoters.BasePresenter;
import com.android.promoters.backend.events.EventsRepository;
import com.android.promoters.model.Event;
import com.android.promoters.organizer_section.events_history.events_list.EventsAdapter;

import java.util.ArrayList;

public interface EventsHistoryContract {

    interface Presenter extends BasePresenter {

        void bindEventsList(ArrayList<Event> events);

        void onBindEventItemAtPosition(EventsAdapter.EventViewHolder viewHolder, int position);

        int getEventsListSize();

        void getEventsOfOrganizer(String organizerId, EventsRepository.EventsRetrievingCallback callback);

    }
}
