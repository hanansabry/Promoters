package com.android.promoters.organizer_section.organizer_profile;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.android.promoters.Injection;
import com.android.promoters.R;
import com.android.promoters.backend.organizer.OrganizerRepository;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class OrganizerHistoryProfile extends AppCompatActivity implements OrganizerRepository.OrganizerHistoryRepositoryCallback {

    private OrganizerProfilePresenter presenter;
    private EditText historyEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_organizer_profile);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        presenter = new OrganizerProfilePresenter(Injection.provideOrganizerRepository());
        presenter.getOrganizerProfileHistory(this);

        historyEditText = findViewById(R.id.history_edit_text);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.profile_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        } else if (item.getItemId() == R.id.apply_action) {
            String newHistory = historyEditText.getText().toString().trim();
            presenter.updateOrganizerProfileHistory(newHistory, this);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onRetrieveProfileHistory(String history) {
        if (history != null && !history.isEmpty()) {
            historyEditText.setText(history);
        }
    }

    @Override
    public void onRetrieveProfileHistoryFailed(String errmsg) {
        Toast.makeText(this, errmsg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onProfileHistoryUpdated() {
        Toast.makeText(this, "Changes Applied", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onProfileHistoryUpdatedFailed(String errmsg) {
        Toast.makeText(this, errmsg, Toast.LENGTH_LONG).show();
    }
}
