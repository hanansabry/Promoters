package com.android.promoters.backend.promoters;

import com.android.promoters.model.Promoter;

import java.util.ArrayList;

public interface PromotersRepository {

    void getAcceptedPromotersListByIds(ArrayList<String> ids, PromotersRetrievingCallback callback);

    interface PromotersRetrievingCallback {

        void onPromotersRetrievedSuccessfully(ArrayList<Promoter> promoter);

        void onPromotersRetrievedFailed(String errmsg);
    }

    void getPromoterById(String promoterId, PromotersRetrievingCallback callback);

    void getCandidatePromotersListByIds(ArrayList<String> ids, PromotersRetrievingCallback callback);
}
