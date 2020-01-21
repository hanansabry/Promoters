package com.android.promoters.register;

import com.android.promoters.backend.authentication.AuthenticationRepository;
import com.android.promoters.model.User;
import com.android.promoters.usecase.AuthenticationUseCaseHandler;

public class RegisterPresenter implements RegisterContract.Presenter {

    private final RegisterContract.View mRegistrationView;
    private final AuthenticationUseCaseHandler mUseCaseHandler;

    public RegisterPresenter(RegisterContract.View registrationView, AuthenticationUseCaseHandler useCaseHandler) {
        mRegistrationView = registrationView;
        mUseCaseHandler = useCaseHandler;

        mRegistrationView.setPresenter(this);
    }

    @Override
    public void registerNewUser(User po, AuthenticationRepository.RegistrationCallback callback) {
        mUseCaseHandler.registerNewUser(po, callback);
    }

    @Override
    public boolean validateUserData(User user) {
        boolean validate = true;
        if (user.getName() == null || user.getName().isEmpty()) {
            validate = false;
            mRegistrationView.setNameInputTextErrorMessage();
        }

        if (user.getPassword() == null || user.getPassword().isEmpty()) {
            validate = false;
            mRegistrationView.setPasswordInputTextErrorMessage("Password can't be empty");
        }

        if (user.getPassword().length() < 6) {
            validate = false;
            mRegistrationView.setPasswordInputTextErrorMessage("Password must be at least 6 characters");
        }

        if (user.getEmail() == null || user.getEmail().isEmpty()) {
            validate = false;
            mRegistrationView.setEmailInputTextErrorMessage();
        }

        if (user.getPhone() == null || user.getPhone().isEmpty()) {
            validate = false;
            mRegistrationView.setPhoneInputTextErrorMessage();
        }
        return validate;
    }

}
