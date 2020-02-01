package com.android.promoters.organizer_section.events_history.events_list;

import android.content.Context;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.promoters.R;
import com.android.promoters.backend.events.EventsRepository;
import com.android.promoters.model.Event;
import com.android.promoters.model.Promoter;
import com.android.promoters.organizer_section.events_history.EventsHistoryPresenter;

import java.util.ArrayList;

import androidx.annotation.NonNull;
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
        private LinearLayout promotersContainerLayout;
        private LinearLayout emptyView;
        private Context context;
        private int currentPosition;

        public EventViewHolder(@NonNull View itemView) {
            super(itemView);

            context = itemView.getContext();
            eventNameTextView = itemView.findViewById(R.id.event_name);
            startDateTextView = itemView.findViewById(R.id.start_date);
            endDateTextView = itemView.findViewById(R.id.end_date);
            eventStatusTextView = itemView.findViewById(R.id.event_status);
            promotersContainerLayout = itemView.findViewById(R.id.promoters_candidates_layout);
            emptyView = itemView.findViewById(R.id.empty_view);
        }

        public void setEventData(Event event) {
            currentPosition = getAdapterPosition();
            promotersContainerLayout.removeAllViews();
            eventNameTextView.setText(event.getTitle());
            startDateTextView.setText(event.getStartDate());
            endDateTextView.setText(event.getEndDate());
            eventStatusTextView.setText(event.getStatus().name());
            if (event.getStatus() != Event.EventStatus.Closed) {
                eventStatusTextView.setTextColor(context.getResources().getColor(R.color.colorThird));
                if (event.getCandidatePromoters() != null) {
                    addCandidatesPromoterToLayout(new ArrayList<>(event.getCandidatePromoters().values()));
                } else {
                    emptyView.setVisibility(View.VISIBLE);
                }
                if (event.getAcceptedPromoters() != null) {
                    addAcceptedPromoterToLayout(new ArrayList<>(event.getAcceptedPromoters().values()));
                }
            } else {
                eventStatusTextView.setTextColor(context.getResources().getColor(R.color.colorAccent));
                if (event.getAcceptedPromoters() != null) {
                    addAcceptedPromoterToLayout(new ArrayList<>(event.getAcceptedPromoters().values()));
                } else {
                    emptyView.setVisibility(View.VISIBLE);
                }
            }
        }

        private void addAcceptedPromoterToLayout(ArrayList<Promoter> promoters) {
            emptyView.setVisibility(View.INVISIBLE);
            promotersContainerLayout.setVisibility(View.VISIBLE);

            //add header
            View promoterItemView = LayoutInflater.from(context)
                    .inflate(R.layout.promoter_accepted_list_item, null, false);
            AcceptedPromoterViewHolder holder = new AcceptedPromoterViewHolder(null, currentPosition, promoterItemView);
            holder.setHeader();
            promotersContainerLayout.addView(promoterItemView);
            for (Promoter promoter : promoters) {
                promoterItemView = LayoutInflater.from(context)
                        .inflate(R.layout.promoter_accepted_list_item, null, false);
                holder = new AcceptedPromoterViewHolder(promoter, currentPosition, promoterItemView);
                holder.setPromoterName(promoter);
                //TODO set rank of promoter for this event if exists
                promotersContainerLayout.addView(promoterItemView);
            }
        }

        private void addCandidatesPromoterToLayout(ArrayList<Promoter> promoters) {
            emptyView.setVisibility(View.INVISIBLE);
            promotersContainerLayout.setVisibility(View.VISIBLE);
            //add header
            View promoterItemView = LayoutInflater.from(context)
                    .inflate(R.layout.promoter_candidate_list_item, null, false);
            CandidatePromoterViewHolder holder = new CandidatePromoterViewHolder(null, currentPosition, promoterItemView);
            holder.setHeader();
            promotersContainerLayout.addView(promoterItemView);
            for (Promoter promoter : promoters) {
                promoterItemView = LayoutInflater.from(context)
                        .inflate(R.layout.promoter_candidate_list_item, null, false);
                holder = new CandidatePromoterViewHolder(promoter, currentPosition, promoterItemView);
                holder.setPromoterData(promoter);
                promotersContainerLayout.addView(promoterItemView);
            }
        }
    }

    class CandidatePromoterViewHolder extends RecyclerView.ViewHolder {

        private TextView promoterNameTextView, rankTextView, acceptTextView;
        private CheckBox acceptCheckbox;
        private Context context;
        private int eventPosition;

        public CandidatePromoterViewHolder(final Promoter promoter, int eventPosition, @NonNull View itemView) {
            super(itemView);
            context = itemView.getContext();
            this.eventPosition = eventPosition;
            promoterNameTextView = itemView.findViewById(R.id.promoter_name);
            rankTextView = itemView.findViewById(R.id.rank);
            acceptTextView = itemView.findViewById(R.id.accept_textview);
            acceptCheckbox = itemView.findViewById(R.id.accept_checkbox);
            acceptCheckbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {
                        acceptCandidatePromoter(promoter);
                    }
                }
            });
        }

        private void setPromoterData(Promoter promoter) {
            promoterNameTextView.setText(promoter.getName());
            rankTextView.setText(String.valueOf(promoter.getRank()));
            acceptCheckbox.setVisibility(View.VISIBLE);
            acceptTextView.setVisibility(View.INVISIBLE);
        }

        private void setHeader() {
            promoterNameTextView.setText(context.getResources().getString(R.string.candidates_promoters));
            rankTextView.setText(context.getResources().getString(R.string.rank));
            acceptTextView.setVisibility(View.VISIBLE);
            acceptCheckbox.setVisibility(View.INVISIBLE);
            itemView.setBackgroundColor(context.getResources().getColor(R.color.colorPrimary));
        }

        private void acceptCandidatePromoter(Promoter promoter) {
            presenter.acceptCandidatePromoter(promoter, eventPosition, new EventsRepository.EventsUpdateCallback() {
                @Override
                public void onEventUpdatedSuccessfully() {
                    Toast.makeText(context, "Accepted", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onEventUpdatedFailed(String errmsg) {
                    Toast.makeText(context, errmsg, Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    public class AcceptedPromoterViewHolder extends RecyclerView.ViewHolder {

        private TextView promoterNameTextView, rankTextView;
        private EditText rankEditText;
        private Context context;
        private Handler handler = new Handler();
        private final long DELAY = 2000; // in ms

        public AcceptedPromoterViewHolder(final Promoter promoter, final int eventPosition, @NonNull View itemView) {
            super(itemView);

            context = itemView.getContext();
            promoterNameTextView = itemView.findViewById(R.id.promoter_name);
            rankTextView = itemView.findViewById(R.id.rank);
            rankEditText = itemView.findViewById(R.id.add_rank_edittext);
            if (promoter != null)
                setPromoterRank(promoter.getRank());

            rankEditText.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    if (handler != null) {
                        handler = null;
                    }
                }

                @Override
                public void afterTextChanged(final Editable s) {
                    if (s.length() >= 1) {
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(context, s.toString(), Toast.LENGTH_SHORT).show();
                                Integer promoterRank = Integer.valueOf(s.toString());
                                presenter.setPromoterRank(promoter, eventPosition, promoterRank);
                            }
                        }, DELAY);
                    }
                }
            });
        }

        public void setPromoterName(Promoter promoter) {
            promoterNameTextView.setText(promoter.getName());
        }

        public void setPromoterRank(int rank) {
            rankEditText.setHint(rank == 0 ? "Set Rank" : "Current Rank : " + String.valueOf(rank));
        }

        public void setHeader() {
            promoterNameTextView.setText("Accepted Promoters");
            rankTextView.setText(context.getResources().getString(R.string.rank));
            rankTextView.setVisibility(View.VISIBLE);
            rankEditText.setVisibility(View.INVISIBLE);
            itemView.setBackgroundColor(context.getResources().getColor(R.color.colorPrimary));
        }
    }
}
