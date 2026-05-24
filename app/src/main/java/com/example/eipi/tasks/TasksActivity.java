package com.example.eipi.tasks;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.eipi.R;
import com.example.eipi.home.HomeActivity;
import com.example.eipi.news.NewsActivity;
import com.example.eipi.profile.ProfileActivity;
import com.example.eipi.terms.TermsActivity;

import java.util.ArrayList;
import java.util.List;

public class TasksActivity extends AppCompatActivity {

    private LinearLayout tasksContainer;
    private LinearLayout bottomNews;
    private LinearLayout bottomTerms;
    private LinearLayout bottomHome;
    private LinearLayout bottomTasks;
    private LinearLayout bottomProfile;

    private final List<TaskItem> tasks = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tasks);

        tasksContainer = findViewById(R.id.tasksContainer);

        bottomNews = findViewById(R.id.bottomNews);
        bottomTerms = findViewById(R.id.bottomTerms);
        bottomHome = findViewById(R.id.bottomHome);
        bottomTasks = findViewById(R.id.bottomTasks);
        bottomProfile = findViewById(R.id.bottomProfile);

        prepareTasks();
        showTasks();
        setupNavigation();
    }

    private void prepareTasks() {
        tasks.add(new TaskItem(
                "Razvoj mobilnih aplikacija",
                "Projekat 2",
                "Rok: 24.05.2026 00:00",
                "Nije završeno",
                "Potrebno je završiti projektni zadatak 2 iz predmeta Razvoj mobilnih aplikacija."
        ));

        tasks.add(new TaskItem(
                "Menadžment informatičkih projekata",
                "Projekat",
                "Rok: 23.05.2026 00:00",
                "Nije završeno",
                "Potrebno je završiti projektni zadatak iz predmeta Menadžment informatičkih projekata."
        ));

        tasks.add(new TaskItem(
                "Sigurnost elektronskog poslovanja",
                "Seminarski rad",
                "Rok: 26.05.2026 00:00",
                "Nije završeno",
                "Potrebno je završiti i predati seminarski rad iz predmeta Sigurnost elektronskog poslovanja."
        ));

        tasks.add(new TaskItem(
                "Sigurnost elektronskog poslovanja",
                "Kviz 10",
                "Rok: 29.05.2026 00:00",
                "Nije završeno",
                "Potrebno je uraditi kviz 10 iz predmeta Sigurnost elektronskog poslovanja."
        ));

        tasks.add(new TaskItem(
                "Sigurnost elektronskog poslovanja",
                "Kviz 9",
                "Rok: 22.05.2026 00:00",
                "Završeno",
                "Kviz 9 iz predmeta Sigurnost elektronskog poslovanja je završen."
        ));

        tasks.add(new TaskItem(
                "Sigurnost elektronskog poslovanja",
                "Kviz 8",
                "Rok: 15.05.2026 00:00",
                "Završeno",
                "Kviz 8 iz predmeta Sigurnost elektronskog poslovanja je završen."
        ));

        tasks.add(new TaskItem(
                "Sigurnost elektronskog poslovanja",
                "Kviz 7",
                "Rok: 08.05.2026 00:00",
                "Završeno",
                "Kviz 7 iz predmeta Sigurnost elektronskog poslovanja je završen."
        ));

        tasks.add(new TaskItem(
                "Sigurnost elektronskog poslovanja",
                "Kviz 6",
                "Rok: 01.05.2026 00:00",
                "Završeno",
                "Kviz 6 iz predmeta Sigurnost elektronskog poslovanja je završen."
        ));

        tasks.add(new TaskItem(
                "Sigurnost elektronskog poslovanja",
                "Kviz 5",
                "Rok: 24.04.2026 00:00",
                "Završeno",
                "Kviz 5 iz predmeta Sigurnost elektronskog poslovanja je završen."
        ));

        tasks.add(new TaskItem(
                "Sigurnost elektronskog poslovanja",
                "Kviz 4",
                "Rok: 17.04.2026 00:00",
                "Završeno",
                "Kviz 4 iz predmeta Sigurnost elektronskog poslovanja je završen."
        ));

        tasks.add(new TaskItem(
                "Sigurnost elektronskog poslovanja",
                "Kviz 3",
                "Rok: 10.04.2026 00:00",
                "Završeno",
                "Kviz 3 iz predmeta Sigurnost elektronskog poslovanja je završen."
        ));

        tasks.add(new TaskItem(
                "Sigurnost elektronskog poslovanja",
                "Kviz 2",
                "Rok: 03.04.2026 00:00",
                "Završeno",
                "Kviz 2 iz predmeta Sigurnost elektronskog poslovanja je završen."
        ));

        tasks.add(new TaskItem(
                "Sigurnost elektronskog poslovanja",
                "Kviz 1",
                "Rok: 27.03.2026 00:00",
                "Završeno",
                "Kviz 1 iz predmeta Sigurnost elektronskog poslovanja je završen."
        ));
    }

    private void showTasks() {
        tasksContainer.removeAllViews();

        for (TaskItem task : tasks) {
            tasksContainer.addView(createTaskCard(task));
        }
    }

    private LinearLayout createTaskCard(TaskItem task) {
        LinearLayout card = new LinearLayout(this);
        card.setLayoutParams(createCardParams());
        card.setOrientation(LinearLayout.VERTICAL);
        card.setPadding(dp(18), dp(16), dp(18), dp(16));
        card.setBackgroundResource(R.drawable.bg_white_card);
        card.setElevation(dp(4));

        TextView status = new TextView(this);
        status.setText(task.getStatus());
        status.setTextSize(13);
        status.setTypeface(null, Typeface.BOLD);
        status.setTextColor(getStatusColor(task.getStatus()));
        status.setGravity(Gravity.END);

        TextView subject = new TextView(this);
        subject.setText(task.getSubject());
        subject.setTextSize(22);
        subject.setTextColor(0xFF000000);
        subject.setPadding(0, dp(6), 0, 0);

        TextView title = new TextView(this);
        title.setText(task.getTitle());
        title.setTextSize(20);
        title.setTypeface(null, Typeface.BOLD);
        title.setTextColor(getResources().getColor(R.color.text_dark));
        title.setPadding(0, dp(8), 0, 0);

        TextView deadline = new TextView(this);
        deadline.setText(task.getDeadline());
        deadline.setTextSize(15);
        deadline.setTextColor(getResources().getColor(R.color.text_dark));
        deadline.setPadding(0, dp(10), 0, 0);

        card.addView(status);
        card.addView(subject);
        card.addView(title);
        card.addView(deadline);

        card.setOnClickListener(v -> openTaskDetail(task));

        return card;
    }

    private void openTaskDetail(TaskItem task) {
        Intent intent = new Intent(TasksActivity.this, TaskDetailActivity.class);
        intent.putExtra("subject", task.getSubject());
        intent.putExtra("title", task.getTitle());
        intent.putExtra("deadline", task.getDeadline());
        intent.putExtra("status", task.getStatus());
        intent.putExtra("description", task.getDescription());
        startActivity(intent);
    }

    private LinearLayout.LayoutParams createCardParams() {
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        params.setMargins(dp(24), 0, dp(24), dp(14));
        return params;
    }

    private int getStatusColor(String status) {
        if (status.equals("Završeno")) {
            return 0xFF2E7D32;
        }

        return getResources().getColor(R.color.warning_icon);
    }

    private void setupNavigation() {
        bottomNews.setOnClickListener(v -> startActivity(new Intent(TasksActivity.this, NewsActivity.class)));
        bottomTerms.setOnClickListener(v -> startActivity(new Intent(TasksActivity.this, TermsActivity.class)));
        bottomHome.setOnClickListener(v -> startActivity(new Intent(TasksActivity.this, HomeActivity.class)));
        bottomTasks.setOnClickListener(v -> {});
        bottomProfile.setOnClickListener(v -> startActivity(new Intent(TasksActivity.this, ProfileActivity.class)));
    }

    private int dp(int value) {
        return (int) (value * getResources().getDisplayMetrics().density);
    }
}