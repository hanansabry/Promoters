package com.android.promoters.backend.promoters;

import com.android.promoters.model.Promoter;

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

    void getPromoterById(String promoterId, PromotersRetrievingCallback callback);

    void updatePromoterData(HashMap<String, Object> updatedValues, UpdatePromoterCallback callback);

    void getCandidatePromotersListByIds(ArrayList<String> ids, PromotersRetrievingCallback callback);

    void getAcceptedPromotersListByIds(ArrayList<String> ids, PromotersRetrievingCallback callback);
}
