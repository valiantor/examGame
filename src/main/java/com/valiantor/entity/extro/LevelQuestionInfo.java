package com.valiantor.entity.extro;

import com.valiantor.entity.Level;
import com.valiantor.entity.Question;

import java.util.List;

public class LevelQuestionInfo {
    private List<Question> questionList;//指定数量的题目
    private int remainingQuestions;//当前关卡剩余题量
    private Level level;//当前关卡信息

    public List<Question> getQuestionList() {
        return questionList;
    }

    public void setQuestionList(List<Question> questionList) {
        this.questionList = questionList;
    }

    public int getRemainingQuestions() {
        return remainingQuestions;
    }

    public void setRemainingQuestions(int remainingQuestions) {
        this.remainingQuestions = remainingQuestions;
    }

    public Level getLevel() {
        return level;
    }

    public void setLevel(Level level) {
        this.level = level;
    }
}
