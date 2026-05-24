package com.example.eipi.tasks;

public class TaskItem {
    private String subject;
    private String title;
    private String deadline;
    private String status;
    private String description;

    public TaskItem(String subject, String title, String deadline, String status, String description) {
        this.subject = subject;
        this.title = title;
        this.deadline = deadline;
        this.status = status;
        this.description = description;
    }

    public String getSubject() {
        return subject;
    }

    public String getTitle() {
        return title;
    }

    public String getDeadline() {
        return deadline;
    }

    public String getStatus() {
        return status;
    }

    public String getDescription() {
        return description;
    }
}