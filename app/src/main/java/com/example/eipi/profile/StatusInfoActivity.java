package com.example.eipi.profile;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.eipi.R;
import com.example.eipi.model.UserProfile;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class StatusInfoActivity extends AppCompatActivity {

    private TextView tvBackStatus;
    private TextView tvStatusFullName;
    private TextView tvStatusIndex;
    private TextView tvStatusEmail;
    private TextView tvStatusProgram;
    private TextView tvStatusYear;

    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase firebaseDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_status_info);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance("https://eipi---test-default-rtdb.europe-west1.firebasedatabase.app");

        tvBackStatus = findViewById(R.id.tvBackStatus);
        tvStatusFullName = findViewById(R.id.tvStatusFullName);
        tvStatusIndex = findViewById(R.id.tvStatusIndex);
        tvStatusEmail = findViewById(R.id.tvStatusEmail);
        tvStatusProgram = findViewById(R.id.tvStatusProgram);
        tvStatusYear = findViewById(R.id.tvStatusYear);

        tvBackStatus.setOnClickListener(v -> finish());

        loadUserData();
    }

    private void loadUserData() {
        if (firebaseAuth.getCurrentUser() == null) {
            finish();
            return;
        }

        String uid = firebaseAuth.getCurrentUser().getUid();

        firebaseDatabase.getReference("users")
                .child(uid)
                .get()
                .addOnSuccessListener(snapshot -> {
                    UserProfile userProfile = snapshot.getValue(UserProfile.class);

                    if (userProfile != null) {
                        tvStatusFullName.setText("Ime i prezime: " + formatFullName(userProfile.getFullName()));
                        tvStatusIndex.setText("Broj indeksa: " + safeText(userProfile.getIndexNumber()));
                        tvStatusEmail.setText("Email: " + safeText(userProfile.getEmail()));
                        tvStatusProgram.setText("Smjer: " + safeText(userProfile.getStudyProgram()));
                        tvStatusYear.setText("Godina studija: " + safeText(userProfile.getStudyYear()));
                    } else {
                        showDefaultData();
                    }
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(this, "Podaci nisu učitani", Toast.LENGTH_SHORT).show();
                    showDefaultData();
                });
    }

    private void showDefaultData() {
        String email = firebaseAuth.getCurrentUser() != null ? firebaseAuth.getCurrentUser().getEmail() : "-";

        tvStatusFullName.setText("Ime i prezime: Student");
        tvStatusIndex.setText("Broj indeksa: -");
        tvStatusEmail.setText("Email: " + safeText(email));
        tvStatusProgram.setText("Smjer: -");
        tvStatusYear.setText("Godina studija: -");
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

    private String safeText(String value) {
        if (value == null || value.trim().isEmpty()) {
            return "-";
        }

        return value;
    }
}