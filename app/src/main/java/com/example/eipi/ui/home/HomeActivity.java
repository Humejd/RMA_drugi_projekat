package com.example.eipi.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import androidx.appcompat.app.AppCompatActivity;

import com.example.eipi.R;
import com.example.eipi.model.UserProfile;
import com.example.eipi.ui.auth.LoginActivity;
import com.example.eipi.ui.news.NewsActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class HomeActivity extends AppCompatActivity {

    private TextView tvHeaderName;
    private TextView tvHeaderSubtitle;
    private TextView tvSeeAllNews;
    private LinearLayout bottomNews;
    private LinearLayout bottomTerms;
    private LinearLayout bottomHome;
    private LinearLayout bottomTasks;
    private LinearLayout bottomProfile;
    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase firebaseDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();

        tvHeaderName = findViewById(R.id.tvHeaderName);
        tvHeaderSubtitle = findViewById(R.id.tvHeaderSubtitle);
        tvSeeAllNews = findViewById(R.id.tvSeeAllNews);
        bottomNews = findViewById(R.id.bottomNews);
        bottomTerms = findViewById(R.id.bottomTerms);
        bottomHome = findViewById(R.id.bottomHome);
        bottomTasks = findViewById(R.id.bottomTasks);
        bottomProfile = findViewById(R.id.bottomProfile);

        if (firebaseAuth.getCurrentUser() == null) {
            startActivity(new Intent(HomeActivity.this, LoginActivity.class));
            finish();
            return;
        }

        loadUserData();
        setupNavigation();
    }

    private void loadUserData() {
        String uid = firebaseAuth.getCurrentUser().getUid();

        firebaseDatabase.getReference("users")
                .child(uid)
                .get()
                .addOnSuccessListener(snapshot -> {
                    UserProfile userProfile = snapshot.getValue(UserProfile.class);

                    if (userProfile != null) {
                        tvHeaderName.setText(userProfile.getFullName().toUpperCase() + " • " + userProfile.getIndexNumber());
                        tvHeaderSubtitle.setText("IPI Akademija");
                    }
                });
    }

    private void setupNavigation() {
        tvSeeAllNews.setOnClickListener(v -> startActivity(new Intent(HomeActivity.this, NewsActivity.class)));

        bottomNews.setOnClickListener(v -> startActivity(new Intent(HomeActivity.this, NewsActivity.class)));
        bottomTerms.setOnClickListener(v -> showMessage("Termini"));
        bottomHome.setOnClickListener(v -> showMessage("Početna"));
        bottomTasks.setOnClickListener(v -> showMessage("Zadaci"));
        bottomProfile.setOnClickListener(v -> showMessage("Profil"));
    }

    private void showMessage(String screenName) {
        Toast.makeText(this, screenName + " će biti dodano u narednom koraku", Toast.LENGTH_SHORT).show();
    }
}