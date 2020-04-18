package com.valiantor.dao;

import com.valiantor.entity.Question;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface QuestionDao {
    public List<Question> findAllQuestionBylNo(int lNo);
    public List<Question> findQuestionPageBylNo(@Param("lNo") int lNo, @Param("offset") int offset, @Param("numPerPage") int numPerPage);
    public int deleteQuestionFromLevel(@Param("qNo") int qNo);

    public List<Question> findQuestionByQNoList(@Param("qNoList") List<Integer> qNoList);
    public Question findQuestionByQNo(int qNo);

    int addQuestion(@Param("question") Question question);
    int updateQuestion(@Param("question") Question question);

    int updateQuestionList(@Param("questionList") List<Question> questionList);

    List<Question> findQuestionFromDB(@Param("offset")int offset, @Param("numPerPage")  int numPerPage);
}
