package com.android.promoters.organizer_section.events_history.promoters_list;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.android.promoters.R;
import com.android.promoters.model.Promoter;
import com.android.promoters.organizer_section.events_history.EventsHistoryPresenter;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class AcceptedPromotersAdapter extends RecyclerView.Adapter<AcceptedPromotersAdapter.PromoterViewHolder> {

    private final EventsHistoryPresenter presenter;

    public AcceptedPromotersAdapter(EventsHistoryPresenter presenter) {
        this.presenter = presenter;
    }

    public void bindPromoters(ArrayList<Promoter> promoters) {
        presenter.bindAcceptedPromotersList(promoters);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public PromoterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.promoter_accepted_list_item, parent, false);
        return new PromoterViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull PromoterViewHolder holder, int position) {
        if (position == 0) {
            holder.setHeader();
        } else {
            presenter.onBindAcceptedPromoterAtPosition(holder, position - 1);
        }
    }

    @Override
    public int getItemCount() {
        if (presenter.getCandidatePromotersListSize() == 0) {
            return 0;
        } else {
            return presenter.getAcceptedPromotersListSize() + 1;
        }
    }


    public class PromoterViewHolder extends RecyclerView.ViewHolder {

        private TextView promoterNameTextView, rankTextView;
        private EditText rankEditText;
        private Context context;

        public PromoterViewHolder(@NonNull View itemView) {
            super(itemView);

            context = itemView.getContext();
            promoterNameTextView = itemView.findViewById(R.id.promoter_name);
            rankTextView = itemView.findViewById(R.id.rank);
            rankEditText = itemView.findViewById(R.id.add_rank_edittext);
        }

        public void setPromoterName(Promoter promoter) {
            promoterNameTextView.setText(promoter.getName());
        }

        public void setPromoterRank() {
            presenter.setPromoterRank(Integer.valueOf(rankEditText.getText().toString()), getAdapterPosition());
        }

        public void setHeader() {
            promoterNameTextView.setText(context.getResources().getString(R.string.promoter_name));
            rankTextView.setText(context.getResources().getString(R.string.rank));
            rankTextView.setVisibility(View.VISIBLE);
            rankEditText.setVisibility(View.INVISIBLE);
        }
    }
}
