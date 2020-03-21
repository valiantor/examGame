package com.valiantor.entity;

public class UserQuestion {
    private int uQNo;

    private String correctness;
    private String date;
    private String uId;
    private int lNo;

    public int getuQNo() {
        return uQNo;
    }

    public void setuQNo(int uQNo) {
        this.uQNo = uQNo;
    }

    public String getCorrectness() {
        return correctness;
    }

    public void setCorrectness(String correctness) {
        this.correctness = correctness;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getuId() {
        return uId;
    }

    public void setuId(String uId) {
        this.uId = uId;
    }

    public int getlNo() {
        return lNo;
    }

    public void setlNo(int lNo) {
        this.lNo = lNo;
    }

    @Override
    public String toString() {
        return "UserQuestion{" +
                "uQNo=" + uQNo +
                ", correctness='" + correctness + '\'' +
                ", date='" + date + '\'' +
                ", uId='" + uId + '\'' +
                ", lNo=" + lNo +
                '}';
    }
}
