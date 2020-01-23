package com.android.promoters;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

import com.android.promoters.backend.users.UsersRepository;
import com.android.promoters.backend.users.UsersRepositoryImpl;
import com.android.promoters.login.LoginActivity;
import com.android.promoters.model.User;
import com.android.promoters.organizer_section.OrganizerMainActivity;
import com.android.promoters.promoter_section.PromoterMainActivity;
import com.google.firebase.auth.FirebaseAuth;

import androidx.appcompat.app.AppCompatActivity;

public class SplashActivity extends AppCompatActivity {

    private static final long SPLASH_DISPLAY_LENGTH = 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (FirebaseAuth.getInstance().getCurrentUser() == null) {
                    Intent mainIntent = new Intent(SplashActivity.this, LoginActivity.class);
                    startActivity(mainIntent);
                } else {
                    UsersRepository usersRepository = new UsersRepositoryImpl();
                    usersRepository.getCurrentUserData(new UsersRepository.UsersRetrievingCallback() {
                        @Override
                        public void onUserRetrievedSuccessfully(User user) {
                            Intent mainIntent = null;
                            if (user.getRole() == User.UserRole.ORGANIZER) {
                                mainIntent = new Intent(SplashActivity.this, OrganizerMainActivity.class);
                            } else {
                                mainIntent = new Intent(SplashActivity.this, PromoterMainActivity.class);
                            }
                            mainIntent.putExtra(User.class.getName(), user.getName());
                            startActivity(mainIntent);
                            finish();
                        }

                        @Override
                        public void onUserRetrievedFailed(String err) {
                            Toast.makeText(SplashActivity.this, err, Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        }, SPLASH_DISPLAY_LENGTH);
    }
}
