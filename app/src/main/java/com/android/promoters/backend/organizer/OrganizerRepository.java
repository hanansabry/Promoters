package com.android.promoters.backend.organizer;

public interface OrganizerRepository {

    interface OrganizerHistoryRepositoryCallback {
        void onRetrieveProfileHistory(String history);

        void onProfileHistoryUpdated();

        void onRetrieveProfileHistoryFailed(String errmsg);

        void onProfileHistoryUpdatedFailed(String errmsg);
    }

    void getOrganizerProfileHistory(OrganizerHistoryRepositoryCallback callback);

    void updateOrganizerProfileHistory(String history, OrganizerHistoryRepositoryCallback callback);
}

