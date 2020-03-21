package com.valiantor.entity;

public class Admin {

    private String aId;
    private String password;

    public String getaId() {
        return aId;
    }

    public void setaId(String aId) {
        this.aId = aId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "Admin{" +
                "aId='" + aId + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
