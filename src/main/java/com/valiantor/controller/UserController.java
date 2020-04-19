package com.valiantor.controller;

import com.google.gson.Gson;
import com.valiantor.entity.User;
import com.valiantor.service.LevelService;
import com.valiantor.service.UserService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("user")
public class UserController {

    @Autowired
    UserService userService;
    @Autowired
    LevelService levelService;

    /**
     * 用户登录
     * @param uId 用户账号
     * @param password 密码
     * @return true或false
     */
    @RequestMapping("login")
    public String login(@RequestParam("uId") String uId,@RequestParam("password") String password,HttpSession session){
        if(userService.login(uId,password)){
            session.setAttribute("uId",uId);
            return "true";
        }

        return "false";
    }

    /**
     * 用户退出登录
     * @param session
     * @return true或false
     */
    @RequestMapping("logout")
    public String logout(HttpSession session){

        session.removeAttribute("uId");
        if(StringUtils.isEmpty(session.getAttribute("uId"))) return "true";

        return "false";
    }


    /**
     * 从session中获取当前已登录用户
     * @return 用户信息
     */
    @RequestMapping("getCurrentUser")
    public User getCurrentUser(HttpSession session){
            Object uId = session.getAttribute("uId");
            User user= userService.findUserByUId(String.valueOf(uId));
        return user;
    }

    /**
     * 用户注册功能实现
     * @param userJson
     * @return
     */
    @RequestMapping("userRegister")
    public String userRegister(@RequestParam("userJson") String userJson){
        User user=new Gson().fromJson(userJson,User.class);
        user.setExperience(0);
        user.setCurrentLevelNo(levelService.findLevelByGrade(1).getlNo());
        if(userService.addUser(user))return "true";

        return "false";
    }

    /**
     * 判断当前userId是否已经存在，若存在返回true，反之，返回false
     * @param uId
     */
    @RequestMapping("checkUserId")
    public boolean checkUserId(String uId){
    User user = userService.findUserByUId(uId);
    return user==null?true:false;
    }


    /**
     * 为当前用户增加经验
     * @param acquireExperience
     * @return
     */
    @RequestMapping("updateUserExperience")
    public boolean updateUserExperience(@RequestParam("acquireExperience") int acquireExperience,@RequestParam("currentLNo") int currentLNo,HttpSession session){

        Object uIdObj = session.getAttribute("uId");
        if(uIdObj==null) return false;

        if(acquireExperience == 0) return false;
        return userService.updateUserExperience(acquireExperience,currentLNo,String.valueOf(uIdObj));
    }

}
