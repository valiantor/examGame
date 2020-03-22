package com.valiantor.service;

import com.valiantor.dao.UserQuestionDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

@Service
public class UserQuestionService {

    @Autowired
    private UserQuestionDao userQuestionDao;



}
