package com.android.promoters.organizer_section.events_history;

import android.os.Bundle;
import android.view.MenuItem;

import com.android.promoters.EmptyRecyclerView;
import com.android.promoters.R;
import com.android.promoters.organizer_section.events_history.events_list.EventsAdapter;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;

public class EventsHistoryActivity extends AppCompatActivity {

    private EventsHistoryPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_events_history);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        presenter = new EventsHistoryPresenter();

        initializeEventsRecyclerView();
    }

    private void initializeEventsRecyclerView() {
        EmptyRecyclerView eventsRecyclerView = findViewById(R.id.organizer_events_history_rv);
        eventsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        eventsRecyclerView.setEmptyView(findViewById(R.id.empty_view));
        EventsAdapter adapter = new EventsAdapter(presenter);
        eventsRecyclerView.setAdapter(adapter);
        adapter.bindEventsList(presenter.retrieveEvents());
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }
}
