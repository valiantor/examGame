package com.valiantor.dao;

import com.valiantor.entity.Question;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface QuestionDao {
    public List<Question> findAllQuestionBylNo(int lNo);
    public List<Question> findQuestionPageBylNo(@Param("lNo") int lNo, @Param("offset") int offset, @Param("numPerPage") int numPerPage);

}
