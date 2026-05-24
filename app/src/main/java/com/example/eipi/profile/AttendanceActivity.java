package com.example.eipi.profile;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.eipi.R;

public class AttendanceActivity extends AppCompatActivity {

    private TextView tvBackAttendance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendance);

        tvBackAttendance = findViewById(R.id.tvBackAttendance);
        tvBackAttendance.setOnClickListener(v -> finish());
    }
}