package com.android.promoters.backend.events;

import com.android.promoters.Injection;
import com.android.promoters.backend.promoters.PromotersRepository;
import com.android.promoters.model.Event;
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
                            event.setId(dataSnapshot.getKey());
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
}
