package com.example.eipi.profile;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.eipi.R;
import com.example.eipi.auth.LoginActivity;
import com.example.eipi.home.HomeActivity;
import com.example.eipi.model.UserProfile;
import com.example.eipi.news.NewsActivity;
import com.example.eipi.terms.TermsActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class ProfileActivity extends AppCompatActivity {

    private TextView tvProfileName;
    private TextView tvProfileEmail;
    private TextView tvProfileIndex;
    private TextView tvProfileProgram;
    private TextView tvProfileYear;
    private Button btnLogout;
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
        setContentView(R.layout.activity_profile);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance("https://eipi---test-default-rtdb.europe-west1.firebasedatabase.app");

        tvProfileName = findViewById(R.id.tvProfileName);
        tvProfileEmail = findViewById(R.id.tvProfileEmail);
        tvProfileIndex = findViewById(R.id.tvProfileIndex);
        tvProfileProgram = findViewById(R.id.tvProfileProgram);
        tvProfileYear = findViewById(R.id.tvProfileYear);
        btnLogout = findViewById(R.id.btnLogout);

        bottomNews = findViewById(R.id.bottomNews);
        bottomTerms = findViewById(R.id.bottomTerms);
        bottomHome = findViewById(R.id.bottomHome);
        bottomTasks = findViewById(R.id.bottomTasks);
        bottomProfile = findViewById(R.id.bottomProfile);

        if (firebaseAuth.getCurrentUser() == null) {
            openLogin();
            return;
        }

        loadUserProfile();
        setupLogout();
        setupNavigation();
    }

    private void loadUserProfile() {
        String uid = firebaseAuth.getCurrentUser().getUid();

        firebaseDatabase.getReference("users")
                .child(uid)
                .get()
                .addOnSuccessListener(snapshot -> {
                    UserProfile userProfile = snapshot.getValue(UserProfile.class);

                    if (userProfile != null) {
                        tvProfileName.setText(formatFullName(userProfile.getFullName()));
                        tvProfileEmail.setText(userProfile.getEmail());
                        tvProfileIndex.setText("Broj indeksa: " + userProfile.getIndexNumber());
                        tvProfileProgram.setText("Smjer: " + userProfile.getStudyProgram());
                        tvProfileYear.setText("Godina studija: " + userProfile.getStudyYear());
                    } else {
                        tvProfileName.setText("Student");
                        tvProfileEmail.setText(firebaseAuth.getCurrentUser().getEmail());
                    }
                })
                .addOnFailureListener(e -> Toast.makeText(this, "Podaci profila nisu učitani", Toast.LENGTH_SHORT).show());
    }

    private void setupLogout() {
        btnLogout.setOnClickListener(v -> {
            firebaseAuth.signOut();
            openLogin();
        });
    }

    private void openLogin() {
        Intent intent = new Intent(ProfileActivity.this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }

    private void setupNavigation() {
        bottomNews.setOnClickListener(v -> {
            startActivity(new Intent(ProfileActivity.this, NewsActivity.class));
            finish();
        });

        bottomTerms.setOnClickListener(v -> {
            startActivity(new Intent(ProfileActivity.this, TermsActivity.class));
            finish();
        });

        bottomHome.setOnClickListener(v -> {
            startActivity(new Intent(ProfileActivity.this, HomeActivity.class));
            finish();
        });

        bottomTasks.setOnClickListener(v -> Toast.makeText(this, "Zadaci će biti dodani u narednom koraku", Toast.LENGTH_SHORT).show());

        bottomProfile.setOnClickListener(v -> Toast.makeText(this, "Već ste na profilu", Toast.LENGTH_SHORT).show());
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
}