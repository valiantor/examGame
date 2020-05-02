package com.valiantor.controller;

import com.valiantor.entity.extro.UserQuestionInfo;
import com.valiantor.service.UserQuestionService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("userQuestion")
public class UserQuestionController {

    @Resource
    private UserQuestionService userQuestionService;

    @RequestMapping("getUserQuestionInfoByPage")
    public List<UserQuestionInfo> getUserQuestionInfoByPage(int currentPage, int numPerPage){

        return userQuestionService.getUserQuestionInfoByPage(currentPage,numPerPage);



    }
}
