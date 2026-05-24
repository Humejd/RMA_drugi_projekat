package com.example.eipi.terms;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.eipi.R;
import com.example.eipi.home.HomeActivity;
import com.example.eipi.news.NewsActivity;

import java.util.List;

public class TermsActivity extends AppCompatActivity {

    private LinearLayout termsContainer;
    private LinearLayout bottomNews;
    private LinearLayout bottomTerms;
    private LinearLayout bottomHome;
    private LinearLayout bottomTasks;
    private LinearLayout bottomProfile;
    private ExamTermViewModel examTermViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_terms);

        termsContainer = findViewById(R.id.termsContainer);
        bottomNews = findViewById(R.id.bottomNews);
        bottomTerms = findViewById(R.id.bottomTerms);
        bottomHome = findViewById(R.id.bottomHome);
        bottomTasks = findViewById(R.id.bottomTasks);
        bottomProfile = findViewById(R.id.bottomProfile);

        examTermViewModel = new ViewModelProvider(this).get(ExamTermViewModel.class);

        observeExamTerms();
        setupNavigation();

        findViewById(R.id.btnFilterTerms).setOnClickListener(v -> Toast.makeText(this, "Filter će biti dodan u narednom koraku", Toast.LENGTH_SHORT).show());
    }

    private void observeExamTerms() {
        examTermViewModel.getAllExamTerms().observe(this, this::showExamTerms);
    }

    private void showExamTerms(List<ExamTermEntity> examTerms) {
        termsContainer.removeAllViews();

        for (ExamTermEntity term : examTerms) {
            termsContainer.addView(createExamCard(term));
        }
    }

    private LinearLayout createExamCard(ExamTermEntity term) {
        LinearLayout card = new LinearLayout(this);
        card.setOrientation(LinearLayout.HORIZONTAL);
        card.setGravity(Gravity.CENTER_VERTICAL);
        card.setBackgroundResource(R.drawable.bg_white_card);
        card.setElevation(5f);
        card.setPadding(dp(18), dp(18), dp(18), dp(18));

        LinearLayout.LayoutParams cardParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                dp(125)
        );
        cardParams.setMargins(dp(24), dp(8), dp(24), dp(14));
        card.setLayoutParams(cardParams);

        LinearLayout content = new LinearLayout(this);
        content.setOrientation(LinearLayout.VERTICAL);

        LinearLayout.LayoutParams contentParams = new LinearLayout.LayoutParams(
                0,
                LinearLayout.LayoutParams.MATCH_PARENT,
                1
        );
        content.setLayoutParams(contentParams);

        TextView subject = new TextView(this);
        subject.setText(term.getSubjectName());
        subject.setTextColor(getResources().getColor(R.color.text_dark));
        subject.setTextSize(19);
        subject.setTypeface(null, Typeface.BOLD);
        subject.setMaxLines(2);

        TextView date = new TextView(this);
        date.setText(term.getDateTime());
        date.setTextColor(getResources().getColor(R.color.text_dark));
        date.setTextSize(16);
        date.setPadding(0, dp(18), 0, 0);

        content.addView(subject);
        content.addView(date);

        LinearLayout actionArea = new LinearLayout(this);
        actionArea.setOrientation(LinearLayout.VERTICAL);
        actionArea.setGravity(Gravity.CENTER);

        LinearLayout.LayoutParams actionParams = new LinearLayout.LayoutParams(
                dp(95),
                LinearLayout.LayoutParams.MATCH_PARENT
        );
        actionArea.setLayoutParams(actionParams);

        TextView checkIcon = new TextView(this);

        LinearLayout.LayoutParams iconParams = new LinearLayout.LayoutParams(dp(56), dp(56));
        checkIcon.setLayoutParams(iconParams);

        checkIcon.setGravity(Gravity.CENTER);
        checkIcon.setIncludeFontPadding(false);
        checkIcon.setText(term.isRegistered() ? "✓" : "+");
        checkIcon.setTextSize(term.isRegistered() ? 28 : 30);
        checkIcon.setTypeface(null, Typeface.BOLD);
        checkIcon.setTextColor(getResources().getColor(term.isRegistered() ? R.color.primary_blue : R.color.warning_icon));
        checkIcon.setBackgroundResource(term.isRegistered() ? R.drawable.bg_exam_registered_circle : R.drawable.bg_exam_not_registered_circle);

        TextView status = new TextView(this);
        status.setText(term.isRegistered() ? "Prijavljen ispit" : "Prijavi ispit");
        status.setTextColor(getResources().getColor(R.color.text_dark));
        status.setTextSize(13);
        status.setGravity(Gravity.CENTER);
        status.setPadding(0, dp(8), 0, 0);

        actionArea.addView(checkIcon);
        actionArea.addView(status);

        card.addView(content);
        card.addView(actionArea);

        card.setOnClickListener(v -> {
            examTermViewModel.toggleRegistration(term);
            Toast.makeText(this, term.isRegistered() ? "Prijava ispita je poništena" : "Ispit je prijavljen", Toast.LENGTH_SHORT).show();
        });

        return card;
    }

    private void setupNavigation() {
        bottomNews.setOnClickListener(v -> {
            startActivity(new Intent(TermsActivity.this, NewsActivity.class));
            finish();
        });

        bottomTerms.setOnClickListener(v -> Toast.makeText(this, "Već ste na ekranu Termini", Toast.LENGTH_SHORT).show());

        bottomHome.setOnClickListener(v -> {
            startActivity(new Intent(TermsActivity.this, HomeActivity.class));
            finish();
        });

        bottomTasks.setOnClickListener(v -> Toast.makeText(this, "Zadaci će biti dodani u narednom koraku", Toast.LENGTH_SHORT).show());
        bottomProfile.setOnClickListener(v -> Toast.makeText(this, "Profil će biti dodan u narednom koraku", Toast.LENGTH_SHORT).show());
    }

    private int dp(int value) {
        return (int) (value * getResources().getDisplayMetrics().density);
    }
}