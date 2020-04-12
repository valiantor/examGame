package com.valiantor.controller;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.valiantor.entity.Question;
import com.valiantor.entity.extro.AnswerQuestion;
import com.valiantor.entity.extro.LevelQuestionInfo;
import com.valiantor.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.List;
@RestController
@RequestMapping("question")
public class QuestionController {
    @Autowired
    QuestionService questionService;


    @RequestMapping("addQuestion")
    public boolean addQuestion(@RequestParam("question") String questionJson){
        Question question = new Gson().fromJson(questionJson, Question.class);
        if(question==null) return false;

        return questionService.addQuestion(question);
    }

    @RequestMapping("addQuestionListFormDb")
    public boolean addQuestionListFormDb(@RequestParam("questionList") String questionListJson){
        List<Question> questionList= new Gson().fromJson(questionListJson,
                new TypeToken<List<Question>>() {
        }.getType());
        if(CollectionUtils.isEmpty(questionList)) return false;

        return questionService.addQuestionListFormDb(questionList);
    }


    @RequestMapping("updateQuestion")
    public boolean updateQuestion(@RequestParam("question") String questionJson){
        Question question = new Gson().fromJson(questionJson, Question.class);
        if(question==null) return false;

        return questionService.updateQuestion(question);
    }


    /**
     * 查询指定关卡下面的所有题
     * @param lNo
     * @return
     */
    @RequestMapping("findAllQuestionByLNo")
    public List<Question> findAllQuestionByLNo(@RequestParam("lNo") int lNo){
        return questionService.findAllQuestionBylNo(lNo);
    }

    /**
     * 获取数据库中未在关卡里的题
     * @param currentPage 当前分页
     * @param numPerPage 每页获取的题数目
     * @return
     */

    @RequestMapping("findQuestionFromDB")
    public List<Question> findQuestionFromDB(@RequestParam("currentPage") int currentPage,@RequestParam("numPerPage") int numPerPage){
        return questionService.findQuestionFromDB(currentPage,numPerPage);
    }



    /**
     * 查询当前关卡下面指定分页的题
     * @param lNo
     * @param currentPage
     * @param numPerPage
     * @return
     */
    @RequestMapping(value = "findQuestionPageByLNo")
    public List<Question> finduestionPageByLNo(@RequestParam("lNo") int lNo,@RequestParam("currentPage") int currentPage,@RequestParam("numPerPage") int numPerPage){

        return questionService.findQuestionPageBylNo(lNo,currentPage,numPerPage);
    }
    @RequestMapping("deleteQuestionFromLevel")
    public Boolean deleteQuestionFromLevel(@RequestParam("qNo") int qNo){

        return questionService.deleteQuestionFromLevel(qNo);
    }

    @RequestMapping(value = "findRandomQuestionByLNoAndNum")
    public LevelQuestionInfo findRandomQuestionByLNoAndNum(@RequestParam("lNo") int lNo, @RequestParam("num") int num, HttpSession session){

        Object uIdObj = session.getAttribute("uId");
        String uId = null;
        if(uIdObj != null) {
            uId = String.valueOf(uIdObj);
        }
        return questionService.findRandomQuestionByLNoAndNum(lNo,num,uId);
    }


    @RequestMapping("answerQuestionList")
    public boolean answerQuestionList(@RequestParam("answerQuestionList") String answerQuestion){

        List<AnswerQuestion> answerQuestionList= new Gson().fromJson(answerQuestion, new TypeToken<List<AnswerQuestion>>() {
        }.getType());

        if(CollectionUtils.isEmpty(answerQuestionList)) return false;


        return questionService.answerQuestionList(answerQuestionList);
    }

    @RequestMapping("answerQuestion")
    public boolean answerQuestion(@RequestParam("answerQuestion") String answerQuestionJson){

        AnswerQuestion answerQuestion = new Gson().fromJson(answerQuestionJson, AnswerQuestion.class);

        if(answerQuestion == null) return false;


        return questionService.answerQuestion(answerQuestion);
    }


}
