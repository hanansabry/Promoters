package com.android.promoters.promoter_section.events_history;

import android.os.Bundle;
import android.view.MenuItem;

import com.android.promoters.EmptyRecyclerView;
import com.android.promoters.Injection;
import com.android.promoters.R;
import com.android.promoters.backend.promoters.PromotersRepository;
import com.android.promoters.model.PromoterEvent;

import java.util.ArrayList;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;

public class PromoterEventsHistoryActivity extends AppCompatActivity implements PromotersRepository.PromoterEventRetrievingCallback {

    private PromoterEventsHistoryPresenter presenter;
    private PromoterEventsHistoryAdapter eventsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_promoter_events_history);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        presenter = new PromoterEventsHistoryPresenter(Injection.providePromotersRepository(), Injection.provideEventsRepository());
        presenter.getPromoterEvents(this);
        initializeRecyclerView();
    }

    private void initializeRecyclerView() {
        EmptyRecyclerView eventsRecyclerView = findViewById(R.id.promoter_events_history_rv);
        eventsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        eventsRecyclerView.setEmptyView(findViewById(R.id.empty_view));
        eventsAdapter = new PromoterEventsHistoryAdapter(presenter);
        eventsRecyclerView.setAdapter(eventsAdapter);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onPromoterEventsRetrievedSuccessfully(ArrayList<PromoterEvent> promoterEvents) {
        eventsAdapter.bindEvents(promoterEvents);
    }

    @Override
    public void onPromoterEventsRetrievedFailed(String errmsg) {

    }
}
