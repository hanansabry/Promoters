package com.android.promoters.backend.users;


import com.android.promoters.model.User;

public interface UsersRepository {

    interface UserInsertionCallback {
        void onUserInsertedSuccessfully();

        void onUserInsertedFailed(String errmsg);
    }

    interface UsersRetrievingCallback {
        void onUserRetrievedSuccessfully(User user);

        void onUserRetrievedFailed(String err);
    }

    void insertNewUser(User user, UserInsertionCallback callback);

    void getCurrentUserData(UsersRetrievingCallback callback);
}
