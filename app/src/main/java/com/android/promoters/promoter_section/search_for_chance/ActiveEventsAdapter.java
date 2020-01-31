package com.android.promoters.promoter_section.search_for_chance;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.promoters.R;
import com.android.promoters.backend.events.EventsRepository;
import com.android.promoters.model.Event;
import com.android.promoters.model.Skill;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ActiveEventsAdapter extends RecyclerView.Adapter<ActiveEventsAdapter.ActiveEventViewHolder> {

    private final ActiveEventsPresenter presenter;

    public ActiveEventsAdapter(ActiveEventsPresenter presenter) {
        this.presenter = presenter;
    }

    public void bindEvents(ArrayList<Event> events) {
        presenter.bindEvents(events);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ActiveEventViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.promoter_active_event_list_item, parent, false);
        return new ActiveEventViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ActiveEventViewHolder holder, int position) {
        presenter.onBindEventItemOnPosition(holder, position);
    }

    @Override
    public int getItemCount() {
        return presenter.getEventsCount();
    }

    class ActiveEventViewHolder extends RecyclerView.ViewHolder implements EventsRepository.EventsUpdateCallback {

        private TextView eventNameTextView, startDateTextView, endDateTextView, skillsTextView, statusTextView;
        private CheckBox applyCheckBox;
        private Context context;

        public ActiveEventViewHolder(@NonNull View itemView) {
            super(itemView);

            context = itemView.getContext();
            eventNameTextView = itemView.findViewById(R.id.event_name);
            startDateTextView = itemView.findViewById(R.id.start_date);
            endDateTextView = itemView.findViewById(R.id.end_date);
            skillsTextView = itemView.findViewById(R.id.required_skills_textview);
            statusTextView = itemView.findViewById(R.id.event_status);
            applyCheckBox = itemView.findViewById(R.id.apply_checkbox);
            applyCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {
                        presenter.applyForEvent(getAdapterPosition(), ActiveEventViewHolder.this);
                    } else {
                        presenter.removeEventApply(getAdapterPosition(), ActiveEventViewHolder.this);
                    }
                }
            });
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
            statusTextView.setText(event.getStatus().name());
        }

        @Override
        public void onEventUpdatedSuccessfully() {
            Toast.makeText(context, "Changes has been saved", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onEventUpdatedFailed(String errmsg) {
            Toast.makeText(context, errmsg, Toast.LENGTH_SHORT).show();
        }
    }
}
