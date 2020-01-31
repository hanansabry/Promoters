package com.android.promoters.promoter_section;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.android.promoters.R;
import com.android.promoters.login.LoginActivity;
import com.android.promoters.model.User;
import com.android.promoters.promoter_section.events_history.PromoterEventsHistoryActivity;
import com.android.promoters.promoter_section.profile.PromoterProfileActivity;
import com.android.promoters.promoter_section.search_for_chance.ActiveEventsActivity;
import com.google.firebase.auth.FirebaseAuth;

import androidx.appcompat.app.AppCompatActivity;

public class PromoterMainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_promoter_main);

        String promoterName = getIntent().getExtras().getString(User.class.getName());
        TextView promoterNameTextView = findViewById(R.id.promoter_name);
        promoterNameTextView.setText(promoterName);
    }

    public void onBackClicked(View view) {
        onBackPressed();
    }

    public void logout(View view) {
        FirebaseAuth.getInstance().signOut();
        Intent intent = new Intent(this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    public void onProfileDetailsClicked(View view) {
        startActivity(new Intent(this, PromoterProfileActivity.class));
    }

    public void onPreviousEventsClicked(View view) {
        startActivity(new Intent(this, PromoterEventsHistoryActivity.class));
    }

    public void onSearchEventsClicked(View view) {
        startActivity(new Intent(this, ActiveEventsActivity.class));
    }
}
