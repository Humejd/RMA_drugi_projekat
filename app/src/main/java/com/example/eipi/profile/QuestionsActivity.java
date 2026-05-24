package com.example.eipi.profile;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.eipi.R;

public class QuestionsActivity extends AppCompatActivity {

    private TextView tvBackQuestions;
    private Spinner spinnerProfessor;
    private EditText etQuestion;
    private Button btnSendQuestion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questions);

        tvBackQuestions = findViewById(R.id.tvBackQuestions);
        spinnerProfessor = findViewById(R.id.spinnerProfessor);
        etQuestion = findViewById(R.id.etQuestion);
        btnSendQuestion = findViewById(R.id.btnSendQuestion);

        String[] professors = {
                "mr. Selena Kurtić, dipl.ing.el.",
                "mr. sc. Mirhat Đulić",
                "Dr. sc. Haris Hamidović",
                "prof. dr. sc. Dino Arnaut",
                "Prof. dr. sc. Damir Šarić"
        };

        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_item,
                professors
        );

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerProfessor.setAdapter(adapter);

        tvBackQuestions.setOnClickListener(v -> finish());

        btnSendQuestion.setOnClickListener(v -> {
            String question = etQuestion.getText().toString().trim();

            if (question.isEmpty()) {
                Toast.makeText(this, "Unesite pitanje prije slanja.", Toast.LENGTH_SHORT).show();
                return;
            }

            Toast.makeText(this, "Pitanje je poslano profesoru.", Toast.LENGTH_SHORT).show();
            etQuestion.setText("");
        });
    }
}