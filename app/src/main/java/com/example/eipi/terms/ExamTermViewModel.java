package com.example.eipi.terms;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class ExamTermViewModel extends AndroidViewModel {

    private final ExamTermRepository repository;
    private final LiveData<List<ExamTermEntity>> allExamTerms;

    public ExamTermViewModel(@NonNull Application application) {
        super(application);
        repository = new ExamTermRepository(application);
        allExamTerms = repository.getAllExamTerms();
    }

    public LiveData<List<ExamTermEntity>> getAllExamTerms() {
        return allExamTerms;
    }

    public void toggleRegistration(ExamTermEntity examTerm) {
        repository.updateRegistrationStatus(examTerm.getId(), !examTerm.isRegistered());
    }
}