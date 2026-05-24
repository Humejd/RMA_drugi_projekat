package com.example.eipi.terms;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "exam_terms")
public class ExamTermEntity {

    @PrimaryKey
    private int id;
    private String subjectName;
    private String dateTime;
    private boolean registered;

    public ExamTermEntity(int id, String subjectName, String dateTime, boolean registered) {
        this.id = id;
        this.subjectName = subjectName;
        this.dateTime = dateTime;
        this.registered = registered;
    }

    public int getId() {
        return id;
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