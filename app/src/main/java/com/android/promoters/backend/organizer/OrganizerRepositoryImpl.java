package com.android.promoters.backend.organizer;

import com.android.promoters.model.Organizer;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class OrganizerRepositoryImpl implements OrganizerRepository {

    private final DatabaseReference mDatabase;

    public OrganizerRepositoryImpl() {
        mDatabase = FirebaseDatabase.getInstance().getReference("organizers");
    }

    @Override
    public void getOrganizerProfileHistory(final OrganizerHistoryRepositoryCallback callback) {
        String id = FirebaseAuth.getInstance().getCurrentUser().getUid();
        mDatabase.child(id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Organizer organizer = dataSnapshot.getValue(Organizer.class);
                callback.onRetrieveProfileHistory(organizer.getHistory());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                callback.onRetrieveProfileHistoryFailed(databaseError.getMessage());
            }
        });
    }

    @Override
    public void updateOrganizerProfileHistory(String history, final OrganizerHistoryRepositoryCallback callback) {
        String id = FirebaseAuth.getInstance().getCurrentUser().getUid();
        HashMap<String, Object> updateValues = new HashMap<>();
        updateValues.put("history", history);
        mDatabase.child(id).updateChildren(updateValues, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(@Nullable DatabaseError databaseError, @NonNull DatabaseReference databaseReference) {
                if (databaseError == null) {
                    callback.onProfileHistoryUpdated();
                } else {
                    callback.onProfileHistoryUpdatedFailed(databaseError.getMessage());
                }
            }
        });
    }
}
