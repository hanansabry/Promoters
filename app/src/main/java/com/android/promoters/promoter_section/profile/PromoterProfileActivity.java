package com.android.promoters.promoter_section.profile;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.promoters.Injection;
import com.android.promoters.R;
import com.android.promoters.backend.promoters.PromotersRepository;
import com.android.promoters.backend.skills_regions.DataRepository;

import java.util.ArrayList;
import java.util.HashMap;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class PromoterProfileActivity extends AppCompatActivity {

    private PromoterProfilePresenter presenter;
    private EditText experienceEditText;
    private Spinner regionsSpinner;
    private String selectedRegion;
    private SkillsAdapter skillsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_promoter_profile);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        presenter = new PromoterProfilePresenter(this,
                Injection.providePromotersRepository(),
                Injection.provideRegionsRepository(),
                Injection.provideSkillsRepository());
        initializeView();
        initializeSkillsRecyclerView();
        presenter.retrieveRegions(new DataRepository.RetrieveDataCallback() {
            @Override
            public void onDataRetrievedSuccessfully(ArrayList<String> list) {
                populateRegionsSpinner(list);
            }

            @Override
            public void onDataRetrievedFailed(String errmsg) {
                Toast.makeText(PromoterProfileActivity.this, "Can't retrieve regions", Toast.LENGTH_LONG).show();
            }
        });
        presenter.retrieveSkills(new DataRepository.RetrieveDataCallback() {
            @Override
            public void onDataRetrievedSuccessfully(ArrayList<String> list) {
                skillsAdapter.bindSkills(list);
            }

            @Override
            public void onDataRetrievedFailed(String errmsg) {
                Toast.makeText(PromoterProfileActivity.this, "Can't retrieve skills", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void initializeSkillsRecyclerView() {
        RecyclerView skillsRecyclerVIew = findViewById(R.id.promoters_skills_rv);
        skillsRecyclerVIew.setLayoutManager(new LinearLayoutManager(this));

        skillsAdapter = new SkillsAdapter(presenter);
        skillsRecyclerVIew.setAdapter(skillsAdapter);

    }

    private void initializeView() {
        experienceEditText = findViewById(R.id.experience_edit_text);
        experienceEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                experienceEditText.setError(null);
            }
        });
        regionsSpinner = findViewById(R.id.regions_spinner);
    }

    private void populateRegionsSpinner(ArrayList<String> regions) {
        final ArrayAdapter<String> regionsAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item);
        //fake date
        for (String region : regions) {
            regionsAdapter.add(region);
        }
        regionsSpinner.setAdapter(regionsAdapter);
        regionsSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedRegion = regionsAdapter.getItem(position);
                Toast.makeText(PromoterProfileActivity.this, regionsAdapter.getItem(position), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    public void showMaxSkillsNumberError() {
        Toast.makeText(this, "You can't choose more than 2 skills", Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.promoter_profile_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        } else if (item.getItemId() == R.id.apply_action) {
            presenter.updatePromoter(getPromoterData(), new PromotersRepository.UpdatePromoterCallback() {
                @Override
                public void onPromoterUpdatedSuccessfully() {
                    Toast.makeText(PromoterProfileActivity.this, "Profile is updated successfully", Toast.LENGTH_SHORT).show();
                    finish();
                }

                @Override
                public void onPromoterUpdatedFailed(String errmsg) {
                    Toast.makeText(PromoterProfileActivity.this, errmsg, Toast.LENGTH_SHORT).show();
                }
            });
        }
        return super.onOptionsItemSelected(item);
    }

    private HashMap<String, Object> getPromoterData() {
        HashMap<String, Object> data = new HashMap<>();
        String experience = experienceEditText.getText().toString().trim();
        data.put("experience", Integer.valueOf(experience.equals("") ? "0" : experience));
        data.put("region", selectedRegion);
        data.put("skills", presenter.getSelectedSkills());
        return data;
    }
}
