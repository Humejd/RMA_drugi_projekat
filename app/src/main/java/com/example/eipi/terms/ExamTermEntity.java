package com.example.eipi.terms;

import androidx.annotation.NonNull;
import androidx.room.Entity;

@Entity(
        tableName = "exam_terms",
        primaryKeys = {"id", "userId"}
)
public class ExamTermEntity {

    private int id;

    @NonNull
    private String userId;

    private String subjectName;
    private String dateTime;
    private boolean registered;

    public ExamTermEntity(int id, @NonNull String userId, String subjectName, String dateTime, boolean registered) {
        this.id = id;
        this.userId = userId;
        this.subjectName = subjectName;
        this.dateTime = dateTime;
        this.registered = registered;
    }

    public int getId() {
        return id;
    }

    @NonNull
    public String getUserId() {
        return userId;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public String getDateTime() {
        return dateTime;
    }

    public boolean isRegistered() {
        return registered;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setUserId(@NonNull String userId) {
        this.userId = userId;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public void setRegistered(boolean registered) {
        this.registered = registered;
    }
}