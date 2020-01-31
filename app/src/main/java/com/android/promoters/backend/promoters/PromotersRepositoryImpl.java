package com.android.promoters.backend.promoters;

import com.android.promoters.model.Event;
import com.android.promoters.model.Promoter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

import androidx.annotation.NonNull;

public class PromotersRepositoryImpl implements PromotersRepository {

    private final String PROMOTERS_COLLECTION = "promoters";

    private final FirebaseDatabase mDatabase;

    public PromotersRepositoryImpl() {
        mDatabase = FirebaseDatabase.getInstance();
    }

    @Override
    public void getPromoterById(String promoterId, final PromotersRetrievingCallback callback) {
//        String promoterId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        mDatabase.getReference(PROMOTERS_COLLECTION).child(promoterId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Promoter promoter = dataSnapshot.getValue(Promoter.class);
                ArrayList<Promoter> promoters = new ArrayList<>();
                promoters.add(promoter);
                callback.onPromotersRetrievedSuccessfully(promoters);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                callback.onPromotersRetrievedFailed(databaseError.getMessage());
            }
        });
    }

    @Override
    public void updatePromoterData(HashMap<String, Object> updatedValues, final UpdatePromoterCallback callback) {
        String promoterId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        mDatabase.getReference(PROMOTERS_COLLECTION).child(promoterId)
                .updateChildren(updatedValues)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            callback.onPromoterUpdatedSuccessfully();
                        } else {
                            callback.onPromoterUpdatedFailed(task.getException().getMessage());
                        }
                    }
                });
    }

    @Override
    public void addEventToPromoter(String promoterId, Event event) {
        HashMap<String, Object> eventsValues = new HashMap<>();
        eventsValues.put(event.getId(), true);
        mDatabase.getReference(PROMOTERS_COLLECTION).child(promoterId).child("events").updateChildren(eventsValues);
    }

}
