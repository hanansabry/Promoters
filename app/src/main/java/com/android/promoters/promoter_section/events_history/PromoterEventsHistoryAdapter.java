package com.android.promoters.promoter_section.events_history;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.promoters.R;
import com.android.promoters.model.Event;
import com.android.promoters.model.PromoterEvent;
import com.android.promoters.model.Skill;

import java.util.ArrayList;
import java.util.Locale;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class PromoterEventsHistoryAdapter extends RecyclerView.Adapter<PromoterEventsHistoryAdapter.EventViewHolder> {

    private final PromoterEventsHistoryPresenter presenter;

    public PromoterEventsHistoryAdapter(PromoterEventsHistoryPresenter presenter) {
        this.presenter = presenter;
    }

    public void bindEvents(ArrayList<PromoterEvent> events) {
        presenter.bindEvents(events);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public EventViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.promoter_event_list_item, parent, false);
        return new EventViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull EventViewHolder holder, int position) {
        presenter.onBindEventItemOnPosition(holder, position);
    }

    @Override
    public int getItemCount() {
        return presenter.getEventsCount();
    }

    class EventViewHolder extends RecyclerView.ViewHolder {

        private TextView eventNameTextView, startDateTextView, endDateTextView, eventStatusTextView, skillsTextView, rankTextView;
        private Context context;

        public EventViewHolder(@NonNull View itemView) {
            super(itemView);

            this.context = itemView.getContext();
            eventNameTextView = itemView.findViewById(R.id.event_name);
            startDateTextView = itemView.findViewById(R.id.start_date);
            endDateTextView = itemView.findViewById(R.id.end_date);
            skillsTextView = itemView.findViewById(R.id.required_skills_textview);
            eventStatusTextView = itemView.findViewById(R.id.event_status);
            rankTextView = itemView.findViewById(R.id.rank_textView);
        }

        public void setEventData(Event event) {
            eventNameTextView.setText(event.getTitle());
            startDateTextView.setText(event.getStartDate());
            endDateTextView.setText(event.getEndDate());
            StringBuilder skills = new StringBuilder();
            for (Skill skill : event.getRequiredSkills()) {
                skills.append(skill.getName()).append("  ");
            }
            skillsTextView.setText(skills.toString());
            eventStatusTextView.setText(event.getStatus().name());
            if (event.getStatus() != Event.EventStatus.Closed) {
                eventStatusTextView.setTextColor(context.getResources().getColor(R.color.colorThird));
            } else {
                eventStatusTextView.setTextColor(context.getResources().getColor(R.color.colorAccent));
            }
        }

        public void setEventRank(int eventRank) {
            rankTextView.setText(String.format(Locale.US, "Rank:%d", eventRank));
        }
    }
}
