package com.android.promoters.promoter_section;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Toast;

import com.android.promoters.R;

public class PromoterMainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_promoter_main);
        Toast.makeText(this, "Promoter Main activity", Toast.LENGTH_SHORT);
    }
}