package com.valiantor.dao;

import com.valiantor.entity.UserQuestion;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserQuestionDao {

    List<UserQuestion> findUserQuestionByUId(String uId);
    List<UserQuestion> findUserQuestionByUIdAndQNo(@Param("uId") String uId,@Param("qNo")int qNo);

        int addUserQuestionList(@Param("userQuestionList") List<UserQuestion> userQuestionList);

    List<UserQuestion> getErrorQuestionList(String uId);

    List<UserQuestion> findUserQuestionByUIdAndLNo(@Param("uId") String uId,@Param("lNo") int lNo);
}
