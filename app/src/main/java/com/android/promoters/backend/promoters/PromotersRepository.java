package com.android.promoters.backend.promoters;

import com.android.promoters.model.Event;
import com.android.promoters.model.Promoter;
import com.android.promoters.model.PromoterEvent;

import java.util.ArrayList;
import java.util.HashMap;

public interface PromotersRepository {

    interface PromotersRetrievingCallback {

        void onPromotersRetrievedSuccessfully(ArrayList<Promoter> promoter);

        void onPromotersRetrievedFailed(String errmsg);
    }

    interface UpdatePromoterCallback {

        void onPromoterUpdatedSuccessfully();

        void onPromoterUpdatedFailed(String errmsg);
    }

    interface PromoterEventRetrievingCallback {
        void onPromoterEventsRetrievedSuccessfully(ArrayList<PromoterEvent> promoterEvents);

        void onPromoterEventsRetrievedFailed(String errmsg);
    }

    void getPromoterById(String promoterId, PromotersRetrievingCallback callback);

    void updatePromoterData(HashMap<String, Object> updatedValues, UpdatePromoterCallback callback);

    void addEventToPromoter(String promoterId, Event event);

    void updatePromoterRankForEvent(Promoter promoter, String eventId, int newRank);

    void getPromoterEvents(PromoterEventRetrievingCallback callback);

}
