package com.valiantor.entity.extro;

import com.valiantor.entity.Level;
import com.valiantor.entity.Question;
import com.valiantor.entity.User;
import com.valiantor.entity.UserQuestion;

public class UserQuestionInfo {
    private User user;
    private Question question;
    private Level level;
    private UserQuestion userQuestion;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }

    public Level getLevel() {
        return level;
    }

    public void setLevel(Level level) {
        this.level = level;
    }

    public UserQuestion getUserQuestion() {
        return userQuestion;
    }

    public void setUserQuestion(UserQuestion userQuestion) {
        this.userQuestion = userQuestion;
    }

    @Override
    public String toString() {
        return "UserQuestionInfo{" +
                "user=" + user +
                ", question=" + question +
                ", level=" + level +
                ", userQuestion=" + userQuestion +
                '}';
    }
}
