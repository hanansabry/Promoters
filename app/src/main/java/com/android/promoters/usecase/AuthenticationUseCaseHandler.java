package com.android.promoters.usecase;


import com.android.promoters.backend.authentication.AuthenticationRepository;
import com.android.promoters.model.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class AuthenticationUseCaseHandler {

    private AuthenticationRepository mRepository;

    public AuthenticationUseCaseHandler(AuthenticationRepository repository) {
        mRepository = repository;
    }

    public void registerNewUser(User po, AuthenticationRepository.RegistrationCallback callback){
        mRepository.registerNewUser(po, callback);
    }

    public void login(String email, String password, User.UserRole userRole, AuthenticationRepository.LoginCallback callback) {
        mRepository.login(email, password, userRole, callback);
    }

    public void logout() {
        FirebaseAuth.getInstance().signOut();
    }

    public String getUserEmail() {
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser != null) {
            return currentUser.getEmail();
        } else {
            return "";
        }
    }
}
