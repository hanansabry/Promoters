package com.android.promoters.register;

import com.android.promoters.BasePresenter;
import com.android.promoters.BaseView;
import com.android.promoters.backend.authentication.AuthenticationRepository;
import com.android.promoters.model.User;

public interface RegisterContract {

    interface View extends BaseView<Presenter> {

        void setNameInputTextErrorMessage();

        void setUserNameInputTextErrorMessage();

        void setPasswordInputTextErrorMessage(String errorMessage);

        void setEmailInputTextErrorMessage();

        void setPhoneInputTextErrorMessage();

        void showProgressBar();

        void hideProgressBar();

    }

    interface Presenter extends BasePresenter {

        void registerNewUser(User po, AuthenticationRepository.RegistrationCallback callback);

        boolean validateUserData(User po);

    }

}
