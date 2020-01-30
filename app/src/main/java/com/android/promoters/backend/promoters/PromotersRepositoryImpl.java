package com.android.promoters.backend.promoters;

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
    public void getPromoterById(final PromotersRetrievingCallback callback) {
        String promoterId = FirebaseAuth.getInstance().getCurrentUser().getUid();
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

    private int promotersCounter1;
    private int promotersCounter2;

    @Override
    public void getCandidatePromotersListByIds(final ArrayList<String> ids, final PromotersRetrievingCallback callback) {
        final ArrayList<Promoter> promoters = new ArrayList<>();
        promotersCounter1 = 0;

        for (String promoterId : ids) {
            mDatabase.getReference(PROMOTERS_COLLECTION).child(promoterId).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    promoters.add(dataSnapshot.getValue(Promoter.class));
                    promotersCounter1++;
                    if (ids.size() == promotersCounter1) {
                        callback.onPromotersRetrievedSuccessfully(promoters);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    callback.onPromotersRetrievedFailed(databaseError.getMessage());
                }
            });
        }

    }

    @Override
    public void getAcceptedPromotersListByIds(final ArrayList<String> ids, final PromotersRetrievingCallback callback) {
        final ArrayList<Promoter> promoters = new ArrayList<>();
        promotersCounter2 = 0;

        for (String promoterId : ids) {
            mDatabase.getReference(PROMOTERS_COLLECTION).child(promoterId).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    promoters.add(dataSnapshot.getValue(Promoter.class));
                    promotersCounter2++;
                    if (ids.size() == promotersCounter2) {
                        callback.onPromotersRetrievedSuccessfully(promoters);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    callback.onPromotersRetrievedFailed(databaseError.getMessage());
                }
            });
        }

    }
}
