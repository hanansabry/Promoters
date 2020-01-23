package com.android.promoters.login;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.android.promoters.Injection;
import com.android.promoters.R;
import com.android.promoters.backend.authentication.AuthenticationRepository;
import com.android.promoters.backend.users.UsersRepository;
import com.android.promoters.model.User;
import com.android.promoters.organizer_section.OrganizerMainActivity;
import com.android.promoters.promoter_section.PromoterMainActivity;
import com.android.promoters.register.RegisterActivity;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseUser;

import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity implements LoginContract.View, AuthenticationRepository.LoginCallback {

    private LoginContract.Presenter mPresenter;
    private TextInputLayout emailTextInput, passwordTextInput;
    private EditText emailEditText, passwordEditText;
    private Button loginButton;
    private ProgressBar progressBar;
    private User.UserRole selectedRole = User.UserRole.ORGANIZER;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mPresenter = new LoginPresenter(this, Injection.provideAuthenticationUseCaseHandler());
        initializeView();
    }

    private void initializeView() {
        emailTextInput = findViewById(R.id.email_text_input_edittext);
        passwordTextInput = findViewById(R.id.password_text_input_edittext);

        emailEditText = findViewById(R.id.email_edit_text);
        emailEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                emailTextInput.setError(null);
                emailTextInput.setErrorEnabled(false);
            }
        });
        passwordEditText = findViewById(R.id.password_edit_text);
        passwordEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                passwordTextInput.setError(null);
                passwordTextInput.setErrorEnabled(false);
            }
        });

        loginButton = findViewById(R.id.login);
        progressBar = findViewById(R.id.progress_bar);

        RadioGroup roleRadioGroup = findViewById(R.id.role_radio_group);
        roleRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.organizer_role) {
                    selectedRole = User.UserRole.ORGANIZER;
                } else if (checkedId == R.id.promoter_role) {
                    selectedRole = User.UserRole.PROMOTER;
                }
            }
        });
    }

    public void onLoginClicked(View view) {
        showProgressBar();

        String email = emailEditText.getText().toString().trim();
        String password = passwordEditText.getText().toString().trim();

        if (mPresenter.validateLoginData(email, password)) {
            mPresenter.login(email, password, selectedRole, this);
        } else {
            hideProgressBar();
        }
    }

    public void OnRegisterClicked(View view) {
        Intent registerIntent = new Intent(this, RegisterActivity.class);
        startActivity(registerIntent);
    }

    @Override
    public void setEmailInputTextErrorMessage() {
        emailTextInput.setError(getString(R.string.email_empty_err_msg));
    }

    @Override
    public void setPasswordInputTextErrorMessage() {
        passwordTextInput.setError(getString(R.string.password_empty_err_msg));
    }

    @Override
    public void showProgressBar() {
        progressBar.setVisibility(View.VISIBLE);
        loginButton.setVisibility(View.GONE);
    }

    @Override
    public void hideProgressBar() {
        progressBar.setVisibility(View.GONE);
        loginButton.setVisibility(View.VISIBLE);
    }

    @Override
    public void setPresenter(LoginContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void onSuccessLogin(FirebaseUser firebaseUser) {
        hideProgressBar();
//        Toast.makeText(this, "Welcome, " + firebaseUser.getEmail(), Toast.LENGTH_LONG).show();
        mPresenter.getUserName(new UsersRepository.UsersRetrievingCallback() {
            @Override
            public void onUserRetrievedSuccessfully(User user) {
                if (selectedRole == User.UserRole.ORGANIZER) {
                    goToOrganizerSection(user);
                } else if (selectedRole == User.UserRole.PROMOTER) {
                    goToPromoterSection(user);
                }
            }

            @Override
            public void onUserRetrievedFailed(String err) {
                Toast.makeText(LoginActivity.this, "Something wrong is happened, Please try again.", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void goToPromoterSection(User user) {
        Intent intent = new Intent(this, PromoterMainActivity.class);
        intent.putExtra(User.class.getName(), user.getName());
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    private void goToOrganizerSection(User user) {
        Intent intent = new Intent(this, OrganizerMainActivity.class);
        intent.putExtra(User.class.getName(), user.getName());
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    @Override
    public void onFailedLogin(String errmsg) {
        hideProgressBar();
        Toast.makeText(this, errmsg, Toast.LENGTH_LONG).show();
    }
}
