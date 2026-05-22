package com.example.eipi.model;

public class UserProfile {
    private String uid;
    private String fullName;
    private String email;
    private String indexNumber;
    private String studyProgram;
    private String studyYear;

    public UserProfile() {
    }

    public UserProfile(String uid, String fullName, String email, String indexNumber, String studyProgram, String studyYear) {
        this.uid = uid;
        this.fullName = fullName;
        this.email = email;
        this.indexNumber = indexNumber;
        this.studyProgram = studyProgram;
        this.studyYear = studyYear;
    }

    public String getUid() {
        return uid;
    }

    public String getFullName() {
        return fullName;
    }

    public String getEmail() {
        return email;
    }

    public String getIndexNumber() {
        return indexNumber;
    }

    public String getStudyProgram() {
        return studyProgram;
    }

    public String getStudyYear() {
        return studyYear;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setIndexNumber(String indexNumber) {
        this.indexNumber = indexNumber;
    }

    public void setStudyProgram(String studyProgram) {
        this.studyProgram = studyProgram;
    }

    public void setStudyYear(String studyYear) {
        this.studyYear = studyYear;
    }
}