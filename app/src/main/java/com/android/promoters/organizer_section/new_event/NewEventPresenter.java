package com.android.promoters.organizer_section.new_event;

import com.android.promoters.backend.events.EventsRepository;
import com.android.promoters.backend.skills_regions.DataRepository;
import com.android.promoters.model.Event;

public class NewEventPresenter {

    final private EventsRepository eventsRepository;
    final private DataRepository regionsRepository;
    final private NewEventActivity view;

    public NewEventPresenter(EventsRepository eventsRepository, DataRepository regionsRepository, NewEventActivity view) {
        this.eventsRepository = eventsRepository;
        this.regionsRepository = regionsRepository;
        this.view = view;
    }


    public boolean validateEventData(Event newEvent) {
        boolean validate = true;
        if (newEvent.getTitle() == null || newEvent.getTitle().isEmpty() ||
                newEvent.getStartDate() == null || newEvent.getStartDate().isEmpty() ||
                newEvent.getEndDate() == null || newEvent.getEndDate().isEmpty() ||
                newEvent.getRequiredSkills() == null || newEvent.getRequiredSkills().size() == 0) {
            view.showErrorMessage();
            return false;
        }
        return true;
    }

    public void addNewEvent(Event event, EventsRepository.EventsInsertionCallback callback) {
        eventsRepository.addNewEvent(event, callback);
    }

    public void retrieveRegions(DataRepository.RetrieveDataCallback callback) {
        regionsRepository.retrieveData(callback);
    }
}
