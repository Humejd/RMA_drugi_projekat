package com.example.eipi.news;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.eipi.R;
import com.example.eipi.home.HomeActivity;
import com.example.eipi.profile.ProfileActivity;
import com.example.eipi.terms.TermsActivity;
import com.example.eipi.tasks.TasksActivity;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class NewsActivity extends AppCompatActivity {

    private EditText etSearchNews;
    private LinearLayout newsContainer;
    private LinearLayout bottomNews;
    private LinearLayout bottomTerms;
    private LinearLayout bottomHome;
    private LinearLayout bottomTasks;
    private LinearLayout bottomProfile;
    private final List<NewsItem> allNews = new ArrayList<>();

    private static class NewsItem {
        String type;
        String title;
        String description;
        String author;
        String date;
        boolean warning;

        NewsItem(String type, String title, String description, String author, String date, boolean warning) {
            this.type = type;
            this.title = title;
            this.description = description;
            this.author = author;
            this.date = date;
            this.warning = warning;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);

        etSearchNews = findViewById(R.id.etSearchNews);
        newsContainer = findViewById(R.id.newsContainer);
        bottomNews = findViewById(R.id.bottomNews);
        bottomTerms = findViewById(R.id.bottomTerms);
        bottomHome = findViewById(R.id.bottomHome);
        bottomTasks = findViewById(R.id.bottomTasks);
        bottomProfile = findViewById(R.id.bottomProfile);

        prepareNews();
        sortNewsByDate();
        showNews(allNews);
        setupSearch();
        setupNavigation();

    }
    private void prepareNews() {
        allNews.add(new NewsItem("Obavijest", "Junsko-julski ispitni rokovi", "Poštovani studenti, obavještavamo vas da su objavljeni termini za junsko-julski ispitni rok.", "Studentska služba", "18.05.2026 13:57", false));

        allNews.add(new NewsItem("Obavijest", "Obavještenje povodom anketiranja studenata", "Obavještenje povodom predstojećeg anketiranja studenata o kvalitetu nastavnog procesa.", "IPI Akademija", "06.05.2026 14:10", false));

        allNews.add(new NewsItem("Obavijest", "Aprilski ispitni rok", "Obavještavamo vas da su aktivni ispitni termini za aprilski ispitni rok.", "Studentska služba", "27.03.2026 15:38", false));

        allNews.add(new NewsItem("Obavijest", "Raspored nastave za ljetni semestar", "Obavještavaju se studenti da je objavljen raspored nastave za ljetni semestar. Studenti mogu pregledati termine predavanja i vježbi u sekciji Nastava na početnoj stranici aplikacije.", "IPI Akademija", "10.02.2026 09:00", false));

        allNews.add(new NewsItem("Obavijest", "Januarsko-februarski popravni ispitni rok", "Ispitni termini za popravni januarsko-februarski rok dostupni su studentima kroz aplikaciju.", "Studentska služba", "15.01.2026 11:30", false));
    }

    private void sortNewsByDate() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm", Locale.getDefault());

        Collections.sort(allNews, (first, second) -> {
            try {
                Date firstDate = dateFormat.parse(first.date);
                Date secondDate = dateFormat.parse(second.date);

                if (firstDate == null || secondDate == null) {
                    return 0;
                }

                return secondDate.compareTo(firstDate);
            } catch (ParseException e) {
                return 0;
            }
        });
    }

    private void setupSearch() {
        etSearchNews.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                filterNews(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }

    private void filterNews(String query) {
        List<NewsItem> filteredNews = new ArrayList<>();
        String lowerQuery = query.toLowerCase().trim();

        for (NewsItem item : allNews) {
            if (item.title.toLowerCase().contains(lowerQuery)
                    || item.description.toLowerCase().contains(lowerQuery)
                    || item.author.toLowerCase().contains(lowerQuery)
                    || item.type.toLowerCase().contains(lowerQuery)) {
                filteredNews.add(item);
            }
        }

        showNews(filteredNews);
    }

    private void showNews(List<NewsItem> newsList) {
        newsContainer.removeAllViews();

        if (newsList.isEmpty()) {
            TextView emptyText = new TextView(this);
            emptyText.setText("Nema rezultata");
            emptyText.setTextColor(getResources().getColor(R.color.text_dark));
            emptyText.setTextSize(22);
            emptyText.setGravity(Gravity.CENTER);
            emptyText.setTypeface(null, android.graphics.Typeface.BOLD);

            LinearLayout.LayoutParams emptyParams = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    300
            );
            emptyText.setLayoutParams(emptyParams);
            newsContainer.addView(emptyText);
            return;
        }

        for (NewsItem item : newsList) {
            newsContainer.addView(createNewsCard(item));
        }
    }

    private LinearLayout createNewsCard(NewsItem item) {
        LinearLayout card = new LinearLayout(this);
        card.setOrientation(LinearLayout.HORIZONTAL);
        card.setBackgroundResource(R.drawable.bg_white_card);
        card.setElevation(5f);

        LinearLayout.LayoutParams cardParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                dp(128)
        );
        cardParams.setMargins(dp(24), dp(8), dp(24), dp(14));
        card.setLayoutParams(cardParams);

        LinearLayout leftBox = new LinearLayout(this);
        leftBox.setOrientation(LinearLayout.VERTICAL);
        leftBox.setGravity(Gravity.CENTER);
        leftBox.setBackgroundResource(item.warning ? R.drawable.bg_news_warning_left : R.drawable.bg_news_info_left);

        LinearLayout.LayoutParams leftParams = new LinearLayout.LayoutParams(dp(78), LinearLayout.LayoutParams.MATCH_PARENT);
        leftBox.setLayoutParams(leftParams);

        TextView icon = new TextView(this);
        icon.setText(item.warning ? "⚠" : "▤");
        icon.setTextColor(getResources().getColor(item.warning ? R.color.warning_icon : R.color.info_icon));
        icon.setTextSize(32);
        icon.setGravity(Gravity.CENTER);

        TextView type = new TextView(this);
        type.setText(item.type);
        type.setTextColor(getResources().getColor(item.warning ? R.color.warning_icon : R.color.info_icon));
        type.setTextSize(12);
        type.setGravity(Gravity.CENTER);

        leftBox.addView(icon);
        leftBox.addView(type);

        LinearLayout content = new LinearLayout(this);
        content.setOrientation(LinearLayout.VERTICAL);
        content.setPadding(dp(18), dp(16), dp(14), dp(12));

        LinearLayout.LayoutParams contentParams = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, 1);
        content.setLayoutParams(contentParams);

        TextView title = new TextView(this);
        title.setText(item.title);
        title.setTextColor(getResources().getColor(R.color.text_dark));
        title.setTextSize(18);
        title.setSingleLine(true);
        title.setTypeface(null, android.graphics.Typeface.BOLD);

        TextView description = new TextView(this);
        description.setText(item.description);
        description.setTextColor(getResources().getColor(R.color.text_dark));
        description.setTextSize(15);
        description.setSingleLine(true);
        description.setPadding(0, dp(7), 0, 0);

        LinearLayout bottom = new LinearLayout(this);
        bottom.setOrientation(LinearLayout.HORIZONTAL);
        bottom.setGravity(Gravity.BOTTOM);

        LinearLayout.LayoutParams bottomParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                0,
                1
        );
        bottom.setLayoutParams(bottomParams);

        TextView author = new TextView(this);
        author.setText(item.author);
        author.setTextColor(getResources().getColor(R.color.text_gray));
        author.setTextSize(13);

        LinearLayout.LayoutParams authorParams = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1);
        author.setLayoutParams(authorParams);

        TextView date = new TextView(this);
        date.setText(item.date);
        date.setTextColor(getResources().getColor(R.color.text_gray));
        date.setTextSize(13);

        bottom.addView(author);
        bottom.addView(date);

        content.addView(title);
        content.addView(description);
        content.addView(bottom);

        card.addView(leftBox);
        card.addView(content);

        card.setOnClickListener(v -> {
            Intent intent = new Intent(NewsActivity.this, NewsDetailActivity.class);
            intent.putExtra("type", item.type);
            intent.putExtra("title", item.title);
            intent.putExtra("description", item.description);
            intent.putExtra("author", item.author);
            intent.putExtra("date", item.date);
            startActivity(intent);
        });

        return card;
    }

    private void setupNavigation() {
        bottomNews.setOnClickListener(v -> Toast.makeText(this, "Već ste na ekranu Vijesti", Toast.LENGTH_SHORT).show());

        bottomTerms.setOnClickListener(v -> {
            startActivity(new Intent(NewsActivity.this, TermsActivity.class));
            finish();
        });

        bottomHome.setOnClickListener(v -> {
            startActivity(new Intent(NewsActivity.this, HomeActivity.class));
            finish();
        });

        bottomTasks.setOnClickListener(v -> startActivity(new Intent(NewsActivity.this, TasksActivity.class)));
        bottomProfile.setOnClickListener(v -> startActivity(new Intent(NewsActivity.this, ProfileActivity.class)));    }

    private int dp(int value) {
        return (int) (value * getResources().getDisplayMetrics().density);
    }
}