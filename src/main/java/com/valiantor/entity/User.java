package com.valiantor.entity;

public class User {
    private String uId;
    private String name;
    private String password;
    private int experience;
    private String profile;
    private int currentLevelNo;//外码

    public String getuId() {
        return uId;
    }

    public void setuId(String uId) {
        this.uId = uId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getExperience() {
        return experience;
    }

    public void setExperience(int experience) {
        this.experience = experience;
    }

    public String getProfile() {
        return profile;
    }

    public void setProfile(String profile) {
        this.profile = profile;
    }

    public int getCurrentLevelNo() {
        return currentLevelNo;
    }

    public void setCurrentLevelNo(int currentLevelNo) {
        this.currentLevelNo = currentLevelNo;
    }

    @Override
    public String toString() {
        return "User{" +
                "uId='" + uId + '\'' +
                ", name='" + name + '\'' +
                ", password='" + password + '\'' +
                ", experience=" + experience +
                ", profile='" + profile + '\'' +
                ", currentLevelNo=" + currentLevelNo +
                '}';
    }
}
