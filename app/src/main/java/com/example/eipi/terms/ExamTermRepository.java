package com.example.eipi.terms;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.eipi.database.AppDatabase;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ExamTermRepository {

    private final ExamTermDao examTermDao;
    private final LiveData<List<ExamTermEntity>> allExamTerms;
    private final ExecutorService executorService;

    public ExamTermRepository(Application application) {
        AppDatabase database = AppDatabase.getInstance(application);
        examTermDao = database.examTermDao();
        allExamTerms = examTermDao.getAllExamTerms();
        executorService = Executors.newSingleThreadExecutor();
        seedExamTerms();
    }

    public LiveData<List<ExamTermEntity>> getAllExamTerms() {
        return allExamTerms;
    }

    public void updateRegistrationStatus(int id, boolean registered) {
        executorService.execute(() -> examTermDao.updateRegistrationStatus(id, registered));
    }

    private void seedExamTerms() {
        executorService.execute(() -> {
            if (examTermDao.getExamTermsCount() == 0) {
                List<ExamTermEntity> terms = new ArrayList<>();

                terms.add(new ExamTermEntity(1, "Razvoj mobilnih aplikacija", "15.06.2026 10:30", false));
                terms.add(new ExamTermEntity(2, "E-usluge", "17.06.2026 10:30", false));
                terms.add(new ExamTermEntity(3, "Sigurnost elektronskog poslovanja", "19.06.2026 10:30", false));
                terms.add(new ExamTermEntity(4, "Menadžment informatičkih projekata", "23.06.2026 10:30", false));
                terms.add(new ExamTermEntity(5, "Poduzetništvo", "25.06.2026 10:30", false));

                examTermDao.insertAll(terms);
            }
        });
    }
}