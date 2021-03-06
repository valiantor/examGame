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
import com.valiantor.entity.extro.LevelQuestionInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

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


    public LevelQuestionInfo findRandomQuestionByLNoAndNum(int lNo, int num,String uId) {

        User user = userDao.findUserByUId(uId);

        LevelQuestionInfo levelQuestionInfo = new LevelQuestionInfo();
        List<Question> allQuestionList = questionDao.findAllQuestionBylNo(lNo);
        List<Question> unDoQuestionList = new ArrayList<>();

        List<UserQuestion> userQuestionList = userQuestionDao.findUserQuestionByUId(uId);
        if(user.getCurrentLevelNo() <= lNo){//如果是在当前关卡答题，则进行筛选，否则不筛选

            Set<Integer> qNoSet = new HashSet<>();//该用户做过的题编号
            for(UserQuestion uq: userQuestionList){
                qNoSet.add(uq.getqNo());
            }
            Iterator<Question> iterator = allQuestionList.iterator();
            while(iterator.hasNext()){
                Question next = iterator.next();
                if(!qNoSet.contains(next.getqNo())){
                    unDoQuestionList.add(next);
                }
            }
        }

        //更新当前关卡题的数量
        levelQuestionInfo.setRemainingQuestions(allQuestionList.size());

        List<Question> questionList = new ArrayList<>();

        //从allQuestionList中随机抽取num个问题
        if(num>=allQuestionList.size()){
            questionList.addAll(allQuestionList);
            //筛选出错题
            Iterator<UserQuestion> userQuestionIterator = userQuestionList.iterator();
            while(userQuestionIterator.hasNext()){
                UserQuestion next = userQuestionIterator.next();
                if("Y".equals(next.getCorrectness())){
                    userQuestionIterator.remove();
                }
            }
            List<Integer> qNoList = new ArrayList<>();

            for(int i = 0;(i<num-allQuestionList.size() && i< userQuestionList.size());i++){
                qNoList.add(userQuestionList.get(i).getqNo());
            }
            if(!CollectionUtils.isEmpty(qNoList)){
                questionList.addAll(questionDao.findQuestionByQNoList(qNoList));
            }

            qNoList = new ArrayList<>();
            if(questionList.size()<num){
                for(int i = 0;(i<num-questionList.size() && i< allQuestionList.size());i++){
                    qNoList.add(allQuestionList.get(i).getqNo());
                }
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

        levelQuestionInfo.setQuestionList(questionList);
        return levelQuestionInfo;
    }

    public boolean answerQuestionList(List<AnswerQuestion> answerQuestionList) {
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

    public boolean updateQuestion(Question question) {

        Question oldQuestion = questionDao.findQuestionByQNo(question.getqNo());
        if(!StringUtils.isEmpty(question.getQuestionDescription())){
            oldQuestion.setQuestionDescription(question.getQuestionDescription());
        }
        if(!StringUtils.isEmpty(question.getChoiceA())){
            oldQuestion.setChoiceA(question.getChoiceA());
        }
        if(!StringUtils.isEmpty(question.getChoiceB())){
            oldQuestion.setChoiceB(question.getChoiceB());
        }
        if(!StringUtils.isEmpty(question.getChoiceC())){
            oldQuestion.setChoiceC(question.getChoiceC());
        }
        if(!StringUtils.isEmpty(question.getChoiceD())){
            oldQuestion.setChoiceD(question.getChoiceD());
        }
        if(!StringUtils.isEmpty(question.getChoiceE())){
            oldQuestion.setChoiceE(question.getChoiceE());
        }
        if(!StringUtils.isEmpty(question.getCorrectOption())){
            oldQuestion.setCorrectOption(question.getCorrectOption());
        }
        if(!StringUtils.isEmpty(question.getAnswerAnalysis())){
            oldQuestion.setAnswerAnalysis(question.getAnswerAnalysis());
        }
        if(question.getExperienceValue()>0){
            oldQuestion.setExperienceValue(question.getExperienceValue());
        }
        if(question.getlNo()>0){
            oldQuestion.setlNo(question.getlNo());
        }


        return questionDao.updateQuestion(oldQuestion) > 0;
    }

    public int answerQuestion(AnswerQuestion answerQuestion) {

        Question question = questionDao.findQuestionByQNo(answerQuestion.getqNo());

        if(question == null) return -1;

        UserQuestion userQuestion = new UserQuestion();
        userQuestion.setqNo(question.getqNo());
        userQuestion.setuId(answerQuestion.getuId());
        userQuestion.setCorrectness("N");
        userQuestion.setDate(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        if(question.getCorrectOption().equals(answerQuestion.getChoice())){
            userQuestion.setCorrectness("Y");
        }

        List<UserQuestion> userQuestions = userQuestionDao.findUserQuestionByUIdAndQNo(answerQuestion.getuId(), answerQuestion.getqNo());
        if(!CollectionUtils.isEmpty(userQuestions) && "Y".equals(userQuestions.get(0).getCorrectness())){
            //用户已经做过该题
            if(question.getCorrectOption().equals(answerQuestion.getChoice())) return 0;
            else return -1;
        }else{
            //未做过或做错了
            List<UserQuestion> userQuestionList = new ArrayList<>();
            userQuestionList.add(userQuestion);
            userQuestionDao.addUserQuestionList(userQuestionList);
            if(question.getCorrectOption().equals(answerQuestion.getChoice())) return question.getExperienceValue();
            else return -1;
        }
    }

    public List<Question> findQuestionFromDB(int currentPage, int numPerPage) {
        int offset =(currentPage-1)*numPerPage;

        return questionDao.findQuestionFromDB(offset,numPerPage);
    }

    public boolean addQuestionListFormDb(List<Question> questionList) {
        for(Question question:questionList){
           if(!updateQuestion(question)) return false;
        }

        return true;
    }

    public boolean addQuestionListFromWord(List<Question> questionList){
        int num=questionDao.updateQuestionList(questionList);

        return num>0;
    }

    public List<Question> getErrorQuestionList(String uId) {

        //userQuestionDao.getErrorQuestionList(uId);

        return null;
    }
}
