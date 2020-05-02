package com.valiantor.service;

import com.valiantor.dao.LevelDao;
import com.valiantor.dao.QuestionDao;
import com.valiantor.dao.UserDao;
import com.valiantor.dao.UserQuestionDao;
import com.valiantor.entity.Level;
import com.valiantor.entity.Question;
import com.valiantor.entity.User;
import com.valiantor.entity.UserQuestion;
import com.valiantor.entity.extro.UserQuestionInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserQuestionService {

    @Autowired
    private UserQuestionDao userQuestionDao;

    @Autowired
    private QuestionDao questionDao;

    @Autowired
    private UserDao userDao;

    @Autowired
    private LevelDao levelDao;


    public List<UserQuestionInfo> getUserQuestionInfoByPage(int currentPage, int numPerPage) {
        int offset =(currentPage-1)*numPerPage;

        List<UserQuestion> userQuestionList = userQuestionDao.getUserQuestionInfoByPage(offset, numPerPage);

        List<UserQuestionInfo> userQuestionInfoList = new ArrayList();
        for(UserQuestion userQuestion: userQuestionList){

            UserQuestionInfo userQuestionInfo = new UserQuestionInfo();
            userQuestionInfo.setUserQuestion(userQuestion);

            Question question = questionDao.findQuestionByQNo(userQuestion.getqNo());
            Level level = levelDao.findLevelByLNo(question.getlNo());

            User user = userDao.findUserByUId(userQuestion.getuId());

            userQuestionInfo.setUser(user);
            userQuestionInfo.setQuestion(question);
            userQuestionInfo.setLevel(level);

            userQuestionInfoList.add(userQuestionInfo);

        }

        return userQuestionInfoList;
    }
}
