package com.android.promoters.backend.promoters;

import com.android.promoters.model.Event;
import com.android.promoters.model.Promoter;

import java.util.ArrayList;
import java.util.HashMap;

public interface PromotersRepository {

    void updatePromoterRankForEvent(Promoter promoter, String eventId, int newRank);

    interface PromotersRetrievingCallback {

        void onPromotersRetrievedSuccessfully(ArrayList<Promoter> promoter);

        void onPromotersRetrievedFailed(String errmsg);
    }

    interface UpdatePromoterCallback {

        void onPromoterUpdatedSuccessfully();

        void onPromoterUpdatedFailed(String errmsg);
    }

    void getPromoterById(String promoterId, PromotersRetrievingCallback callback);

    void updatePromoterData(HashMap<String, Object> updatedValues, UpdatePromoterCallback callback);

    void addEventToPromoter(String promoterId, Event event);

}
