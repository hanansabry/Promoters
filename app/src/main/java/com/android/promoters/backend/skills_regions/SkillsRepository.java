package com.android.promoters.backend.skills_regions;

import com.android.promoters.model.Skill;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import androidx.annotation.NonNull;

public class SkillsRepository implements DataRepository {

    private final DatabaseReference mDatabase;

    public SkillsRepository() {
        mDatabase = FirebaseDatabase.getInstance().getReference("skills");
    }

    @Override
    public void retrieveData(final RetrieveDataCallback callback) {
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ArrayList<String> skills = new ArrayList<>();
                for (DataSnapshot skillSnapshot : dataSnapshot.getChildren()) {
                    Skill skill = skillSnapshot.getValue(Skill.class);
                    skills.add(skill.getName());
                }
                callback.onDataRetrievedSuccessfully(skills);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                callback.onDataRetrievedFailed(databaseError.getMessage());
            }
        });
    }
}
