package com.example.eipi.news;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.eipi.R;

public class NewsDetailActivity extends AppCompatActivity {

    private TextView tvDetailType;
    private TextView tvDetailTitle;
    private TextView tvDetailDescription;
    private TextView tvDetailAuthor;
    private TextView tvDetailDate;
    private TextView btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_detail);

        tvDetailType = findViewById(R.id.tvDetailType);
        tvDetailTitle = findViewById(R.id.tvDetailTitle);
        tvDetailDescription = findViewById(R.id.tvDetailDescription);
        tvDetailAuthor = findViewById(R.id.tvDetailAuthor);
        tvDetailDate = findViewById(R.id.tvDetailDate);
        btnBack = findViewById(R.id.btnBack);

        String type = getIntent().getStringExtra("type");
        String title = getIntent().getStringExtra("title");
        String description = getIntent().getStringExtra("description");
        String author = getIntent().getStringExtra("author");
        String date = getIntent().getStringExtra("date");

        tvDetailType.setText(type);
        tvDetailTitle.setText(title);
        tvDetailDescription.setText(description);
        tvDetailAuthor.setText(author);
        tvDetailDate.setText(date);

        btnBack.setOnClickListener(v -> finish());
    }
}