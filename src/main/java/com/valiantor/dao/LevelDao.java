package com.valiantor.dao;

import com.valiantor.entity.Level;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LevelDao {

    public List<Level> findAllLevel();
    public int addLevel(@Param("level") Level level);
}
