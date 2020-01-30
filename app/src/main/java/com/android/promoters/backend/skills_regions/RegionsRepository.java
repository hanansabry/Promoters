package com.android.promoters.backend.skills_regions;

import com.android.promoters.model.Region;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import androidx.annotation.NonNull;

public class RegionsRepository implements DataRepository {

    private final DatabaseReference mDatabase;

    public RegionsRepository() {
        mDatabase = FirebaseDatabase.getInstance().getReference("regions");
    }

    @Override
    public void retrieveData(final RetrieveDataCallback callback) {
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ArrayList<String> regions = new ArrayList<>();
                for (DataSnapshot regionSnapshot : dataSnapshot.getChildren()) {
                    Region region = regionSnapshot.getValue(Region.class);
                    regions.add(region.getName());
                }
                callback.onDataRetrievedSuccessfully(regions);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                callback.onDataRetrievedFailed(databaseError.getMessage());
            }
        });
    }
}
