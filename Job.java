package com.example.jobsearchapp;

public class Job {
    private String title;
    private String company;

    public Job(String title, String company) {
        this.title = title;
        this.company = company;
    }

    public String getTitle() {
        return title;
    }

    public String getCompany() {
        return company;
    }
}
