package com.example.eipi.profile;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.eipi.R;

public class GradesActivity extends AppCompatActivity {

    private TextView tvBackGrades;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grades);

        tvBackGrades = findViewById(R.id.tvBackGrades);
        tvBackGrades.setOnClickListener(v -> finish());
    }
}