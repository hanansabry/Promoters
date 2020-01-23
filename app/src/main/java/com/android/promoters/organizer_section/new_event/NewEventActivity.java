package com.android.promoters.organizer_section.new_event;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.text.InputType;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.promoters.R;
import com.android.promoters.model.Event;
import com.android.promoters.model.Skill;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class NewEventActivity extends AppCompatActivity {

    private TextInputLayout eventTitleTextInput, startDateTextInput, endDateTextInput;
    private EditText eventTitleEditText, startDateEditText, endDateEditText;
    private Spinner regionsSpinner;
    private LinearLayout requiredSkillsView;
    private NewEventPresenter presenter;
    private int skillViewId = 0;
    private String selectedRegion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_event);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        presenter = new NewEventPresenter(this);

        initializeViews();
        populateRegionsSpinner();
//        getAllSkills();
    }

    private void initializeViews() {
        requiredSkillsView = findViewById(R.id.required_skills_view);
        eventTitleTextInput = findViewById(R.id.title_text_input);
        startDateTextInput = findViewById(R.id.startdate_text_input);
        endDateTextInput = findViewById(R.id.enddate_text_input);
        regionsSpinner = findViewById(R.id.regions_spinner);

        eventTitleEditText = findViewById(R.id.title_edit_text);
        eventTitleEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                eventTitleTextInput.setError(null);
                eventTitleTextInput.setErrorEnabled(false);
            }
        });

        startDateEditText = findViewById(R.id.startdate_edit_text);
        startDateEditText.setInputType(InputType.TYPE_NULL);
        startDateEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setupCalenderPickerDialog(startDateEditText);
            }
        });
        startDateEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                startDateTextInput.setError(null);
                startDateTextInput.setErrorEnabled(false);
                if (hasFocus) {
                    setupCalenderPickerDialog(startDateEditText);
                }
            }
        });

        endDateEditText = findViewById(R.id.enddate_edit_text);
        endDateEditText.setInputType(InputType.TYPE_NULL);
        endDateEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setupCalenderPickerDialog(endDateEditText);
            }
        });
        endDateEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                endDateTextInput.setError(null);
                endDateTextInput.setErrorEnabled(false);
                if (hasFocus) {
                    setupCalenderPickerDialog(endDateEditText);
                }
            }
        });
    }

    private void populateRegionsSpinner() {
        final ArrayAdapter<String> regionsAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item);
        //fake date
        regionsAdapter.add("Region1");
        regionsAdapter.add("Region2");
        regionsSpinner.setAdapter(regionsAdapter);
        regionsSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedRegion = regionsAdapter.getItem(position);
                Toast.makeText(NewEventActivity.this, regionsAdapter.getItem(position), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void setupCalenderPickerDialog(final EditText editText) {
        Calendar calendar = Calendar.getInstance();
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int month = calendar.get(Calendar.MONTH);
        int year = calendar.get(Calendar.YEAR);
        //date picker dialog
        DatePickerDialog pickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                editText.setText(String.format(Locale.US, "%1d/%2d/%3d", dayOfMonth, month + 1, year));
            }
        }, year, month, day);
        pickerDialog.show();
    }

    public void addNewSkill(View view) {
        findViewById(R.id.empty_view).setVisibility(View.GONE);
        View newSkillView = getLayoutInflater().inflate(R.layout.required_skill_item, null);
        newSkillView.setId(++skillViewId);
        requiredSkillsView.addView(newSkillView);
    }

    private void getAllSkills() {
        ArrayList<Skill> skills = new ArrayList<>();
        Skill s1 = new Skill();
        s1.setSkillName("Skill Name 1");
        s1.setRequiredPromoters(5);

        Skill s2 = new Skill();
        s2.setSkillName("Skill Name 2");
        s2.setRequiredPromoters(3);
        skills.add(s1);
        skills.add(s2);

        if (!skills.isEmpty()) {
            findViewById(R.id.empty_view).setVisibility(View.GONE);
            for (Skill skill : skills) {
                View newSkillView = getLayoutInflater().inflate(R.layout.required_skill_item, null);
                requiredSkillsView.addView(newSkillView);
                EditText skillName = newSkillView.findViewById(R.id.skill_name_edittext);
                EditText promotersNum = newSkillView.findViewById(R.id.promoters_number_edittext);

                skillName.setText(skill.getSkillName());
                skillName.setFocusable(false);

                promotersNum.setText(String.valueOf(skill.getRequiredPromoters()));
                promotersNum.setFocusable(false);
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.new_event_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        } else if (item.getItemId() == R.id.create_action) {
            Event newEvent = getEventData();
            if (presenter.validateEventData(newEvent)) {
                Toast.makeText(this, "New event is added successfully", Toast.LENGTH_SHORT).show();
            }
        }
        return super.onOptionsItemSelected(item);
    }

    private Event getEventData() {
        Event newEvent = new Event();
        newEvent.setTitle(eventTitleEditText.getText().toString().trim());
        newEvent.setStartDate(startDateEditText.getText().toString().trim());
        newEvent.setEndDate(endDateEditText.getText().toString().trim());
        newEvent.setRegion(selectedRegion);
        //get added skills
        ArrayList<Skill> requiredSkills = new ArrayList<>();
        int newSkillsCount = 0;
        for (int index = 0; index < requiredSkillsView.getChildCount(); ++index) {
            View skillChild = requiredSkillsView.getChildAt(index);
            if (skillChild.getId() != R.id.empty_view) {
                EditText skillName = skillChild.findViewById(R.id.skill_name_edittext);
                EditText promotersNum = skillChild.findViewById(R.id.promoters_number_edittext);

                if (skillName.isFocusable() && promotersNum.isFocusable()) {
                    newSkillsCount++;
                    Skill skill = new Skill();
                    if (skillName.getText().toString().isEmpty() && promotersNum.getText().toString().isEmpty()) {
                        newSkillsCount--;
                        continue;
                    }
                    if (skillName.getText().toString().isEmpty()) {
                        skillName.setError("Please enter skill name");
                    } else {
                        skill.setSkillName(skillName.getText().toString().trim());
                    }
                    if (promotersNum.getText().toString().isEmpty()) {
                        promotersNum.setError("Please enter number of promoters");
                    } else {
                        skill.setRequiredPromoters(Integer.valueOf(promotersNum.getText().toString().trim()));
                    }
                    if (!(skill.getSkillName() == null || skill.getSkillName().isEmpty()) && skill.getRequiredPromoters() != 0) {
                        requiredSkills.add(skill);
                    }
                }
            }
        }
        if (requiredSkills.size() == newSkillsCount) {
            newEvent.setRequiredSkills(requiredSkills);
        }
        return newEvent;
    }

    public void showErrorMessage() {
        Toast.makeText(this, "Please enter all fields.", Toast.LENGTH_SHORT).show();
    }
}
