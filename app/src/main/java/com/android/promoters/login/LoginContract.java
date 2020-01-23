package com.android.promoters.login;

import com.android.promoters.BasePresenter;
import com.android.promoters.BaseView;
import com.android.promoters.backend.authentication.AuthenticationRepository;
import com.android.promoters.backend.users.UsersRepository;
import com.android.promoters.model.User;

public interface LoginContract {

    interface View extends BaseView<Presenter> {

        void setEmailInputTextErrorMessage();

        void setPasswordInputTextErrorMessage();

        void showProgressBar();

        void hideProgressBar();

    }

    interface Presenter extends BasePresenter {
        void login(String email, String password, User.UserRole userRole, AuthenticationRepository.LoginCallback callback);

        boolean validateLoginData(String email, String password);

        void getUserName(UsersRepository.UsersRetrievingCallback callback);
    }
}