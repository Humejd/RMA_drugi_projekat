package com.example.eipi.nastava;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.eipi.R;
import com.example.eipi.home.HomeActivity;
import com.example.eipi.news.NewsActivity;
import com.example.eipi.profile.ProfileActivity;
import com.example.eipi.tasks.TasksActivity;
import com.example.eipi.terms.TermsActivity;

import java.util.ArrayList;
import java.util.List;

public class NastavaActivity extends AppCompatActivity {

    private LinearLayout nastavaContainer;
    private LinearLayout bottomNews;
    private LinearLayout bottomTerms;
    private LinearLayout bottomHome;
    private LinearLayout bottomTasks;
    private LinearLayout bottomProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nastava);

        nastavaContainer = findViewById(R.id.nastavaContainer);
        bottomNews = findViewById(R.id.bottomNews);
        bottomTerms = findViewById(R.id.bottomTerms);
        bottomHome = findViewById(R.id.bottomHome);
        bottomTasks = findViewById(R.id.bottomTasks);
        bottomProfile = findViewById(R.id.bottomProfile);

        showSchedule();
        setupNavigation();
    }

    private void showSchedule() {
        nastavaContainer.removeAllViews();

        for (TeachingItem item : getTeachingItems()) {
            nastavaContainer.addView(createTeachingCard(item));
        }
    }

    private LinearLayout createTeachingCard(TeachingItem item) {
        LinearLayout card = new LinearLayout(this);
        card.setOrientation(LinearLayout.VERTICAL);
        card.setPadding(dp(18), dp(16), dp(18), dp(16));
        card.setElevation(4f);
        card.setBackgroundResource(item.type.equals("Predavanje") ? R.drawable.bg_lecture_card : R.drawable.bg_exercise_card);

        LinearLayout.LayoutParams cardParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        cardParams.setMargins(dp(24), dp(8), dp(24), dp(14));
        card.setLayoutParams(cardParams);

        LinearLayout topRow = new LinearLayout(this);
        topRow.setOrientation(LinearLayout.HORIZONTAL);
        topRow.setGravity(Gravity.CENTER_VERTICAL);

        TextView subject = new TextView(this);
        subject.setText(item.subject);
        subject.setTextColor(getResources().getColor(R.color.text_dark));
        subject.setTextSize(18);
        subject.setTypeface(null, Typeface.BOLD);
        subject.setMaxLines(2);

        LinearLayout.LayoutParams subjectParams = new LinearLayout.LayoutParams(
                0,
                LinearLayout.LayoutParams.WRAP_CONTENT,
                1
        );
        subject.setLayoutParams(subjectParams);

        TextView type = new TextView(this);
        type.setText(item.type);
        type.setTextColor(item.type.equals("Predavanje") ? getResources().getColor(R.color.lecture_badge) : getResources().getColor(R.color.exercise_badge));
        type.setTextSize(13);
        type.setTypeface(null, Typeface.BOLD);
        type.setGravity(Gravity.CENTER);
        type.setPadding(dp(10), dp(5), dp(10), dp(5));

        topRow.addView(subject);
        topRow.addView(type);

        TextView professor = new TextView(this);
        professor.setText(item.professor);
        professor.setTextColor(getResources().getColor(R.color.text_gray));
        professor.setTextSize(15);
        professor.setPadding(0, dp(10), 0, 0);

        TextView time = new TextView(this);
        time.setText(item.day + " • " + item.time);
        time.setTextColor(getResources().getColor(R.color.text_dark));
        time.setTextSize(16);
        time.setTypeface(null, Typeface.BOLD);
        time.setPadding(0, dp(14), 0, 0);

        TextView classroom = new TextView(this);
        classroom.setText("Učionica: " + item.classroom);
        classroom.setTextColor(getResources().getColor(R.color.text_dark));
        classroom.setTextSize(15);
        classroom.setPadding(0, dp(6), 0, 0);

        card.addView(topRow);
        card.addView(professor);
        card.addView(time);
        card.addView(classroom);

        return card;
    }

    private List<TeachingItem> getTeachingItems() {
        List<TeachingItem> items = new ArrayList<>();

        items.add(new TeachingItem("Razvoj mobilnih aplikacija", "mr. Selena Kurtić, dipl.ing.el.", "Ponedjeljak", "17:00 – 18:35", "RC1", "Predavanje"));
        items.add(new TeachingItem("Razvoj mobilnih aplikacija", "mr. Selena Kurtić, dipl.ing.el.", "Ponedjeljak", "18:35 – 21:00", "RC1", "Vježbe"));

        items.add(new TeachingItem("Menadžment informatičkih projekata", "prof. dr. sc. Dino Arnaut", "Utorak", "16:35 – 18:10", "A", "Predavanje"));
        items.add(new TeachingItem("Menadžment informatičkih projekata", "prof. dr. sc. Dino Arnaut", "Utorak", "18:10 – 20:35", "A", "Vježbe"));

        items.add(new TeachingItem("E-usluge", "mr. sc. Mirhat Đulić", "Srijeda", "15:55 – 17:30", "U2", "Predavanje"));
        items.add(new TeachingItem("E-usluge", "mr. sc. Mirhat Đulić", "Srijeda", "17:30 – 19:55", "A", "Vježbe"));

        items.add(new TeachingItem("Poduzetništvo", "Prof. dr. sc. Damir Šarić", "Četvrtak", "15:55 – 17:30", "T1", "Predavanje"));
        items.add(new TeachingItem("Poduzetništvo", "Prof. dr. sc. Damir Šarić", "Četvrtak", "17:30 – 19:55", "T1", "Vježbe"));

        items.add(new TeachingItem("Sigurnost elektronskog poslovanja", "Dr. sc. Haris Hamidović", "Petak", "15:55 – 17:30", "U2", "Predavanje"));
        items.add(new TeachingItem("Sigurnost elektronskog poslovanja", "Dr. sc. Haris Hamidović", "Petak", "17:30 – 19:55", "U2", "Vježbe"));

        return items;
    }

    private void setupNavigation() {
        bottomNews.setOnClickListener(v -> {
            startActivity(new Intent(NastavaActivity.this, NewsActivity.class));
            finish();
        });

        bottomTerms.setOnClickListener(v -> {
            startActivity(new Intent(NastavaActivity.this, TermsActivity.class));
            finish();
        });

        bottomHome.setOnClickListener(v -> {
            startActivity(new Intent(NastavaActivity.this, HomeActivity.class));
            finish();
        });

        bottomTasks.setOnClickListener(v -> startActivity(new Intent(NastavaActivity.this, TasksActivity.class)));        bottomProfile.setOnClickListener(v -> {
            startActivity(new Intent(NastavaActivity.this, ProfileActivity.class));
            finish();
        });
    }

    private int dp(int value) {
        return (int) (value * getResources().getDisplayMetrics().density);
    }

    private static class TeachingItem {
        String subject;
        String professor;
        String day;
        String time;
        String classroom;
        String type;

        TeachingItem(String subject, String professor, String day, String time, String classroom, String type) {
            this.subject = subject;
            this.professor = professor;
            this.day = day;
            this.time = time;
            this.classroom = classroom;
            this.type = type;
        }
    }
}