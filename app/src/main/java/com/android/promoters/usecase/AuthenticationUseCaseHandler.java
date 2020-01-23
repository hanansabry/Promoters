package com.android.promoters.usecase;


import com.android.promoters.backend.authentication.AuthenticationRepository;
import com.android.promoters.backend.users.UsersRepository;
import com.android.promoters.model.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class AuthenticationUseCaseHandler {

    private AuthenticationRepository mRepository;
    private UsersRepository mUsersRepository;

    public AuthenticationUseCaseHandler(AuthenticationRepository repository, UsersRepository usersRepository) {
        mRepository = repository;
        mUsersRepository = usersRepository;
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

    public void getUserName(UsersRepository.UsersRetrievingCallback callback) {
        mUsersRepository.getCurrentUserData(callback);
    }
}
