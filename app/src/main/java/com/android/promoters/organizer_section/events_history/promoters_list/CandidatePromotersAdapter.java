package com.android.promoters.organizer_section.events_history.promoters_list;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.android.promoters.R;
import com.android.promoters.model.Promoter;
import com.android.promoters.organizer_section.events_history.EventsHistoryPresenter;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class CandidatePromotersAdapter extends RecyclerView.Adapter<CandidatePromotersAdapter.PromoterViewHolder> {

    private final EventsHistoryPresenter presenter;

    public CandidatePromotersAdapter(EventsHistoryPresenter presenter) {
        this.presenter = presenter;
    }

    public void bindPromoters(ArrayList<Promoter> promoters) {
        presenter.bindCandidatePromotersList(promoters);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public PromoterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.promoter_candidate_list_item, parent, false);
        return new PromoterViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull PromoterViewHolder holder, int position) {
        if (position == 0) {
            holder.setHeader();
        } else {
            presenter.onBindCandidatePromoterAtPosition(holder, position - 1);
        }
    }

    @Override
    public int getItemCount() {
        if (presenter.getCandidatePromotersListSize() == 0) {
            return 0;
        } else {
            return presenter.getCandidatePromotersListSize() + 1;
        }
    }

    public class PromoterViewHolder extends RecyclerView.ViewHolder {

        private TextView promoterNameTextView, rankTextView, acceptTextView;
        private CheckBox acceptCheckbox;
        private Context context;

        public PromoterViewHolder(@NonNull View itemView) {
            super(itemView);

            context = itemView.getContext();
            promoterNameTextView = itemView.findViewById(R.id.promoter_name);
            rankTextView = itemView.findViewById(R.id.rank);
            acceptTextView = itemView.findViewById(R.id.accept_textview);
            acceptCheckbox = itemView.findViewById(R.id.accept_checkbox);
        }

        public void setPromoterData(Promoter promoter) {
            promoterNameTextView.setText(promoter.getName());
            rankTextView.setText(String.valueOf(promoter.getRank()));
        }

        public void setHeader() {
            promoterNameTextView.setText(context.getResources().getString(R.string.candidates_promoters));
            rankTextView.setText(context.getResources().getString(R.string.rank));
            acceptTextView.setVisibility(View.VISIBLE);
            acceptCheckbox.setVisibility(View.INVISIBLE);
        }
    }
}
