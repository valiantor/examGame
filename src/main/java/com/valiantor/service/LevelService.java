package com.valiantor.service;

import com.valiantor.dao.LevelDao;
import com.valiantor.dao.QuestionDao;
import com.valiantor.entity.Level;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
public class LevelService {

    @Autowired
    private LevelDao levelDao;

    @Autowired
    private QuestionDao questionDao;

    public List<Level> findAllLevel(){
        return levelDao.findAllLevel();
    }

    public boolean addLevel(Level level){
        int row = levelDao.addLevel(level);
        return row > 0;
    }

    public boolean updateLevel(Level level){
        Level  oldLevel = levelDao.findLevelByLNo(level.getlNo());


        if(!StringUtils.isEmpty(level.getDescription())){
            oldLevel.setDescription(level.getDescription());
        }


        if(level.getRate()>0 && level.getRate()<=100){
            oldLevel.setRate(level.getRate());
        }

        int row = levelDao.updateLevel(oldLevel);
        return row > 0;
    }


    public Level findLevelByGrade(int grade) {
        return levelDao.findLevelByGrade(grade);
    }

    public Level findLevelByLNo(int lNo) {
        return levelDao.findLevelByLNo(lNo);

    }

    public boolean deleteLevel(int lNo) {
       questionDao.updateQuestionLNoByLNo(lNo);
       int n2 = levelDao.deleteLevel(lNo);

       if (n2 > 0) return true;

       return false;
    }
}
