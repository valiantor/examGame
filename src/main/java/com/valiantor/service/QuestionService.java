package com.valiantor.service;

import com.valiantor.dao.LevelDao;
import com.valiantor.dao.QuestionDao;
import com.valiantor.dao.UserDao;
import com.valiantor.dao.UserQuestionDao;
import com.valiantor.entity.Level;
import com.valiantor.entity.Question;
import com.valiantor.entity.User;
import com.valiantor.entity.UserQuestion;
import com.valiantor.entity.extro.AnswerQuestion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class QuestionService {
    @Autowired
    QuestionDao questionDao;

    @Autowired
    private LevelDao levelDao;

    @Autowired
    UserQuestionDao userQuestionDao;

    @Autowired
    UserDao userDao;

    public List<Question> findAllQuestionBylNo(int lNo){
    return questionDao.findAllQuestionBylNo(lNo);
    }

    public List<Question> findQuestionPageBylNo(int lNo,int currentPage,int numPerPage){
       int offset =(currentPage-1)*numPerPage;
       return questionDao.findQuestionPageBylNo(lNo,offset,numPerPage);
    }

    public Boolean deleteQuestionFromLevel(int qNo){
        int row = questionDao.deleteQuestionFromLevel(qNo);
        return row>0;
    }


    public List<Question> findRandomQuestionByLNoAndNum(int lNo, int num,String uId) {
        List<Question> allQuestionList = questionDao.findAllQuestionBylNo(lNo);
        List<UserQuestion> userQuestionList = userQuestionDao.findUserQuestionByUId(uId);
        Set<Integer> qNoSet = new HashSet<>();//该用户做过的题编号
        for(UserQuestion uq: userQuestionList){
            qNoSet.add(uq.getqNo());
        }
        Iterator<Question> iterator = allQuestionList.iterator();
        while(iterator.hasNext()){
            Question next = iterator.next();
            if(qNoSet.contains(next.getqNo())){
                iterator.remove();
            }
        }
        List<Question> questionList = new ArrayList<>();

        //从allQuestionList中随机抽取num个问题
        if(num>=allQuestionList.size()){
            questionList.addAll(allQuestionList);
            //然后从
            List<Integer> qNoList = new ArrayList<>();
            for(int i = 0;(i<num-allQuestionList.size() && i< userQuestionList.size());i++){
                qNoList.add(userQuestionList.get(i).getqNo());
            }
            if(!CollectionUtils.isEmpty(qNoList)){
                questionList.addAll(questionDao.findQuestionByQNoList(qNoList));
            }
        }else{
            //否则就从中随机抽取num个
            int n = num;
            while(n>0){
                int index = (int)(Math.random()*allQuestionList.size());
                questionList.add(allQuestionList.get(index));
                allQuestionList.remove(index);
                n --;
            }
        }
        return questionList;
    }

    public boolean answerQuestion(List<AnswerQuestion> answerQuestionList) {
        List<Integer> qNoList = new ArrayList<>();
        Map<Integer,AnswerQuestion> answerQuestionMap = new HashMap<>();
        String uId = answerQuestionList.get(0).getuId();
        for(AnswerQuestion aq: answerQuestionList){
            qNoList.add(aq.getqNo());
            answerQuestionMap.put(aq.getqNo(),aq);

        }
        List<Question> questionList = questionDao.findQuestionByQNoList(qNoList);
        if(CollectionUtils.isEmpty(questionList)) return false;

        int lNo = questionList.get(0).getlNo();

        Level level = levelDao.findLevelByLNo(lNo);


        //记录UserQuestion


        List<UserQuestion> userQuestionList = new ArrayList<>();

        int rate = level.getRate();
        int limitNum = (int)(((float)rate)/100  * answerQuestionList.size());

        int experience = 0;
        for(Question q: questionList){
            AnswerQuestion aq = answerQuestionMap.get(q.getqNo());
            UserQuestion userQuestion = new UserQuestion();
            userQuestion.setqNo(q.getqNo());
            userQuestion.setuId(aq.getuId());
            userQuestion.setCorrectness("N");
            userQuestion.setDate(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));

            if(q.getCorrectOption().equals(aq.getChoice())){
                experience += q.getExperienceValue();
                userQuestion.setCorrectness("Y");
                limitNum -- ;
            }
            userQuestionList.add(userQuestion);
        }

        userQuestionDao.addUserQuestionList(userQuestionList);

        User user = userDao.findUserByUId(uId);
        user.setExperience(user.getExperience()+experience);

        Level nextLevel = levelDao.findLevelByGrade(level.getGrade()+1);
        if(nextLevel != null && user.getExperience()>=nextLevel.getExperienceNeed()){
            user.setCurrentLevelNo(nextLevel.getlNo());
        }

        if(userDao.updateUser(user)<=0) return false;

        return limitNum<=0;
    }

    public boolean addQuestion(Question question) {
       return questionDao.addQuestion(question) > 0;
    }
}
