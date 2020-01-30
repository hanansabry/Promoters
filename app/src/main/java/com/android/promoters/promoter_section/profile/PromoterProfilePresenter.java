package com.android.promoters.promoter_section.profile;

import com.android.promoters.backend.promoters.PromotersRepository;
import com.android.promoters.backend.skills_regions.DataRepository;
import com.android.promoters.backend.skills_regions.RegionsRepository;
import com.android.promoters.backend.skills_regions.SkillsRepository;

import java.util.ArrayList;
import java.util.HashMap;

public class PromoterProfilePresenter {

    private ArrayList<String> skills = new ArrayList<>();
    private ArrayList<String> selectedSkills = new ArrayList<>();
    private PromoterProfileActivity view;
    private final PromotersRepository promotersRepository;
    private final RegionsRepository regionsRepository;
    private final SkillsRepository skillsRepository;

    public PromoterProfilePresenter(PromoterProfileActivity view,
                                    PromotersRepository promotersRepository,
                                    RegionsRepository regionsRepository,
                                    SkillsRepository skillsRepository) {
        this.view = view;
        this.promotersRepository = promotersRepository;
        this.regionsRepository = regionsRepository;
        this.skillsRepository = skillsRepository;
    }

    public void bindSkills(ArrayList<String> skills) {
        this.skills = skills;
    }

    public void onBindSkillItemOnPosition(SkillsAdapter.SkillViewHolder holder, int position) {
        String skillName = skills.get(position);
        holder.setSkillName(skillName);
        //getPromoterSkills to check previous ones
    }

    public int getSkillsSize() {
        return skills.size();
    }

    public void getPromoterSkills() {
        //from the repository
    }

    public void showMaxSkillsNumberError() {
        view.showMaxSkillsNumberError();
    }

    public void retrieveRegions(DataRepository.RetrieveDataCallback callback) {
        regionsRepository.retrieveData(callback);
    }

    public void retrieveSkills(DataRepository.RetrieveDataCallback callback) {
        skillsRepository.retrieveData(callback);
    }

    public void addSkill(int position) {
        selectedSkills.add(skills.get(position));
    }

    public void removeSkill(int position) {
        selectedSkills.remove(skills.get(position));
    }

    public ArrayList<String> getSelectedSkills() {
        return selectedSkills;
    }

    public void getCurrentPromoter(PromotersRepository.PromotersRetrievingCallback callback) {
        promotersRepository.getPromoterById(callback);
    }

    public void updatePromoter(HashMap<String, Object> promoterData, PromotersRepository.UpdatePromoterCallback callback) {
        promotersRepository.updatePromoterData(promoterData, callback);
    }
}
