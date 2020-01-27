package com.android.promoters.organizer_section.organizer_profile;

import com.android.promoters.backend.organizer.OrganizerRepository;

public class OrganizerProfilePresenter {

    private final OrganizerRepository repository;

    public OrganizerProfilePresenter(OrganizerRepository repository) {
        this.repository = repository;
    }

    public void getOrganizerProfileHistory(OrganizerRepository.OrganizerHistoryRepositoryCallback callback) {
        repository.getOrganizerProfileHistory(callback);
    }

    public void updateOrganizerProfileHistory(String history, OrganizerRepository.OrganizerHistoryRepositoryCallback callback) {
        repository.updateOrganizerProfileHistory(history, callback);
    }
}
