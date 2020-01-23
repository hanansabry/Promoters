package com.android.promoters.organizer_section.new_event;

import com.android.promoters.model.Event;
import com.android.promoters.model.Skill;

import java.util.ArrayList;

public class NewEventPresenter {

    private ArrayList<Skill> requiredSkills = new ArrayList<>();
    final private NewEventActivity view;

    public NewEventPresenter(NewEventActivity view) {
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
}
