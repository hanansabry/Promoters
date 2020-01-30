package com.android.promoters.backend.skills_regions;

import java.util.ArrayList;

public interface DataRepository {

    interface RetrieveDataCallback {

        void onDataRetrievedSuccessfully(ArrayList<String> list);

        void onDataRetrievedFailed(String errmsg);

    }

    void retrieveData(RetrieveDataCallback callback);
}
