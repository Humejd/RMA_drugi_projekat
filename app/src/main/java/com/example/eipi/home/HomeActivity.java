package com.example.eipi.home;

import android.content.Intent;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.eipi.R;
import com.example.eipi.auth.LoginActivity;
import com.example.eipi.model.UserProfile;
import com.example.eipi.nastava.NastavaActivity;
import com.example.eipi.news.NewsActivity;
import com.example.eipi.news.NewsDetailActivity;
import com.example.eipi.profile.ProfileActivity;
import com.example.eipi.terms.TermsActivity;
import com.example.eipi.tasks.TasksActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class HomeActivity extends AppCompatActivity {

    private TextView tvHeaderName;
    private TextView tvHeaderSubtitle;
    private TextView tvSeeAllNews;
    private TextView tvSeeAllNastava;

    private LinearLayout cardHomeNewsOne;
    private LinearLayout cardHomeNewsTwo;
    private LinearLayout cardHomeNewsThree;

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
        firebaseDatabase = FirebaseDatabase.getInstance("https://eipi---test-default-rtdb.europe-west1.firebasedatabase.app");

        tvHeaderName = findViewById(R.id.tvHeaderName);
        tvHeaderSubtitle = findViewById(R.id.tvHeaderSubtitle);
        tvSeeAllNews = findViewById(R.id.tvSeeAllNews);
        tvSeeAllNastava = findViewById(R.id.tvSeeAllNastava);

        cardHomeNewsOne = findViewById(R.id.cardHomeNewsOne);
        cardHomeNewsTwo = findViewById(R.id.cardHomeNewsTwo);
        cardHomeNewsThree = findViewById(R.id.cardHomeNewsThree);

        bottomNews = findViewById(R.id.bottomNews);
        bottomTerms = findViewById(R.id.bottomTerms);
        bottomHome = findViewById(R.id.bottomHome);
        bottomTasks = findViewById(R.id.bottomTasks);
        bottomProfile = findViewById(R.id.bottomProfile);

        if (firebaseAuth.getCurrentUser() == null) {
            openLogin();
            return;
        }

        setupNavigation();
        setupNewsClicks();
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (firebaseAuth.getCurrentUser() != null) {
            loadUserData();
        }
    }

    private void loadUserData() {
        String uid = firebaseAuth.getCurrentUser().getUid();

        firebaseDatabase.getReference("users")
                .child(uid)
                .get()
                .addOnSuccessListener(snapshot -> {
                    if (snapshot.exists()) {
                        UserProfile userProfile = snapshot.getValue(UserProfile.class);

                        if (userProfile != null) {
                            String formattedName = formatFullName(userProfile.getFullName());
                            String indexNumber = userProfile.getIndexNumber();

                            if (indexNumber != null && !indexNumber.trim().isEmpty()) {
                                tvHeaderName.setText(formattedName + " • " + indexNumber);
                            } else {
                                tvHeaderName.setText(formattedName);
                            }

                            tvHeaderSubtitle.setText("IPI Akademija");
                        } else {
                            tvHeaderName.setText("Student");
                            tvHeaderSubtitle.setText("IPI Akademija");
                        }
                    } else {
                        tvHeaderName.setText("Student");
                        tvHeaderSubtitle.setText("Profil nije pronađen");
                    }
                })
                .addOnFailureListener(e -> {
                    tvHeaderName.setText("Student");
                    tvHeaderSubtitle.setText("Greška pri učitavanju");
                });
    }

    private void setupNavigation() {
        tvSeeAllNews.setOnClickListener(v -> startActivity(new Intent(HomeActivity.this, NewsActivity.class)));
        tvSeeAllNastava.setOnClickListener(v -> startActivity(new Intent(HomeActivity.this, NastavaActivity.class)));

        bottomNews.setOnClickListener(v -> startActivity(new Intent(HomeActivity.this, NewsActivity.class)));
        bottomTerms.setOnClickListener(v -> startActivity(new Intent(HomeActivity.this, TermsActivity.class)));
        bottomHome.setOnClickListener(v -> showMessage("Već ste na Početnoj"));
        bottomTasks.setOnClickListener(v -> startActivity(new Intent(HomeActivity.this, TasksActivity.class)));
        bottomProfile.setOnClickListener(v -> startActivity(new Intent(HomeActivity.this, ProfileActivity.class)));
    }

    private void setupNewsClicks() {
        cardHomeNewsOne.setOnClickListener(v -> openNewsDetail(
                "Obavijest",
                "Junsko-julski ispitni rokovi",
                "Poštovani studenti, obavještavamo vas da su objavljeni termini za junsko-julski ispitni rok.",
                "Studentska služba",
                "18.05.2026 13:57"
        ));

        cardHomeNewsTwo.setOnClickListener(v -> openNewsDetail(
                "Obavijest",
                "Obavještenje povodom anketiranja studenata",
                "Obavještenje povodom predstojećeg anketiranja studenata o kvalitetu nastavnog procesa.",
                "IPI Akademija",
                "06.05.2026 14:10"
        ));

        cardHomeNewsThree.setOnClickListener(v -> openNewsDetail(
                "Obavijest",
                "Aprilski ispitni rok",
                "Obavještavamo vas da su aktivni ispitni termini za aprilski ispitni rok.",
                "Studentska služba",
                "27.03.2026 15:38"
        ));
    }

    private void openNewsDetail(String type, String title, String description, String author, String date) {
        Intent intent = new Intent(HomeActivity.this, NewsDetailActivity.class);
        intent.putExtra("type", type);
        intent.putExtra("title", title);
        intent.putExtra("description", description);
        intent.putExtra("author", author);
        intent.putExtra("date", date);
        startActivity(intent);
    }

    private void openLogin() {
        Intent intent = new Intent(HomeActivity.this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }

    private String formatFullName(String fullName) {
        if (fullName == null || fullName.trim().isEmpty()) {
            return "Student";
        }

        String[] words = fullName.trim().toLowerCase().split("\\s+");
        StringBuilder formattedName = new StringBuilder();

        for (String word : words) {
            if (!word.isEmpty()) {
                formattedName.append(word.substring(0, 1).toUpperCase())
                        .append(word.substring(1))
                        .append(" ");
            }
        }

        return formattedName.toString().trim();
    }

    private void showMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}