package com.valiantor.controller;

import com.valiantor.entity.Question;
import com.valiantor.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
@RestController
@RequestMapping("question")
public class QuestionController {
    @Autowired
    QuestionService questionService;

    /**
     * 查询指定关卡下面的所有题
     * @param lNo
     * @return
     */
    @RequestMapping("findQuestionByLNo")
    public List<Question> findQuestionByLNo(@RequestParam("lNo") int lNo){
        if(questionService.findAllQuestionBylNo(lNo)==null){
            return null;
        }
        return questionService.findAllQuestionBylNo(lNo);
    }

    /**
     * 查询当前关卡下面指定分页的题
     * @param lNo
     * @param currentPage
     * @param numPerPage
     * @return
     */
    @RequestMapping("findQuestionPageByLNo")
    public List<Question> findQuestionPageByLNo(@RequestParam("lNo") int lNo,@RequestParam("currentPage") int currentPage,@RequestParam("numPerPage") int numPerPage){


        return questionService.findQuestionPageBylNo(lNo,currentPage,numPerPage);
    }


}
