package com.android.promoters.login;

import com.android.promoters.backend.authentication.AuthenticationRepository;
import com.android.promoters.backend.users.UsersRepository;
import com.android.promoters.model.User;
import com.android.promoters.usecase.AuthenticationUseCaseHandler;

public class LoginPresenter implements LoginContract.Presenter {

    private final LoginContract.View mLoginView;
    private final AuthenticationUseCaseHandler mUseCasHandler;

    public LoginPresenter(LoginContract.View loginView, AuthenticationUseCaseHandler useCasHandler) {
        mLoginView = loginView;
        mUseCasHandler = useCasHandler;

        mLoginView.setPresenter(this);
    }

    @Override
    public void login(String email, String password, User.UserRole userRole, AuthenticationRepository.LoginCallback callback) {
        mUseCasHandler.login(email, password, userRole, callback);
    }

    @Override
    public void getUserName(UsersRepository.UsersRetrievingCallback callback) {
        mUseCasHandler.getUserName(callback);
    }

    @Override
    public boolean validateLoginData(String email, String password) {
        boolean validate = true;
        if (email == null || email.isEmpty()) {
            validate = false;
            mLoginView.setEmailInputTextErrorMessage();
        }

        if (password == null || password.isEmpty()) {
            validate = false;
            mLoginView.setPasswordInputTextErrorMessage();
        }
        return validate;
    }
}

