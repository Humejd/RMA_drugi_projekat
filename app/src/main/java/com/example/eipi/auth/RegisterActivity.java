package com.example.eipi.auth;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.eipi.R;
import com.example.eipi.home.HomeActivity;
import com.example.eipi.model.UserProfile;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity extends AppCompatActivity {

    private EditText etFullName;
    private EditText etIndexNumber;
    private EditText etEmail;
    private EditText etPassword;
    private EditText etConfirmPassword;
    private Button btnRegister;
    private TextView tvGoToLogin;
    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase firebaseDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance("https://eipi---test-default-rtdb.europe-west1.firebasedatabase.app");

        etFullName = findViewById(R.id.etFullName);
        etIndexNumber = findViewById(R.id.etIndexNumber);
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        etConfirmPassword = findViewById(R.id.etConfirmPassword);
        btnRegister = findViewById(R.id.btnRegister);
        tvGoToLogin = findViewById(R.id.tvGoToLogin);

        btnRegister.setOnClickListener(v -> registerUser());
        tvGoToLogin.setOnClickListener(v -> finish());
    }

    private void registerUser() {
        String fullName = etFullName.getText().toString().trim();
        String indexNumber = etIndexNumber.getText().toString().trim();
        String email = etEmail.getText().toString().trim();
        String password = etPassword.getText().toString().trim();
        String confirmPassword = etConfirmPassword.getText().toString().trim();

        if (TextUtils.isEmpty(fullName)) {
            etFullName.setError("Unesite ime i prezime");
            return;
        }

        if (TextUtils.isEmpty(indexNumber)) {
            etIndexNumber.setError("Unesite broj indeksa");
            return;
        }

        if (TextUtils.isEmpty(email)) {
            etEmail.setError("Unesite e-mail adresu");
            return;
        }

        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            etEmail.setError("Unesite ispravnu e-mail adresu");
            return;
        }

        if (TextUtils.isEmpty(password)) {
            etPassword.setError("Unesite lozinku");
            return;
        }

        if (password.length() < 6) {
            etPassword.setError("Lozinka mora imati najmanje 6 karaktera");
            return;
        }

        if (TextUtils.isEmpty(confirmPassword)) {
            etConfirmPassword.setError("Potvrdite lozinku");
            return;
        }

        if (!password.equals(confirmPassword)) {
            etConfirmPassword.setError("Lozinke se ne poklapaju");
            return;
        }

        btnRegister.setEnabled(false);

        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful() && firebaseAuth.getCurrentUser() != null) {
                        saveUserProfile(fullName, indexNumber, email);
                    } else {
                        btnRegister.setEnabled(true);

                        String message = "Registracija nije uspjela";

                        if (task.getException() != null) {
                            Log.e("REGISTER_ERROR", "Greška pri registraciji", task.getException());

                            if (task.getException().getMessage() != null) {
                                message = task.getException().getClass().getSimpleName() + ": " + task.getException().getMessage();
                            }
                        }

                        Toast.makeText(RegisterActivity.this, message, Toast.LENGTH_LONG).show();
                    }
                });
    }

    private void saveUserProfile(String fullName, String indexNumber, String email) {
        String uid = firebaseAuth.getCurrentUser().getUid();

        UserProfile userProfile = new UserProfile(
                uid,
                fullName,
                email,
                indexNumber,
                "Informatika i računarstvo",
                "III godina"
        );

        firebaseDatabase.getReference("users")
                .child(uid)
                .setValue(userProfile)
                .addOnCompleteListener(saveTask -> {
                    btnRegister.setEnabled(true);

                    if (saveTask.isSuccessful()) {
                        Toast.makeText(RegisterActivity.this, "Registracija uspješna", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(RegisterActivity.this, HomeActivity.class));
                        finish();
                    } else {
                        String message = "Korisnik je kreiran, ali profil nije spremljen";

                        if (saveTask.getException() != null) {
                            Log.e("REGISTER_PROFILE_ERROR", "Greška pri spremanju profila", saveTask.getException());

                            if (saveTask.getException().getMessage() != null) {
                                message = saveTask.getException().getClass().getSimpleName() + ": " + saveTask.getException().getMessage();
                            }
                        }

                        Toast.makeText(RegisterActivity.this, message, Toast.LENGTH_LONG).show();
                    }
                });
    }
}