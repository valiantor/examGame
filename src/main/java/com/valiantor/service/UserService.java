package com.valiantor.service;

import com.valiantor.dao.LevelDao;
import com.valiantor.dao.UserDao;
import com.valiantor.entity.Level;
import com.valiantor.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    UserDao userDao;

    @Autowired
    LevelDao levelDao;

    public Boolean login(String uId,String password){
        User user= userDao.findUserByUIdAndPassword(uId,password);
        return user!=null?true:false;
    }
    public User findUserByUId(String uId){
        return userDao.findUserByUId(uId);
    }

    public boolean addUser(User user){
        int raw=userDao.addUser(user);
        return raw>0;
    }

    public boolean updateUserExperience(int acquireExperience,String uId) {
        User user = userDao.findUserByUId(uId);
        Level level = levelDao.findLevelByLNo(user.getCurrentLevelNo());
        if(level != null){
            Level nextLevel = levelDao.findLevelByGrade(level.getGrade() + 1);
            if(nextLevel != null){
                user.setCurrentLevelNo(nextLevel.getlNo());
            }
        }

        user.setExperience(user.getExperience()+acquireExperience);

       return userDao.updateUser(user) > 0;

    }
}
