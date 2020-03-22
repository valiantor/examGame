package com.valiantor.service;

import com.valiantor.dao.QuestionDao;
import com.valiantor.entity.Question;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuestionService {
    @Autowired
    QuestionDao questionDao;
    public List<Question> findAllQuestionBylNo(int lNo){

        if(questionDao.findAllQuestionBylNo(lNo)==null){
            return null;
        }
    return questionDao.findAllQuestionBylNo(lNo);
    }

    public List<Question> findQuestionPageBylNo(int lNo,int currentPage,int numPerPage){
       int offset =(currentPage-1)*numPerPage;
       return questionDao.findQuestionPageBylNo(lNo,offset,numPerPage);
    }



}
