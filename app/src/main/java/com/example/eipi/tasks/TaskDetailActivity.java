package com.example.eipi.tasks;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.OpenableColumns;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.eipi.R;

public class TaskDetailActivity extends AppCompatActivity {

    private static final int PICK_FILE_REQUEST = 1001;

    private TextView tvTaskSubject;
    private TextView tvTaskTitle;
    private TextView tvTaskDeadline;
    private TextView tvTaskStatus;
    private TextView tvTaskDescription;
    private TextView tvBackTasks;
    private TextView tvSelectedFile;

    private Button btnSelectFile;
    private Button btnSubmitTask;

    private Uri selectedFileUri;
    private String taskTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_detail);

        tvTaskSubject = findViewById(R.id.tvTaskSubject);
        tvTaskTitle = findViewById(R.id.tvTaskTitle);
        tvTaskDeadline = findViewById(R.id.tvTaskDeadline);
        tvTaskStatus = findViewById(R.id.tvTaskStatus);
        tvTaskDescription = findViewById(R.id.tvTaskDescription);
        tvBackTasks = findViewById(R.id.tvBackTasks);
        tvSelectedFile = findViewById(R.id.tvSelectedFile);

        btnSelectFile = findViewById(R.id.btnSelectFile);
        btnSubmitTask = findViewById(R.id.btnSubmitTask);

        taskTitle = getIntent().getStringExtra("title");

        tvTaskSubject.setText(getIntent().getStringExtra("subject"));
        tvTaskTitle.setText(taskTitle);
        tvTaskDeadline.setText(getIntent().getStringExtra("deadline"));

        String status = getIntent().getStringExtra("status");
        tvTaskStatus.setText(status);
        setStatusColor(status);

        tvTaskDescription.setText(getIntent().getStringExtra("description"));

        tvBackTasks.setOnClickListener(v -> finish());

        if (taskTitle != null && taskTitle.toLowerCase().contains("kviz")) {
            showQuizMode();
        } else {
            showSubmissionMode();
        }

        btnSelectFile.setOnClickListener(v -> openFilePicker());

        btnSubmitTask.setOnClickListener(v -> {
            if (selectedFileUri == null) {
                Toast.makeText(this, "Prvo odaberite fajl za predaju.", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Zadatak je uspješno predan.", Toast.LENGTH_SHORT).show();
                tvTaskStatus.setText("Završeno");
                setStatusColor("Završeno");
            }
        });
    }

    private void showSubmissionMode() {
        btnSelectFile.setVisibility(View.VISIBLE);
        btnSubmitTask.setVisibility(View.VISIBLE);
        tvSelectedFile.setVisibility(View.VISIBLE);
    }

    private void showQuizMode() {
        btnSelectFile.setVisibility(View.GONE);
        btnSubmitTask.setVisibility(View.GONE);
        tvSelectedFile.setVisibility(View.GONE);
    }

    private void openFilePicker() {
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.setType("*/*");
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        startActivityForResult(intent, PICK_FILE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_FILE_REQUEST && resultCode == RESULT_OK && data != null) {
            selectedFileUri = data.getData();

            if (selectedFileUri != null) {
                tvSelectedFile.setText("Odabrani fajl: " + getFileName(selectedFileUri));
            }
        }
    }

    private String getFileName(Uri uri) {
        String fileName = "fajl";

        Cursor cursor = getContentResolver().query(uri, null, null, null, null);

        if (cursor != null) {
            int nameIndex = cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME);

            if (cursor.moveToFirst() && nameIndex >= 0) {
                fileName = cursor.getString(nameIndex);
            }

            cursor.close();
        }

        return fileName;
    }

    private void setStatusColor(String status) {
        if ("Završeno".equals(status)) {
            tvTaskStatus.setTextColor(0xFF2E7D32);
        } else {
            tvTaskStatus.setTextColor(getResources().getColor(R.color.warning_icon));
        }
    }
}