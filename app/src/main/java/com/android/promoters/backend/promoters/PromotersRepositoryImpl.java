package com.android.promoters.backend.promoters;

import com.android.promoters.model.Event;
import com.android.promoters.model.Promoter;
import com.android.promoters.model.PromoterEvent;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
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
        HashMap<String, Object> eventRank = new HashMap<>();
        eventRank.put("eventRank", 0);
        mDatabase.getReference(PROMOTERS_COLLECTION)
                .child(promoterId)
                .child("events")
                .child(event.getId())
                .updateChildren(eventRank);
    }

    @Override
    public void updatePromoterRankForEvent(Promoter promoter, String eventId, int rank) {
        HashMap<String, Object> eventRank = new HashMap<>();
        eventRank.put("eventRank", rank);
        mDatabase.getReference(PROMOTERS_COLLECTION)
                .child(promoter.getId())
                .child("events")
                .child(eventId)
                .updateChildren(eventRank);

        //update promoter rank in promoters and eventscollection
        mDatabase.getReference("events")
                .child(eventId)
                .child("acceptedPromoters")
                .child(promoter.getId())
                .child("rank")
                .setValue(rank);

        final DatabaseReference promoterRef = mDatabase.getReference(PROMOTERS_COLLECTION).child(promoter.getId());
        promoterRef.child("events").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                int totalRank = 0;
                for (DataSnapshot eventSnapshot : dataSnapshot.getChildren()) {
                    int eventRank = eventSnapshot.child("eventRank").getValue(Integer.class);
                    totalRank += eventRank;
                }
                promoterRef.child("rank").setValue(totalRank);
//                        eventPromoterRef.child("rank").setValue(totalRank);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
//
//        //update promoter rank in events collection
//        mDatabase.getReference("events")
//                .child(eventId)
//                .child("acceptedPromoters")
//                .child(promoter.getId())
//                .child("rank")
//                .setValue(promoter.getRank() + rank);
    }

    @Override
    public void getPromoterEvents(final PromoterEventRetrievingCallback callback) {
        String promoterId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        mDatabase.getReference(PROMOTERS_COLLECTION)
                .child(promoterId)
                .child("events")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        ArrayList<PromoterEvent> promoterEvents = new ArrayList<>();
                        for (DataSnapshot eventSnapshot : dataSnapshot.getChildren()) {
                            HashMap<String, Long> eventRank = (HashMap<String, Long>) eventSnapshot.getValue();
                            PromoterEvent promoterEvent = new PromoterEvent();
                            promoterEvent.setEventId(eventSnapshot.getKey());
                            promoterEvent.setEventRank(eventRank.get("eventRank").intValue());
                            promoterEvents.add(promoterEvent);
                        }
                        callback.onPromoterEventsRetrievedSuccessfully(promoterEvents);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        callback.onPromoterEventsRetrievedFailed(databaseError.getMessage());
                    }
                });
    }

//    private void getEventDataByEventId(String eventId, )
}
