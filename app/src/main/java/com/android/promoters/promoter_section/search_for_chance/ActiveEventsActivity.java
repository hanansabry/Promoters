package com.android.promoters.promoter_section.search_for_chance;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.android.promoters.EmptyRecyclerView;
import com.android.promoters.Injection;
import com.android.promoters.R;
import com.android.promoters.backend.events.EventsRepository;
import com.android.promoters.backend.promoters.PromotersRepository;
import com.android.promoters.model.Event;
import com.android.promoters.model.Promoter;

import java.util.ArrayList;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;

public class ActiveEventsActivity extends AppCompatActivity implements PromotersRepository.PromotersRetrievingCallback, EventsRepository.EventsRetrievingCallback {

    private ActiveEventsPresenter presenter;
    private ActiveEventsAdapter activeEventsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_active_events);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        presenter = new ActiveEventsPresenter(Injection.provideEventsRepository(), Injection.providePromotersRepository());
        presenter.getPromoterData(this);
        initializeRecyclerView();
    }

    private void initializeRecyclerView() {
        EmptyRecyclerView eventsRecyclerView = findViewById(R.id.active_events_rv);
        eventsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        eventsRecyclerView.setEmptyView(findViewById(R.id.empty_view));
        activeEventsAdapter = new ActiveEventsAdapter(presenter);
        eventsRecyclerView.setAdapter(activeEventsAdapter);
    }

    @Override
    public void onPromotersRetrievedSuccessfully(ArrayList<Promoter> promoters) {
        Promoter currentPromoter = promoters.get(0);
        if (currentPromoter.getRegion() != null) {
            presenter.retrieveEventsByRegion(currentPromoter.getRegion(), this);
        } else {
            Toast.makeText(this, "Please update your profile firstly to get events in your region", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onPromotersRetrievedFailed(String errmsg) {
        Toast.makeText(this, errmsg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onEventsRetrievedSuccessfully(ArrayList<Event> events) {
        activeEventsAdapter.bindEvents(events);
    }

    @Override
    public void onEventsRetrievedFailed(String errmsg) {
        Toast.makeText(this, errmsg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }
}
