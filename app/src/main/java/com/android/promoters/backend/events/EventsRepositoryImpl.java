package com.android.promoters.backend.events;

import com.android.promoters.Injection;
import com.android.promoters.backend.promoters.PromotersRepository;
import com.android.promoters.model.Event;
import com.android.promoters.model.Promoter;
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
import androidx.annotation.Nullable;

public class EventsRepositoryImpl implements EventsRepository {

    private static String EVENTS_COLLECTION = "events";
    private static String ORGANIZERS_COLLECTION = "organizers";
    private final FirebaseDatabase mDatabase;
    private PromotersRepository promotersRepository;

    public EventsRepositoryImpl() {
        mDatabase = FirebaseDatabase.getInstance();
        promotersRepository = Injection.providePromotersRepository();
    }

    @Override
    public void addNewEvent(final Event event, final EventsInsertionCallback callback) {
        event.setOrganizerId(FirebaseAuth.getInstance().getCurrentUser().getUid());
        final DatabaseReference eventRef = mDatabase.getReference(EVENTS_COLLECTION).push();
        eventRef.setValue(event).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    addEventToCurrentOrganizer(eventRef.getKey());
                    callback.onEventInsertedSuccessfully();
                } else {
                    callback.onEventInsertedFailed(task.getException().getMessage());
                }
            }
        });
    }

    private void addEventToCurrentOrganizer(String eventId) {
        String organizerId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        HashMap<String, Object> eventChild = new HashMap<>();
        eventChild.put(eventId, true);
        mDatabase.getReference(ORGANIZERS_COLLECTION).child(organizerId)
                .child("events")
                .updateChildren(eventChild);

    }

    @Override
    public void getAllEventsOfOrganizer(String organizerId, final EventsRetrievingCallback callback) {
        mDatabase.getReference(EVENTS_COLLECTION)
                .orderByChild("organizerId").equalTo(organizerId)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        ArrayList<Event> events = new ArrayList<>();
                        for (DataSnapshot eventSnapshot : dataSnapshot.getChildren()) {
                            Event event = eventSnapshot.getValue(Event.class);
                            event.setId(eventSnapshot.getKey());
                            events.add(event);
                        }
                        callback.onEventsRetrievedSuccessfully(events);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        callback.onEventsRetrievedFailed(databaseError.getMessage());
                    }
                });
    }

    @Override
    public void getEventsByRegion(String regionName, final EventsRetrievingCallback callback) {
        mDatabase.getReference(EVENTS_COLLECTION)
                .orderByChild("region")
                .equalTo(regionName)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        ArrayList<Event> events = new ArrayList<>();
                        for (DataSnapshot eventSnapshot : dataSnapshot.getChildren()) {
                            Event event = eventSnapshot.getValue(Event.class);
                            event.setId(eventSnapshot.getKey());
                            events.add(event);
                        }
                        callback.onEventsRetrievedSuccessfully(events);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        callback.onEventsRetrievedFailed(databaseError.getMessage());
                    }
                });
    }

    @Override
    public void addOrRemoveCandidatePromoterForEvent(final String eventId, final String promoterId, final boolean add, final EventsUpdateCallback callback) {
        promotersRepository.getPromoterById(promoterId, new PromotersRepository.PromotersRetrievingCallback() {
            @Override
            public void onPromotersRetrievedSuccessfully(ArrayList<Promoter> promoters) {
                Promoter currentPromoter = promoters.get(0);
                HashMap<String, Object> candidatePromoter = new HashMap<>();
                candidatePromoter.put(promoterId, currentPromoter);
                DatabaseReference candidateRef = mDatabase.getReference(EVENTS_COLLECTION).child(eventId).child("candidatePromoters");
                if (add) {
                    candidateRef.updateChildren(candidatePromoter, new UpdateCompletionListener(callback));
                } else {
                    candidateRef.child(promoterId).removeValue(new UpdateCompletionListener(callback));
                }
            }

            @Override
            public void onPromotersRetrievedFailed(String errmsg) {
                callback.onEventUpdatedFailed(errmsg);
            }
        });

    }

    class UpdateCompletionListener implements DatabaseReference.CompletionListener {

        private final EventsUpdateCallback callback;

        UpdateCompletionListener(EventsUpdateCallback callback) {
            this.callback = callback;
        }

        @Override
        public void onComplete(@Nullable DatabaseError databaseError, @NonNull DatabaseReference databaseReference) {
            if (databaseError == null) {
                callback.onEventUpdatedSuccessfully();
            } else {
                callback.onEventUpdatedFailed(databaseError.getMessage());
            }
        }
    }
}
