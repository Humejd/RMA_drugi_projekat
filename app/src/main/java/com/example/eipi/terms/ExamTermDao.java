package com.example.eipi.terms;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface ExamTermDao {

    @Query("SELECT * FROM exam_terms WHERE userId = :userId ORDER BY id ASC")
    LiveData<List<ExamTermEntity>> getAllExamTerms(String userId);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<ExamTermEntity> examTerms);

    @Query("UPDATE exam_terms SET registered = :registered WHERE id = :id AND userId = :userId")
    void updateRegistrationStatus(int id, String userId, boolean registered);

    @Query("SELECT COUNT(*) FROM exam_terms WHERE userId = :userId")
    int getExamTermsCount(String userId);
}