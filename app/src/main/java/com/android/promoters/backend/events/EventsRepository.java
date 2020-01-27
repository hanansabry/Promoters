package com.android.promoters.backend.events;

import com.android.promoters.model.Event;

import java.util.ArrayList;

public interface EventsRepository {

    interface EventsInsertionCallback {

        void onEventInsertedSuccessfully();

        void onEventInsertedFailed(String errmsg);
    }

    interface EventsRetrievingCallback {

        void onEventsRetrievedSuccessfully(ArrayList<Event> events);

        void onEventsRetrievedFailed(String errmsg);
    }

    void addNewEvent(Event event, EventsInsertionCallback callback);

    void getAllEventsOfOrganizer(String organizerId, EventsRetrievingCallback callback);
}
