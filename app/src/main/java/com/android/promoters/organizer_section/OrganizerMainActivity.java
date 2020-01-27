package com.android.promoters.organizer_section;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.android.promoters.R;
import com.android.promoters.login.LoginActivity;
import com.android.promoters.model.User;
import com.android.promoters.organizer_section.events_history.EventsHistoryActivity;
import com.android.promoters.organizer_section.new_event.NewEventActivity;
import com.android.promoters.organizer_section.organizer_profile.OrganizerHistoryProfile;
import com.google.firebase.auth.FirebaseAuth;

import androidx.appcompat.app.AppCompatActivity;

public class OrganizerMainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_organizer_main);

        String organizerName = getIntent().getExtras().getString(User.class.getName());
        TextView organizerNameTextView = findViewById(R.id.organizer_name);
        organizerNameTextView.setText(organizerName);
    }

    public void onNewEventClicked(View view) {
        startActivity(new Intent(this, NewEventActivity.class));
    }

    public void onEventsHistoryClicked(View view) {
        startActivity(new Intent(this, EventsHistoryActivity.class));
    }

    public void onCompanyProfileClicked(View view) {
        startActivity(new Intent(this, OrganizerHistoryProfile.class));
    }

    public void logout(View view) {
        FirebaseAuth.getInstance().signOut();
        Intent intent = new Intent(this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    public void onBackClicked(View view) {
        onBackPressed();
    }
}
