package com.android.promoters.organizer_section;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Toast;

import com.android.promoters.R;

public class OrganizerMainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_organizer_main);
        Toast.makeText(this, "Organizer Main activity", Toast.LENGTH_LONG);
    }
}
