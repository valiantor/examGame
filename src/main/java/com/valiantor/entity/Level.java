package com.valiantor.entity;

public class Level {
    private int lNo;
    private int grade;
    private String description;
    private int rate;

    public int getlNo() {
        return lNo;
    }

    public void setlNo(int lNo) {
        this.lNo = lNo;
    }

    public int getGrade() {
        return grade;
    }

    public void setGrade(int grade) {
        this.grade = grade;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getRate() {
        return rate;
    }

    public void setRate(int rate) {
        this.rate = rate;
    }

    @Override
    public String toString() {
        return "Level{" +
                "lNo='" + lNo + '\'' +
                ", grade=" + grade +
                ", description='" + description + '\'' +
                ", rate=" + rate +
                '}';
    }
}
