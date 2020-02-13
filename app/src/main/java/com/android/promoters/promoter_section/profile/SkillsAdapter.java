package com.android.promoters.promoter_section.profile;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.android.promoters.R;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class SkillsAdapter extends RecyclerView.Adapter<SkillsAdapter.SkillViewHolder> {

    private int skillCheckedNumber;
    private final PromoterProfilePresenter presenter;

    public SkillsAdapter(PromoterProfilePresenter presenter) {
        this.presenter = presenter;
        notifyDataSetChanged();
    }

    public void bindSkills(ArrayList<String> skills) {
        presenter.bindSkills(skills);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public SkillViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.promoter_skill_item, parent, false);
        return new SkillViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull SkillViewHolder holder, int position) {
        presenter.onBindSkillItemOnPosition(holder, position);
    }

    @Override
    public int getItemCount() {
        return presenter.getSkillsSize();
    }

    class SkillViewHolder extends RecyclerView.ViewHolder {

        private TextView skillNameTextView;
        private CheckBox skillCheckBox;

        public SkillViewHolder(@NonNull View itemView) {
            super(itemView);

            skillNameTextView = itemView.findViewById(R.id.skill_name);
            skillCheckBox = itemView.findViewById(R.id.skill_checkbox);
            skillCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {
                        if (skillCheckedNumber >= 2) {
                            skillCheckBox.setChecked(false);
                            presenter.showMaxSkillsNumberError();
                        } else {
                            skillCheckedNumber++;
                            presenter.addSkill(getAdapterPosition());
                        }
                    } else {
                        skillCheckedNumber--;
                        presenter.removeSkill(getAdapterPosition());
                    }

                }
            });
        }

        public void setSkillName(String skillName) {
            skillNameTextView.setText(skillName);
        }

        public void setSkillChecked(boolean checked) {
            skillCheckBox.setChecked(checked);
        }
    }
}
