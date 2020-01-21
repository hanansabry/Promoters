package com.android.promoters.backend.authentication;

import com.android.promoters.backend.users.UsersRepository;
import com.android.promoters.backend.users.UsersRepositoryImpl;
import com.android.promoters.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import androidx.annotation.NonNull;

public class AuthenticationRepositoryImpl implements AuthenticationRepository {

    private final FirebaseAuth auth;
    private final DatabaseReference mDatabase;

    public AuthenticationRepositoryImpl() {
        auth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference("roles");
    }


    @Override
    public void registerNewUser(final User user, final RegistrationCallback callback) {
        auth.createUserWithEmailAndPassword(user.getEmail(), user.getPassword())
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull final Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            final FirebaseUser firebaseUser = task.getResult().getUser();
                            user.setId(firebaseUser.getUid());

                            UsersRepositoryImpl firebaseUsersRepository = new UsersRepositoryImpl();
                            firebaseUsersRepository.insertNewUser(user, new UsersRepository.UserInsertionCallback() {
                                @Override
                                public void onUserInsertedSuccessfully() {
                                    callback.onSuccessfulRegistration(task.getResult().getUser());
                                }

                                @Override
                                public void onUserInsertedFailed(String errmsg) {
                                    firebaseUser.delete();
                                    callback.onFailedRegistration(errmsg);
                                }
                            });
                        } else {
                            if (task.getException() != null) {
                                callback.onFailedRegistration(task.getException().getMessage());
                            } else {
                                callback.onFailedRegistration("Something wrong is happened! Please try again..");
                            }
                        }
                    }
                });
    }

    @Override
    public void login(String email, String password, final User.UserRole userRole, final LoginCallback callback) {
        auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            final FirebaseUser firebaseUser = task.getResult().getUser();
                            //check user role
                            mDatabase.child(firebaseUser.getUid()).addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    String role = dataSnapshot.getValue(String.class);
                                    if (userRole.name().equalsIgnoreCase(role)) {
                                        callback.onSuccessLogin(firebaseUser);
                                    } else {
                                        callback.onFailedLogin("Wrong username/password, Please try again");
                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {
                                    callback.onFailedLogin(databaseError.getMessage());
                                }
                            });
                        } else {
                            if (task.getException() != null) {
                                callback.onFailedLogin(task.getException().getMessage());
                            } else {
                                callback.onFailedLogin("Something wrong is happened! Please try again..");
                            }
                        }
                    }
                });
    }
}
