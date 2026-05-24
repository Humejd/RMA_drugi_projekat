package com.example.eipi.terms;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.eipi.database.AppDatabase;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ExamTermRepository {

    private final ExamTermDao examTermDao;
    private final LiveData<List<ExamTermEntity>> allExamTerms;
    private final ExecutorService executorService;
    private final String userId;

    public ExamTermRepository(Application application) {
        AppDatabase database = AppDatabase.getInstance(application);
        examTermDao = database.examTermDao();
        executorService = Executors.newSingleThreadExecutor();

        if (FirebaseAuth.getInstance().getCurrentUser() != null) {
            userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        } else {
            userId = "guest";
        }

        allExamTerms = examTermDao.getAllExamTerms(userId);
        seedExamTerms();
    }

    public LiveData<List<ExamTermEntity>> getAllExamTerms() {
        return allExamTerms;
    }

    public void updateRegistrationStatus(int id, boolean registered) {
        executorService.execute(() -> examTermDao.updateRegistrationStatus(id, userId, registered));
    }

    private void seedExamTerms() {
        executorService.execute(() -> {
            if (examTermDao.getExamTermsCount(userId) == 0) {
                List<ExamTermEntity> terms = new ArrayList<>();

                terms.add(new ExamTermEntity(1, userId, "Razvoj mobilnih aplikacija", "15.06.2026 10:30", false));
                terms.add(new ExamTermEntity(2, userId, "E-usluge", "17.06.2026 10:30", false));
                terms.add(new ExamTermEntity(3, userId, "Sigurnost elektronskog poslovanja", "19.06.2026 10:30", false));
                terms.add(new ExamTermEntity(4, userId, "Menadžment informatičkih projekata", "23.06.2026 10:30", false));
                terms.add(new ExamTermEntity(5, userId, "Poduzetništvo", "25.06.2026 10:30", false));

                examTermDao.insertAll(terms);
            }
        });
    }
}