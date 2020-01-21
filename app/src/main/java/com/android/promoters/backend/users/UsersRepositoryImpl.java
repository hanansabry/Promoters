package com.android.promoters.backend.users;

import com.android.promoters.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

import androidx.annotation.NonNull;

public class UsersRepositoryImpl implements UsersRepository {

    private static final String ROLES = "roles";
    private static String USERS_COLLECTION = "users";
    private final FirebaseDatabase mDatabase;

    public UsersRepositoryImpl() {
        mDatabase = FirebaseDatabase.getInstance();
    }

    @Override
    public void insertNewUser(User user, final UserInsertionCallback callback) {
        String userId = user.getId();
        mDatabase.getReference(USERS_COLLECTION).child(userId).setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    callback.onUserInsertedSuccessfully();
                } else {
                    callback.onUserInsertedFailed(task.getException().getMessage());
                }
            }
        });

        HashMap<String, Object> userRoles = new HashMap<>();
        userRoles.put(userId, user.getRole().name());
        mDatabase.getReference(ROLES).updateChildren(userRoles);
    }

    @Override
    public void getCurrentUserData(final UsersRetrievingCallback callback) {
        String currentUserId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        mDatabase.getReference(USERS_COLLECTION).child(currentUserId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);
                callback.onUserRetrievedSuccessfully(user);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                callback.onUserRetrievedFailed(databaseError.getMessage());
            }
        });
    }
}
