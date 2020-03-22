package com.valiantor.controller;

import com.google.gson.Gson;
import com.valiantor.entity.Question;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("question")
public class QuestionController {

    
    private Gson gson = new Gson();
    
    /**
     * 根据关卡号查询当前关卡下的所有题
     * @param lNo 关卡号
     * @return 所有问题集合
     */
    @RequestMapping("findAllQuestionByLNo")
    public List<Question> findAllQuestionByLNo(@RequestParam("lNo") int lNo){


        return null;
    }

    /**
     * 查询指定关卡下的指定分页的题
     * @param lNo 关卡号
     * @param currPage 当前页
     * @param numPerPage 每页显示数量
     * @return
     */
    @RequestMapping("findQuestionPageByLNo")
    public List<Question> findQuestionPageByLNo(@RequestParam("lNo") int lNo, int currPage,int numPerPage){


        return null;
    }

    /**
     * 添加一条题
     * @param questionJson 题对象的json字符串
     * @return
     */

    public boolean addQuestion(@RequestParam("question") String questionJson){
        Question question = gson.fromJson(questionJson, Question.class);

        return false;
    }
}
