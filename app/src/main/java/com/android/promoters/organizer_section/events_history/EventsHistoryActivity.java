package com.android.promoters.organizer_section.events_history;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.android.promoters.EmptyRecyclerView;
import com.android.promoters.Injection;
import com.android.promoters.R;
import com.android.promoters.backend.events.EventsRepository;
import com.android.promoters.model.Event;
import com.android.promoters.organizer_section.events_history.events_list.EventsAdapter;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;

public class EventsHistoryActivity extends AppCompatActivity implements EventsRepository.EventsRetrievingCallback {

    private EventsHistoryPresenter presenter;
    private EventsAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_events_history);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        presenter = new EventsHistoryPresenter(Injection.provideEventsRepository(), Injection.providePromotersRepository());

        initializeEventsRecyclerView();
        presenter.getEventsOfOrganizer(FirebaseAuth.getInstance().getCurrentUser().getUid(), this);
    }

    private void initializeEventsRecyclerView() {
        EmptyRecyclerView eventsRecyclerView = findViewById(R.id.organizer_events_history_rv);
        eventsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        eventsRecyclerView.setEmptyView(findViewById(R.id.empty_view));
        adapter = new EventsAdapter(presenter);
        eventsRecyclerView.setAdapter(adapter);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onEventsRetrievedSuccessfully(ArrayList<Event> events) {
        adapter.bindEventsList(events);
    }

    @Override
    public void onEventsRetrievedFailed(String errmsg) {
        Toast.makeText(this, errmsg, Toast.LENGTH_SHORT).show();
    }
}
