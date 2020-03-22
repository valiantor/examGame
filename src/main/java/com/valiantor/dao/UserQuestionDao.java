package com.valiantor.dao;

import com.valiantor.entity.UserQuestion;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserQuestionDao {

    List<UserQuestion> findUserQuestionByUId(String uId);

    int addUserQuestionList(@Param("userQuestionList") List<UserQuestion> userQuestionList);
}
