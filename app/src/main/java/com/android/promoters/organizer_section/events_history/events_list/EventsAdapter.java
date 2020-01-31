package com.android.promoters.organizer_section.events_history.events_list;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.promoters.EmptyRecyclerView;
import com.android.promoters.R;
import com.android.promoters.model.Event;
import com.android.promoters.model.Promoter;
import com.android.promoters.organizer_section.events_history.EventsHistoryPresenter;
import com.android.promoters.organizer_section.events_history.promoters_list.AcceptedPromotersAdapter;
import com.android.promoters.organizer_section.events_history.promoters_list.CandidatePromotersAdapter;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class EventsAdapter extends RecyclerView.Adapter<EventsAdapter.EventViewHolder> {

    private final EventsHistoryPresenter presenter;

    public EventsAdapter(EventsHistoryPresenter presenter) {
        this.presenter = presenter;
    }

    public void bindEventsList(ArrayList<Event> events) {
        presenter.bindEventsList(events);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public EventViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.organizer_event_list_item, parent, false);
        return new EventViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull EventViewHolder holder, int position) {
        System.out.println("Event position:" + position);
        presenter.onBindEventItemAtPosition(holder, position);
    }

    @Override
    public int getItemCount() {
        return presenter.getEventsListSize();
    }

    public class EventViewHolder extends RecyclerView.ViewHolder {

        private TextView eventNameTextView, startDateTextView, endDateTextView, eventStatusTextView;
        private EmptyRecyclerView promotersRecyclerView;
        private CandidatePromotersAdapter candidatePromotersAdapter;
        private AcceptedPromotersAdapter acceptedPromotersAdapter;
        private Context context;

        public EventViewHolder(@NonNull View itemView) {
            super(itemView);

            context = itemView.getContext();
            eventNameTextView = itemView.findViewById(R.id.event_name);
            startDateTextView = itemView.findViewById(R.id.start_date);
            endDateTextView = itemView.findViewById(R.id.end_date);
            eventStatusTextView = itemView.findViewById(R.id.event_status);
        }

        private void initializePromotersRecyclerView(View itemView) {
            promotersRecyclerView = itemView.findViewById(R.id.promoters_candidates_rv);
            promotersRecyclerView.setEmptyView(itemView.findViewById(R.id.empty_view));
            promotersRecyclerView.setLayoutManager(new LinearLayoutManager(context));
        }

        public void setEventData(Event event) {
            initializePromotersRecyclerView(itemView);
            eventNameTextView.setText(event.getTitle());
            startDateTextView.setText(event.getStartDate());
            endDateTextView.setText(event.getEndDate());
            eventStatusTextView.setText(event.getStatus().name());
            if (event.getStatus() != Event.EventStatus.Closed) {
                eventStatusTextView.setTextColor(context.getResources().getColor(R.color.colorThird));
                candidatePromotersAdapter = new CandidatePromotersAdapter(presenter);
                candidatePromotersAdapter.bindPromoters(
                        event.getCandidatePromoters() != null ?
                                new ArrayList<>(event.getCandidatePromoters().values()) :
                                new ArrayList<Promoter>()
                );
                promotersRecyclerView.setAdapter(candidatePromotersAdapter);
            } else {
                eventStatusTextView.setTextColor(context.getResources().getColor(R.color.colorAccent));
                acceptedPromotersAdapter = new AcceptedPromotersAdapter(presenter);
                acceptedPromotersAdapter.bindPromoters(
                        event.getAcceptedPromoters() != null ?
                                new ArrayList<Promoter>(event.getAcceptedPromoters().values()) :
                                new ArrayList<Promoter>()
                );
                promotersRecyclerView.setAdapter(acceptedPromotersAdapter);
            }
        }
    }
}
