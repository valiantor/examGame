package com.valiantor.controller;

import com.google.gson.Gson;
import com.valiantor.entity.Level;
import com.valiantor.service.LevelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpRequest;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.util.List;

@RestController
@RequestMapping("level")
public class LevelController {

    @Autowired
    LevelService levelService;

    @RequestMapping("findAllLevel")
    public List<Level> findAllLevel(){
        return levelService.findAllLevel();
    }
    @RequestMapping("addLevel")
    public boolean addLevel(@RequestParam("level") String levelStr){

        Level level = new Gson().fromJson(levelStr, Level.class);

        return levelService.addLevel(level);

    }

    @RequestMapping("findLevelByLNo")
    public Level findLevelByLNo(@RequestParam("lNo") int lNo){
        return levelService.findLevelByLNo(lNo);
    }

    @RequestMapping("findLevelByGrade")
    public Level findLevelByGrade(@RequestParam("grade") int grade){
        return levelService.findLevelByGrade(grade);
    }

}
