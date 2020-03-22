package com.valiantor.service;

import com.valiantor.dao.UserDao;
import com.valiantor.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    UserDao userDao;

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

}
